package fall2018.csc2017.GameCenter.GameCenter.blocks;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GridManagerTest {

    private GridManager testGridManager;

    /**
     * Sets up a test grid manager.
     */
    @Before
    public void setUp() {
        testGridManager = new GridManager();
    }

    /**
     * Tests if get saved grids consisting of initial grid returns correct list.
     */
    @Test
    public void testGetSavedGridsInitial() {
        Grid initialGrid = testGridManager.getGrid();
        ArrayList<Grid> initialGridList = new ArrayList<>();
        initialGridList.add(initialGrid);
        assertEquals(initialGridList, testGridManager.getSavedGrids());
    }

    /**
     * Tests if setting a new grid works, returns grid when calling get grid.
     */
    @Test
    public void testSetGetGrid() {
        int[] blockXs = {1};
        int[] blockYs = {1};
        int[] foodXs = {2, 4, 5, 6};
        int[] foodYs = {2, 4, 5, 6};
        Grid newGrid = new Grid(3, 3, blockXs, blockYs, foodXs, foodYs, 0);
        testGridManager.setGrid(newGrid);
        assertEquals(newGrid, testGridManager.getGrid());
    }

    /**
     * Tests if game over returns true when player is immediately surrounded by blocks.
     */
    @Test
    public void testGameOverTrue() {
        int[] blockXs = {1, 2};
        int[] blockYs = {2, 1};
        int[] foodXs = {2, 3, 4, 5};
        int[] foodYs = {2, 3, 4, 5};
        Grid gameOverGrid = new Grid(1, 1, blockXs, blockYs, foodXs, foodYs, 0);
        testGridManager.setGrid(gameOverGrid);
        assertTrue(testGridManager.gameOver());
    }

    /**
     * Tests if game over returns false when player is able to move.
     */
    @Test
    public void testGameOverFalse() {
        int[] blockXs = {};
        int[] blockYs = {};
        int[] foodXs = {2, 3, 4, 5};
        int[] foodYs = {2, 3, 4, 5};
        Grid notGameOverGrid = new Grid(1, 1, blockXs, blockYs, foodXs, foodYs, 0);
        testGridManager.setGrid(notGameOverGrid);
        assertFalse(testGridManager.gameOver());
    }

    /**
     * Tests if placing a block is reflected in the new grid's arrays of block x and y positions.
     */
    @Test
    public void testPlaceBlock() {
        int[] blockXs = {1};
        int[] blockYs = {2};
        int[] foodXs = {5, 6, 7, 8};
        int[] foodYs = {5, 6, 7, 8};
        Grid baseGrid = new Grid(3, 3, blockXs, blockYs, foodXs, foodYs, 0);
        testGridManager.setGrid(baseGrid);
        testGridManager.placeBlock(5, 5);
        testGridManager.placeBlock(2, 1);
        int[] newBlockXs = {1, 2};
        int[] newBlockYs = {2, 1};
        assertTrue((Arrays.equals(testGridManager.getGrid().getBlockXsIntArray(), newBlockXs))
                && Arrays.equals(testGridManager.getGrid().getBlockYsIntArray(), newBlockYs));
    }

    /**
     * Tests if undoing one move results in the correct state.
     */
    @Test
    public void testUndoOneMove() {
        ArrayList<Grid> testerSavedGrids = new ArrayList<>();
        Grid initialGrid = testGridManager.getGrid();
        initialGrid.setScore(-1);
        testerSavedGrids.add(initialGrid);
        int[] blockXs = {};
        int[] blockYs = {};
        int[] foodXs = {5, 6, 7, 8};
        int[] foodYs = {5, 6, 7, 8};
        Grid oneMove = new Grid(3, 3, blockXs, blockYs, foodXs, foodYs, 0);
        testGridManager.setGrid(oneMove);
        testGridManager.addToSavedGrids();
        testGridManager.undo(1);
        assertTrue((testerSavedGrids.equals(testGridManager.getSavedGrids())) &&
                (initialGrid.equals(testGridManager.getGrid())));
    }

    /**
     * Tests if undoing multiple moves results in the correct state.
     */
    @Test
    public void testUndoMultipleMoves() {
        ArrayList<Grid> testerSavedGrids = new ArrayList<>();
        testerSavedGrids.add(testGridManager.getGrid());
        int[] blockXs1 = {};
        int[] blockYs1 = {};
        int[] foodXs = {5, 6, 7, 8};
        int[] foodYs = {5, 6, 7, 8};
        Grid oneMove = new Grid(3, 3, blockXs1, blockYs1, foodXs, foodYs, 0);
        testGridManager.setGrid(oneMove);
        testGridManager.addToSavedGrids();
        int[] blockXs2 = {2};
        int[] blockYs2 = {2};
        Grid twoMove = new Grid(3, 3, blockXs2, blockYs2, foodXs, foodYs, 0);
        testGridManager.setGrid(twoMove);
        testGridManager.addToSavedGrids();
        Grid threeMove = new Grid(3, 1, blockXs2, blockYs2, foodXs, foodYs, 0);
        testGridManager.setGrid(threeMove);
        testGridManager.addToSavedGrids();
        Grid fourMove = new Grid(1, 1, blockXs2, blockYs2, foodXs, foodYs, 0);
        testGridManager.setGrid(fourMove);
        testGridManager.addToSavedGrids();
        testGridManager.undo(3);
        Grid tester = new Grid(3, 3, blockXs1, blockYs1, foodXs, foodYs, -5);
        testerSavedGrids.add(tester);
        assertTrue((testerSavedGrids.equals(testGridManager.getSavedGrids())) &&
                (tester.equals(testGridManager.getGrid())));
    }

    /**
     * Tests a successful move if move is added to saved grids.
     */
    @Test
    public void testMovePlayerSuccess() {
    }

    /**
     * Tests a failed move if move is not added to saved grids.
     */
    @Test
    public void testMovePlayerFailed() {
    }
}