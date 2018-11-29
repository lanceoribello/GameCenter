package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.graphics.Point;
import android.view.Display;

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
                SnakeController.Direction.RIGHT, fps, 5, 5};
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
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        assertTrue(snakeController.detectDeath());
    }
    /**
     * Test that a snake dies upon hitting the side wall
     */
    @Test
    public void testDetectDeathByHittingBottomWall() {
        int[] snakeXs = {0};
        int[] snakeYs = {numBlocksHigh + 1};
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 5, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
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
     * Test that a snake doesn't die while playing
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
    @Test
    public void testEatApple(){
        int[] snakeXs = {1};
        int[] snakeYs = {2};
        Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, "Snake Easy Mode",
                SnakeController.Direction.RIGHT, fps, 5, 5};
        snakeController = new SnakeController(difficulty, savedData, numBlocksHigh);
        snakeController.updateGame();
        snakeController.updateGame();
        assertTrue(snakeController.getSnakeLength() > 1);
    }

    @Test
    public void createSaveData() {
    }

    @Test
    public void getAutoSaveData() {
    }

    @Test
    public void getSavePoint() {
    }

    @Test
    public void updateGame() {
    }

    @Test
    public void getScore() {
    }

    @Test
    public void getAppleX() {
    }

    @Test
    public void getAppleY() {
    }

    @Test
    public void getBombX() {
    }

    @Test
    public void getBombY() {
    }

    @Test
    public void getSnakeXs() {
    }

    @Test
    public void getSnakeYs() {
    }

    @Test
    public void getSnakeLength() {
    }

    @Test
    public void getDirection() {
    }

    @Test
    public void setDirection() {
    }

    @Test
    public void getFps() {
    }

    @Test
    public void setAutoSaveData() {
    }
}