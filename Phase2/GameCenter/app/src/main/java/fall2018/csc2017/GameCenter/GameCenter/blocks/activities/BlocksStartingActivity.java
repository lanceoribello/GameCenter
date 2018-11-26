package fall2018.csc2017.GameCenter.GameCenter.blocks.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.GameCenter.GameCenter.blocks.BlocksView;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;

/**
 * The Blocks starting activity.
 */
public class BlocksStartingActivity extends AppCompatActivity {

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
        //find out the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        // Create a new View based on the SnakeView class
        blocksView = new BlocksView(this, size);
        // Make snakeView the default view of the Activity
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
        currentUserAccount.addSnakeGame("autoSave", blocksView.getSavePointData());
        updateUserAccounts();
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
        //blocksView.pause();
        updateHighScore();
    }

    /**
     * Save the board manager to blocks_save_file_tmp.ser, the file used for temporarily holding
     * the save point data.
     */
    public void saveToTempFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(BlocksMenuActivity.TEMP_SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(blocksView.getSavePointData());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

