package fall2018.csc2017.GameCenter.GameCenter.lobby.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesMenuActivity;
import fall2018.csc2017.GameCenter.GameCenter.snake.activities.SnakeMenuActivity;

/**
 * The game select screen shown after login screen. User can select a game to play or view the
 * scoreboards of all the games.
 */
public class GameSelectActivity extends AppCompatActivity {

    /**
     * The user account file containing all UserAccount objects.
     */
    private static final String USER_ACCOUNTS_FILENAME = "accounts.ser";

    /**
     * The current user account obtained from the login screen.
     */
    private UserAccount currentUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserAccount = (UserAccount) getIntent().getSerializableExtra(
                "currentUserAccount");
        setContentView(R.layout.activity_game_select);
        addSlidingTilesButtonListener();
        addSnakeButtonListener();
        addScoreboardButtonListener();
    }

    /**
     * Activate the sliding tiles button.
     */
    private void addSlidingTilesButtonListener() {
        Button slidingTilesButton = findViewById(R.id.slidingTilesButton);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingTiles();
            }
        });
    }

    /**
     * Activate the snake button.
     */

    private void addSnakeButtonListener() {
        Button slidingTilesButton = findViewById(R.id.snakeButton);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSnake();
            }
        });
    }

    /**
     * Activate the View ScoreboardActivity button.
     */
    private void addScoreboardButtonListener() {
        Button scoreboardButton = findViewById(R.id.scoreboardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboard();
            }
        });
    }

    /**
     * Switch to the Sliding Tiles Menu.
     */
    private void switchToSlidingTiles() {
        Intent intent = new Intent(this, SlidingTilesMenuActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        startActivity(intent);
    }

    /**
     * Switch to the Snake Menu.
     */
    private void switchToSnake() {
        Intent intent = new Intent(this, SnakeMenuActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        startActivity(intent);
    }

    /**
     * Switch to the ScoreboardActivity view to view the per-user and per-game scoreboards.
     */
    private void switchToScoreboard() {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        startActivity(intent);
    }

    /**
     * Update current user account by finding and setting it from the file.
     *
     * @param fileName the name of the file
     */
    public void setCurrentUserAccount(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                ArrayList<UserAccount> userAccountList = (ArrayList<UserAccount>) input.readObject();
                // Update current user account from file
                for (UserAccount user : userAccountList) {
                    if (user.getUsername().equals(this.currentUserAccount.getUsername())) {
                        this.currentUserAccount = user;
                    }
                }
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
     * Update current user account from file on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        setCurrentUserAccount(USER_ACCOUNTS_FILENAME);
    }
}
