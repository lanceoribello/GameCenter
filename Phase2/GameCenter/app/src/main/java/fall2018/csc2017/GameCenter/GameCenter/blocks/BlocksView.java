package fall2018.csc2017.GameCenter.GameCenter.blocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

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
     * The gridManager of the Blocks game.
     */
    GridManager gridManager;

    /**
     * The rectangle object representations of the ones which have been drawn on the grid.
     * Used to check where an OnTouch event is contained on the grid
     */
    ArrayList<Rect> rectangles = new ArrayList<>();

    /**
     * The canvas of the Blocks game.
     * Used to display the game.
     */
    Canvas canvas;
    /**
     * The context of the Blocks game.
     * Used to reference the game's activity.
     */
    Context context;
    /**
     * The height of the screen being displayed upon.
     */
    private int screenHeight;
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
    /**
     * The width of the screen being displayed upon.
     */
    private int screenWidth;
    /**
     * The size in pixels of a block for the game display.
     * Corresponds to the size of a grid square.
     */
    private int blockSize;

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
        blockSize = screenWidth / Grid.GRID_LENGTH;
        holder = getHolder();
        paint = new Paint();
        gridManager = new GridManager();
    }

    /**
     * Runs the game. Draws a new display whenever a touch is processed.
     */
    @Override
    public void run() {
        while (playing) {
            if (performClick()) {
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
            canvas.drawColor(Color.argb(255, 250,195,65));
            drawGrid();
            drawControls();
            drawText();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Draws the score text and the GAME OVER text.
     */
    private void drawText() {
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(SCORE_TEXT_SIZE);
        canvas.drawText("Score:" + gridManager.getGrid().getScore(), 10,
                SCORE_TEXT_SIZE, paint);
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
        canvas.drawRect(0, screenWidth - 1, screenWidth, screenHeight, paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(screenWidth / 3, screenWidth - 1, 2 * screenWidth / 3,
                screenWidth + (screenHeight - screenWidth) / 3, paint);
        canvas.drawRect(screenWidth / 3, screenWidth + 2 *
                        (screenHeight - screenWidth) / 3,
                2 * screenWidth / 3,
                screenHeight, paint);
        canvas.drawRect(0, screenWidth + (screenHeight - screenWidth) / 3,
                screenWidth / 3,
                screenWidth + 2 * (screenHeight - screenWidth) / 3, paint);
        canvas.drawRect(2 * screenWidth / 3, screenWidth + (screenHeight -
                        screenWidth) / 3,
                screenWidth,
                screenWidth + 2 * (screenHeight - screenWidth) / 3, paint);
    }

    /**
     * Draws the entire grid of the game.
     */
    private void drawGrid() {
        for (int row = 0; row != Grid.GRID_LENGTH; row++) {
            for (int col = 0; col != Grid.GRID_LENGTH; col++) {
                int gridLocation = gridManager.getGrid().gridState[row][col];
                if (gridLocation != Grid.EMPTY) {
                    if (gridLocation == Grid.PLAYER) {
                        paint.setColor(Color.argb(255, 231, 126, 235));
                    } else if (gridLocation == Grid.BLOCK) {
                        paint.setColor(Color.argb(255, 0, 0, 0));
                    } else if (gridLocation == Grid.FOOD) {
                        paint.setColor(Color.argb(255, 65, 36, 255));
                    }
                    drawGridSquare(row, col);
                }
                rectangles.add(new Rect(row * blockSize, col * blockSize,
                        (row * blockSize) + blockSize, (col * blockSize) + blockSize));
            }
        }
    }

    /**
     * Helper method for drawGrid.
     * Draws individual squares of the grid for the game.
     *
     * @param row the grid row associated with the grid square
     * @param col the grid col associated with the grid square
     */
    private void drawGridSquare(int row, int col) {
        canvas.drawRect(row * blockSize, col * blockSize,
                (row * blockSize) + blockSize, (col * blockSize) + blockSize,
                paint);
    }

    /**
     * Necessary for onTouchEvent to have no warnings.
     *
     * @return true
     */
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        performClick();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenWidth / 3
                        && motionEvent.getX() <= 2 * screenWidth / 3
                        && motionEvent.getY() >= screenWidth
                        && motionEvent.getY() <= screenWidth + (screenHeight - screenWidth) / 3) {
                    gridManager.movePlayer("down");
                } else if (motionEvent.getX() >= screenWidth / 3
                        && motionEvent.getX() <= 2 * screenWidth / 3
                        && motionEvent.getY() <= screenHeight
                        && motionEvent.getY() >= screenHeight - (screenHeight - screenWidth) / 3) {
                    gridManager.movePlayer("up");
                } else if (motionEvent.getX() >= 2 * screenWidth / 3
                        && motionEvent.getX() <= screenWidth
                        && motionEvent.getY() <= screenHeight - (screenHeight - screenWidth) / 3
                        && motionEvent.getY() >= screenWidth + (screenHeight - screenWidth) / 3) {
                    gridManager.movePlayer("right");
                } else if (motionEvent.getX() >= 0
                        && motionEvent.getX() <= screenWidth / 3
                        && motionEvent.getY() <= screenHeight - (screenHeight - screenWidth) / 3
                        && motionEvent.getY() >= screenWidth + (screenHeight - screenWidth) / 3) {
                    gridManager.movePlayer("left");
                }
                for (int i = 0; i < rectangles.size(); i++) {
                    if (rectangles.get(i).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                        gridManager.placeBlock(rectangles.get(i).left / blockSize,
                                rectangles.get(i).top / blockSize);
                    }
                }
        }
        return true;
    }
}
