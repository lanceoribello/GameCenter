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
import fall2018.csc2017.GameCenter.GameCenter.lobby.ScoreboardController;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;

/**
 * The scoreboard activity that is displayed when "View Scoreboards" button is clicked in
 * game select activity. Displays the per-user scoreboard (top user of each game level)
 * and the per-game scoreboard (top score of current user for each game level).
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
        setUserAccountList(USER_ACCOUNTS_FILENAME);
        String[] gameLevels = UserAccount.gameLevels;
        String[] topScorers = ScoreboardController.findTopScorers(gameLevels,
                this.userAccountList);
        String[] topScores = ScoreboardController.findTopScores(gameLevels,
                this.currentUserAccount);
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
                setCurrentUserAccount();
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
     * Update current user account from user account list.
     */
    private void setCurrentUserAccount() {
        for (UserAccount user : this.userAccountList) {
            if (user.getUsername().equals(this.currentUserAccount.getUsername())) {
                this.currentUserAccount = user;
            }
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
