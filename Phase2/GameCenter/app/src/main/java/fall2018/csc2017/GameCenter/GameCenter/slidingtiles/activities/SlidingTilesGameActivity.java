package fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.Board;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.CustomAdapter;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.GestureDetectGridView;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.SlidingTilesBoardManager;

/**
 * The Sliding Tiles game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SlidingTilesBoardManager boardManager;

    /**
     * The account obtained from the login screen.
     */
    private UserAccount currentUserAccount;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * String Array of the game level names.
     */
    private String[] gameLevels = {"Sliding Tiles 3x3", "Sliding Tiles 4x4", "Sliding Tiles 5x5"};

    /**
     * Integer Array of the Sliding Tiles complexities.
     */
    private Integer[] complexities = {3, 4, 5};

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromTempFile();
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.numCols);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        columnWidth = displayWidth / Board.numCols;
                        columnHeight = displayHeight / Board.numRows;
                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.numRows; row++) {
            for (int col = 0; col != Board.numCols; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.numRows;
            int col = nextPos % Board.numCols;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToTempFile();
        updateHighScores();
        createAutoSave();
    }

    /**
     * Dispatch onStop() to fragments.
     */
    @Override
    protected void onStop() {
        super.onStop();
        saveToTempFile();
        updateHighScores();
        createAutoSave();
    }

    /**
     * Updates the high scores of the currentUserAccount if a new high score was achieved.
     */
    private void updateHighScores() {
        boolean scoreUpdated = false;
        int complexity = boardManager.getComplexity();
        int numMoves = boardManager.getMoves();
        if (boardManager.puzzleSolved()) {
            for (int i = 0; i < this.gameLevels.length; i++) {
                if (complexity == this.complexities[i] &&
                        this.currentUserAccount.getTopScore(this.gameLevels[i]) > numMoves) {
                    this.currentUserAccount.setTopScore(this.gameLevels[i], numMoves);
                    scoreUpdated = true;
                }
            }
        }
        if (scoreUpdated) {
            updateUserAccounts();
        }
    }

    /**
     * Updates the master userAccountList to account for any changes to the local userAccount.
     */
    private void updateUserAccounts() {
        LoginActivity.userAccountList.remove(currentUserAccount);
        LoginActivity.userAccountList.add(currentUserAccount);
        userAccountsToFile(LoginActivity.USER_ACCOUNTS_FILENAME);
    }

    /**
     * Writes the current boardManager to the current userAccount.
     */
    private void createAutoSave() {
        currentUserAccount.addSlidingTilesGame("autoSave", boardManager);
        updateUserAccounts();
    }

    /**
     * Load the board manager from save_file_tmp.ser, the file used for temporarily holding a
     * boardManager.
     */
    private void loadFromTempFile() {
        try {
            InputStream inputStream = this.openFileInput(
                    SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
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
    public void saveToTempFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Saves the LoginActivity.userAccountList to a file.
     *
     * @param fileName the name of the file
     */
    public void userAccountsToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(LoginActivity.userAccountList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
