package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;


import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesMenuActivity;

import static org.junit.Assert.*;

public class BoardTest {

    /**
     * Sliding tiles starting activity used for our test board
     */
    private SlidingTilesMenuActivity menu = new SlidingTilesMenuActivity();

    /**
     * Number of tiles on a board, 9 for 3x3, 16 for 4x4, 25 for 5x5
     */
    private int numTiles;

    /**
     * List of tiles used for our board, in row major order
     */
    private List<Tile> setTileList() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, tileIdList.get(tileNum)));
        }
        return tiles;
    }

    /**
     * Background IDs in row-major depending on numTiles
     */
    ArrayList<Integer> tileIdList = menu.getTileIdList((int)Math.sqrt(numTiles));


    /**
     * Board used to be tested
     */
    private Board board = new Board(setTileList());

    @Test
    public void getTilesIsCorrect3x3() {
        numTiles = 9;


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