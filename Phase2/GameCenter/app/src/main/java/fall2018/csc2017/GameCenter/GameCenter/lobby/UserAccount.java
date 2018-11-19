package fall2018.csc2017.GameCenter.GameCenter.lobby;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.SlidingTilesBoardManager;

/**
 * Stores a user's account information.
 */
public class UserAccount implements Serializable {

    /**
     * The account user's username.
     */
    private String username;

    /**
     * The account user's password.
     */
    private String password;

    /**
     * The account user's top score for Snake on easy mode.
     */
    private int easySnakeScore;

    /**
     * The account user's top score for Snake on hard mode.
     */
    private int hardSnakeScore;

    /**
     * Returns the user account's top score for Snake on easy mode.
     *
     * @return the user's top score for Snake on easy mode.
     */
    public int getEasySnakeScore() {
        return easySnakeScore;
    }

    /**
     * Sets the account top score for Snake on easy mode.
     *
     * @param easySnakeScore the account's new top score for Snake on easy mode
     */
    public void setEasySnakeScore(int easySnakeScore) {
        this.easySnakeScore = easySnakeScore;
    }

    /**
     * Returns the user account's top score for Snake on hard mode.
     *
     * @return the user's top score for Snake on hard mode.
     */
    public int getHardSnakeScore() {
        return hardSnakeScore;
    }

    /**
     * Sets the account top score for Snake on hard mode.
     *
     * @param hardSnakeScore the account's new top score for Snake on hard mode
     */
    public void setHardSnakeScore(int hardSnakeScore) {
        this.hardSnakeScore = hardSnakeScore;
    }

    /**
     * The account user's top score for boards of 3x3 complexity in Sliding Tiles.
     */
    private int slidingTilesTop3x3;

    /**
     * The account user's top score for boards of 4x4 complexity in Sliding Tiles.
     */
    private int slidingTilesTop4x4;

    /**
     * The account user's top score for boards of 5x5 complexity in Sliding Tiles.
     */
    private int slidingTilesTop5x5;

    /**
     * The names of the account user's past Sliding Tiles games.
     */
    private Map<String, SlidingTilesBoardManager> slidingTilesGameNames;

    /**
     * The names of the account user's past Snake games.
     */
    private Map<String, Object[]> snakeGameNames;

    /**
     * An instance of an account with a username, password, and a blank list of game names.
     * Default top score is 1000000, which displays as "None" on the scoreboards.
     *
     * @param username the username of the account
     * @param password the password of the account
     */
    UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.slidingTilesGameNames = new HashMap<>();
        this.snakeGameNames = new HashMap<>();
        this.slidingTilesTop3x3 = 1000000;
        this.slidingTilesTop4x4 = 1000000;
        this.slidingTilesTop5x5 = 1000000;
        this.easySnakeScore = 0;
        this.hardSnakeScore = 0;
    }


    /**
     * Returns the username of the account.
     *
     * @return the account's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the account.
     *
     * @return the account's password
     */
    String getPassword() {
        return password;
    }

    /**
     * Returns the account's top score for boards of 3x3 complexity in Sliding Tiles.
     *
     * @return the account's top score for boards of 3x3 complexity in Sliding Tiles.
     */
    public int getSlidingTilesTop3x3() {
        return slidingTilesTop3x3;
    }

    /**
     * Sets the account's top score for boards of 3x3 complexity in Sliding Tiles.
     *
     * @param top3x3 the account's new top 3x3 score in Sliding Tiles
     */
    public void setSlidingTilesTop3x3(int top3x3) {
        this.slidingTilesTop3x3 = top3x3;
    }

    /**
     * Returns the account's top score for boards of 4x4 complexity in Sliding Tiles.
     *
     * @return the account's top score for boards of 4x4 complexity in Sliding Tiles
     */
    public int getSlidingTilesTop4x4() {
        return slidingTilesTop4x4;
    }

    /**
     * Sets the account's top score for boards of 4x4 complexity in Sliding Tiles.
     *
     * @param top4x4 the account's new top 4x4 score in Sliding Tiles
     */
    public void setSlidingTilesTop4x4(int top4x4) {
        this.slidingTilesTop4x4 = top4x4;
    }

    /**
     * Returns the account's top score for boards of 5x5 complexity in Sliding Tiles.
     *
     * @return the account's top score for boards of 5x5 complexity in Sliding Tiles
     */
    public int getSlidingTilesTop5x5() {
        return slidingTilesTop5x5;
    }

    /**
     * Sets the account's top score for boards of 5x5 complexity in Sliding Tiles.
     *
     * @param top5x5 the account's new top 5x5 score in Sliding Tiles
     */
    public void setSlidingTilesTop5x5(int top5x5) {
        this.slidingTilesTop5x5 = top5x5;
    }

    /**
     * Adds a new game name to this account
     *
     * @param newName a new name added to the account
     */
    public void addSlidingTilesGame(String newName, SlidingTilesBoardManager game) {
        this.slidingTilesGameNames.put(newName, game);
    }

    /**
     * Get a game saved by the name gameName.
     */
    public SlidingTilesBoardManager getSlidingTilesGame(String gameName) {
        if (this.slidingTilesGameNames.containsKey(gameName)) {
            return this.slidingTilesGameNames.get(gameName);
        } else {
            return null;
        }
    }

    /**
     * Returns this UserAccount's key set of saved game names.
     * @return key set of this UserAccount's saved game names
     */
    public Set<String> getSlidingTilesGameNames() {
        return slidingTilesGameNames.keySet();
    }
    /**
     * Adds a new game name to this account
     *
     * @param newName a new name added to the account
     */
    public void addSnakeGame(String newName, Object[] savedData) {
        this.snakeGameNames.put(newName, savedData);
    }
    /**
     * Get a game saved by the name gameName.
     */
    public Object[] getSnakeGame(String gameName) {
        if (this.snakeGameNames.containsKey(gameName)) {
            return this.snakeGameNames.get(gameName);
        } else {
            return null;
        }
    }
    /**
     * Returns this UserAccount's key set of saved game names.
     * @return key set of this UserAccount's saved game names
     */
    public Set<String> getSnakeGameNames() {
        return snakeGameNames.keySet();
    }

}
