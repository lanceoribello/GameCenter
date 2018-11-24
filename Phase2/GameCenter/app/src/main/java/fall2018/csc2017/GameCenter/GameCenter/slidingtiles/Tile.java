package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc2017.GameCenter.GameCenter.R;


/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * A Tile with tile number and background id to find image in drawables based on the tile number.
     *
     * @param tileNum the id
     */
    Tile(int tileNum, Integer backgroundID) {

        id = tileNum;
        if (tileNum + 1 == Board.numCols * Board.numRows) {
            background = R.drawable.blank;
        } else {
            background = backgroundID;
        }
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
