package fall2018.csc2017.GameCenter.GameCenter.blocks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GridTest {
    /**
     * The int representing an empty location on the grid.
     */
    final static int EMPTY = 0;

    /**
     * The int representing the player's location on the grid.
     */
    final static int PLAYER = 1;

    /**
     * The int representing a block's location on the grid.
     */
    final static int BLOCK = 2;

    private int[] blockXs = {};
    private int[] blockYs = {};
    private int[] foodXs = {1,7,1,7};
    private int[] foodYs = {1,1,7,7};
    private Grid testerGrid =  new Grid(4,4,blockXs,blockYs,foodXs,foodYs);

    /**
     * Sets up a new grid where the player starts in the middle, with 1 food object
     * in each corner of the grid.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        testerGrid = new Grid(4,4,blockXs,blockYs,foodXs,foodYs);
    }

    /**
     * Tries to place a block on a food object. Should not affect the grid
     * as this is in illegal move. Before and after grid should be the same
     */
    @Test
    public void placeBlockAtFood() {
        Grid beforeGrid = new Grid(4,4,blockXs,blockYs,foodXs,foodYs);
        testerGrid.placeBlockAt(1, 1);
        assertEquals(beforeGrid.gridState[1][1], testerGrid.gridState[1][1]);

    }

    /**
     * Tries to place a block on an empty square. Should return a new grid with
     * this new block placement
     */
    @Test
    public void placeBlockAtEmpty() {
        Grid beforeGrid = new Grid(4,4,blockXs,blockYs,foodXs,foodYs);
        testerGrid.placeBlockAt(1, 2);
        assertNotEquals(beforeGrid.gridState[1][2], testerGrid.gridState[1][2]);

    }

    /**
     * Makes a vertical move upwards on the player from when the player starts at (4,4) and
     * there is no food overhead. Player should thus end up at (4,1), where he collides
     * with the wall
     */
    @Test
    public void verticalMoveUp() {
        testerGrid.verticalMove(-1, true);
        assertEquals(PLAYER, testerGrid.gridState[4][1]);
    }

    /**
     * Makes a vertical move downwards on the player from when the player starts at (4,4) and
     * there is no food overhead. Player should thus end up at (4,7), where he collides
     * with the wall
     */
    @Test
    public void verticalMoveDown() {
        testerGrid.verticalMove(1, true);
        assertEquals(PLAYER, testerGrid.gridState[4][7]);
    }
    /**
     * Makes a vertical move downwards on the player from when the player starts at (4,4) and
     * there is no food overhead. Player should thus end up at (7,4), where he collides
     * with the wall
     */
    @Test
    public void horizontalMoveRight() {
        testerGrid.horizontalMove(1, true);
        assertEquals(PLAYER, testerGrid.gridState[7][4]);
    }

    /**
     * Makes a vertical move downwards on the player from when the player starts at (4,4) and
     * there is no food overhead. Player should thus end up at (1,4), where he collides
     * with the wall
     */
    @Test
    public void horizontalMoveLeft() {
        testerGrid.horizontalMove(-1, true);
        assertEquals(PLAYER, testerGrid.gridState[1][4]);
    }

    /**
     * Gets the array of non-border block x values on the grid. Should be empty as we haven't placed
     * any blocks
     */
    @Test
    public void getBlockXsIntArray() {

        assertEquals(0, testerGrid.getBlockXsIntArray().length);
    }
    /**
     * Gets the array of non-border block y values on the grid. Should be empty as we haven't placed
     * any blocks
     */
    @Test
    public void getBlockYsIntArray() {
        assertEquals(0, testerGrid.getBlockYsIntArray().length);

    }

    /**
     * Gets the array of non-border block x values on the grid. Should be the same as our
     * initialized foodXs
     */
    @Test
    public void getFoodXsIntArray() {
        assert(Arrays.equals(foodXs, testerGrid.getFoodXsIntArray()));

    }

    /**
     * Gets the array of non-border block y values on the grid. Should be the same as our
     * initialized foodYs
     */
    @Test
    public void getFoodYsIntArray() {
        assert(Arrays.equals(foodYs, testerGrid.getFoodYsIntArray()));

    }

    /**
     * Gets the x-coordinate of the player. Should be 4 as that's what we initialized the player to
     * be
     */
    @Test
    public void getPlayerX() {
        assertEquals(4, testerGrid.getPlayerX());
    }

    /**
     * Gets the x-coordinate of the player. Should be 4 as that's what we initialized the player to
     * be
     */
    @Test
    public void getPlayerY() {
        assertEquals(4, testerGrid.getPlayerY());

    }

    /**
     * Gets the current score of the player. Should be 0 as the game starts at 0
     */
    @Test
    public void getScore() {
        assertEquals(0,testerGrid.getScore() );
    }

    /**
     * Tests if two of the same grid return equal for our equals method
     */
    @Test
    public void equalsCorrect(){
        Grid test = new Grid();
        Grid test2 = test;
        assert(test.equals(test2));
    }

    /**
     * Tests if food is eaten correctly
     */
    @Test
    public void foodIsEaten(){
        testerGrid.verticalMove(-1, true);
        testerGrid.horizontalMove(-1, true);
        assertEquals(PLAYER, testerGrid.gridState[1][1]);
        testerGrid.verticalMove(1, true);
        assertEquals(PLAYER, testerGrid.gridState[1][7]);
    }

}