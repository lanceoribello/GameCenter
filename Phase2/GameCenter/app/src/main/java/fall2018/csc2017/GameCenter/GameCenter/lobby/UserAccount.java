package fall2018.csc2017.GameCenter.GameCenter.lobby;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fall2018.csc2017.GameCenter.GameCenter.blocks.GridManager;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.SlidingTilesBoardManager;

/**
 * Stores a user account's account information.
 */
public class UserAccount implements Serializable {

    /**
     * The user account's username.
     */
    private String username;

    /**
     * The user account's password.
     */
    private String password;

    /**
     * Map of Sliding Tiles games of user account.
     * Keys: The names of the user account's past Sliding Tiles games.
     * Values: The board manager of each game.
     */
    private Map<String, SlidingTilesBoardManager> slidingTilesGameNames;

    /**
     * Map of Snake games of user account.
     * Keys: The names of the user account's past Snake games.
     * Values: The saved data of each game.
     */
    private Map<String, Object[]> snakeGameNames;

    /**
     * Map of Blocks games of user account.
     * Keys: The names of the user account's past Blocks games.
     * Values: The grid state of each game.
     */
    private Map<String, GridManager> blocksGameNames;

    /**
     * Map of scores.
     * Keys: The game levels.
     * Values: The user account's scores for each game level.
     */
    private Map<String, Integer> scores;

    /**
     * String Array of the game level names.
     */
    public static final String[] gameLevels = {"Sliding Tiles 3x3", "Sliding Tiles 4x4",
            "Sliding Tiles 5x5", "Snake Easy Mode", "Snake Hard Mode", "Blocks"};

    /**
     * An instance of a user account with a username, password, and a blank list of game names.
     * Default top score for Sliding Tiles is 1000000, which displays as "None" on the scoreboard.
     * Default top score for Snake and Blocks is 0, which displays as "None" on the scoreboard.
     *
     * @param username the username of the user account
     * @param password the password of the user account
     */
    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.slidingTilesGameNames = new HashMap<>();
        this.snakeGameNames = new HashMap<>();
        this.blocksGameNames = new HashMap<>();
        this.scores = new HashMap<>();
        // Set the default starting scores
        for (int i = 0; i < gameLevels.length; i++) {
            if (i < 3) {
                this.scores.put(gameLevels[i], 1000000);
            } else {
                this.scores.put(gameLevels[i], 0);
            }
        }
    }

    /**
     * Returns the username of the user account.
     *
     * @return the user account's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user account.
     *
     * @return the user account's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the top score of the specified game level for the user account.
     *
     * @param levelName name of the game's level as stored in the score map
     * @return top score of specified game level
     */
    public Integer getTopScore(String levelName) {
        if (this.scores.containsKey(levelName)) {
            return this.scores.get(levelName);
        } else {
            return null;
        }
    }

    /**
     * Sets the top score of the specified game level for the user account.
     *
     * @param levelName name of the game's level as stored in the score map
     * @param newScore  new top score of specified game level
     */
    public void setTopScore(String levelName, Integer newScore) {
        this.scores.replace(levelName, newScore);
    }

    /**
     * Adds a new game name to this user account.
     *
     * @param newName a new game name added to the user account
     * @param game    the board manager of the new game
     */
    public void addSlidingTilesGame(String newName, SlidingTilesBoardManager game) {
        this.slidingTilesGameNames.put(newName, game);
    }

    /**
     * Get a game saved by the game name.
     *
     * @param gameName a game name saved to this user account
     */
    public SlidingTilesBoardManager getSlidingTilesGame(String gameName) {
        if (this.slidingTilesGameNames.containsKey(gameName)) {
            return this.slidingTilesGameNames.get(gameName);
        } else {
            return null;
        }
    }

    /**
     * Returns this user account's key set of saved Sliding Tiles game names.
     *
     * @return key set of this user account's saved Sliding Tiles game names
     */
    public Set<String> getSlidingTilesGameNames() {
        return slidingTilesGameNames.keySet();
    }

    /**
     * Adds a new game name to this user account
     *
     * @param newName   a new game name added to the user account
     * @param savedData saved data of the new game
     */
    public void addSnakeGame(String newName, Object[] savedData) {
        this.snakeGameNames.put(newName, savedData);
    }

    /**
     * Get a game saved by the game name.
     *
     * @param gameName a game name saved to the user account
     * @return saved data of the game
     */
    public Object[] getSnakeGame(String gameName) {
        if (this.snakeGameNames.containsKey(gameName)) {
            return this.snakeGameNames.get(gameName);
        } else {
            return null;
        }
    }

    /**
     * Returns this user account's key set of saved Snake game names.
     *
     * @return key set of this user account's saved Snake game names
     */
    public Set<String> getSnakeGameNames() {
        return snakeGameNames.keySet();
    }

    /**
     * Adds a new game name to this user account
     *
     * @param newName   a new game name added to the user account
     * @param gridManager grid manager of the new game
     */
    public void addBlocksGame(String newName, GridManager gridManager) {
        this.blocksGameNames.put(newName, gridManager);
    }

    /**
     * Get a game saved by the game name.
     *
     * @param gameName a game name saved to the user account
     * @return grid manager of the game
     */
    public GridManager getBlocksGame(String gameName) {
        if (this.blocksGameNames.containsKey(gameName)) {
            return this.blocksGameNames.get(gameName);
        } else {
            return null;
        }
    }

    /**
     * Returns this user account's key set of saved Blocks game names.
     *
     * @return key set of this user account's saved Blocks game names
     */
    public Set<String> getBlocksGameNames() {
        return blocksGameNames.keySet();
    }

    /**
     * Returns whether Object o equals this UserAccount - only if username and password match.
     * @param o Object to compare to
     * @return true if UserAccounts are equal
     */
    @Override
    public boolean equals(Object o) {
        return ((o != null) &&
                (this.getClass() == o.getClass()) &&
                (this.getUsername().equals(((UserAccount) o).getUsername())) &&
                (this.getPassword().equals(((UserAccount) o).getPassword())));
    }
}
