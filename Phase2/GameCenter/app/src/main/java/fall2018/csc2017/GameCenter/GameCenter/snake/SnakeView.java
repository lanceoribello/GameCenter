package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import fall2018.csc2017.GameCenter.GameCenter.R;

/*
Adapted from: https://androidgameprogramming.com/programming-a-snake-game/
Manages a Snake game, running separately from the UI.
 */

/**
 * The view for Snake.
 */
public class SnakeView extends SurfaceView implements Runnable {

    /**
     * The text size of the displayed score and save point text.
     */
    private final static int SMALL_TEXT_SIZE = 50;

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
    private final static int NUM_BLOCKS_WIDE = 20;

    /**
     * The canvas of the Snake game.
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
     * The long that controls when the game will be updated next.
     */
    private long nextFrameTime;

    /**
     * The size in pixels of a block for the game display.
     * Corresponds to the size of an individual snake segment and the size of a apple.
     */
    private int blockSize;

    /**
     * The controller for this game.
     */
    private SnakeController snakeController;

    /**
     * Constructor of SnakeView that only takes in context.
     * Necessary to have such a constructor for a custom view class like SnakeView.
     *
     * @param context the context used to create the SnakeView
     */
    public SnakeView(Context context) {
        super(context);
    }

    /**
     * An instance of SnakeView that manages the Snake game.
     * Sets up the display the game.
     * Processes whether a new game is started or a past game is being loaded.
     *
     * @param context     the context of SnakeView
     * @param size        the size to be displayed
     * @param difficulty  the difficulty of the current Snake game
     * @param oldSaveData the data used to load a saved game; can be null if a new game is started
     */
    public SnakeView(Context context, Point size, String difficulty, Object[] oldSaveData) {
        super(context);
        this.context = context;
        screenWidth = size.x;
        screenHeight = size.y;
        blockSize = screenWidth / NUM_BLOCKS_WIDE;
        // Bottom third of the screen used for the movement buttons
        holder = getHolder();
        paint = new Paint();
        snakeController = new SnakeController(difficulty,oldSaveData,
                2 * (screenHeight / blockSize) / 3);
    }

    /**
     * Runs the game.
     */
    @Override
    public void run() {
        while (playing) {
            if (checkForUpdate()) {
                snakeController.updateGame();
                drawGame();
                snakeController.setAutoSaveData(snakeController.createSaveData());
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
            drawSnake();
            drawApple();
            drawBomb();
            drawControls();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Draws the bomb.
     */
    private void drawBomb() {
        Paint p = new Paint();
        Bitmap bomb = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
        int bombX = snakeController.getBombX();
        int bombY = snakeController.getBombY();
        canvas.drawBitmap(bomb, null, new Rect(bombX * blockSize,
                (bombY * blockSize),
                (bombX * blockSize) + blockSize,
                (bombY * blockSize) + blockSize), p);
    }

    /**
     * Draws the apple.
     */
    private void drawApple() {
        Paint p = new Paint();
        int appleX = snakeController.getAppleX();
        int appleY = snakeController.getAppleY();
        Bitmap apple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        canvas.drawBitmap(apple, null, new Rect(appleX * blockSize,
                (appleY * blockSize),
                (appleX * blockSize) + blockSize,
                (appleY * blockSize) + blockSize), p);
    }

    /**
     * Draws the score text, save point text, and the GAME OVER text.
     */
    private void drawText() {
        paint.setColor(Color.BLACK);
        paint.setTextSize(SMALL_TEXT_SIZE);
        int score = snakeController.getScore();
        canvas.drawText("Score:" + score, 10, SMALL_TEXT_SIZE, paint);
        if (score % SnakeController.SAVE_POINT_EVERY == 0 && score != 0) {
            canvas.drawText("New save point!", screenWidth / 4,
                    SMALL_TEXT_SIZE, paint);
        }
        if (snakeController.detectDeath()) {
            paint.setTextSize(GAME_OVER_SIZE);
            canvas.drawText("GAME OVER", screenWidth / 8, screenHeight / 3, paint);
        }
    }

    /**
     * Draws the snake.
     */
    private void drawSnake() {
        paint.setColor(Color.argb(255, 255, 255, 255));
        int[] snakeXs = snakeController.getSnakeXs();
        int[] snakeYs = snakeController.getSnakeYs();
        for (int i = 0; i < snakeController.getSnakeLength(); i++) {
            canvas.drawRect(snakeXs[i] * blockSize,
                    (snakeYs[i] * blockSize),
                    (snakeXs[i] * blockSize) + blockSize,
                    (snakeYs[i] * blockSize) + blockSize, paint);
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
        paint.setTextSize(SMALL_TEXT_SIZE);
        canvas.drawText("Pause", 2 * screenWidth / 3 + screenWidth / 12,
                8 * screenHeight / 9
                        + screenHeight / 18, paint);
    }

    /**
     * Determines whether the game should be updated for the timing of when updateGame() and
     * drawGame() should be called, depending on the FPS of the game.
     *
     * @return whether the game is to be updated
     */
    private boolean checkForUpdate() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_IN_A_SECOND / snakeController.getFps();
            return true;
        }
        return false;
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

    /**
     * Changes the direction of our snake based on if the mouseClick event falls in the bounds
     * of the 4 black rectangles I set up as buttons to move up, down, left or right.
     *
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        performClick();
        SnakeController.Direction direction = snakeController.getDirection();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenWidth / 3
                        && motionEvent.getX() <= 2 * screenWidth / 3
                        && motionEvent.getY() <= 7 * screenHeight / 9
                        && motionEvent.getY() >= 2 * screenHeight / 3
                        && direction != SnakeController.Direction.DOWN) {
                    snakeController.setDirection(SnakeController.Direction.UP);
                } else if (motionEvent.getX() >= screenWidth / 3
                        && motionEvent.getX() <= 2 * screenWidth / 3
                        && motionEvent.getY() <= screenHeight
                        && motionEvent.getY() >= 8 * screenHeight / 9
                        && direction != SnakeController.Direction.UP) {
                    snakeController.setDirection(SnakeController.Direction.DOWN);
                } else if (motionEvent.getX() >= 2 * screenWidth / 3
                        && motionEvent.getX() <= screenWidth
                        && motionEvent.getY() <= 8 * screenHeight / 9
                        && motionEvent.getY() >= 7 * screenHeight / 9
                        && direction != SnakeController.Direction.LEFT) {
                    snakeController.setDirection(SnakeController.Direction.RIGHT);
                } else if (motionEvent.getX() >= 0
                        && motionEvent.getX() <= screenWidth / 3
                        && motionEvent.getY() <= 8 * screenHeight / 9
                        && motionEvent.getY() >= 7 * screenHeight / 9
                        && direction != SnakeController.Direction.RIGHT) {
                    snakeController.setDirection(SnakeController.Direction.LEFT);
                } else if (motionEvent.getX() >= 2 * screenWidth / 3
                        && motionEvent.getX() <= screenWidth
                        && motionEvent.getY() <= screenHeight
                        && motionEvent.getY() >= 8 * screenHeight / 9) {
                    pauseGameClick();
                }
        }
        return true;
    }

    /**
     * Changes the status of the playing boolean when the pause button is clicked.
     */
    private void pauseGameClick() {
        if (this.playing) {
            pause();
        } else {
            resume();
        }
    }

    /**
     * Returns the controller of the current Snake game.
     * @return the game's controller
     */
    public SnakeController getSnakeController() {
        return snakeController;
    }
}