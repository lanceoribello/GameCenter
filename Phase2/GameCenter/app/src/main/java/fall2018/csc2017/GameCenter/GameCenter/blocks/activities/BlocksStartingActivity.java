package fall2018.csc2017.GameCenter.GameCenter.blocks.activities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.GameCenter.GameCenter.blocks.GridManager;
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
     * An instance of GridManager that will initialized in onCreate after getting more details
     * about the device.
     */
    GridManager gridManager;

    /**
     * Updates the high scores of the currentUserAccount if a new high score was achieved.
     */
    private void updateHighScore() {
        int finalScore = gridManager.getGrid().getScore();
        if (gridManager.gameOver() &&
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

}
