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

    @Test
    public void testGetSavedGridsInitial() {
        Grid initialGrid = testGridManager.getGrid();
        ArrayList<Grid> initialGridList = new ArrayList<>();
        initialGridList.add(initialGrid);
        assertEquals(initialGridList, testGridManager.getSavedGrids());
    }

    @Test
    public void testSetGetGrid() {
        int[] blockXs = {1};
        int[] blockYs = {1};
        int[] foodXs = {2, 4, 5, 6};
        int[] foodYs = {2, 4, 5, 6};
        Grid newGrid = new Grid(3, 3, blockXs, blockYs, foodXs, foodYs);
        testGridManager.setGrid(newGrid);
        assertEquals(newGrid, testGridManager.getGrid());
    }

    @Test
    public void testGameOver() {
        int[] blockXs = {1, 2};
        int[] blockYs = {2, 1};
        int[] foodXs = {2, 3, 4, 5};
        int[] foodYs = {2, 3, 4, 5};
        Grid gameOverGrid = new Grid(1, 1, blockXs, blockYs, foodXs, foodYs);
        testGridManager.setGrid(gameOverGrid);
        assertTrue(testGridManager.gameOver());
    }

    @Test
    public void testPlaceBlock() {
        int[] blockXs = {1};
        int[] blockYs = {2};
        int[] foodXs = {5, 6, 7, 8};
        int[] foodYs = {5, 6, 7, 8};
        Grid baseGrid = new Grid(3, 3, blockXs, blockYs, foodXs, foodYs);
        testGridManager.setGrid(baseGrid);
        testGridManager.placeBlock(5, 5);
        testGridManager.placeBlock(2, 1);
        int[] newBlockXs = {1, 2};
        int[] newBlockYs = {2, 1};
        assertTrue((Arrays.equals(testGridManager.getGrid().getBlockXsIntArray(), newBlockXs))
                && Arrays.equals(testGridManager.getGrid().getBlockYsIntArray(), newBlockYs));
    }

    @Test
    public void testUndo() {
    }

    @Test
    public void testMovePlayer() {
    }
}