package fall2018.csc2017.GameCenter.GameCenter.blocks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void horizontalMoveRight() {

    }

    @Test
    public void getBlockXsIntArray() {
    }

    @Test
    public void getBlockYsIntArray() {
    }

    @Test
    public void getFoodXsIntArray() {
    }

    @Test
    public void getFoodYsIntArray() {
    }

    @Test
    public void getPlayerX() {
    }

    @Test
    public void getPlayerY() {
    }

    @Test
    public void getScore() {
    }
}