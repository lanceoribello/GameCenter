package fall2018.csc2017.GameCenter.GameCenter.lobby;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import difflib.myers.Snake;
import fall2018.csc2017.GameCenter.GameCenter.blocks.GridManager;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.BoardManager;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeController;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeView;

import static org.junit.Assert.*;

public class UserAccountTest {

    /**
     * The user account for testing.
     */
    private UserAccount tester = new UserAccount("nancyishaneelance", "207");

    /**
     * Drawable tile ID's for 3x3 board.
     */
    private Integer[] tileIds3x3 = {2131099773, 2131099784, 2131099790,
            2131099791, 2131099792, 2131099793,
            2131099794, 2131099795, 2131099796};

    /**
     * Drawable tile ID's for 3x3 board.
     */
    private Integer[] tileIds4x4 = {2131099773, 2131099784, 2131099790, 2131099791,
            2131099792, 2131099793, 2131099794, 2131099795,
            2131099796, 2131099774, 2131099775, 2131099776,
            2131099777, 2131099778, 2131099779, 2131099780};

    /**
     * Drawable tile ID's for 3x3 board.
     */
    private Integer[] tileIds5x5 = {2131099773, 2131099784, 2131099790, 2131099791, 2131099792,
            2131099793, 2131099794, 2131099795, 2131099796, 2131099774,
            2131099775, 2131099776, 2131099777, 2131099778, 2131099779,
            2131099780, 2131099781, 2131099782, 2131099783, 2131099785,
            2131099786, 2131099787, 2131099788, 2131099789, 0};

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
        ArrayList<Integer> gameTileIds = new ArrayList<>(Arrays.asList(this.tileIds3x3));
        BoardManager gameBoardManager = new
                BoardManager(3, gameTileIds);
        tester.addSlidingTilesGame(gameName, gameBoardManager);
        assertEquals(gameBoardManager, tester.getSlidingTilesGame(gameName));
    }

    /**
     * Tests if Sliding Tiles game names are returned correctly.
     */
    @Test
    public void testGetSlidingTilesGameNames() {
        String gameName1 = "Game 1";
        ArrayList<Integer> gameTileIds3x3 = new ArrayList<>(Arrays.asList(this.tileIds3x3));
        BoardManager gameBoardManager1 = new BoardManager(3, gameTileIds3x3);
        String gameName2 = "Game 2";
        ArrayList<Integer> gameTileIds4x4 = new ArrayList<>(Arrays.asList(this.tileIds4x4));
        BoardManager gameBoardManager2 = new BoardManager(4, gameTileIds4x4);
        String gameName3 = "Game 3";
        ArrayList<Integer> gameTileIds5x5 = new ArrayList<>(Arrays.asList(this.tileIds5x5));
        BoardManager gameBoardManager3 = new BoardManager(5, gameTileIds5x5);
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
        int[] snakeXs = {0};
        int[] snakeYs = {0};
        Object[] gameSavedData = {snakeXs, snakeYs, 2, 2, 1, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT};
        tester.addSnakeGame(gameName, gameSavedData);
        assertArrayEquals(gameSavedData, tester.getSnakeGame(gameName));
    }

    /**
     * Tests if Snake game names are returned correctly.
     * Snake saved data consists of: {snakeXs, snakeYs, mouseX, mouseY, snakeLength, score,
     * difficulty, direction, FPS, bombX, bombY}
     */
    @Test
    public void testGetSnakeGameNames() {
        int[] snakeXs = {0};
        int[] snakeYs = {0};
        String gameName1 = "Game 1";
        Object[] gameSavedData1 = {snakeXs, snakeYs, 2, 2, 1, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, 10, 5, 5};
        String gameName2 = "Game 2";
        Object[] gameSavedData2 = {snakeXs, snakeYs, 3, 3, 1, 0, "Snake Hard Mode",
                SnakeController.Direction.LEFT, 14, 5, 5};
        String gameName3 = "Game 3";
        Object[] gameSavedData3 = {snakeXs, snakeYs, 4, 4, 1, 0, "Snake Easy Mode",
                SnakeController.Direction.UP, 10, 5, 5};
        tester.addSnakeGame(gameName1, gameSavedData1);
        tester.addSnakeGame(gameName2, gameSavedData2);
        tester.addSnakeGame(gameName3, gameSavedData3);
        Set<String> gameNames = new HashSet<>(Arrays.asList("Game 1", "Game 2", "Game 3"));
        assertEquals(gameNames, tester.getSnakeGameNames());
    }

    /**
     * Tests if Blocks game is added and returned correctly.
     */
    @Test
    public void testAddGetBlocksGame() {
        String gameName = "Saved Game";
        GridManager gameGridManager = new GridManager();
        tester.addBlocksGame(gameName, gameGridManager);
        assertEquals(gameGridManager, tester.getBlocksGame(gameName));
    }

    /**
     * Tests if Blocks game names are returned correctly.
     */
    @Test
    public void testGetBlocksGameNames() {
        String gameName1 = "Game 1";
        GridManager gameGridManager1 = new GridManager();
        String gameName2 = "Game 2";
        GridManager gameGridManager2 = new GridManager();
        String gameName3 = "Game 3";
        GridManager gameGridManager3 = new GridManager();
        tester.addBlocksGame(gameName1, gameGridManager1);
        tester.addBlocksGame(gameName2, gameGridManager2);
        tester.addBlocksGame(gameName3, gameGridManager3);
        Set<String> gameNames = new HashSet<>(Arrays.asList("Game 1", "Game 2", "Game 3"));
        assertEquals(gameNames, tester.getBlocksGameNames());
    }

    /**
     * Tests if auto saved Snake game is overwritten correctly.
     */
    @Test
    public void testReplaceAutoSavedSlidingTilesGame() {
        String autoSavedGameName = "autoSave";
        ArrayList<Integer> gameTileIds3x3 = new ArrayList<>(Arrays.asList(this.tileIds3x3));
        BoardManager autoSave1 = new BoardManager(3, gameTileIds3x3);
        tester.addSlidingTilesGame(autoSavedGameName, autoSave1);
        ArrayList<Integer> gameTileIds4x4 = new ArrayList<>(Arrays.asList(this.tileIds4x4));
        BoardManager autoSave2 = new BoardManager(4, gameTileIds4x4);
        tester.addSlidingTilesGame(autoSavedGameName, autoSave2);
        assertEquals(autoSave2, tester.getSlidingTilesGame(autoSavedGameName));
    }

    /**
     * Tests if auto saved Sliding Tiles game is overwritten correctly.
     */
    @Test
    public void testReplaceAutoSavedSnakeGame() {
        int[] snakeXs = {0};
        int[] snakeYs = {0};
        String autoSavedGameName = "autoSave";
        Object[] autoSave1 = {snakeXs, snakeYs, 2, 2, 1, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, 10, 5, 5};
        tester.addSnakeGame(autoSavedGameName, autoSave1);
        Object[] autoSave2 = {snakeXs, snakeYs, 3, 3, 1, 0, "Snake Hard Mode",
                SnakeController.Direction.LEFT, 14, 5, 5};
        tester.addSnakeGame(autoSavedGameName, autoSave2);
        assertArrayEquals(autoSave2, tester.getSnakeGame(autoSavedGameName));
    }

    /**
     * Tests if auto saved Blocks game is overwritten correctly.
     */
    @Test
    public void testReplaceAutoSavedBlocksGame() {
        String autoSavedGameName = "autoSave";
        GridManager autoSave1 = new GridManager();
        tester.addBlocksGame(autoSavedGameName, autoSave1);
        GridManager autoSave2 = new GridManager();
        tester.addBlocksGame(autoSavedGameName, autoSave2);
        assertEquals(autoSave2, tester.getBlocksGame(autoSavedGameName));
    }

    /**
     * Tests if get score returns null for game level that does not exist.
     */
    @Test
    public void testGetScoreNull() {
        assertNull(tester.getTopScore("Scrabble"));
    }

    /**
     * Tests if get Sliding Tiles game returns null for game name that does not exist.
     */
    @Test
    public void testGetSlidingTilesGameNull() {
        assertNull(tester.getSlidingTilesGame("Not there"));
    }

    /**
     * Tests if get Snake game returns null for game name that does not exist.
     */
    @Test
    public void testGetSnakeGameNull() {
        assertNull(tester.getSnakeGame("Not there"));
    }

    /**
     * Tests if get Blocks game returns null for game name that does not exist.
     */
    @Test
    public void testGetBlocksGameNull() {
        assertNull(tester.getBlocksGame("Not there"));
    }

    /**
     * Tests if equals returns true on two identical user accounts.
     */
    @Test
    public void testEqualsTrue() {
        UserAccount testerTwin = new UserAccount("nancyishaneelance", "207");
        String gameLevel = "Sliding Tiles 3x3";
        Integer topScore = 42;
        tester.setTopScore(gameLevel, topScore);
        testerTwin.setTopScore(gameLevel, topScore);
        String gameName = "Blocks Game";
        GridManager gameGridManager = new GridManager();
        tester.addBlocksGame(gameName, gameGridManager);
        testerTwin.addBlocksGame(gameName, gameGridManager);
        boolean isEqual = testerTwin.equals(tester);
        assertTrue(isEqual);
    }

    /**
     * Tests if equals returns false on two different user accounts.
     */
    @Test
    public void testEqualsFalseDifferentUserAccount() {
        UserAccount differentTester = new UserAccount("someone else", "password");
        tester.setTopScore("Sliding Tiles 4x4", 123);
        differentTester.setTopScore("Snake Easy Mode", 5);
        String blocksGameName = "Blocks Game";
        GridManager gameGridManager = new GridManager();
        tester.addBlocksGame(blocksGameName, gameGridManager);
        String snakeGameName = "Snake Game";
        int[] snakeXs = {0};
        int[] snakeYs = {0};
        Object[] gameSavedData = {snakeXs, snakeYs, 2, 2, 1, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT};
        differentTester.addSnakeGame(snakeGameName, gameSavedData);
        assertNotEquals(differentTester, tester);
    }

    /**
     * Tests if equals returns false on two different objects.
     */
    @Test
    public void testEqualsFalseDifferentObjects() {
        assertNotEquals("Hello", tester);
    }

    /**
     * Tests if equals returns false for comparing to null.
     */
    @Test
    public void testEqualsFalseNull() {
        assertNotEquals(null, tester);
    }
}