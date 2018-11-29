package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import android.content.Context;
import android.widget.Toast;

/**
 * Processes the tapping of moves in Sliding Tiles.
 */
class MovementController {

    /**
     * The boardManager of this MovementController.
     */
    private BoardManager boardManager = null;

    /**
     * Sets the boardManager to the given boardManager
     * @param boardManager the given boardManager
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Processes the tapping of moves in Sliding Tiles.
     * @param context the context of the activity
     * @param position the position of the tap
     * @param display whether the display is shown
     */
    void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
