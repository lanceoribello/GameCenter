package fall2018.csc2017.GameCenter.GameCenter.blocks;

import java.util.Random;
import java.util.ArrayList;

/**
 * The grid used for the Blocks game.
 * A new grid can be initialized to begin a new game, or a grid from a previous game can be loaded.
 * Contains methods for changing the grid's values: ex) moving the player, spawning food,
 * placing blocks.
 */
public class Grid {
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
     * The int representing a food's location on the grid.
     */
    final static int FOOD = 3;

    /**
     * The length of the grid.
     */
    final static int GRID_LENGTH = 13;

    /**
     * The number of food that must be on the grid at any point in the game.
     */
    final static int FOOD_NUM = 4;

    /**
     * The 2d int array representing the state of each coordinate-pair on the grid.
     */
    int[][] gridState;

    /**
     * The arrayList of all non-border block x-values on the grid.
     */
    private ArrayList<Integer> blockXs = new ArrayList<Integer>();

    /**
     * The arrayList of all non-border block y-values on the grid.
     */
    private ArrayList<Integer> blockYs = new ArrayList<Integer>();

    /**
     * The arrayList of all food x-values currently on the grid.
     */
    private ArrayList<Integer> foodXs = new ArrayList<Integer>();

    /**
     * The arrayList of all food y-values currently on the grid.
     */
    private ArrayList<Integer> foodYs = new ArrayList<Integer>();

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
        gridState = new int[GRID_LENGTH][GRID_LENGTH];
        generateEmptyGrid();
        playerX = GRID_LENGTH / 2;
        playerY = GRID_LENGTH / 2;
        gridState[playerX][playerY] = PLAYER;
        spawnMultipleFoods(FOOD_NUM);
    }

    Grid(int pX, int pY, int[] blockXs, int[] blockYs, int[] foodXs, int[] foodYs) {
        gridState = new int[GRID_LENGTH][GRID_LENGTH];
        generateEmptyGrid();
        this.playerX = pX;
        this.playerY = pY;
        gridState[playerX][playerY] = PLAYER;
        placeObjectsFromData(blockXs, blockYs, "block");
        placeObjectsFromData(foodXs, foodYs, "food");

    }

    /**
     * Returns all the necessary data to load this grid at a later point.
     *
     * @return an object array of the data
     */
    public Object[] saveData() {
        return new Object[]{playerX, playerY, intArrayListToArray(blockXs),
                intArrayListToArray(blockYs), intArrayListToArray(foodXs),
                intArrayListToArray(foodYs)};
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
    public void placeObjectsFromData(int[] objXs, int[] objYs, String foodOrBlock) {
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
        gridState = new int[GRID_LENGTH][GRID_LENGTH];
        for (int row = 0; row != GRID_LENGTH; row++) {
            for (int col = 0; col != GRID_LENGTH; col++) {
                if (row == 0 || col == 0 || row == GRID_LENGTH - 1 || col == GRID_LENGTH - 1) {
                    gridState[row][col] = BLOCK;
                } else {
                    gridState[row][col] = EMPTY;
                }
            }
        }
    }

    /**
     * Spawns a food at a random empty location.
     */
    public void spawnFood() {
        Random random = new Random();
        int foodX = random.nextInt(GRID_LENGTH - 1) + 1;
        int foodY = random.nextInt(GRID_LENGTH - 1) + 1;
        while (gridState[foodX][foodY] != EMPTY) {
            foodX = random.nextInt(GRID_LENGTH - 1) + 1;
            foodY = random.nextInt(GRID_LENGTH - 1) + 1;
        }
        gridState[foodX][foodY] = FOOD;
        foodXs.add(foodX);
        foodYs.add(foodY);
    }

    /**
     * Spawns multiple foods at random locations.
     */
    public void spawnMultipleFoods(int howMany) {
        for (int a = 0; a < howMany; a++) {
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
        gridState[x][y] = FOOD;
        foodXs.add(x);
        foodYs.add(y);
    }

    /**
     * Places a block at a specific location.
     *
     * @param x the x-value of the block on the grid
     * @param y the y-value of the block on the grid
     */
    public void placeBlockAt(int x, int y) {
        gridState[x][y] = BLOCK;
        blockXs.add(x);
        blockYs.add(y);
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
     * @param makeMove whether the method shall move the player or not
     * @return the maximum number of grid-squares the player can move up or down
     */
    public int verticalMove(int direction, boolean makeMove) {
        ArrayList<Integer> emptyValuesY = new ArrayList<Integer>();
        int yVal = playerY + direction;
        boolean foodEaten = false;
        while (gridState[playerX][yVal] != BLOCK) {
            emptyValuesY.add(yVal);
            if (gridState[playerX][yVal] == FOOD) {
                foodEaten = true;
                eatFood(playerX, yVal);
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
            gridState[playerX][playerY] = EMPTY;
            int newXY = emptyValues.get(emptyValues.size() - 1);
            if (foodEaten) {
                newXY = xyVal;
            }
            if (vertical) {
                gridState[playerX][newXY] = PLAYER;
                playerY = newXY;
            } else {
                gridState[newXY][playerY] = PLAYER;
                playerX = newXY;
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
     * Returns how far the player has moved.
     * Precondition: direction must be 1 or -1; 1 accounts for rightward movement, -1 for leftward.
     *
     * @param direction which direction the method checks for
     * @param makeMove  whether the method shall move the player or not
     * @return the maximum number of grid-squares the player can move left or right
     */
    public int horizontalMove(int direction, boolean makeMove) {
        ArrayList<Integer> emptyValuesX = new ArrayList<>();
        int xVal = playerX + direction;
        boolean foodEaten = false;
        while (gridState[xVal][playerY] != BLOCK) {
            emptyValuesX.add(xVal);
            if (gridState[xVal][playerY] == FOOD) {
                foodEaten = true;
                eatFood(xVal, playerY);
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
        gridState[x][y] = EMPTY;
        for (int i = 0; i < foodXs.size(); i++) {
            if (foodXs.get(i) == x && foodYs.get(i) == y) {
                foodXs.remove(i);
                foodYs.remove(i);
                break;
            }
        }
        score++;
        spawnFood();
    }

    /**
     * Returns whether a specific location on the grid is a valid location to place a block.
     *
     * @param x the x-value of the location
     * @param y the y-value of the location
     * @return whether the location is empty
     */
    public boolean validBlockPlacement(int x, int y) {
        return gridState[x][y] == EMPTY;
    }

    /**
     * Getter for the arrayList of all non-border block x-values on the grid converted into an
     * int array.
     *
     * @return an int array of all non-border block x-values on the grid.
     */
    public int[] getBlockXsIntArray() {
        return intArrayListToArray(blockXs);
    }

    /**
     * Getter for the arrayList of all non-border block y-values on the grid converted into an
     * int array.
     *
     * @return an int array of all non-border block y-values on the grid
     */
    public int[] getBlockYsIntArray() {
        return intArrayListToArray(blockYs);
    }

    /**
     * Getter for the arrayList of all food x-values on the grid converted into an int array.
     *
     * @return an int array of all food x-values on the grid
     */
    public int[] getFoodXsIntArray() {
        return intArrayListToArray(foodXs);
    }

    /**
     * Getter for the arrayList of all food y-values on the grid converted into an int array.
     *
     * @return an int array of all food y-values on the grid
     */
    public int[] getFoodYsIntArray() {
        return intArrayListToArray(foodYs);
    }

    /**
     * Returns the x-value of the player on the grid.
     *
     * @return the player's x-value on the grid
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Returns the y-value of the player on the grid.
     *
     * @return the player's y-value on the grid
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Returns the score of the player for the current grid.
     *
     * @return the player's score
     */
    public int getScore() { return score; }
}
