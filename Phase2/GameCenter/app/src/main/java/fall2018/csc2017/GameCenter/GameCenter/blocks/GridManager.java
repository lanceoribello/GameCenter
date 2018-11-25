package fall2018.csc2017.GameCenter.GameCenter.blocks;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.Board;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.Tile;

/**
 * Manage a grid.
 */
public class GridManager {

    /**
     * The grid being managed.
     */
    private Grid grid;

    /**
     * The number of moves played so far in the current instance of the game.
     */
    private int numMoves;

    /**
     * The list of each grid that is associated with each turn
     */
    private ArrayList<Grid> savedGrids;

    /**
     * Manage a new Grid.
     */
    public GridManager() {
        this.savedGrids = new ArrayList<>();
        this.grid = new Grid();
        this.savedGrids.add(copiedGrid(grid));
        this.numMoves = 0;
    }

    /**
     * A new grid that is identical to another given grid.
     *
     * @param gridToBeCopied the board whose tiles are copied
     */
    Grid copiedGrid(Grid gridToBeCopied) {
        int pX = gridToBeCopied.getPlayerX();
        int pY = gridToBeCopied.getPlayerY();
        int[] blockXs = gridToBeCopied.getBlockXsIntArray();
        int[] blockYs = gridToBeCopied.getBlockYsIntArray();
        int[] foodXs = gridToBeCopied.getFoodXsIntArray();
        int[] foodYs = gridToBeCopied.getFoodYsIntArray();
        return new Grid(pX, pY, blockXs, blockYs, foodXs, foodYs);
    }

    /**
     * Returns the list of saved grids for this GridManager.
     *
     * @return this GridManager's saved boards
     */
    public ArrayList<Grid> getSavedGrids() {
        return this.savedGrids;
    }

    /**
     * Returns the current grid.
     * @return the current grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Sets the grid of this GridManager
     * @param grid the GridManager's new grid
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Returns whether the Blocks game is over, wherein the player is unable to move any more.
     * @return whether the game is over
     */
    private boolean gameOver(){
        return (grid.horizontalMove(1, false) == 0 &&
                grid.horizontalMove(-1,false ) == 0 &&
                grid.verticalMove(1, false) == 0 &&
                grid.verticalMove(-1, false) == 0);
    }

    /**
     * Places a block on the grid.
     * @param x the x-value of the new block.
     * @param y the y-value of the new block.
     */
    public void placeBlock(int x, int y){
        if(grid.validBlockPlacement(x,y)){
            grid.placeBlockAt(x,y);
        }
    }
}
