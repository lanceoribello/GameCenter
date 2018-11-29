package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.graphics.Point;
import android.view.Display;

import net.bytebuddy.dynamic.loading.ClassInjector;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnakeControllerTest {
    /**
     * An instance of a snake controller
     */
    private SnakeController snakeController;

    /**
     * The frames per second of the current Snake game.
     */
    private long fps = 10;

    /**
     *private String difficulty;
     */
    private String difficulty = "Snake Easy Mode";
    /**
     * Avialable playing area for the snake.
     */
    private int numBlocksHigh = 22;

    /**
     * Test that a snake dies upon hitting the side wall
     */
    @Test
    public void testDetectDeathByHittingLeftWall() {
        int[] snakeXs = {-1};
        int[] snakeYs = {0};
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                SnakeController.Direction.LEFT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }
    /**
     * Test that a snake dies upon hitting the side wall
     */
     @Test
    public void testDetectDeathByHittingRightWall() {
         int[] snakeXs = {22};
         int[] snakeYs = {0};
         Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                 SnakeController.Direction.RIGHT, fps, 5, 5};
         snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }
    /**
     * Test that a snake dies upon hitting the side wall
     */
    @Test
    public void testDetectDeathByHittingTopWall() {
       int[] snakeXs = {0};
        int[] snakeYs = {-1};
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                SnakeController.Direction.UP, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }
    /**
     * Test that a snake dies upon hitting the side wall
     */
    @Test
    public void testDetectDeathByHittingBottomWall() {
        int[] snakeXs = {0};
        int[] snakeYs = {numBlocksHigh};
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                SnakeController.Direction.DOWN, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }
    /**
     * Test that a snake dies upon hitting the side wall
     */
    @Test
    public void testDetectDeathByBomb() {
        int[] snakeXs = {0};
        int[] snakeYs = {0};
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeXs[0] = snakeController.getBombX();
        snakeYs[0] = snakeController.getBombY();
        Object[] savedData1 = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData1, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }
    /**
     * Test that a snake dies upon hitting the side wall
     */
    @Test
    public void testDetectDeathByCannibalism() {
        int[] snakeXs = {5, 1, 2, 3, 5};
        int[] snakeYs = {5, 1, 2, 3, 5};
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }
    /**
     * Test that a snake doesn't die while playing in non-lethal conditions.
     */
    @Test
    public void testDetectDeathFalse() {
        int[] snakeXs = {0, 1, 2, 3, 5};
        int[] snakeYs = {0, 1, 2, 3, 5};
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertFalse(snakeController.detectDeath());
    }

    /**
     * Test that score and snake length increase by 1 when snake eats an apple.
     */
    @Test
    public void testEatApple(){
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 2;
        snakeYs[0] = 2;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, "Snake Easy Mode",
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
    public void testIncreaseDifficulty(){
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 2;
        snakeYs[0] = 2;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        updateGame();
        assertTrue(snakeController.getSnakeLength() == 1);
    }

    /**
     * Test that the snake moves as desired in a given direction, in this test, right.
     */
    @Test
    public void testMoveSnake(){
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertTrue(snakeController.getSnakeXs()[0] == 1 &&
                snakeController.getSnakeYs()[0] == 0);
    }

    /**
     * Test if a save point is made every three moves.
     */
    @Test
    public void testCreateSavePoint(){
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 4;
        snakeXs[1] = 3;
        snakeYs[0] = 0;
        snakeYs[1] = 0;
        Object[] savedData = {snakeXs, snakeYs, 4, 0, 1, 2, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 6, 6};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        assertNotEquals(null,snakeController.getSavePoint());
    }

    /**
     * test that savedData is created with the right data.
     */

    @Test
    public void createSaveData() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertArrayEquals(savedData, snakeController.createSaveData());

    }

    /**
     * Test that the right AutoSaveData is returned.
     */
    @Test
    public void getAutoSaveData() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.setAutoSaveData(savedData);
        assertArrayEquals(savedData, snakeController.getAutoSaveData());
    }



    @Test
    public void updateGame() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        int[] snakeXs1 = new int[15];
        int[] snakeYs1 = new int[15];
        snakeXs1[0] = 1;
        snakeYs1[0] = 0;
        Object[] expected = {snakeXs1, snakeYs1, 2, 2, 15,1, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        Object[] got = {snakeController.getSnakeXs(), snakeController.getSnakeYs(),
                snakeController.getAppleX(), snakeController.getAppleY(), snakeController.getScore(),
                snakeController.getSnakeLength(), "Snake Easy Mode",
                snakeController.getDirection(), snakeController.getFps(),
                snakeController.getBombX(), snakeController.getBombY()} ;
        assertArrayEquals(expected, got);

    }

    @Test
    public void getScore() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 15, 1, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(1, snakeController.getScore());
    }

    @Test
    public void getAppleX() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(2, snakeController.getAppleX());
    }

    @Test
    public void getAppleY() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(2, snakeController.getAppleY());
    }

    @Test
    public void getBombX() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(5, snakeController.getBombX());
    }

    @Test
    public void getBombY() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(5, snakeController.getBombY());
    }

    @Test
    public void getSnakeXs() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(snakeXs, snakeController.getSnakeXs());
    }

    @Test
    public void getSnakeYs() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(snakeYs, snakeController.getSnakeYs());
    }

    @Test
    public void getSnakeLength() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(1, snakeController.getSnakeLength());
    }

    @Test
    /**
     * test that a correct direction is outputted.
     */
    public void getDirection() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(SnakeController.Direction.RIGHT, snakeController.getDirection());
    }

    @Test
    public void setDirection() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.setDirection(SnakeController.Direction.DOWN);
        assertEquals(SnakeController.Direction.DOWN,snakeController.getDirection());
    }

    /**
     * test that the correct fps is outputted when getFps is called.
     */
    @Test
    public void testGetFps() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 0;
        snakeYs[0] = 0;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertEquals(fps, snakeController.getFps());
    }

    /**
     * test that setAutoSaveData sets the correct data. The getter has already been tested
     */
    @Test
    public void testSetAutoSaveData() {
        int[] snakeXs = new int[15];
        int[] snakeYs = new int[15];
        snakeXs[0] = 1;
        snakeYs[0] = 1;
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 15, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.setAutoSaveData(savedData);
        assertArrayEquals(savedData, snakeController.getAutoSaveData());

    }
}