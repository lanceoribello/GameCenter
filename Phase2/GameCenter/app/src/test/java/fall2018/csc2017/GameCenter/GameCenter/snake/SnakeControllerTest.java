package fall2018.csc2017.GameCenter.GameCenter.snake;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Class for SnakeController.
 */
public class SnakeControllerTest {

    /**
     * An instance of a snake controller.
     */
    private SnakeController snakeController;

    /**
     * The frames per second of the current Snake game.
     */
    private long fps = 7;

    /**
     * Game level name for easy mode.
     */
    private String difficulty = "Snake Easy Mode";

    /**
     * Available playing area for the snake.
     */
    private int numBlocksHigh = 22;

    /**
     * Test that creating a Snake Controller with null save data starts a new game with new game
     * values for: snakeXs, snakeYs, snakeLength, score.
     */
    @Test
    public void testSnakeControllerNullData() {
        snakeController = new SnakeController(difficulty, null, numBlocksHigh);
        int[] snakeXs = snakeController.getSnakeXs();
        int[] snakeYs = snakeController.getSnakeYs();
        int snakeLength = snakeController.getSnakeLength();
        int score = snakeController.getScore();
        assertEquals(4, snakeXs[0]);
        assertEquals(11, snakeYs[0]);
        assertEquals(1, snakeLength);
        assertEquals(0, score);
    }

    /**
     * Test that setting easy difficulty works correctly.
     */
    @Test
    public void testSetDifficultyEasy() {
        snakeController = new SnakeController(difficulty, null, numBlocksHigh);
        assertEquals(fps, snakeController.getFps());
    }

    /**
     * Test that setting hard difficulty works correctly.
     */
    @Test
    public void testSetDifficultyHard() {
        SnakeController snakeControllerHard = new SnakeController("Snake Hard Mode",
                null, numBlocksHigh);
        assertEquals(10, snakeControllerHard.getFps());
    }

