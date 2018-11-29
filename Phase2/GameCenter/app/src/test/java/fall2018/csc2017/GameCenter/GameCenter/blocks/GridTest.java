package fall2018.csc2017.GameCenter.GameCenter.blocks;

import org.junit.After;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test Class for Grid.
 */
public class GridTest {

    /**
     * The int representing the player's location on the grid.
     */
    private final static int PLAYER = 1;

    /**
     * The int representing a food's location on the grid.
     */
    private final static int FOOD = 3;

    /**
     * Values for grid.
     */
    private int[] blockXs = {5};
    private int[] blockYs = {5};
    private int[] foodXs = {1, 7, 1, 7};
    private int[] foodYs = {1, 1, 7, 7};

    /**
     * Grid to be tested.
     */
    private Grid testerGrid = new Grid(4, 4, blockXs, blockYs, foodXs, foodYs, 0);

    /**
     * Sets up a new grid where the player starts in the middle, with 1 food object
     * in each corner of the grid and one block at (5,5).
     */
    @After
    public void tearDown() {
        testerGrid = new Grid(4, 4, blockXs, blockYs, foodXs, foodYs, 0);
    }

    /**
     * Tries to place a block on a food object. Should not affect the grid
     * as this is in illegal move. Before and after grid should be the same.
     */
    @Test
    public void placeBlockAtFood() {
        Grid beforeGrid = new Grid(4, 4, blockXs, blockYs, foodXs, foodYs, 0);
        testerGrid.placeBlockAt(1, 1);
        assertEquals(beforeGrid.gridState[1][1], testerGrid.gridState[1][1]);
    }

    /**
     * Tries to place a block on an empty square. Should return a new grid with
     * this new block placement.
     */
    @Test
    public void placeBlockAtEmpty() {
        Grid beforeGrid = new Grid(4, 4, blockXs, blockYs, foodXs, foodYs, 0);
        testerGrid.placeBlockAt(1, 2);
        assertNotEquals(beforeGrid.gridState[1][2], testerGrid.gridState[1][2]);
    }

    /**
     * Makes a vertical move upwards on the player from when the player starts at (4,4) and
     * there is no food overhead. Player should thus end up at (4,1), where he collides
     * with the wall.
     */
    @Test
    public void verticalMoveUp() {
        testerGrid.verticalMove(-1, true);
        assertEquals(PLAYER, testerGrid.gridState[4][1]);
    }

    /**
     * Makes a vertical move downwards on the player from when the player starts at (4,4) and
     * there is no food overhead. Player should thus end up at (4,7), where he collides
     * with the wall.
     */
    @Test
    public void verticalMoveDown() {
        testerGrid.verticalMove(1, true);
        assertEquals(PLAYER, testerGrid.gridState[4][7]);
    }

    /**
     * Makes a vertical move downwards on the player from when the player starts at (4,4) and
     * there is no food overhead. Player should thus end up at (7,4), where he collides
     * with the wall.
     */
    @Test
    public void horizontalMoveRight() {
        testerGrid.horizontalMove(1, true);
        assertEquals(PLAYER, testerGrid.gridState[7][4]);
    }

    /**
     * Makes a vertical move downwards on the player from when the player starts at (4,4) and
     * there is no food overhead. Player should thus end up at (1,4), where he collides
     * with the wall.
     */
    @Test
    public void horizontalMoveLeft() {
        testerGrid.horizontalMove(-1, true);
        assertEquals(PLAYER, testerGrid.gridState[1][4]);
    }

    /**
     * Gets the array of non-border block y values on the grid. Should be the same as our
     * initialized blockXs.
     */
    @Test
    public void getBlockXsIntArray() {
        assert (Arrays.equals(blockXs, testerGrid.getBlockXsIntArray()));
    }

    /**
     * Gets the array of non-border block y values on the grid. Should be the same as our
     * initialized blockYs.
     */
    @Test
    public void getBlockYsIntArray() {
        assert (Arrays.equals(blockYs, testerGrid.getBlockYsIntArray()));
    }

    /**
     * Gets the array of non-border block x values on the grid. Should be the same as our
     * initialized foodXs.
     */
    @Test
    public void getFoodXsIntArray() {
        assert (Arrays.equals(foodXs, testerGrid.getFoodXsIntArray()));
    }

    /**
     * Gets the array of non-border block y values on the grid. Should be the same as our
     * initialized foodYs.
     */
    @Test
    public void getFoodYsIntArray() {
        assert (Arrays.equals(foodYs, testerGrid.getFoodYsIntArray()));
    }

    /**
     * Gets the x-coordinate of the player. Should be 4 as that's what we initialized the player to
     * be.
     */
    @Test
    public void getPlayerX() {
        assertEquals(4, testerGrid.getPlayerX());
    }

    /**
     * Gets the x-coordinate of the player. Should be 4 as that's what we initialized the player to
     * be.
     */
    @Test
    public void getPlayerY() {
        assertEquals(4, testerGrid.getPlayerY());
    }

    /**
     * Gets and Sets the current score of the player. Should be 0 as the game starts at 0.
     */
    @Test
    public void getSetScore() {
        testerGrid.setScore(1);
        assertEquals(1, testerGrid.getScore());
    }

    /**
     * Tests if a hard copy of a test grid returns true if the equals method is applied between them
     */
    @Test
    public void equalsCorrect() {
        Grid test = new Grid();
        int pX = test.getPlayerX();
        int pY = test.getPlayerY();
        int[] blockXs = test.getBlockXsIntArray();
        int[] blockYs = test.getBlockYsIntArray();
        int[] foodXs = test.getFoodXsIntArray();
        int[] foodYs = test.getFoodYsIntArray();
        int score = test.getScore();
        Grid testCopy = new Grid(pX, pY, blockXs, blockYs, foodXs, foodYs, score);
        assert (test.equals(testCopy));
    }

    /**
     * Tests if food is eaten correctly. First makes sure that (1,1) and (1,7) are foods
     * previous to moves being made. Then the player gets moved to those locations, and checks
     * to make sure that those grids are now the Player instead of food, confirming that the food
     * has been eaten.
     */
    @Test
    public void foodIsEaten() {
        assertEquals(FOOD, testerGrid.gridState[1][1]);
        assertEquals(FOOD, testerGrid.gridState[1][7]);
        testerGrid.verticalMove(-1, true);
        testerGrid.horizontalMove(-1, true);
        assertEquals(PLAYER, testerGrid.gridState[1][1]);
        testerGrid.verticalMove(1, true);
        assertEquals(PLAYER, testerGrid.gridState[1][7]);
    }
}