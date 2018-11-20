package fall2018.csc2017.GameCenter.GameCenter.snake;

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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.snake.activities.SnakeStartingActivity;

/**
 * The menu activity for the Snake game.
 */
public class SnakeMenuActivity extends AppCompatActivity {

    /**
     * The current user account obtained from the game select screen.
     */
    private UserAccount currentUserAccount;

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
//        addLoadButtonListener();
//        addSaveButtonListener();
//        addLoadAutoSaveButtonListener();
    }

    /**
     * Switch to SnakeStartingActivity to play the game.
     */
    private void switchToGame(String difficulty) {
        Intent intent = new Intent(this, SnakeStartingActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        intent.putExtra("difficulty", difficulty);
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
                        switchToGame("easy");
                        break;
                    case 1:
                        switchToGame("hard");
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
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DateFormat dateFormat =
                        DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
                String datetime = dateFormat.format(c.getTime());
                LoginActivity.userAccountList.remove(currentUserAccount);
                currentUserAccount.addSnakeGame(datetime, null); //Need savedData here.
                LoginActivity.userAccountList.add(currentUserAccount);
                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(
                            openFileOutput(LoginActivity.USER_ACCOUNTS_FILENAME, MODE_PRIVATE));
                    outputStream.writeObject(LoginActivity.userAccountList);
                    outputStream.close();
                } catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
                makeToastSavedText();
            }
        });
    }

    /**
     * Activate the load button listener.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        SnakeMenuActivity.this);
                builder.setTitle("Choose a game");
                String[] games = new String[(currentUserAccount.getSnakeGameNames().size())];
                int i = 0;
                for (String s : currentUserAccount.getSnakeGameNames()) {
                    games[i++] = s;
                }
                int checkedItem = 1;
                builder.setSingleChoiceItems(games, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        Object selectedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        String selectedGame = selectedItem.toString();
                        savedData = currentUserAccount.getSnakeGame(selectedGame);
//                        Board.numRows = Board.numCols = boardManager.getComplexity();
                        makeToastLoadedText();
                        dialog.dismiss();
                        switchToGame("add difficulty here");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }
}
