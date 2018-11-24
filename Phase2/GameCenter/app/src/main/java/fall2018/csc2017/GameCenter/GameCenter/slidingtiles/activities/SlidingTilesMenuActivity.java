package fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.Board;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.SlidingTilesBoardManager;

/**
 * The menu activity for the Sliding Tile game.
 */
public class SlidingTilesMenuActivity extends AppCompatActivity {

    /**
     * The file containing a temp version of the boardManager.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The current user account obtained from the game select screen.
     */
    private UserAccount currentUserAccount;

    /**
     * The board manager.
     */
    private SlidingTilesBoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new SlidingTilesBoardManager(3, getTileIdList(3));
        saveToTempFile();
        currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        setContentView(R.layout.activity_sliding_tiles_menu);
        addLoadButtonListener();
        addSaveButtonListener();
        addLoadAutoSaveButtonListener();
    }

    /**
     * Activate the start button. Once the start button is pressed, a new alert dialog
     * prompts the user to choose between the 3 complexities.
     *
     * @param view the current view.
     */
    public void newGame(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                SlidingTilesMenuActivity.this);
        builder.setTitle("Choose a Complexity");
        String[] levels = {"3x3", "4x4", "5x5"};
        builder.setItems(levels, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        boardManager = new
                                SlidingTilesBoardManager(3, getTileIdList(3));
                        switchToGame();
                        break;
                    case 1:
                        boardManager = new
                                SlidingTilesBoardManager(4, getTileIdList(4));
                        switchToGame();
                        break;
                    case 2:
                        boardManager = new
                                SlidingTilesBoardManager(5, getTileIdList(5));
                        switchToGame();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Undoes the number of moves specified in the text input, if possible.
     * Calls the undo method in BoardManager when the undo button is tapped.
     *
     * @param view the current view.
     */
    public void undoMoves(View view) {
        EditText movesView = findViewById(R.id.Undoers);
        String moves = movesView.getText().toString();
        try {
            int numberMoves = Integer.parseInt(moves);
            if (numberMoves > boardManager.getSavedBoards().size()) {
                Toast.makeText(this,
                        "Invalid number of undoes", Toast.LENGTH_SHORT).show();
            } else {
                boardManager.undo(numberMoves);
                switchToGame();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Please enter a valid number of undoes", Toast.LENGTH_SHORT).show();
            Log.e("undo moves", "Text entered was not an integer: " + e.toString());
        }

    }

    /**
     * Activate the load button. Once the load button is pressed, it displays a list of previously
     * saved games for the user to load. The games are identified by the time and date
     * of the save.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        SlidingTilesMenuActivity.this);
                builder.setTitle("Choose a game");
                int checkedItem = 1;
                builder.setSingleChoiceItems(savedGamesList(), checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        Object selectedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        String selectedGame = selectedItem.toString();
                        boardManager = currentUserAccount.getSlidingTilesGame(selectedGame);
                        Board.numRows = Board.numCols = boardManager.getComplexity();
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
     * Make a list of games names for displaying in load games.
     */
    private String[] savedGamesList(){
        String[] games = new String[(currentUserAccount.getSlidingTilesGameNames().size())];
        int i = 0;
        for (String s : currentUserAccount.getSlidingTilesGameNames()) {
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
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
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
     * Activate the Load autoSave button, which loads the latest autoSave of the currentAccount.
     */
    private void addLoadAutoSaveButtonListener() {
        Button load = findViewById(R.id.LoadAutoSave);
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
                        boardManager = currentUserAccount.getSlidingTilesGame("autoSave");
                        Board.numRows = Board.numCols = boardManager.getComplexity();
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
     * Gets the list of background Ids of the tile images in the drawable folder based on game level
     *
     * @param complexity level of the game
     * @return Arraylist of id numbers of the tile corresponding to the tile in the drawable folder
     */
    private ArrayList<Integer> getTileIdList(int complexity) {
        ArrayList<Integer> tileIdList = new ArrayList<>();
        for (int tileNum = 0; tileNum != Math.pow(complexity, 2); tileNum++) {
            String idString = Integer.toString(tileNum + 1);
            String tileName = "tile_" + idString;
            tileIdList.add(getResources().getIdentifier(tileName, "drawable", getPackageName()));
        }
        return tileIdList;
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent intent = new Intent(this, SlidingTilesGameActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        saveToTempFile();
        startActivity(intent);
    }

    /**
     * Load the board manager from save_file_tmp.ser, the file used for temporarily holding a
     * boardManager.
     */
    private void loadFromTempFile() {
        try {
            InputStream inputStream = this.openFileInput(TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (SlidingTilesBoardManager) input.readObject();
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
     * Save the board manager to save_file_tmp.ser, the file used for temporarily holding a
     * boardManager.
     */
    private void saveToTempFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(TEMP_SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
        currentUserAccount.addSlidingTilesGame(datetime, boardManager);
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
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromTempFile();
    }
}

