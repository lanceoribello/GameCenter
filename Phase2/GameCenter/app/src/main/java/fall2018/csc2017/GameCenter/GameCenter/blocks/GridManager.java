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
    private Grid copiedGrid(Grid gridToBeCopied) {
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
     *
     * @return the current grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Sets the grid of this GridManager
     *
     * @param grid the GridManager's new grid
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Returns whether the Blocks game is over, wherein the player is unable to move any more.
     *
     * @return whether the game is over
     */
    public boolean gameOver() {
        return (grid.horizontalMove(1, false) == 0 &&
                grid.horizontalMove(-1, false) == 0 &&
                grid.verticalMove(1, false) == 0 &&
                grid.verticalMove(-1, false) == 0);
    }

    /**
     * Places a block on the grid.
     *
     * @param x the x-value of the new block.
     * @param y the y-value of the new block.
     */
    public void placeBlock(int x, int y) {
        grid.placeBlockAt(x, y);
    }

    /**
     * Returns whether the location at the the given x and y is a valid place to place a block.
     *
     * @param x the x-value on the grid that is checked
     * @param y the y-value on the grid that is checked
     * @return whether the given location is a valid block placement location
     */
    public boolean isValidBlockPlacement(int x, int y) {
        return grid.validBlockPlacement(x, y);
    }

    /**
     * Adds a copy of the current grid to the savedGrids list.
     */
    void addToSavedGrids() {
        this.savedGrids.add(copiedGrid((this.grid)));
    }

    /**
     * Returns the number of moves made in this game so far/
     *
     * @return the number of moves made so far in the game
     */
    public int getMoves() {
        return this.numMoves;
    }

    /**
     * Sets the GridManagers's current grid to one of its past grids.
     *
     * @param numTurns the number of turns that are undone
     */
    public void undo(int numTurns) {
        for (int i = 1; i != this.savedGrids.size(); i++) {
            if (i == numTurns) {
                this.setGrid(this.savedGrids.get(this.savedGrids.size() - i));
                break;
            }
        }
        for (int i = 0; i != numTurns; i++) {
            this.savedGrids.remove(this.savedGrids.size() - 1);
        }
    }

    /**
     * Returns whether the player makes a successful move on the grid.
     * Potentially moves the player the maximum number of grid-squares the player can move in a
     * direction until it meets a food or a block (the player stops before a block
     * and stops when a food is eaten).
     *
     * @param direction the direction the player wishes to move; can be up, down, left, or right
     * @return whether the player actually moves
     */
    public boolean moveSuccess(String direction) {
        switch (direction) {
            case "up":
                return grid.verticalMove(1, true) > 0;
            case "down":
                return grid.verticalMove(-1, true) > 0;
            case "right":
                return grid.horizontalMove(1, true) > 0;
        }
        return grid.horizontalMove(-1, true) > 0;
    }
}
