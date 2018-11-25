package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 * Also keeps track of the number of moves made and the boards corresponding to each move.
 */
public class SlidingTilesBoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The number of moves played so far in the current instance of the game.
     */
    private int numMoves;

    /**
     * The complexity of the current board being managed (the number of tiles on a side).
     */
    private int complexity;

    /**
     * The list of each board that is associated with each move
     */
    private ArrayList<Board> savedBoards;

    /**
     * The list of background Ids of the tile images in the drawable folder based on game level
     * This was done in order to get rid of the switch statement code smell in the tiles class
     * as now the tile class can be set up for any arbitrary complexity
     */
    private ArrayList<Integer> tileIdList;

    /**
     * Manage a new shuffled board. Board is always solvable.
     *
     * @param complexity complexity of the game
     * @param tileIdList list of tile IDs
     */
    public SlidingTilesBoardManager(int complexity, ArrayList<Integer> tileIdList) {
        this.tileIdList = tileIdList;
        this.complexity = complexity;
        this.savedBoards = new ArrayList<>();
        Board.numRows = Board.numCols = complexity;
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.numRows * Board.numCols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, tileIdList.get(tileNum)));
        }
        this.board = new Board(tiles);
        makeRandomMoves(complexity);
        this.savedBoards = new ArrayList<>();
        this.savedBoards.add(copiedBoard(board));
        this.numMoves = 0;
    }

    /**
     * Shuffles a board 1000 times by making 1000 random valid moves.
     *
     * @param complexity complexity of the game
     */
    private void makeRandomMoves(int complexity) {
        int i = 0;
        while (i < 1000) {
            int randomPosition =
                    ThreadLocalRandom.current().nextInt(0, complexity * complexity);
            if (isValidTap(randomPosition)) {
                touchMove(randomPosition);
                i++;
            }
        }
    }

    /**
     * Returns the list of saved boards for this BoardManager.
     *
     * @return this BoardManager's saved boards
     */
    public ArrayList<Board> getSavedBoards() {
        return this.savedBoards;
    }

    /**
     * Returns the complexity of this BoardManager.
     *
     * @return this BoardManager's complexity.
     */
    public int getComplexity() {
        return this.complexity;
    }

    /**
     * Return the current board.
     *
     * @return the current board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Sets the board of this BoardManager.
     *
     * @param board the BoardManager's new Board
     */
    void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        Iterator<Tile> iter = this.board.iterator();
        int prevId = 0;
        for (int a = 0; a != this.board.numTiles(); a++) {
            Tile currentTile = iter.next();
            if (currentTile.getId() != prevId + 1) {
                return false;
            }
            prevId = currentTile.getId();
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / Board.numCols;
        int col = position % Board.numCols;
        int blankId = this.board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : this.board.getTile(row - 1, col);
        Tile below = row == Board.numRows - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : this.board.getTile(row, col - 1);
        Tile right = col == Board.numCols - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * Updates savedBoards with the current board instance each move.
     * Increments numMoves by 1.
     *
     * @param position the position
     */
    void touchMove(int position) {
        int row = position / Board.numCols;
        int col = position % Board.numCols;
        int blankId = this.board.numTiles();
        int blankRow = 0;
        int blankCol = 0;
        Iterator<Tile> iter = this.board.iterator();
        for (int rowIndex = 0; rowIndex != Board.numRows; rowIndex++) {
            for (int colIndex = 0; colIndex != Board.numCols; colIndex++) {
                if (iter.next().getId() == blankId) {
                    blankRow = rowIndex;
                    blankCol = colIndex;
                }
            }
        }
        addToSavedBoards();
        this.board.swapTiles(row, col, blankRow, blankCol);
        this.numMoves += 1;
    }

    /**
     * Adds a copy of the current instance of board to the savedBoards list.
     */
    void addToSavedBoards() {
        this.savedBoards.add(copiedBoard(this.board));
    }

    /**
     * Returns the number of moves made in this game so far, which corresponds to the user's
     * score at the end of the game.
     *
     * @return the number of moves made so far in the game
     */
    public int getMoves() {
        return this.numMoves;
    }

    /**
     * Sets the BoardManager's current board to one of its past boards.
     *
     * @param numTurns the number of turns that are undone
     */
    public void undo(int numTurns) {
        for (int i = 1; i != this.savedBoards.size(); i++) {
            if (i == numTurns) {
                this.setBoard(this.savedBoards.get(this.savedBoards.size() - i));
                break;
            }
        }
        for (int i = 0; i != numTurns; i++) {
            this.savedBoards.remove(this.savedBoards.size() - 1);
        }
    }

    /**
     * A new board of tiles that copies the tiles of another given board.
     *
     * @param boardToBeCopied the board whose tiles are copied
     */
    Board copiedBoard(Board boardToBeCopied) {
        List<Integer> tileNums = new ArrayList<>();
        Tile[][] tilesToBeCopied = boardToBeCopied.getTiles();
        for (int row = 0; row != Board.numRows; row++) {
            for (int col = 0; col != Board.numCols; col++) {
                tileNums.add(tilesToBeCopied[row][col].getId() - 1);
            }
        }
        List<Tile> copiedTileList = new ArrayList<>();
        for (Integer tileNum : tileNums) {
            copiedTileList.add(new Tile(tileNum, this.tileIdList.get(tileNum)));
        }
        return new Board(copiedTileList);
    }
}
