package fall2018.csc2017.GameCenter.GameCenter.lobby;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ScoreboardControllerTest {

    /**
     * The user accounts for testing.
     */
    private UserAccount tester1 = new UserAccount("nancy", "207");
    private UserAccount tester2 = new UserAccount("ishan", "207");
    private UserAccount tester3 = new UserAccount("neel", "207");
    private UserAccount tester4 = new UserAccount("lance", "207");

    /**
     * The user account list for testing.
     */
    private ArrayList<UserAccount> testUserAccountList = new ArrayList<>();

    /**
     * The game levels from UserAccount: "Sliding Tiles 3x3", "Sliding Tiles 4x4",
     * "Sliding Tiles 5x5", "Snake Easy Mode", "Snake Hard Mode", "Blocks".
     */
    private String[] gameLevels = UserAccount.gameLevels;

    /**
     * Sets up user account list to begin with tester user accounts.
     */
    @Before
    public void setUp() {
        tester1.setTopScore(gameLevels[1], 100);
        tester2.setTopScore(gameLevels[1], 200);
        tester2.setTopScore(gameLevels[2], 200);
        tester3.setTopScore(gameLevels[4], 20);
        tester3.setTopScore(gameLevels[5], 5);
        tester4.setTopScore(gameLevels[4], 15);
        tester4.setTopScore(gameLevels[5], 10);
        testUserAccountList.add(tester1);
        testUserAccountList.add(tester2);
        testUserAccountList.add(tester3);
        testUserAccountList.add(tester4);
    }

    /**
     * Clears the user account list.
     */
    @After
    public void tearDown() {
        testUserAccountList = new ArrayList<>();
    }

    /**
     * Tests if find top scores returns a String[] of the correct top scores.
     * i.e. uses "None" for default base scores, compares correctly between Sliding Tiles scores
     * (lower score is inputted) and Snake/Blocks scores (higher score is inputted).
     */
    @Test
    public void testFindTopScorers() {
        String[] topScorers = {"None", "nancy: 100", "ishan: 200",
                "None", "neel: 20", "lance: 10"};
        assertArrayEquals(topScorers,
                ScoreboardController.findTopScorers(gameLevels, testUserAccountList));
    }

    /**
     * Tests if find top scorers returns the correct top scorers for Sliding Tiles and none
     * for all other games.
     */
    @Test
    public void testFindTopScoresSlidingTiles() {
        tester1.setTopScore(gameLevels[0], 100);
        String[] topScores = {"100", "100", "None", "None", "None", "None"};
        assertArrayEquals(topScores,
                ScoreboardController.findTopScores(gameLevels, tester1));
    }

    /**
     * Tests if find top scorers returns the correct top scorers for Snake and none
     * for all other games.
     */
    @Test
    public void testFindTopScoresSnake() {
        tester3.setTopScore(gameLevels[3], 15);
        tester3.setTopScore(gameLevels[5], 0);
        String[] topScores = {"None", "None", "None", "15", "20", "None"};
        assertArrayEquals(topScores,
                ScoreboardController.findTopScores(gameLevels, tester3));
    }

    /**
     * Tests if find top scorers returns the correct top scorers for Blocks and none
     * for all other games.
     */
    @Test
    public void testFindTopScoresBlocks() {
        tester4.setTopScore(gameLevels[4], 0);
        String[] topScores = {"None", "None", "None", "None", "None", "10"};
        assertArrayEquals(topScores,
                ScoreboardController.findTopScores(gameLevels, tester4));
    }
}