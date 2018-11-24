package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    /**
     * The arraylist of background IDs representing the tile images in R.drawable in row-major
     * order for a 3x3 board
     */

    private List<Integer> threeByList = Arrays.asList(2131099773, 2131099784, 2131099790, 2131099791,
            2131099792, 2131099793, 2131099794, 2131099795, 2131099796);

    private ArrayList<Integer> threeByArray = new ArrayList<>(threeByList);

    /**
     * The arraylist of background IDs representing the tile images in R.drawable in row-major
     * order for a 3x3 board
     */

    private List<Integer> fourByList = Arrays.asList(2131099773, 2131099784, 2131099790, 2131099791,
            2131099792, 2131099793, 2131099794, 2131099795, 2131099796, 2131099774,
            2131099775, 2131099776, 2131099777, 2131099778, 2131099779, 2131099780);

    private ArrayList<Integer> fourByArray = new ArrayList<>(fourByList);

    /**
     * The arraylist of background IDs representing the tile images in R.drawable in row-major
     * order for a 3x3 board
     */

    private List<Integer> fiveByList = Arrays.asList(2131099773, 2131099784, 2131099790, 2131099791,
            2131099792, 2131099793, 2131099794, 2131099795, 2131099796, 2131099774,
            2131099775, 2131099776, 2131099777, 2131099778, 2131099779, 2131099780,
            2131099781, 2131099782, 2131099783, 2131099785, 2131099786, 2131099787,
            2131099788, 2131099789, 0);

    private ArrayList<Integer> fiveByArray = new ArrayList<>(fiveByList);

    /**
     * Number of tiles on a board, 9 for 3x3, 16 for 4x4, 25 for 5x5
     */
    private int numTiles;

    /**
     * List of tiles used for an arbitrary board, in row major order
     */
    private List<Tile> setTileList() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (numTiles == 9) {
                tiles.add(new Tile(tileNum, threeByArray.get(tileNum)));
            } else if (numTiles == 16) {
                tiles.add(new Tile(tileNum, fourByArray.get(tileNum)));
            } else {
                tiles.add(new Tile(tileNum, fiveByArray.get(tileNum)));
            }
        }
        return tiles;
    }

    /**
     * Tests if the getTile method is correct for a 3x3 board
     */
    @Test
    public void getTileIsCorrect3x3() {
        Board.numRows = Board.numCols = 3;
        numTiles = Board.numRows * Board.numCols;
        List<Tile> tiles = setTileList();
        Board board = new Board(tiles);
        Tile testTile = new Tile(0, threeByArray.get(0));

        assertEquals(testTile.getId(), board.getTile(0, 0).getId());
    }

    /**
     * Tests if the getTile method is correct for a 4x4 board
     */
    @Test
    public void getTileIsCorrect4x4() {
        Board.numRows = Board.numCols = 4;
        numTiles = Board.numRows * Board.numCols;
        List<Tile> tiles = setTileList();
        Board board = new Board(tiles);
        Tile testTile = new Tile(0, fourByArray.get(0));

        assertEquals(testTile.getId(), board.getTile(0, 0).getId());
    }

    /**
     * Tests if the getTile method is correct for a 5x5 board
     */
    @Test
    public void getTileIsCorrect5x5() {
        Board.numRows = Board.numCols = 5;
        numTiles = Board.numRows * Board.numCols;
        List<Tile> tiles = setTileList();
        Board board = new Board(tiles);
        Tile testTile = new Tile(0, fiveByArray.get(0));

        assertEquals(testTile.getId(), board.getTile(0, 0).getId());
    }

    /**
     * Tests if the numTiles method is correct for a 3x3, 4x4, and 5x5 board, should be 9, 16 and 25
     * respectively
     */
    @Test
    public void numTiles() {
        for (int start = 3; start <= 5; start++) {
            Board.numRows = Board.numCols = start;
            numTiles = Board.numRows * Board.numCols;
            List<Tile> tiles = setTileList();
            Board board = new Board(tiles);
            assertEquals(numTiles, board.numTiles());
        }
    }

    /**
     * Tests if the getTiles method is correct for a 3x3, 4x4 and 5x5 board,
     * should return an identical list to the one used in the board constructor each time
     */
    @Test
    public void getTiles() {

        for (int start = 3; start <= 5; start++) {
            Board.numRows = Board.numCols = start;

            numTiles = Board.numRows * Board.numCols;
            List<Tile> tiles = setTileList();
            Board board = new Board(tiles);
            Iterator<Tile> iter = board.iterator();

            for (int a = 0; a != board.numTiles(); a++) {
                Tile currentTile = iter.next();
                assertEquals(tiles.get(a), currentTile);
            }
        }
    }

    /**
     * Tests if the first 2 tiles swap on a 3x3 board
     */
    @Test
    public void swapFirstTwoTiles() {
        Board.numRows = Board.numCols = 3;

        numTiles = Board.numRows * Board.numCols;
        List<Tile> tiles = setTileList();
        Board board = new Board(tiles);

        Tile tileHold = board.getTile(0, 0);
        Tile tileHold2 = board.getTile(0, 1);

        board.swapTiles(0, 0, 0, 1);

        assert (board.getTile(0, 0) == tileHold2 &&
                board.getTile(0, 1) == tileHold);

    }

    /**
     * Tests if the last 2 tiles swap on a 3x3 board
     */
    @Test
    public void swapLastTwoTiles() {
        Board.numRows = Board.numCols = 3;

        numTiles = Board.numRows * Board.numCols;
        List<Tile> tiles = setTileList();
        Board board = new Board(tiles);

        Tile tileHold = board.getTile(2, 1);
        Tile tileHold2 = board.getTile(2, 2);

        board.swapTiles(2, 1, 2, 2);

        assert (board.getTile(2, 1) == tileHold2 &&
                board.getTile(2, 2) == tileHold);

    }

    /**
     * Tests if the iterator iterated correctly through the tiles in boards of 3x3, 4x4 and 5x5
     */
    @Test
    public void iterator() {

        for (int start = 3; start <= 5; start++) {
            Board.numRows = Board.numCols = start;

            numTiles = Board.numRows * Board.numCols;
            List<Tile> tiles = setTileList();
            Board board = new Board(tiles);
            Iterator<Tile> iter = board.iterator();

            for (int row = 0; row != Board.numRows; row++) {
                for (int col = 0; col != Board.numCols; col++) {
                    assertEquals(board.getTile(row, col), iter.next());
                }
            }
        }


    }
}