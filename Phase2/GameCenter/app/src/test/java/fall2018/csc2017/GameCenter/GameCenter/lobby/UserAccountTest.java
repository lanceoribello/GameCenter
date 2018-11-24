package fall2018.csc2017.GameCenter.GameCenter.lobby;

import org.junit.Test;

import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.SlidingTilesBoardManager;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesMenuActivity;

import static org.junit.Assert.*;

public class UserAccountTest {

    /**
     * The user account for testing.
     */
    private UserAccount tester = new UserAccount("nancyishaneelance", "207");

    /**
     * Tests if user account returns the correct username.
     */
    @Test
    public void testGetUsername() {
        String username = "nancyishaneelance";
        assertEquals(username, tester.getUsername());
    }

    /**
     * Tests if user account returns the correct password.
     */
    @Test
    public void testGetPassword() {
        String password = "207";
        assertEquals(password, tester.getPassword());
    }

    /**
     * Tests if updated high score is saved correctly.
     */
    @Test
    public void setTopScore() {
        String gameLevel = "Sliding Tiles 3x3";
        Integer topScore = 42;
        tester.setTopScore(gameLevel, topScore);
        assertEquals(topScore, tester.getTopScore(gameLevel));
    }

    /**
     * Tests if updated high score is returned correctly.
     */
    @Test
    public void getTopScore() {
        String gameLevel = "Sliding Tiles 3x3";
        Integer topScore = 42;
        assertEquals(topScore, tester.getTopScore(gameLevel));
    }

    /**
     * Tests if Sliding Tiles game is added correctly.
     */
    @Test
    public void addSlidingTilesGame() {
        //String gameName = "Saved Game";
    }

    /**
     * Tests if Sliding Tiles game is returned correctly.
     */
    @Test
    public void getSlidingTilesGame() {
    }

    /**
     * Tests if Sliding Tiles game names are returned correctly.
     */
    @Test
    public void getSlidingTilesGameNames() {
    }

    /**
     * Tests if Snake game is added correctly.
     */
    @Test
    public void addSnakeGame() {
    }

    /**
     * Tests if Snake game is returned correctly.
     */
    @Test
    public void getSnakeGame() {
    }

    /**
     * Tests if Snake game names are returned correctly.
     */
    @Test
    public void getSnakeGameNames() {
    }
}