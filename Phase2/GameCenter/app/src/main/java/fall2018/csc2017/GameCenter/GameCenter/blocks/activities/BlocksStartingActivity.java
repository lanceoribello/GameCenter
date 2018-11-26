package fall2018.csc2017.GameCenter.GameCenter.blocks.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.GameCenter.GameCenter.blocks.BlocksView;
import fall2018.csc2017.GameCenter.GameCenter.blocks.GridManager;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;

/**
 * The Blocks starting activity.
 */
public class BlocksStartingActivity extends AppCompatActivity {

    /**
     * The file containing a temp version of blocksView.gridManager.
     */
    public static final String TEMP_SAVE_FILENAME = "blocks_save_file_tmp.ser";

    /**
     * The current user account obtained from the game select screen.
     */
    private UserAccount currentUserAccount;

    /**
     * An instance of BlocksViews that will initialized in onCreate after getting more details
     * about the device.
     */
    BlocksView blocksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromTempFile();
        //find out the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        // Create a new View based on the BlocksView class
        blocksView = new BlocksView(this, size);
        // Make blocksView the default view of the Activity
        setContentView(blocksView);
    }
    
    /**
     * Updates the high scores of the currentUserAccount if a new high score was achieved.
     */
    private void updateHighScore() {
        int finalScore = blocksView.gridManager.getGrid().getScore();
        if (blocksView.gridManager.gameOver() &&
                this.currentUserAccount.getTopScore("Blocks") < finalScore) {
            this.currentUserAccount.setTopScore("Blocks", finalScore);
            updateUserAccounts();
        }
    }

    /**
     * Writes new high scores to file.
     * Helper method for updateHighScore.
     */
    private void updateUserAccounts() {
        LoginActivity.userAccountList.remove(currentUserAccount);
        LoginActivity.userAccountList.add(currentUserAccount);
        userAccountsToFile(LoginActivity.USER_ACCOUNTS_FILENAME);
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

    /**
     * Writes the current boardManager to the current userAccount.
     */
    private void createAutoSave() {
        currentUserAccount.addBlocksGame("autoSave", blocksView.gridManager);
        updateUserAccounts();
    }

    /**
     * Save the board manager to blocks_save_file_tmp.ser, the file used for temporarily holding
     * the save point data.
     */
    public void saveToTempFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(BlocksMenuActivity.TEMP_SAVE_FILENAME, MODE_PRIVATE));
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
     * Start the thread in snakeView when this Activity is shown to the player.
     */
    @Override
    protected void onResume() {
        super.onResume();
        blocksView.resume();
        updateHighScore();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToTempFile();
        createAutoSave();
        blocksView.pause();
        updateHighScore();
    }

    /**
     * Dispatch onStop() to fragments.
     */
    @Override
    protected void onStop() {
        super.onStop();
        saveToTempFile();
        createAutoSave();
        blocksView.pause();
        updateHighScore();
    }
}

