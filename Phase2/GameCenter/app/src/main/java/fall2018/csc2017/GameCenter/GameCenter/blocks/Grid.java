package fall2018.csc2017.GameCenter.GameCenter.blocks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * The grid used for the Blocks game.
 * A new grid can be initialized to begin a new game, or a grid from a previous game can be loaded.
 * Contains methods for changing the grid's values: ex) moving the player, spawning food,
 * placing blocks.
 */
public class Grid implements Serializable {

    /**
     * The int representing an empty location on the grid.
     */
    final static int EMPTY = 0;

    /**
     * The int representing the player's location on the grid.
     */
    final static int PLAYER = 1;

    /**
     * The int representing a block's location on the grid.
     */
    final static int BLOCK = 2;
    /**
     * The length of the grid.
     */
    final static int GRID_LENGTH = 9;
    /**
     * The int representing a food's location on the grid.
     */
    final static int FOOD = 3;
    /**
     * The number of food that must be on the grid at any point in the game.
     */
    private final static int FOOD_NUM = 4;

    /**
     * The 2d int array representing the state of each coordinate-pair on the grid.
     */
    int[][] gridState;

    /**
     * The arrayList of all non-border block x-values on the grid.
     */
    private ArrayList<Integer> blockXs = new ArrayList<>();

    /**
     * The arrayList of all non-border block y-values on the grid.
     */
    private ArrayList<Integer> blockYs = new ArrayList<>();

    /**
     * The arrayList of all food x-values currently on the grid.
     */
    private ArrayList<Integer> foodXs = new ArrayList<>();

    /**
     * The arrayList of all food y-values currently on the grid.
     */
    private ArrayList<Integer> foodYs = new ArrayList<>();

    /**
     * The x-value of the player on the grid.
     */
    private int playerX;

    /**
     * The y-value of the player on the grid.
     */
    private int playerY;

    /**
     * The score of the current game of Blocks.
     */
    private int score;

    /**
     * A new grid for the blocks game wherein the borders of the grid are all blocks, the player
     * is spawned in the middle of the grid, and a number of foods is spawned in random locations
     * of the grid.
     */
    Grid() {
        this.gridState = new int[GRID_LENGTH][GRID_LENGTH];
        generateEmptyGrid();
        this.playerX = GRID_LENGTH / 2;
        this.playerY = GRID_LENGTH / 2;
        this.gridState[this.playerX][this.playerY] = PLAYER;
        spawnMultipleFoods();
        this.score = 0;
    }

    /**
     * Creates a grid for the blocks games from the given data.
     *
     * @param pX      the x-value of the player
     * @param pY      the y-value of the player
     * @param blockXs the x-values of the blocks on the grid
     * @param blockYs the y-values of the blocks on the grid
     * @param foodXs  the x-values of the food on the grid
     * @param foodYs  the y-values of the food on the grid
     */
    Grid(int pX, int pY, int[] blockXs, int[] blockYs, int[] foodXs, int[] foodYs, int score) {
        this.gridState = new int[GRID_LENGTH][GRID_LENGTH];
        generateEmptyGrid();
        this.playerX = pX;
        this.playerY = pY;
        this.gridState[this.playerX][this.playerY] = PLAYER;
        placeObjectsFromData(foodXs, foodYs, "food");
        placeObjectsFromData(blockXs, blockYs, "block");
        this.score = score;
    }

    /**
     * Converts an arrayList of integers into an int array, returning the new int array.
     *
     * @param aList the arrayList to be converted
     * @return an int array corresponding to the values of aList
     */
    private int[] intArrayListToArray(ArrayList<Integer> aList) {
        int[] newArray = new int[aList.size()];
        for (int i = 0; i < aList.size(); i++) {
            if (aList.get(i) != null) {
                newArray[i] = aList.get(i);
            }
        }
        return newArray;
    }

    /**
     * Places either blocks or food onto the grid from a set of x-values and y-values.
     *
     * @param objXs       the x-values of the objects to be placed
     * @param objYs       the y-values of the objects to be placed
     * @param foodOrBlock determines whether food or blocks are being placed
     */
    private void placeObjectsFromData(int[] objXs, int[] objYs, String foodOrBlock) {
        for (int i = 0; i != objXs.length; i++) {
            if (foodOrBlock.equals("food")) {
                spawnFoodAt(objXs[i], objYs[i]);
            } else {
                placeBlockAt(objXs[i], objYs[i]);
            }
        }
    }

