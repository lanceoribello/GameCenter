package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/*
Adapted from: https://androidgameprogramming.com/programming-a-snake-game/
 */

public class SnakeView extends SurfaceView implements Runnable {

    // All the code will run separately to the UI
    private Thread thread = null;
    // This variable determines when the game is playing
    // It is declared as volatile because
    // it can be accessed from inside and outside the thread
    private volatile boolean playing;

    // This is what we draw on
    Canvas canvas;
    // This is required by the Canvas class to do the drawing
    private SurfaceHolder holder;
    // This lets us control colors etc
    private Paint paint;

    // This will be a reference to the Activity
    Context context;

    // For tracking movement direction
    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    // Start by heading to the right
    private Direction direction = Direction.RIGHT;

    // What is the screen resolution
    private int screenWidth;
    int screenHeight;

    // Control pausing between updates
    private long nextFrameTime;
    // Update the game 10 times per second
    long FPS = 10;
    // There are 1000 milliseconds in a second
    final long MILLIS_IN_A_SECOND = 1000;
    // We will draw the frame much more often

    // The current score
    private int score;

    // The location in the grid of all the segments
    private int[] snakeXs;
    private int[] snakeYs;

    // How long is the snake at the moment
    private int snakeLength;

    // Where is the mouse
    private int mouseX;
    private int mouseY;

    // The size in pixels of a snake segment
    private int blockSize;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh; // determined dynamically;

    Object[] savePointData;

    public SnakeView(Context context) {
        super(context);
    }

    public SnakeView(Context context, Point size, String difficulty, Object[] oldSaveData) {
        super(context);

        this.context = context;

        screenWidth = size.x;
        screenHeight = size.y;

        //Determine the size of each block/place on the game board
        blockSize = screenWidth / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        numBlocksHigh = screenHeight / blockSize;

        // Initialize the drawing objects
        holder = getHolder();
        paint = new Paint();

        // If you score 200 you are rewarded with a crash achievement!
        snakeXs = new int[200];
        snakeYs = new int[200];

        if (difficulty.equals("easy")) {
            FPS = 10;
        } else {
            FPS = 14;
        }

        try {
            resumeOldGame(oldSaveData);
        } catch (NullPointerException e) {
            startGame();
        }
    }

    @Override
    public void run() {
        // The check for playing prevents a crash at the start
        // You could also extend the code to provide a pause feature
        while (playing) {
            if (checkForUpdate()) {
                updateGame();
                drawGame();
                setAutoSavePoint();
            }
        }
    }

    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public void startGame() {
        snakeLength = 1;
        snakeXs[0] = NUM_BLOCKS_WIDE / 2;
        snakeYs[0] = numBlocksHigh / 2;
        spawnMouse();
        score = 0;

        // Setup nextFrameTime so an update is triggered immediately
        nextFrameTime = System.currentTimeMillis();
    }

    public void resumeOldGame(Object[] oldSaveData) {
        snakeXs = (int[]) oldSaveData[0];
        snakeYs = (int[]) oldSaveData[1];
        spawnMouse((int) oldSaveData[2], (int) oldSaveData[3]);
        snakeLength = (int) oldSaveData[4];
        score = (int) oldSaveData[5];

        // Setup nextFrameTime so an update is triggered immediately
        nextFrameTime = System.currentTimeMillis();
    }

    public void spawnMouse(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    public void spawnMouse() {
        Random random = new Random();
        mouseX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        mouseY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    private void eatMouse() {
        snakeLength++;
        spawnMouse();
        score = score + 1;
    }

    private void moveSnake() {
        // Move the body
        for (int i = snakeLength; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];

            // Exclude the head because
            // the head has nothing in front of it
        }

        // Move the head in the appropriate direction
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

    public boolean detectDeath() {
        // Has the snake died?
        boolean dead = false;

        // Hit a wall?
        if (snakeXs[0] == -1) dead = true;
        if (snakeXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (snakeYs[0] == -1) dead = true;
        if (snakeYs[0] == numBlocksHigh) dead = true;

        // Eaten itself?
        for (int i = snakeLength - 1; i > 0; i--) {
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                dead = true;
            }
        }

        return dead;
    }

    public void setAutoSavePoint() {
        savePointData = new Object[]{snakeXs, snakeYs, mouseX, mouseY, snakeLength, score};
    }

    public void updateGame() {
        // Did the head of the snake touch the mouse?
        if (snakeXs[0] == mouseX && snakeYs[0] == mouseY) {
            eatMouse();
        }

        moveSnake();
    }

    public void drawGame() {
        // Prepare to draw
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            // Clear the screen with my favorite color
            canvas.drawColor(Color.argb(255, 102, 204, 255));

            // Set the color of the paint to draw the snake and mouse with
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Choose how big the score will be
            paint.setTextSize(30);
            canvas.drawText("Score:" + score, 10, 30, paint);

            //Draw the snake
            for (int i = 0; i < snakeLength; i++) {
                canvas.drawRect(snakeXs[i] * blockSize,
                        (snakeYs[i] * blockSize),
                        (snakeXs[i] * blockSize) + blockSize,
                        (snakeYs[i] * blockSize) + blockSize,
                        paint);
            }

            if (detectDeath()) {
                paint.setTextSize(150);
                canvas.drawText("GAME OVER", 10, 200, paint);
            }

            //draw the mouse
            canvas.drawRect(mouseX * blockSize,
                    (mouseY * blockSize),
                    (mouseX * blockSize) + blockSize,
                    (mouseY * blockSize) + blockSize,
                    paint);

            // Draw the whole frame
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean checkForUpdate() {

        // Are we due to update the frame
        if (nextFrameTime <= System.currentTimeMillis()) {
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime = System.currentTimeMillis() + MILLIS_IN_A_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }

    public int getScore() { return score; }

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
                if (motionEvent.getX() >= screenWidth / 2) {
                    switch (direction) {
                        case UP:
                            direction = Direction.RIGHT;
                            break;
                        case RIGHT:
                            direction = Direction.DOWN;
                            break;
                        case DOWN:
                            direction = Direction.LEFT;
                            break;
                        case LEFT:
                            direction = Direction.UP;
                            break;
                    }
                } else {
                    switch (direction) {
                        case UP:
                            direction = Direction.LEFT;
                            break;
                        case LEFT:
                            direction = Direction.DOWN;
                            break;
                        case DOWN:
                            direction = Direction.RIGHT;
                            break;
                        case RIGHT:
                            direction = Direction.UP;
                            break;
                    }
                }
        }
        return true;
    }
}