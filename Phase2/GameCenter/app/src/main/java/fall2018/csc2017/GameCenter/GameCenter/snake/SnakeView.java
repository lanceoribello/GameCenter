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

import java.util.Random;

import fall2018.csc2017.GameCenter.GameCenter.R;

/*
Adapted from: https://androidgameprogramming.com/programming-a-snake-game/
Manages a Snake game, running separately from the UI.
Keeps track of

 */
public class SnakeView extends SurfaceView implements Runnable {

    /**
     * The text size of the displayed score.
     */
    private final static int SCORE_TEXT_SIZE = 40;
    /**
     * The size of the GAME OVER text.
     */
    private final static int GAME_OVER_SIZE = 150;
    /**
     * The default FPS for easy mode.
     */
    private final static long EASY_MODE_FPS = 10;
    /**
     * The default FPS for hard mode.
     */
    private final static long HARD_MODE_FPS = 14;
    /**
     * How much the FPS is increased once the snake reaches its maximum size.
     */
    private final static long FPS_INCREASE = 2;
    /**
     * The maximum snake length before the snake is reset and the game is sped up.
     */
    private final static int MAX_SNAKE_SIZE = 15;
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
    /**
     * The current snake's direction.
     * Set to right for new games by default.
     */
    private Direction direction = Direction.RIGHT;
    /**
     * The width of the screen being displayed upon.
     */
    private int screenWidth;
    /**
     * The long that controls when the game will be updated next.
     */
    private long nextFrameTime;
    /**
     * The frames per second of the current Snake game.
     */
    private long FPS;
    /**
     * The current score of the game.
     */
    private int score;
    /**
     * The locations of all x-values of the snake.
     */
    private int[] snakeXs;
    /**
     * The locations of all y-values of the snake.
     */
    private int[] snakeYs;
    /**
     * The current length of the snake.
     */
    private int snakeLength;
    /**
     * The x value of the current apple to be eaten.
     */
    private int appleX;
    /**
     * The y value of the current apple to be eaten.
     */
    private int appleY;
    /**
     * The x value of the current bomb to be avoided
     */
    private int bombX;
    /**
     * The y value of the current bomb to be avoided
     */
    private int bombY;
    /**
     * The size in pixels of a block for the game display.
     * Corresponds to the size of an individual snake segment and the size of a apple.
     */
    private int blockSize;
    /**
     * The height of the playable area in terms of the number of blocks.
     * Determined dynamically.
     */
    private int numBlocksHigh;
    /**
     * The difficulty level of the game.
     */
    private String difficulty;

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
        //bottom third of the screen used for the movement buttons
        numBlocksHigh = 2 * (screenHeight / blockSize) / 3;
        holder = getHolder();
        paint = new Paint();
        snakeXs = new int[MAX_SNAKE_SIZE];
        snakeYs = new int[MAX_SNAKE_SIZE];
        try {
            resumeOldGame(oldSaveData);
        } catch (NullPointerException e) {
            setDifficulty(difficulty);
            startGame();
        }
    }

    /**
     * Sets the difficulty level of the game, which is based on how many frames per second are
     * shown. A higher number of frames per second results in the snake moving faster.
     *
     * @param difficulty the game's difficulty
     */
    private void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        if (difficulty.equals("Snake Easy Mode")) {
            FPS = EASY_MODE_FPS;
        } else {
            FPS = HARD_MODE_FPS;
        }
    }

    @Override
    public void run() {
        while (playing) {
            if (checkForUpdate()) {
                updateGame();
                drawGame();
                setAutoSavePoint();
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
     * Begins a new game of Snake.
     * Spawns a snake with length 1 in the middle of the grid area as well as a apple in a random
     * location.
     */
    private void startGame() {
        snakeLength = 1;
        snakeXs[0] = NUM_BLOCKS_WIDE / 2;
        snakeYs[0] = numBlocksHigh / 2;
        spawnApple();
        spawnBomb();
        score = 0;
        // Setup nextFrameTime so an update is triggered immediately
        nextFrameTime = System.currentTimeMillis();
    }

    /**
     * Resumes a past game of Snake using an Object array of saved data.
     *
     * @param oldSaveData saved data of a past Snake game Consists of: {snakeXs, snakeYs, mouseX,
     * mouseY, snakeLength, score, difficulty, direction, FPS, bombX, bombY}.
     */
    private void resumeOldGame(Object[] oldSaveData) {
        snakeXs = (int[]) oldSaveData[0];
        snakeYs = (int[]) oldSaveData[1];
        spawnAppleAt((int) oldSaveData[2], (int) oldSaveData[3]);
        spawnBombAt((int) oldSaveData[9], (int) oldSaveData[10]);
        snakeLength = (int) oldSaveData[4];
        score = (int) oldSaveData[5];
        difficulty = (String) oldSaveData[6];
        direction = (Direction) oldSaveData[7];
        FPS = (long) oldSaveData[8];
        // Setup nextFrameTime so an update is triggered immediately
        nextFrameTime = System.currentTimeMillis();
    }

    /**
     * Spawns a bomb at a given location
     *
     * @param x x coordinate of bomb
     * @param y y coordinate of bomb
     */
    private void spawnBombAt(int x, int y) {
        bombX = x;
        bombY = y;
    }

    /**
     * Spawns a apple at a given location.
     *
     * @param x the x-value of the apple to be spawned
     * @param y the y-value of the apple to be spawned
     */
    private void spawnAppleAt(int x, int y) {
        appleX = x;
        appleY = y;
    }


    /**
     * Spawns a apple at a random location.
     */
    private void spawnApple() {
        Random random = new Random();
        appleX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        appleY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    /**
     * Spawn a bomb at a random location
     */

    private void spawnBomb() {
        Random random = new Random();
        bombX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        bombY = random.nextInt(numBlocksHigh - 1) + 1;

    }

    /**
     * Processes a snake eating a apple, increasing its length and the score by 1.
     * Spawns a new apple at a random location.
     * Also checks if the snake has reached its maximum length to determine whether the difficulty
     * should be increased.
     */
    private void eatApple() {
        snakeLength++;
        spawnApple();
        score = score + 1;
        if (score % 2 == 0) {
            spawnBomb();
        }
        if ((snakeLength) % (MAX_SNAKE_SIZE) == 0) {
            increaseDifficulty();
        }
    }


    /**
     * Moves all segments of the snake by one.
     */
    private void moveSnake() {
        for (int i = snakeLength; i > 0; i--) {
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];
        }
        switch (direction) {
            case UP:
                snakeYs[0]--;
                break;
            case RIGHT:
                snakeXs[0]++;
                break;
            case DOWN:
                snakeYs[0]++;
                break;
            case LEFT:
                snakeXs[0]--;
                break;
        }
    }

    /**
     * Returns whether the snake has died, either through hitting the wall of the game area or by
     * making contact with one of its own body segments. If the snake[0] touches a bomb the game
     * ends as well and the snake is now dead.
     *
     * @return whether the snake has died
     */
    public boolean detectDeath() {
        boolean dead = false;
        if (snakeXs[0] == -1) dead = true;
        if (snakeXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (snakeYs[0] == -1) dead = true;
        if (snakeYs[0] == numBlocksHigh) dead = true;

        if (snakeXs[0] == bombX && snakeYs[0] == bombY) {
            dead = true;
        }

        for (int i = snakeLength - 1; i > 0; i--) {
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                dead = true;
            }
        }
        return dead;
    }

    /**
     * Sets the savePointData object array to include all the data necessary for loading up the
     * current game again at a later point.
     */
    public void setAutoSavePoint() {
        savePointData = new Object[]{snakeXs, snakeYs, appleX, appleY, snakeLength, score,
                difficulty, direction, FPS, bombX, bombY};
    }

    /**
     * Returns the current save point data of this Snake game.
     *
     * @return this game's current save point data
     */
    public Object[] getSavePointData() {
        return this.savePointData;
    }

    /**
     * Processes when a snake eats a apple on contact and the movement of the snake.
     */
    private void updateGame() {
        if (snakeXs[0] == appleX && snakeYs[0] == appleY) {
            eatApple();
        }
        if (!detectDeath()) {
            moveSnake();
        }
    }

    /**
     * Increases the difficulty of the game, resetting the snake's length to 1 and increasing
     * the FPS.
     */
    private void increaseDifficulty() {
        FPS += FPS_INCREASE;
        snakeLength = 1;
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
     * Draws the bomb
     */
    private void drawBomb() {
        Paint p = new Paint();
        Bitmap bomb = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
        canvas.drawBitmap(bomb, null, new Rect(bombX * blockSize,
                (bombY * blockSize),
                (bombX * blockSize) + blockSize,
                (bombY * blockSize) + blockSize), p);
    }

    /**
     * Draws the apple
     */
    private void drawApple() {
        Paint p = new Paint();
        Bitmap apple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        canvas.drawBitmap(apple, null, new Rect(appleX * blockSize,
                (appleY * blockSize),
                (appleX * blockSize) + blockSize,
                (appleY * blockSize) + blockSize), p);
    }

    /**
     * Draws the score text and the GAME OVER text.
     */
    private void drawText() {
        paint.setTextSize(SCORE_TEXT_SIZE);
        canvas.drawText("Score:" + score, 10, SCORE_TEXT_SIZE, paint);
        if (detectDeath()) {
            paint.setTextSize(GAME_OVER_SIZE);
            canvas.drawText("GAME OVER", 10, GAME_OVER_SIZE + 50, paint);
        }
    }

    /**
     * Draws the snake.
     */
    private void drawSnake() {
        paint.setColor(Color.argb(255, 255, 255, 255));
        for (int i = 0; i < snakeLength; i++) {
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
     * Returns the current score of the Snake game.
     *
     * @return the current score of the game
     */
    public int getScore() {
        return score;
    }

    //Necessary for onTouchEvent to have no warnings
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    /**
     * Changes the direction of our snake based on if the mouseClick event falls in the bounds
     * of the 4 black rectangles I set up as buttons to move up, down, left or right
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        performClick();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenWidth / 3
                        && motionEvent.getX() <= 2 * screenWidth / 3
                        && motionEvent.getY() <= 7 * screenHeight / 9
                        && motionEvent.getY() >= 2 * screenHeight / 3) {
                    direction = Direction.UP;
                } else if (motionEvent.getX() >= screenWidth / 3
                        && motionEvent.getX() <= 2 * screenWidth / 3
                        && motionEvent.getY() <= screenHeight
                        && motionEvent.getY() >= 8 * screenHeight / 9) {
                    direction = Direction.DOWN;
                } else if (motionEvent.getX() >= 2 * screenWidth / 3
                        && motionEvent.getX() <= screenWidth
                        && motionEvent.getY() <= 8 * screenHeight / 9
                        && motionEvent.getY() >= 7 * screenHeight / 9) {
                    direction = Direction.RIGHT;
                } else if (motionEvent.getX() >= 0
                        && motionEvent.getX() <= screenWidth / 3
                        && motionEvent.getY() <= 8 * screenHeight / 9
                        && motionEvent.getY() >= 7 * screenHeight / 9) {
                    direction = Direction.LEFT;
                }


        }
        return true;
    }

    /**
     * The directions used for controlling movement.
     */
    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
}