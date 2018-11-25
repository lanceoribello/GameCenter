package fall2018.csc2017.GameCenter.GameCenter.lobby;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The controller for the scoreboard activity. Logic for getting the top users of each game level
 * and the top scores of current user for each game level.
 */
public class ScoreboardController {

    /**
     * Returns an array of the top scorers for each game level.
     * Default top score for Sliding Tiles is 1000000, which displays as "None" on the scoreboard.
     * Default top score for Snake and Blocks is 0, which displays as "None" on the scoreboard.
     *
     * @return topScorers, a list of top scorers and their scores for each game level
     */
    public static String[] findTopScorers(String[] gameLevels,
                                          ArrayList<UserAccount> userAccountList) {
        String[] topScorers = new String[gameLevels.length];
        Arrays.fill(topScorers, "None");
        Integer[] baseTopScores = {1000000, 1000000, 1000000, 0, 0, 0};
        for (UserAccount user : userAccountList) {
            for (int i = 0; i < gameLevels.length; i++) {
                // Update score if less than base (Sliding Tiles)/greater than base (Snake, Blocks)
                if ((i < 3 && user.getTopScore(gameLevels[i]) < baseTopScores[i]) ||
                        (i >= 3 && user.getTopScore(gameLevels[i]) > baseTopScores[i])) {
                    topScorers[i] = (user.getUsername() + ": "
                            + String.valueOf(user.getTopScore(gameLevels[i])));
                    baseTopScores[i] = user.getTopScore(gameLevels[i]);
                }
            }
        }
        return topScorers;
    }

    /**
     * Returns an array of the top scores for each game level for the current user account.
     * Default top score for Sliding Tiles is 1000000, which displays as "None" on the scoreboard.
     * Default top score for Snake and Blocks is 0, which displays as "None" on the scoreboard.
     *
     * @return topScores, a list of scores of the current user account for each game level
     */
    public static String[] findTopScores(String[] gameLevels, UserAccount currentUserAccount) {
        String[] topScores = new String[gameLevels.length];
        Arrays.fill(topScores, "None");
        for (int i = 0; i < gameLevels.length; i++) {
            Integer userTopScore = currentUserAccount.getTopScore(gameLevels[i]);
            // Update top score if not set as default score for respective game level
            if ((i < 3 && userTopScore != 1000000) || (i >= 3 && userTopScore != 0)) {
                topScores[i] = String.valueOf(userTopScore);
            }
        }
        return topScores;
    }
}
