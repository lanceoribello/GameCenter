package fall2018.csc2017.GameCenter.GameCenter.snake;

import java.util.Random;

/**
 * Adapted (loosely) from https://androidgameprogramming.com/programming-a-snake-game/
 * The controller for Snake; manages the game's logic.
 * Encompasses the starting and resuming of games, the saving and loading of games, setting the
 * difficulty of the game, spawning bombs and apples, and managing the snake's growth and movement.
 */
public class SnakeController {

    /**
     * The default FPS for easy mode.
     */
    private final static long EASY_MODE_FPS = 7;

    /**
     * The default FPS for hard mode.
     */
    private final static long HARD_MODE_FPS = 10;

    /**
     * How much the FPS is increased once the snake reaches its maximum size.
     */
    private final static long FPS_INCREASE = 2;

    /**
     * The maximum snake length before the snake is reset and the game is sped up.
     */
    private final static int MAX_SNAKE_SIZE = 15;

    /**
     * An object array that contains any relevant data in the game used for saving and loading
     * autoSaves. Consists of: {snakeXs, snakeYs, mouseX, mouseY, snakeLength, score,
     * difficulty, direction, FPS, bombX, bombY}.
     */
    private Object[] autoSaveData;


    /**
     * An object array of game data that is updated every time the player reaches certain scores.
     * Used for setting automatic save points.
     * Consists of: {snakeXs, snakeYs, mouseX, mouseY, snakeLength, score,
     * difficulty, direction, FPS, bombX, bombY}.
     */
    private Object[] savePoint;

    /**
     * Determines when a save point is created at certain scores.
     */
    static final int SAVE_POINT_EVERY = 3;

    /**
     * The current snake's direction.
     * Set to right for new games by default.
     */
    private Direction direction = Direction.RIGHT;

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
     * The x value of the current bomb to be avoided.
     */
    private int bombX;

    /**
     * The y value of the current bomb to be avoided.
     */
    private int bombY;

    /**
     * The difficulty level of the game.
     */
    private String difficulty;

    /**
     * The height of the playable area in terms of the number of blocks.
     */
    private int numBlocksHigh;

    /**
     * The width of the playable area in terms of the number of blocks.
     */
    private final static int NUM_BLOCKS_WIDE = 20;

    /**
     * The frames per second of the current Snake game.
     */
    private long fps;

