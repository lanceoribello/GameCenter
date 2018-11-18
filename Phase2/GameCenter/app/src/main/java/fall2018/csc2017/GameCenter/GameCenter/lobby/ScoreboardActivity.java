package fall2018.csc2017.GameCenter.GameCenter.lobby;

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

/**
 * The scoreboard screen that is displayed after "View Scoreboards" button is clicked.
 * Displays the per-user scoreboard (top user of each complexity) and the per-game scoreboard
 * (top score of current user for each complexity).
 */
public class ScoreboardActivity extends AppCompatActivity {

    /**
     * The user account file containing all UserAccount objects.
     */
    private static final String USER_ACCOUNTS_FILENAME = "accounts.ser";

    /**
     * Array List of UserAccount objects, which store account usernames and passwords.
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
        setUserAccountList(USER_ACCOUNTS_FILENAME);
        this.currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
        TableLayout scoreboardTable = findViewById(R.id.scoreboardsTable);
        String[] topScorers = findTopScorers();
        String[] topScores = findTopScores();
        String[] headings = {"3x3", "4x4", "5x5", "Easy Mode", "Hard Mode"};
        int i = 0;
        for (String heading : headings) {
            TableRow scoreboardRow = new TableRow(this);
            TextView headingView = new TextView(this);
            headingView.setText(heading);
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
     * Returns an array of the three top scorers for each complexity, in order of 3x3, 4x4, 5x5.
     * Default top score is 1000000, which displays as "None".
     *
     * @return topScorers, a list of usernames of the top scorer for each complexity.
     */
    private String[] findTopScorers() {
        String[] topScorers = new String[5];
        Arrays.fill(topScorers, "None");
        int top3x3 = 1000000, top4x4 = 1000000, top5x5 = 1000000;
        int easySnakeScore = 0, hardSnakeScore = 0;
        for (UserAccount user : this.userAccountList) {
            if (user.getSlidingTilesTop3x3() < top3x3) {
                topScorers[0] = (user.getUsername() + ": " + String.valueOf(user.getSlidingTilesTop3x3()));
                top3x3 = user.getSlidingTilesTop3x3();
            }
            if (user.getSlidingTilesTop4x4() < top4x4) {
                topScorers[1] = (user.getUsername() + ": " + String.valueOf(user.getSlidingTilesTop4x4()));
                top4x4 = user.getSlidingTilesTop4x4();
            }
            if (user.getSlidingTilesTop5x5() < top5x5) {
                topScorers[2] = (user.getUsername() + ": " + String.valueOf(user.getSlidingTilesTop5x5()));
                top5x5 = user.getSlidingTilesTop5x5();
            }
            if (user.getEasySnakeScore() > easySnakeScore) {
                topScorers[3] = (user.getUsername() + ": " + String.valueOf(user.getEasySnakeScore()));
                easySnakeScore = user.getEasySnakeScore();
            }
            if (user.getHardSnakeScore() > hardSnakeScore) {
                topScorers[4] = (user.getUsername() + ": " + String.valueOf(user.getHardSnakeScore()));
                hardSnakeScore = user.getHardSnakeScore();
            }
        }
        return topScorers;
    }

    /**
     * Returns an array of the three top scores for each complexity, in order of 3x3, 4x4, 5x5.
     * Default top score is 1000000, which displays as "None".
     *
     * @return topScores, a list of scores of the current user for each complexity.
     */
    private String[] findTopScores() {
        String[] topScores = new String[5];
        UserAccount current;
        setUserAccountList(USER_ACCOUNTS_FILENAME);
        for (UserAccount user : this.userAccountList) {
            if (user.getUsername().equals(this.currentUserAccount.getUsername())) {
                current = user;
                topScores[0] = String.valueOf(current.getSlidingTilesTop3x3());
                topScores[1] = String.valueOf(current.getSlidingTilesTop4x4());
                topScores[2] = String.valueOf(current.getSlidingTilesTop5x5());
                topScores[3] = String.valueOf(current.getEasySnakeScore());
                topScores[4] = String.valueOf(current.getHardSnakeScore());
            }
        }
        for (int i = 0; i < 3; i++) {
            if (topScores[i].equals("1000000") || topScores[i] == null) {
                topScores[i] = "None";
            }
        }
        for (int i = 3; i < 5; i++) {
            if (topScores[i] == null || topScores[i].equals("0")) {
                topScores[i] = "None";
            }
        }
        return topScores;
    }

    /**
     * Set list of user accounts from fileName.
     *
     * @param fileName the name of the file
     */
    public void setUserAccountList(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.userAccountList = (ArrayList<UserAccount>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
}
