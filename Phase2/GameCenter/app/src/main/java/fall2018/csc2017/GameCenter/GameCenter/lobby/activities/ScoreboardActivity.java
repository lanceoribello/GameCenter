package fall2018.csc2017.GameCenter.GameCenter.lobby.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;

/**
 * The scoreboard activity that is displayed when "View Scoreboards" button is clicked in
 * game select activity. Displays the per-user scoreboard (top user of each game level)
 * and the per-game scoreboard(top score of current user for each game level).
 */
public class ScoreboardActivity extends AppCompatActivity {

    /**
     * The user account file containing all UserAccount objects.
     */
    private static final String USER_ACCOUNTS_FILENAME = "accounts.ser";

    /**
     * Array List of UserAccount objects, which store user account user names and passwords.
     */
    private ArrayList<UserAccount> userAccountList;

    /**
     * Current user account that is signed into.
     */
    private UserAccount currentUserAccount;

    /**
     * String Array of the game level names, from UserAccount.
     */
    private String[] gameLevels = UserAccount.gameLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        this.currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        setUserAccountList(USER_ACCOUNTS_FILENAME);
        createScoreboardTitle();
        createScoreboardTable();
    }

    /**
     * Set the title of the Scoreboard screen with the current user's username.
     */
    private void createScoreboardTitle() {
        TextView scoreboardTitle = findViewById(R.id.scoreboardTitle);
        String userScoreboardTitle = "Scoreboard of User: " + this.currentUserAccount.getUsername();
        scoreboardTitle.setText(userScoreboardTitle);
    }

    /**
     * Set the table of scores by iterating over each game level
     */
    private void createScoreboardTable() {
        TableLayout scoreboardTable = findViewById(R.id.scoreboardTable);
        String[] topScorers = findTopScorers();
        String[] topScores = findTopScores();
        int i = 0;
        // Set each row of scores for each game level
        for (String gameLevel : gameLevels) {
            TableRow scoreboardRow = new TableRow(this);
            TextView headingView = new TextView(this);
            headingView.setText(gameLevel);
            scoreboardRow.addView(headingView);
            String scorer = topScorers[i];
            String score = topScores[i];
            TextView scorerView = new TextView(this);
            scorerView.setText(scorer);
            TextView scoreView = new TextView(this);
            scoreView.setText(score);
            scoreboardRow.addView(scorerView);
            scoreboardRow.addView(scoreView);
            scoreboardTable.addView(scoreboardRow);
            i++;
        }
    }

    /**
     * Returns an array of the top scorers for each game level.
     * Default top score for Sliding Tiles is 1000000, which displays as "None" on the scoreboard.
     * Default top score for Snake is 0, which displays as "None" on the scoreboard.
     *
     * @return topScorers, a list of top scorers and their scores for each game level
     */
    private String[] findTopScorers() {
        String[] topScorers = new String[this.gameLevels.length];
        Arrays.fill(topScorers, "None");
        Integer[] baseTopScores = {1000000, 1000000, 1000000, 0, 0};
        setUserAccountList(USER_ACCOUNTS_FILENAME);
        for (UserAccount user : this.userAccountList) {
            for (int i = 0; i < this.gameLevels.length; i++) {
                // Update score if less than base score for Sliding Tiles
                if (i < 3) {
                    if (user.getTopScore(this.gameLevels[i]) < baseTopScores[i]) {
                        topScorers[i] = (user.getUsername() + ": "
                                + String.valueOf(user.getTopScore(this.gameLevels[i])));
                        baseTopScores[i] = user.getTopScore(this.gameLevels[i]);
                    }
                }
                // Update score if greater than base score for Snake and Blocks
                else {
                    if (user.getTopScore(this.gameLevels[i]) > baseTopScores[i]) {
                        topScorers[i] = (user.getUsername() + ": "
                                + String.valueOf(user.getTopScore(this.gameLevels[i])));
                        baseTopScores[i] = user.getTopScore(this.gameLevels[i]);
                    }
                }
            }
        }
        return topScorers;
    }

    /**
     * Returns an array of the top scores for each game level for the current user account.
     * Default top score for Sliding Tiles is 1000000, which displays as "None" on the scoreboard.
     * Default top score for Snake is 0, which displays as "None" on the scoreboard.
     *
     * @return topScores, a list of scores of the current user account for each game level
     */
    private String[] findTopScores() {
        String[] topScores = new String[this.gameLevels.length];
        Arrays.fill(topScores, "None");
        setUserAccountList(USER_ACCOUNTS_FILENAME);
        for (int i = 0; i < this.gameLevels.length; i++) {
            Integer userTopScore = this.currentUserAccount.getTopScore(this.gameLevels[i]);
            // Update top score if not set as default score in the user account
            if ((i < 3 && userTopScore != 1000000) || (i >= 3 && userTopScore != 0)) {
                topScores[i] = String.valueOf(userTopScore);
            }
        }
        return topScores;
    }

    /**
     * Set list of user accounts from fileName and updates current user account.
     *
     * @param fileName the name of the file
     */
    public void setUserAccountList(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.userAccountList = (ArrayList<UserAccount>) input.readObject();
                // Update current user account from file
                for (UserAccount user : this.userAccountList) {
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
     * Update current user account from file.
     */
    @Override
    protected void onResume() {
        super.onResume();
        setUserAccountList(USER_ACCOUNTS_FILENAME);
    }
}
