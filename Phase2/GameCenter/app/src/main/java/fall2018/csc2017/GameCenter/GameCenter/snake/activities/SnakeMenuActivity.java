package fall2018.csc2017.GameCenter.GameCenter.snake.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;

/**
 * The menu activity for the Snake game.
 */
public class SnakeMenuActivity extends AppCompatActivity {

    /**
     * The current user account obtained from the game select screen.
     */
    private UserAccount currentUserAccount;
    /**
     * The file containing a temp version of the boardManager.
     */
    public static final String TEMP_SAVE_FILENAME = "snake_save_file_tmp.ser";

    /**
     * The current User snake game saved data.
     */
    private Object[] savedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_menu);
        currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        addLoadButtonListener();
        addSaveButtonListener();
        addLoadAutoSaveButtonListener();
    }

    /**
     * Switch to SnakeStartingActivity to play the game.
     * Passes savedData and difficulty into SnakeStartingActivity.
     */
    private void switchToGame(String difficulty, Object[] savedData) {
        Intent intent = new Intent(this, SnakeStartingActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("savedData", savedData);
        startActivity(intent);
    }

    /**
     * Activate the start button. Once the start button is pressed, a new alert dialog
     * prompts the user to choose between the 2 difficulties: easy, hard.
     *
     * @param view the current view.
     */
    public void newGame(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                SnakeMenuActivity.this);
        builder.setTitle("Choose a Difficulty Level");
        String[] difficulties = {"Easy", "Hard"};
        builder.setItems(difficulties, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        switchToGame("Snake Easy Mode", null);
                        break;
                    case 1:
                        switchToGame("Snake Hard Mode", null);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Activate the save button listener.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveSnake);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserAccounts();
                userAccountsToFile();
                makeToastSavedText();
            }
    });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the load button listener. Users will be given a list of previously saved games to
     * choose from.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadSnake);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        SnakeMenuActivity.this);
                builder.setTitle("Choose a game");
                int checkedItem = 1; //Sets the choice to the first element.
                builder.setSingleChoiceItems(savedGamesList(), checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        Object selectedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        String selectedGame = selectedItem.toString();
                        savedData = currentUserAccount.getSnakeGame(selectedGame);
                        makeToastLoadedText();
                        dialog.dismiss();
                        switchToGame((String)savedData[6], savedData);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * Make a list of games names for displaying in load games.
     */
    private String[] savedGamesList(){
        String[] games = new String[(currentUserAccount.getSnakeGameNames().size())];
        int i = 0;
        for (String s : currentUserAccount.getSnakeGameNames()) {
            games[i++] = s;
        }
        return games;
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }
    /**
     * Activate the Load autoSave button, which loads the latest autoSave of Snake of
     * the currentAccount.
     */
    private void addLoadAutoSaveButtonListener() {
        Button load = findViewById(R.id.AutoSnake);
        load.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<UserAccount> userAccountList;
                    InputStream inputStream = openFileInput(LoginActivity.USER_ACCOUNTS_FILENAME);
                    if (inputStream != null) {
                        ObjectInputStream input = new ObjectInputStream(inputStream);
                        userAccountList = (ArrayList<UserAccount>) input.readObject();
                        inputStream.close();
                        for (UserAccount ua : userAccountList) {
                            if (ua.getUsername().equals(currentUserAccount.getUsername())) {
                                currentUserAccount = ua;
                            }
                        }
                        savedData = currentUserAccount.getSnakeGame("autoSave");
                        switchToGame((String)savedData[6], savedData);
                    } else {
                        makeToastLoadAutoSaveFailText();
                    }
                } catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                } catch (ClassNotFoundException e) {
                    Log.e("login activity", "File contained unexpected data type: "
                            + e.toString());
                }
            }
        }));
    }
    /**
     * Display that there is no autoSaved game to load.
     */
    private void makeToastLoadAutoSaveFailText() {
        Toast.makeText(this, "No Autosaved Game to Load", Toast.LENGTH_SHORT).show();
    }
    /**
     * Load the savePointData from snake_save_file_tmp.ser, the file used for temporarily holding a
     * savePointData.
     */
    private void loadFromTempFile() {
        try {
            InputStream inputStream = this.openFileInput(TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                savedData = (Object[]) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }
    /**
     * Saves a new game to the currentUserAccount.
     */
    private void updateUserAccounts() {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        LoginActivity.userAccountList.remove(currentUserAccount);
        loadFromTempFile();
        currentUserAccount.addSnakeGame(datetime, savedData);
        LoginActivity.userAccountList.add(currentUserAccount);
        userAccountsToFile();
    }
    /**
     * Saves the userAccountList to a file.
     */

    private void userAccountsToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(LoginActivity.USER_ACCOUNTS_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(LoginActivity.userAccountList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
