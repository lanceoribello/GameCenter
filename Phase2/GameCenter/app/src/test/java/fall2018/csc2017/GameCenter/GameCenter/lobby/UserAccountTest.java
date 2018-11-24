package fall2018.csc2017.GameCenter.GameCenter.lobby;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.SlidingTilesBoardManager;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesMenuActivity;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeView;

import static org.junit.Assert.*;

public class UserAccountTest {

    /**
     * The user account for testing.
     */
    private UserAccount tester = new UserAccount("nancyishaneelance", "207");

    /**
     * The Sliding Tiles menu for testing.
     */
    private SlidingTilesMenuActivity slidingTilesMenu = new SlidingTilesMenuActivity();

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
     * Tests if updated high score is saved and returned correctly.
     */
    @Test
    public void testSetGetTopScore() {
        String gameLevel = "Sliding Tiles 3x3";
        Integer topScore = 42;
        tester.setTopScore(gameLevel, topScore);
        assertEquals(topScore, tester.getTopScore(gameLevel));
    }

    /**
     * Tests if Sliding Tiles game is added and returned correctly.
     */
    @Test
    public void testAddGetSlidingTilesGame() {
        String gameName = "Saved Game";
        SlidingTilesBoardManager gameBoardManager = new SlidingTilesBoardManager(3,
                slidingTilesMenu.getTileIdList(3));
        tester.addSlidingTilesGame(gameName, gameBoardManager);
        assertEquals(gameBoardManager, tester.getSlidingTilesGame(gameName));
    }

    /**
     * Tests if Sliding Tiles game names are returned correctly.
     */
    @Test
    public void testGetSlidingTilesGameNames() {
        String gameName1 = "Game 1";
        SlidingTilesBoardManager gameBoardManager1 = new SlidingTilesBoardManager(3,
                slidingTilesMenu.getTileIdList(3));
        String gameName2 = "Game 2";
        SlidingTilesBoardManager gameBoardManager2 = new SlidingTilesBoardManager(4,
                slidingTilesMenu.getTileIdList(4));
        String gameName3 = "Game 3";
        SlidingTilesBoardManager gameBoardManager3 = new SlidingTilesBoardManager(5,
                slidingTilesMenu.getTileIdList(5));
        tester.addSlidingTilesGame(gameName1, gameBoardManager1);
        tester.addSlidingTilesGame(gameName2, gameBoardManager2);
        tester.addSlidingTilesGame(gameName3, gameBoardManager3);
        Set<String> gameNames = new HashSet<>(Arrays.asList("Game 1", "Game 2", "Game 3"));
        assertEquals(gameNames, tester.getSlidingTilesGameNames());
    }

    /**
     * Tests if Snake game is added and returned correctly.
     */
    @Test
    public void testAddGetSnakeGame() {
        String gameName = "Saved Game";
        Object[] gameSavedData = {1, 1, 2, 2, 1, 0, "Snake Easy Mode", SnakeView.Direction.RIGHT};
        tester.addSnakeGame(gameName, gameSavedData);
        assertArrayEquals(gameSavedData, tester.getSnakeGame(gameName));
    }

    /**
     * Tests if Snake game names are returned correctly.
     */
    @Test
    public void testGetSnakeGameNames() {
        String gameName1 = "Game 1";
        Object[] gameSavedData1 = {1, 1, 2, 2, 1, 0, "Snake Easy Mode", SnakeView.Direction.RIGHT};
        String gameName2 = "Game 2";
        Object[] gameSavedData2 = {2, 2, 3, 3, 2, 0, "Snake Hard Mode", SnakeView.Direction.LEFT};
        String gameName3 = "Game 3";
        Object[] gameSavedData3 = {3, 3, 4, 4, 3, 0, "Snake Easy Mode", SnakeView.Direction.UP};
        tester.addSnakeGame(gameName1, gameSavedData1);
        tester.addSnakeGame(gameName2, gameSavedData2);
        tester.addSnakeGame(gameName3, gameSavedData3);
        Set<String> gameNames = new HashSet<>(Arrays.asList("Game 1", "Game 2", "Game 3"));
        assertEquals(gameNames, tester.getSnakeGameNames());
    }
}