package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test Class for BoardManager.
 */
public class BoardManagerTest {

    /**
     * The array list of background IDs representing the tile images in R.drawable in row-major
     * order for a 5x5 board.
     */
    private List<Integer> fiveByList = Arrays.asList(2131099773, 2131099784, 2131099790, 2131099791,
            2131099792, 2131099793, 2131099794, 2131099795, 2131099796, 2131099774,
            2131099775, 2131099776, 2131099777, 2131099778, 2131099779, 2131099780,
            2131099781, 2131099782, 2131099783, 2131099785, 2131099786, 2131099787,
            2131099788, 2131099789, 0);

    private ArrayList<Integer> fiveByArray = new ArrayList<>(fiveByList);

    /**
     * Board manager used for testing.
     */
    private BoardManager boardManager = new BoardManager(5, fiveByArray);

    /**
     * List of tiles used for a 5x5 board, in row major order.
     */
    private List<Tile> setTileList() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 25; tileNum++) {
            tiles.add(new Tile(tileNum, fiveByArray.get(tileNum)));
        }
        return tiles;
    }

    /**
     * Tests if saved boards getter returns the correct value.
     */
    @Test
    public void getSavedBoards() {
        ArrayList<Board> testerSavedBoards = boardManager.getSavedBoards();
        Board savedBoard = testerSavedBoards.get(0);
        testerSavedBoards = new ArrayList<>();
        testerSavedBoards.add(savedBoard);
        assertEquals(testerSavedBoards, boardManager.getSavedBoards());
    }

    /**
     * Tests if the complexity getter returns the correct value, which should be 5 for a 5x5 game.
     */
    @Test
    public void getComplexity() {
        assertEquals(5, boardManager.getComplexity());
    }

    /**
     * Tests if the board getter returns the correct value.
     */
    @Test
    public void getBoard() {
        Board board = boardManager.getBoard();
        assertEquals(board, boardManager.getBoard());
    }

    /**
     * Tests the 2 cases if the puzzle solved method works:
     * If the board is in row major order of tiles, it should return true.
     * Otherwise, it should return false.
     * This also implicitly tests if the setBoard functionality works.
     */
    @Test
    public void puzzleSolvedIsCorrect() {
        Board solvedBoard = new Board(setTileList());
        boardManager.setBoard(solvedBoard);
        assert (boardManager.puzzleSolved());
        boardManager.touchMove(5);
        assert (!boardManager.puzzleSolved());
    }

    /**
     * Tests 3 cases of isValidTap on a solved board. Case 1 is where you tap a valid tile on a
     * row major board of 5x5, our test case using tile #24, the one to the left of the blank tile.
     * Also tests then the user taps the blank tile, being tile#25, or when the user taps
     * an unplayable tile which isn't blank, our case being tile#1.
     */
    @Test
    public void isValidTapIsCorrect() {
        Board solvedBoard = new Board(setTileList());
        boardManager.setBoard(solvedBoard);
        assert (boardManager.isValidTap(23));
        assert (!boardManager.isValidTap(24));
        assert (!boardManager.isValidTap(0));

    }

    /**
     * Tests whether touch move works when swapping the blank tile, which in this case
     * is tile 25, and tile 24, the one directly to its left.
     */
    @Test
    public void touchMoveIsCorrect() {
        Board solvedBoard = new Board(setTileList());
        boardManager.setBoard(solvedBoard);
        Tile tileHold = boardManager.getBoard().getTile(4, 3);
        Tile blankTile = boardManager.getBoard().getTile(4, 4);
        boardManager.touchMove(23);
        assert (boardManager.getBoard().getTile(4, 4) == tileHold &&
                boardManager.getBoard().getTile(4, 3) == blankTile);

    }

    /**
     * Tests if the moves getter returns the correct value.
     */
    @Test
    public void getMovesIsCorrect() {
        int totalMoves = boardManager.getMoves();
        assertEquals(totalMoves, boardManager.getMoves());
    }

    /**
     * First sets up a solved 5x5 board. 1 move is made where tile 24 is swapped with
     * tile 25. The move is then undone. This test checks if the move is undone by comparing
     * the new board post-undo to the solvedBoard, as these should now contain the same tile
     * configuration
     */
    @Test
    public void undoIsCorrect() {
        Board solvedBoard = new Board(setTileList());
        boardManager.setBoard(solvedBoard);
        boardManager.touchMove(23);
        boardManager.undo(1);
        Iterator<Tile> iter = solvedBoard.iterator();
        Iterator<Tile> iter2 = boardManager.getBoard().iterator();
        for (int b = 0; b != boardManager.getComplexity(); b++) {
            Tile currentTile = iter.next();
            Tile currentManagerTile = iter2.next();
            assertEquals(currentTile.getId(), currentManagerTile.getId());
        }
    }
}