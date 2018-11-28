package fall2018.csc2017.GameCenter.GameCenter.blocks;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Manages a grid; includes placing blocks, moving the player, and undoing turns.
 * Also keeps track of the moves made by saving the grids corresponding to each move.
 */
public class GridManager implements Serializable {

    /**
     * The grid being managed.
     */
    private Grid grid;

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
        addToSavedGrids();
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
    void placeBlock(int x, int y) {
        grid.placeBlockAt(x, y);
    }

    /**
     * Adds a copy of the current grid to the savedGrids list.
     */
    void addToSavedGrids() {
        this.savedGrids.add(copiedGrid(this.grid));
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
     * Potentially moves the player the maximum number of grid-squares the player can move in a
     * direction until it meets a food or a block (the player stops before a block
     * and stops when a food is eaten).
     * Adds the grid to saveGrids if a move was successfully made.
     *
     * @param direction the direction the player wishes to move; can be up, down, left, or right
     */
    void movePlayer(String direction) {
        boolean success = false;
        addToSavedGrids();
        if (direction.equals("up")) {
            success = grid.verticalMove(-1, true) > 0;
        } else if (direction.equals("down"))
            success = grid.verticalMove(1, true) > 0;
        else if (direction.equals("right")) {
            success = grid.horizontalMove(1, true) > 0;
        } else if (direction.equals("left")) {
            success = grid.horizontalMove(-1, true) > 0;
        }
        if (!success) {
            savedGrids.remove(savedGrids.size() - 1);
        }
    }
}