    /**
     * An instance of SnakeController that manages the logic of the Snake game.
     * Processes whether a new game is started or a past game is being loaded.
     *
     * @param difficulty  the difficulty of the current Snake game
     * @param oldSaveData the data used to load a saved game; can be null if a new game is started
     */
    SnakeController(String difficulty, Object[] oldSaveData, int numBlocksHigh) {
        this.numBlocksHigh = numBlocksHigh;
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
            fps = EASY_MODE_FPS;
        } else {
            fps = HARD_MODE_FPS;
        }
    }

    /**
     * Begins a new game of Snake.
     * Spawns a snake with length 1 in the left side of the grid area as well as an apple
     * and a bomb in random location.
     */
    private void startGame() {
        snakeLength = 1;
        snakeXs[0] = NUM_BLOCKS_WIDE / 5;
        snakeYs[0] = numBlocksHigh / 2;
        spawnApple();
        spawnBomb();
        score = 0;
    }

    /**
     * Resumes a past game of Snake using an Object array of saved data.
     *
     * @param oldSaveData saved data of a past Snake game Consists of: {snakeXs, snakeYs, mouseX,
     *                    mouseY, snakeLength, score, difficulty, direction, FPS, bombX, bombY}.
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
        fps = (long) oldSaveData[8];
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
     * Spawns an apple at a given location.
     *
     * @param x the x-value of the apple to be spawned
     * @param y the y-value of the apple to be spawned
     */
    private void spawnAppleAt(int x, int y) {
        appleX = x;
        appleY = y;
    }

    /**
     * Spawns an apple at a random location.
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
     * Spawns a new apple and a new bomb at random locations.
     * Also checks if the snake has reached its maximum length to determine whether the difficulty
     * should be increased and the snake's length should be reset.
     */
    private void eatApple() {
        snakeLength++;
        spawnApple();
        score = score + 1;
        spawnBomb();

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
     * making contact with one of its own body segments. A bomb touching the snake's head also
     * kills the snake.
     *
     * @return whether the snake has died
     */
    boolean detectDeath() {
        boolean dead = false;
        if (snakeXs[0] == -1) dead = true;
        if (snakeXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (snakeYs[0] == -1) dead = true;
        if (snakeYs[0] == numBlocksHigh) dead = true;
        if (snakeXs[0] == bombX && snakeYs[0] == bombY) {
            dead = true;
        }
        for (int i = snakeLength - 1; i > 0; i--) {
            //impossible for snake to eat itself if length is <= 4
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                dead = true;
            }
        }
        return dead;
    }

    /**
     * Returns an object array of all the data necessary for loading up the game again at a later
     * point.
     *
     * @return an object array of game data
     */
    Object[] createSaveData() {
        return new Object[]{snakeXs.clone(), snakeYs.clone(), appleX, appleY,
                snakeLength, score, difficulty, direction, fps, bombX, bombY};
    }

    /**
     * Creates a save point every time the player gets a number of points that is a multiple of
     * SAVE_POINT_EVERY.
     */
    private void createSavePoint() {
        if (getScore() % SAVE_POINT_EVERY == 0 && getScore() != 0) {
            savePoint = createSaveData();
        }
    }

    /**
     * Returns the current autoSave data of this Snake game.
     *
     * @return this game's current autoSave data
     */
    public Object[] getAutoSaveData() {
        return this.autoSaveData;
    }

    /**
     * Returns the data of the latest savePoint of this Snake game.
     *
     * @return the data of the latest savePoint
     */
    public Object[] getSavePoint() {
        return savePoint;
    }

    /**
     * Processes when a snake eats a apple on contact, the movement of the snake, and whether
     * a save point should be created.
     */
    void updateGame() {
        if (snakeXs[0] == appleX && snakeYs[0] == appleY) {
            eatApple();
            createSavePoint();
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
        fps += FPS_INCREASE;
        snakeLength = 1;
    }


    /**
     * Returns the current score of the Snake game.
     *
     * @return the current score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * The directions used for controlling movement.
     */
    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    /**
     * Return the x-value of the apple.
     *
     * @return the apple's x-value
     */
    int getAppleX() {
        return appleX;
    }

    /**
     * Return the y-value of the apple.
     *
     * @return the apple's y-value
     */
    int getAppleY() {
        return appleY;
    }

    /**
     * Return the x-value of the bomb.
     *
     * @return the bomb's x-value
     */
    int getBombX() {
        return bombX;
    }

    /**
     * Return the y-value of the bomb.
     *
     * @return the bomb's y-value
     */
    int getBombY() {
        return bombY;
    }

    /**
     * Return the x-values of the snake.
     *
     * @return the snake's x-values
     */
    int[] getSnakeXs() {
        return snakeXs;
    }

    /**
     * Return the y-values of the snake.
     *
     * @return the snake's y-values
     */
    int[] getSnakeYs() {
        return snakeYs;
    }

    /**
     * Return the length of the snake.
     *
     * @return the snake's length
     */
    int getSnakeLength() {
        return snakeLength;
    }

    /**
     * Return the snake's current direction of movement
     *
     * @return the snake's direction
     */
    Direction getDirection() {
        return direction;
    }

    /**
     * Sets the snake's direction of movement.
     *
     * @param direction the new direction the snake will move in
     */
    void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Return the current frames-per-second of the game.
     *
     * @return the game's frames-per-second
     */
    long getFps() {
        return fps;
    }

    /**
     * Sets the autoSaveData of the current game
     *
     * @param autoSaveData the game's current autoSaveData
     */
    void setAutoSaveData(Object[] autoSaveData) {
        this.autoSaveData = autoSaveData;
    }
}