    /**
     * Test that a snake dies upon hitting the left wall.
     */
    @Test
    public void testDetectDeathByHittingLeftWall() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = -1;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, difficulty,
                SnakeController.Direction.LEFT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }

    /**
     * Test that a snake dies upon hitting the right wall.
     */
    @Test
    public void testDetectDeathByHittingRightWall() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 22;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }

    /**
     * Test that a snake dies upon hitting the top wall.
     */
    @Test
    public void testDetectDeathByHittingTopWall() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = -1;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, difficulty,
                SnakeController.Direction.UP, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }

    /**
     * Test that a snake dies upon hitting the bottom wall.
     */
    @Test
    public void testDetectDeathByHittingBottomWall() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = numBlocksHigh;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, difficulty,
                SnakeController.Direction.DOWN, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }

    /**
     * Test that a snake dies upon hitting the bomb.
     */
    @Test
    public void testDetectDeathByBomb() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeXs[0] = snakeController.getBombX();
        snakeYs[0] = snakeController.getBombY();
        Object[] savedData1 = {snakeXs, snakeYs, 2, 2, 1, 0, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData1, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }

    /**
     * Test that a snake dies upon hitting itself.
     */
    @Test
    public void testDetectDeathByCannibalism() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 1;
        snakeXs[1] = 0;
        snakeXs[2] = 0;
        snakeXs[3] = 1;
        snakeXs[4] = 1;
        snakeYs[0] = 1;
        snakeYs[1] = 1;
        snakeYs[2] = 0;
        snakeYs[3] = 0;
        snakeYs[4] = 1;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 6, 0, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }

    /**
     * Test that a snake doesn't die while playing in non-lethal conditions.
     */
    @Test
    public void testDetectDeathFalse() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeXs[1] = 0;
        snakeXs[2] = 0;
        snakeXs[3] = 0;
        snakeXs[4] = 0;
        snakeYs[0] = 5;
        snakeYs[1] = 4;
        snakeYs[2] = 3;
        snakeYs[3] = 2;
        snakeYs[4] = 1;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertFalse(snakeController.detectDeath());
    }

    /**
     * Test that score and snake length increase by 1 when snake eats an apple.
     */
    @Test
    public void testEatApple() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 2;
        snakeYs[0] = 2;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertTrue(snakeController.getSnakeLength() > 1 && snakeController.getScore() > 0);
    }

    /**
     * Test that snake resets its size after eating an apple when it was max snake length for that
     * fps speed.
     */
    @Test
    public void testIncreaseDifficultyMaxSnakeLength() {
        int[] snakeXs = new int[14];
        int[] snakeYs = new int[14];
        snakeXs[0] = 2;
        snakeYs[0] = 2;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 14, 14, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertEquals(1, snakeController.getSnakeLength());
    }

    /**
     * Test that the snake moves as desired in a given direction, in this test, left.
     */
    @Test
    public void testMoveSnakeLeft() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 3;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, difficulty,
                SnakeController.Direction.LEFT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertTrue(snakeController.getSnakeXs()[0] == 2 &&
                snakeController.getSnakeYs()[0] == 0);
    }

    /**
     * Test that the snake moves as desired in a given direction, in this test, right.
     */
    @Test
    public void testMoveSnakeRight() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertTrue(snakeController.getSnakeXs()[0] == 1 &&
                snakeController.getSnakeYs()[0] == 0);
    }

    /**
     * Test that the snake moves as desired in a given direction, in this test, up.
     */
    @Test
    public void testMoveSnakeUp() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 1;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, difficulty,
                SnakeController.Direction.UP, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertTrue(snakeController.getSnakeXs()[0] == 0 &&
                snakeController.getSnakeYs()[0] == 0);
    }

    /**
     * Test that the snake moves as desired in a given direction, in this test, down.
     */
    @Test
    public void testMoveSnakeDown() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, difficulty,
                SnakeController.Direction.DOWN, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertTrue(snakeController.getSnakeXs()[0] == 0 &&
                snakeController.getSnakeYs()[0] == 1);
    }

    /**
     * Test if a save point is made every three moves.
     */
    @Test
    public void testCreateSavePoint() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 4;
        snakeXs[1] = 3;
        snakeYs[0] = 0;
        snakeYs[1] = 0;
        Object[] savedData = {snakeXs, snakeYs, 4, 0, 1, 2, difficulty,
                SnakeController.Direction.RIGHT, fps, 6, 6};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertNotEquals(null, snakeController.getSavePoint());
    }

    /**
     * Test that savedData is created with the right data.
     */
    @Test
    public void testCreateSaveData() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertArrayEquals(savedData, snakeController.createSaveData());
    }

    /**
     * Test that the right AutoSaveData is returned.
     */
    @Test
    public void testGetAutoSaveData() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.setAutoSaveData(savedData);
        assertArrayEquals(savedData, snakeController.getAutoSaveData());
    }

    /**
     * Tests if game updates properly.
     */
    @Test
    public void testUpdateGame() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        int[] snakeXs1 = new int[15];
        int[] snakeYs1 = new int[15];
        snakeXs1[0] = 1;
        snakeYs1[0] = 0;
        Object[] expected = {snakeXs1, snakeYs1, 2, 2, 15, 1, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        Object[] got = {snakeController.getSnakeXs(), snakeController.getSnakeYs(),
                snakeController.getAppleX(), snakeController.getAppleY(), snakeController.getScore(),
                snakeController.getSnakeLength(), difficulty,
                snakeController.getDirection(), snakeController.getFps(),
                snakeController.getBombX(), snakeController.getBombY()};
        assertArrayEquals(expected, got);
    }

    /**
     * Tests if score is returned correctly.
     */
    @Test
    public void testGetScore() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 15, 1, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(1, snakeController.getScore());
    }

    /**
     * Tests if apple x coordinate is returned correctly.
     */
    @Test
    public void testGetAppleX() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(2, snakeController.getAppleX());
    }

    /**
     * Tests if apple y coordinate is returned correctly.
     */
    @Test
    public void testGetAppleY() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(2, snakeController.getAppleY());
    }

    /**
     * Tests if bomb x coordinate is returned correctly.
     */
    @Test
    public void testGetBombX() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(5, snakeController.getBombX());
    }

    /**
     * Tests if bomb y coordinate is returned correctly.
     */
    @Test
    public void testGetBombY() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(5, snakeController.getBombY());
    }

    /**
     * Tests if snake x coordinates are returned correctly.
     */
    @Test
    public void testGetSnakeXs() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(snakeXs, snakeController.getSnakeXs());
    }

    /**
     * Tests if snake y coordinates are returned correctly.
     */
    @Test
    public void testGetSnakeYs() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(snakeYs, snakeController.getSnakeYs());
    }

    /**
     * Tests if snake length is returned correctly.
     */
    @Test
    public void testGetSnakeLength() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(1, snakeController.getSnakeLength());
    }

    /**
     * Test that a correct direction is outputted.
     */
    @Test
    public void testGetDirection() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(SnakeController.Direction.RIGHT, snakeController.getDirection());
    }

    /**
     * Test that direction is set correctly.
     */
    @Test
    public void testSetDirection() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.setDirection(SnakeController.Direction.DOWN);
        assertEquals(SnakeController.Direction.DOWN, snakeController.getDirection());
    }

    /**
     * Test that the correct fps is outputted when getFps is called.
     */
    @Test
    public void testGetFps() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(fps, snakeController.getFps());
    }

    /**
     * Test that setAutoSaveData sets the correct data. The getter has already been tested
     */
    @Test
    public void testSetAutoSaveData() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 1;
        snakeYs[0] = 1;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, difficulty,
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.setAutoSaveData(savedData);
        assertArrayEquals(savedData, snakeController.getAutoSaveData());
    }
}