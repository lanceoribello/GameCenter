package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.validation.TypeInfoProvider;

import static org.junit.Assert.*;

public class SlidingTilesBoardManagerTest {

    /**
     * The arraylist of background IDs representing the tile images in R.drawable in row-major
     * order for a 5x5 board
     */

    private List<Integer> fiveByList = Arrays.asList(2131099773, 2131099784, 2131099790, 2131099791,
            2131099792, 2131099793, 2131099794, 2131099795, 2131099796, 2131099774,
            2131099775, 2131099776, 2131099777, 2131099778, 2131099779, 2131099780,
            2131099781, 2131099782, 2131099783, 2131099785, 2131099786, 2131099787,
            2131099788, 2131099789, 0);

    private ArrayList<Integer> fiveByArray = new ArrayList<>(fiveByList);

    /**
     * Boardmanager used for testing
     */

    SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager(5, fiveByArray);

    /**
     * Tests if the boardmanager is able to save boards in an array list properly
     * and if it's able to return that list back when the getter is called
     */
    @Test
    public void getAndSetSavedBoards() {
        ArrayList<Board> savedBoards = new ArrayList<>();

        savedBoards.add(boardManager.getBoard());
        boardManager.addToSavedBoards();
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        savedBoards.add(boardManager.getBoard());
        boardManager.addToSavedBoards();
        savedBoards.add(boardManager.getBoard());

        for (int a = 0; a<=2; a++){
            Iterator<Tile> iter = savedBoards.get(a).iterator();
            Iterator<Tile> iter2 = boardManager.getBoard().iterator();

            for (int b = 0; b != boardManager.getComplexity(); b++) {
                Tile currentTile = iter.next();
                Tile currentManagerTile = iter2.next();
                assertEquals(currentTile, currentManagerTile);
            }
        }
    }

    @Test
    public void getComplexity() {
    }

    @Test
    public void getBoard() {
    }

    @Test
    public void puzzleSolved() {
    }

    @Test
    public void isValidTap() {
    }

    @Test
    public void touchMove() {
    }

    @Test
    public void getMoves() {
    }

    @Test
    public void undo() {
    }
}