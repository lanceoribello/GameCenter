package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    /**
     * The Tile for testing, creates a tile with id 4 set to the background image of 4 in drawables
     */

    Tile tile = new Tile(3, 2131099791);

    /**
     * Checks if the tile returns the correct background based on its id
     */

    @Test
    public void getBackgroundIsCorrect() {
        int tile4BackgroundId = 2131099791; // The resource id for the tile image representing 4
        assertEquals(tile4BackgroundId, tile.getBackground());

    }

    /**
     * Checks if the tile returns the correct id
     */
    @Test
    public void getIdIsCorrect() {
        int tile4Id = 4; // The id which a tile 4 should contain
        assertEquals(tile4Id, tile.getId());
    }

    /**
     * Checks if two tiles with different IDs compare as different tiles
     */
    @Test
    public void compareToDifferentTile() {
        Tile tileCompare = new Tile(5, 2131099792);
        assertNotSame(tileCompare, tile);
    }

    /**
     * Checks if two of the same tiles compare as the same
     */
    @Test
    public void compareToSameTile() {
        assertSame(tile, tile);
    }
}