    /**
     * Sets the gridState to be completely empty, other than the blocks at the grid's borders.
     */
    private void generateEmptyGrid() {
        this.gridState = new int[GRID_LENGTH][GRID_LENGTH];
        for (int row = 0; row != GRID_LENGTH; row++) {
            for (int col = 0; col != GRID_LENGTH; col++) {
                if (row == 0 || col == 0 || row == GRID_LENGTH - 1 || col == GRID_LENGTH - 1) {
                    this.gridState[row][col] = BLOCK;
                } else {
                    this.gridState[row][col] = EMPTY;
                }
            }
        }
    }

    /**
     * Spawns a food at a random empty location.
     */
    private void spawnFood() {
        Random random = new Random();
        int foodX = random.nextInt(GRID_LENGTH - 3) + 1;
        int foodY = random.nextInt(GRID_LENGTH - 3) + 1;
        while (this.gridState[foodX][foodY] != EMPTY) {
            foodX = random.nextInt(GRID_LENGTH - 3) + 1;
            foodY = random.nextInt(GRID_LENGTH - 3) + 1;
        }
        this.gridState[foodX][foodY] = FOOD;
        this.foodXs.add(foodX);
        this.foodYs.add(foodY);
    }

    /**
     * Spawns multiple foods at random locations.
     */
    private void spawnMultipleFoods() {
        for (int a = 0; a < FOOD_NUM; a++) {
            spawnFood();
        }
    }

    /**
     * Places a food at a specific location.
     *
     * @param x the x-value of the food on the grid
     * @param y the y-value of the food on the grid
     */
    private void spawnFoodAt(int x, int y) {
        this.gridState[x][y] = FOOD;
        this.foodXs.add(x);
        this.foodYs.add(y);
    }

    /**
     * Places a block at a specific location if it is empty.
     *
     * @param x the x-value of the block on the grid
     * @param y the y-value of the block on the grid
     */
    void placeBlockAt(int x, int y) {
        if (this.gridState[x][y] == EMPTY) {
            this.gridState[x][y] = BLOCK;
            this.blockXs.add(x);
            this.blockYs.add(y);
        }
    }

    /**
     * Returns how far the player is able to move vertically (players cannot move past blocks and
     * stop moving at food).
     * If makeMove is true:
     * Moves the player the maximum number of grid-squares the player can move in the up or down
     * direction until it meets a food or a block (the player stops before a block and stops
     * when a food is eaten).
     * Precondition: direction must be 1 or -1; 1 accounts for upward movement, -1 for downward.
     *
     * @param direction which direction is used; up or down
     * @param makeMove  whether the method shall move the player or not
     * @return the maximum number of grid-squares the player can move up or down
     */
    int verticalMove(int direction, boolean makeMove) {
        ArrayList<Integer> emptyValuesY = new ArrayList<>();
        int yVal = this.playerY + direction;
        boolean foodEaten = false;
        while (this.gridState[this.playerX][yVal] != BLOCK) {
            emptyValuesY.add(yVal);
            if (this.gridState[this.playerX][yVal] == FOOD) {
                foodEaten = true;
                break;
            }
            if (direction == 1) {
                yVal++;
            } else {
                yVal--;
            }
        }
        if (makeMove) {
            moveHelper(emptyValuesY, foodEaten, yVal, true);
        }
        return emptyValuesY.size();
    }

    /**
     * Helper method for verticalMove and horizontalMove.
     *
     * @param emptyValues an arrayList of either x or y values which correspond to either playerX
     *                    or playerY that are empty on the grid
     * @param foodEaten   whether a food was eaten during the player's movement
     * @param xyVal       the x or y location of where the food was eaten (if a food was eaten at all)
     * @param vertical    whether the helper method is being used for vertical or horizontal movement
     */
    private void moveHelper(ArrayList<Integer> emptyValues, boolean foodEaten, int xyVal,
                            boolean vertical) {
        if (emptyValues.size() != 0) {
            this.gridState[this.playerX][this.playerY] = EMPTY;
            int newXY = emptyValues.get(emptyValues.size() - 1);
            if (foodEaten) {
                newXY = xyVal;
                if (vertical) {
                    eatFood(this.playerX, newXY);
                } else {
                    eatFood(newXY, this.playerY);
                }
            }
            if (vertical) {
                this.gridState[this.playerX][newXY] = PLAYER;
                this.playerY = newXY;
            } else {
                this.gridState[newXY][this.playerY] = PLAYER;
                this.playerX = newXY;
            }
        }
    }

