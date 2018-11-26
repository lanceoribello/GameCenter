package fall2018.csc2017.GameCenter.GameCenter.blocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * The view for Blocks.
 */
public class BlocksView extends SurfaceView implements Runnable {

    /**
     * The text size of the displayed score.
     */
    private final static int SCORE_TEXT_SIZE = 40;

    /**
     * The size of the GAME OVER text.
     */
    private final static int GAME_OVER_SIZE = 150;

    /**
     * How many milliseconds in a second.
     */
    private final static long MILLIS_IN_A_SECOND = 1000;

    /**
     * The width of the playable area in terms of the number of blocks.
     * 40 by default.
     */
    private final static int NUM_BLOCKS_WIDE = 40;

    /**
     * The canvas of the Blocks game.
     * Used to display the game.
     */
    Canvas canvas;

    /**
     * The context of the Snake game.
     * Used to reference the game's activity.
     */
    Context context;

    /**
     * The height of the screen being displayed upon.
     */
    int screenHeight;

    /**
     * An object array that contains any relevant data in the game used for saving and loading
     * save points.
     */
    Object[] savePointData;

    /**
     * The thread of the Snake game.
     */
    private Thread thread = null;

    /**
     * The volatile that determines whether the game is currently being played.
     * As a volatile, it can be accessed from inside and outside the thread.
     */
    private volatile boolean playing;

    /**
     * The SurfaceHolder used by the Canvas class to display the game.
     */
    private SurfaceHolder holder;

    /**
     * The paint used to select colors for displaying the game.
     */
    private Paint paint;

    private int screenWidth;

    /**
     * The long that controls when the game will be updated next.
     */
    private long nextFrameTime;

    /**
     * The frames per second of the current Snake game.
     */
    private long FPS = 10;

    /**
     * The current score of the game.
     */
    private int score;

    private int blockSize;

    /**
     * The height of the playable area in terms of the number of blocks.
     * Determined dynamically.
     */
    private int numBlocksHigh;

    /**
     * The gridManager of the Blocks game.
     */
    public GridManager gridManager;

    /**
     * Constructor of BlocksView that only takes in context.
     * Necessary to have such a constructor for a custom view class like BlocksView.
     *
     * @param context the context used to create the BlocksView
     */
    public BlocksView(Context context) {
        super(context);
    }

    /**
     * An instance of BlocksView that manages the Blocks game.
     * Sets up the display the game.
     * Processes whether a new game is started or a past game is being loaded.
     *
     * @param context the context of BlocksView
     * @param size    the size to be displayed
     */
    public BlocksView(Context context, Point size) {
        super(context);
        this.context = context;
        screenWidth = size.x;
        screenHeight = size.y;
        blockSize = screenWidth / NUM_BLOCKS_WIDE;
        //bottom third of the screen used for the movement buttons
        numBlocksHigh = 2 * (screenHeight / blockSize) / 3;
        holder = getHolder();
        paint = new Paint();
        gridManager = new GridManager();
    }

    @Override
    public void run() {
        while (playing) {
            if (checkForUpdate()) {
                drawGame();
            }
        }
    }

    /**
     * Pauses the game.
     */
    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    /**
     * Resumes the game.
     */
    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Creates the display of the game.
     */
    private void drawGame() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 102, 204, 255));
            drawText();
            drawGrid();
            drawControls();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Draws the score text and the GAME OVER text.
     */
    private void drawText() {
        paint.setTextSize(SCORE_TEXT_SIZE);
        canvas.drawText("Score:" + score, 10, SCORE_TEXT_SIZE, paint);
        if (gridManager.gameOver()) {
            paint.setTextSize(GAME_OVER_SIZE);
            canvas.drawText("GAME OVER", 10, GAME_OVER_SIZE + 50, paint);
        }
    }

    /**
     * Draws the controls for movement at the bottom of the screen.
     */
    private void drawControls() {
        paint.setColor(Color.argb(255, 255, 255, 255));
        canvas.drawRect(0, 2 * screenHeight / 3, screenWidth, screenHeight, paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(screenWidth / 3, 2 * screenHeight / 3, 2 * screenWidth / 3,
                7 * screenHeight / 9, paint);
        canvas.drawRect(screenWidth / 3, 8 * screenHeight / 9, 2 * screenWidth / 3,
                screenHeight, paint);
        canvas.drawRect(0, 7 * screenHeight / 9, screenWidth / 3,
                8 * screenHeight / 9, paint);
        canvas.drawRect(2 * screenWidth / 3, 7 * screenHeight / 9, screenWidth,
                8 * screenHeight / 9, paint);
    }

    /**
     * Draws the entire grid of the game.
     */
    private void drawGrid() {

    }

    /**
     * Determines whether the game should be updated for the timing of when updateGame() and
     * drawGame() should be called, depending on the FPS of the game.
     *
     * @return whether the game is to be updated
     */
    private boolean checkForUpdate() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_IN_A_SECOND / FPS;
            return true;
        }
        return false;
    }

    /**
     * Returns the current save point data of this Blocks game.
     *
     * @return this game's current save point data
     */
    public Object[] getSavePointData() {
        return this.savePointData;
    }
}
