package fall2018.csc2017.GameCenter.GameCenter.blocks.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import fall2018.csc2017.GameCenter.GameCenter.blocks.BlocksView;
import fall2018.csc2017.GameCenter.GameCenter.blocks.GridManager;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;

/**
 * The menu activity for the Blocks game.
 */
public class BlocksMenuActivity extends AppCompatActivity {

    /**
     * The file containing a temp version of blocksView.gridManager.
     */
    public static final String TEMP_SAVE_FILENAME = "blocks_save_file_tmp.ser";

    /**
     * The current user account obtained from the game select screen.
     */
    private UserAccount currentUserAccount;

    /**
     * The game GridManager.
     */
    private BlocksView blocksView;

    /**
     * The game save data.
     */
    private Object[] savedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocks_menu);
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        blocksView = new BlocksView(this, size);
        saveToTempFile();
        currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        addNewGameButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addLoadAutoSaveButtonListener();
    }

    /**
     * Undoes the number of moves specified in the text input, if possible.
     * Calls the undo method in GridManager when the undo button is tapped.
     *
     * @param view the current view
     */
    public void undoMoves(View view) {
        EditText movesView = findViewById(R.id.NumUndo);
        String moves = movesView.getText().toString();
        try {
            int numberMoves = Integer.parseInt(moves);
            if (numberMoves > blocksView.gridManager.getSavedGrids().size()) {
                Toast.makeText(this,
                        "Invalid number of undoes", Toast.LENGTH_SHORT).show();
            } else {
                blocksView.gridManager.undo(numberMoves);
                switchToGame();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Please enter a valid number of undoes", Toast.LENGTH_SHORT).show();
            Log.e("undo moves", "Text entered was not an integer: " + e.toString());
        }
    }

    /**
     * Activate the new game button.
     */
    private void addNewGameButtonListener() {
        Button newGameButton = findViewById(R.id.NewGameBlocks);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveBlocks);
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
        Button loadButton = findViewById(R.id.LoadBlocks);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        BlocksMenuActivity.this);
                builder.setTitle("Choose a game");
                int checkedItem = 1; //Sets the choice to the first element.
                builder.setSingleChoiceItems(savedGamesList(), checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        Object selectedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        String selectedGame = selectedItem.toString();
                        blocksView.gridManager = currentUserAccount.getBlocksGame(selectedGame);
                        makeToastLoadedText();
                        dialog.dismiss();
                        switchToGame();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the Load autoSave button, which loads the latest autoSave of the currentAccount.
     */
    private void addLoadAutoSaveButtonListener() {
        Button load = findViewById(R.id.LoadAutoSaveBlocks);
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
                        for (UserAccount user : userAccountList) {
                            if (user.getUsername().equals(currentUserAccount.getUsername())) {
                                currentUserAccount = user;
                            }
                        }
                        blocksView.gridManager =
                                currentUserAccount.getBlocksGame("autoSave");
                        switchToGame();
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
     * Saves a new game to the currentUserAccount with game name as date and time.
     */
    private void updateUserAccounts() {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        LoginActivity.userAccountList.remove(currentUserAccount);
        loadFromTempFile();
        currentUserAccount.addBlocksGame(datetime, blocksView.gridManager);
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

    /**
     * Make a list of games names for displaying in load games.
     */
    private String[] savedGamesList(){
        String[] games = new String[(currentUserAccount.getBlocksGameNames().size())];
        int i = 0;
        for (String s : currentUserAccount.getBlocksGameNames()) {
            games[i++] = s;
        }
        return games;
    }

    /**
     * Switch to SnakeGameActivity to play the game.
     * Passes savedData and difficulty into BlocksGameActivity.
     */
    private void switchToGame() {
        Intent intent = new Intent(this, BlocksGameActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        saveToTempFile();
        startActivity(intent);
    }

    /**
     * Save the board manager to save_file_tmp.ser, the file used for temporarily holding a
     * boardManager.
     */
    private void saveToTempFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(TEMP_SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(blocksView.gridManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
                blocksView.gridManager = (GridManager) input.readObject();
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
     * Read the temporary grid manager from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromTempFile();
    }
}