    /**
     * Returns how far the player is able to move horizontally (players cannot move past blocks and
     * stop moving at food).
     * If makeMove is true:
     * Moves the player the maximum number of grid-squares the player can move in the left or right
     * direction until it meets a food or a block (the player stops before a block and stops
     * when a food is eaten).
     * Precondition: direction must be 1 or -1; 1 accounts for rightward movement, -1 for leftward.
     *
     * @param direction which direction the method checks for
     * @param makeMove  whether the method shall move the player or not
     * @return the maximum number of grid-squares the player can move left or right
     */
    int horizontalMove(int direction, boolean makeMove) {
        ArrayList<Integer> emptyValuesX = new ArrayList<>();
        int xVal = this.playerX + direction;
        boolean foodEaten = false;
        while (this.gridState[xVal][this.playerY] != BLOCK) {
            emptyValuesX.add(xVal);
            if (this.gridState[xVal][this.playerY] == FOOD) {
                foodEaten = true;
                break;
            }
            if (direction == 1) {
                xVal++;
            } else {
                xVal--;
            }
        }
        if (makeMove) {
            moveHelper(emptyValuesX, foodEaten, xVal, false);
        }
        return emptyValuesX.size();
    }

    /**
     * Processes the eating of food at a specific location, incrementing the score by 1.
     *
     * @param x the x-value where the food is eaten
     * @param y the y-value where the food is eaten
     */
    private void eatFood(int x, int y) {
        spawnFood();
        this.gridState[x][y] = EMPTY;
        for (int i = 0; i < foodXs.size(); i++) {
            if (this.foodXs.get(i) == x && this.foodYs.get(i) == y) {
                this.foodXs.remove(i);
                this.foodYs.remove(i);
                break;
            }
        }
        this.score++;
    }

    /**
     * Getter for the arrayList of all non-border block x-values on the grid converted into an
     * int array.
     *
     * @return an int array of all non-border block x-values on the grid.
     */
    int[] getBlockXsIntArray() {
        return intArrayListToArray(this.blockXs);
    }

    /**
     * Getter for the arrayList of all non-border block y-values on the grid converted into an
     * int array.
     *
     * @return an int array of all non-border block y-values on the grid
     */
    int[] getBlockYsIntArray() {
        return intArrayListToArray(this.blockYs);
    }

    /**
     * Getter for the arrayList of all food x-values on the grid converted into an int array.
     *
     * @return an int array of all food x-values on the grid
     */
    int[] getFoodXsIntArray() {
        return intArrayListToArray(this.foodXs);
    }

    /**
     * Getter for the arrayList of all food y-values on the grid converted into an int array.
     *
     * @return an int array of all food y-values on the grid
     */
    int[] getFoodYsIntArray() {
        return intArrayListToArray(this.foodYs);
    }

    /**
     * Returns the x-value of the player on the grid.
     *
     * @return the player's x-value on the grid
     */
    int getPlayerX() {
        return this.playerX;
    }

    /**
     * Returns the y-value of the player on the grid.
     *
     * @return the player's y-value on the grid
     */
    int getPlayerY() {
        return this.playerY;
    }

    /**
     * Returns the score of the player for the current grid.
     *
     * @return the player's score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the score of the player for the current grid.
     *
     * @param newScore new score to set to
     */
    public void setScore(int newScore) {
        this.score = newScore;
    }

    /**
     * Returns whether Object o equals this Grid.
     * Compares for pX, pY, blockXs, blockYs, foodXs, foodYs, score
     *
     * @param o Object to compare to
     * @return true if Grids are equal
     */
    @Override
    public boolean equals(Object o) {
        return ((o != null) && (this.getClass() == o.getClass()) &&
                (this.getPlayerX() == ((Grid) o).getPlayerX()) &&
                (this.getPlayerY() == ((Grid) o).getPlayerY()) &&
                (Arrays.equals(this.getBlockXsIntArray(), ((Grid) o).getBlockXsIntArray())) &&
                (Arrays.equals(this.getBlockYsIntArray(), ((Grid) o).getBlockYsIntArray())) &&
                (Arrays.equals(this.getFoodXsIntArray(), ((Grid) o).getFoodXsIntArray())) &&
                (Arrays.equals(this.getFoodYsIntArray(), ((Grid) o).getFoodYsIntArray())) &&
                (this.getScore() == ((Grid) o).getScore()));
    }
}
