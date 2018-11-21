package fall2018.csc2017.GameCenter.GameCenter.snake.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.graphics.Point;

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeView;

/*
Adapted from: https://androidgameprogramming.com/programming-a-snake-game/
 */

/**
 * The Snake starting activity.
 */
public class SnakeStartingActivity extends AppCompatActivity {

    /**
     * The current user account obtained from the game select screen.
     */
    private UserAccount currentUserAccount;

    /**
     * The selected complexity for the game: "Snake Easy Mode" or "Snake Hard Mode".
     */
    private String difficulty;

    /**
     * An instance of SnakeView that will initialized in onCreate after getting more details
     * about the device.
     */
    SnakeView snakeView;

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
        difficulty = (String) getIntent().getSerializableExtra("difficulty");
        // Create a new View based on the SnakeView class
        snakeView = new SnakeView(this, size, difficulty, null);
        // Make snakeView the default view of the Activity
        setContentView(snakeView);
    }

    /**
     * Updates the high scores of the currentUserAccount if a new high score was achieved.
     */
    private void updateHighScore() {
        boolean scoreUpdated = false;
        int finalScore = snakeView.getScore();
        if (snakeView.detectDeath() &&
                this.currentUserAccount.getTopScore(difficulty) < finalScore) {
            this.currentUserAccount.setTopScore(difficulty, finalScore);
            scoreUpdated = true;
        }
        if (scoreUpdated) {
            updateUserAccounts(difficulty, finalScore);
        }
    }

    /**
     * Writes new high scores to file.
     * Helper method for updateHighScore.
     *
     * @param difficulty either easy or hard
     * @param finalScore the final score of the game
     */
    private void updateUserAccounts(String difficulty, int finalScore) {
        LoginActivity.userAccountList.remove(currentUserAccount);
        this.currentUserAccount.setTopScore(difficulty, finalScore);
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
     * Start the thread in snakeView when this Activity is shown to the player.
     */
    @Override
    protected void onResume() {
        super.onResume();
        snakeView.resume();
        updateHighScore();
    }

    /**
     * Make sure the thread in snakeView is stopped if this Activity is about to be closed.
     */
    @Override
    protected void onPause() {
        super.onPause();
        snakeView.pause();
        updateHighScore();
    }
}
