package fall2018.csc2017.GameCenter.GameCenter.lobby;

import org.junit.Test;

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
     * Tests if updated high score is returned correctly.
     */
    @Test
    public void getTopScore() {
        String gameLevel = "Sliding Tiles 3x3";
        int topScore = 42;
        tester.setTopScore(gameLevel, 42);
        //assertEquals(topScore, tester.getTopScore(gameLevel));
    }

    /**
     * Tests if updated high score is saved correctly.
     */
    @Test
    public void setTopScore() {

    }

    @Test
    public void addSlidingTilesGame() {
    }

    @Test
    public void getSlidingTilesGame() {
    }

    @Test
    public void getSlidingTilesGameNames() {
    }

    @Test
    public void addSnakeGame() {
    }

    @Test
    public void getSnakeGame() {
    }

    @Test
    public void getSnakeGameNames() {
    }
}