package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesMenuActivity;

import static org.junit.Assert.*;

public class BoardTest {

    /**
     * Sliding tiles starting activity used for our test board
     */
    SlidingTilesMenuActivity menu = new SlidingTilesMenuActivity();

    /**
     * Board manager used to initialize 5x5 board
     */
    SlidingTilesBoardManager boardManager = new
            SlidingTilesBoardManager(5, menu.getTileIdList(5));

    /**
     * Board used to be tested
     */
    private Board board = boardManager.getBoard();

    @Test
    public void getTiles() {
    }

    @Test
    public void numTiles() {
    }

    @Test
    public void getTile() {
    }

    @Test
    public void swapTiles() {
    }

    @Test
    public void iterator() {
    }
}