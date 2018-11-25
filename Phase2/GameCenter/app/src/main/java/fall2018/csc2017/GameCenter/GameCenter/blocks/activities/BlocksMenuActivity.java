package fall2018.csc2017.GameCenter.GameCenter.blocks.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.SlidingTilesBoardManager;
import fall2018.csc2017.GameCenter.GameCenter.snake.activities.SnakeMenuActivity;
import fall2018.csc2017.GameCenter.GameCenter.snake.activities.SnakeStartingActivity;

public class BlocksMenuActivity extends AppCompatActivity {
    /**
     * The file containing a temp version of the boardManager.
     */
    public static final String TEMP_SAVE_FILENAME = "blocks_save_file_tmp.ser";

    /**
     * The current user account obtained from the game select screen.
     */
    private UserAccount currentUserAccount;

    /**
     * The board manager.
     */
    private Object savedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocks_menu);
//        saveToTempFile();                 if need be
        currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        addLoadButtonListener();
        addSaveButtonListener();
//        addLoadAutoSaveButtonListener();
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
                        savedData = currentUserAccount.getBlocksGame(selectedGame);
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
    /**
     * Saves a new game to the currentUserAccount with game name as date and time.
     */
    private void updateUserAccounts() {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        LoginActivity.userAccountList.remove(currentUserAccount);
        currentUserAccount.addBlocksGame(datetime, null);
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
     * Undoes the number of moves specified in the text input, if possible.
     * Calls the undo method in BoardManager when the undo button is tapped.
     *
     * @param view the current view.
     */
    public void undoMoves(View view) {
        EditText movesView = findViewById(R.id.NumUndo);
//        String moves = movesView.getText().toString();
//        try {
//            int numberMoves = Integer.parseInt(moves);
//            if (numberMoves > boardManager.getSavedBoards().size()) {
//                Toast.makeText(this,
//                        "Invalid number of undoes", Toast.LENGTH_SHORT).show();
//            } else {
//                boardManager.undo(numberMoves);
//                switchToGame();
//            }
//        } catch (NumberFormatException e) {
//            Toast.makeText(this,
//                    "Please enter a valid number of undoes", Toast.LENGTH_SHORT).show();
//            Log.e("undo moves", "Text entered was not an integer: " + e.toString());
//        }

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
     * Switch to SnakeStartingActivity to play the game.
     * Passes savedData and difficulty into BlocksStartingActivity.
     */
    private void switchToGame() {
        Intent intent = new Intent(this, BlocksStartingActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        startActivity(intent);
    }
}
