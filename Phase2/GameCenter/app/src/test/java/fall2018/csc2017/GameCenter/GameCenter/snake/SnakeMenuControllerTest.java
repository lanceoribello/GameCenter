package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;

import java.util.Calendar;


import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;


import static org.junit.Assert.*;

public class SnakeMenuControllerTest{
    /**
     * an Instance of UserAccount for testing purposes.
     */
    private UserAccount account = new UserAccount("testName", "testPassword");

    /**
     * The user account list for testing.
     */
    private ArrayList<UserAccount> testUserAccountList = new ArrayList<>();

    /**
     * Sets up user account list to begin with tester.
     */
    @Before
    public void setUp() {
        testUserAccountList.add(account);
    }

    /**
     * Some data to write and load
     */
    private Object[] savedData = {1, 1, 2, 2, 1, 0, "Snake Easy Mode", SnakeView.Direction.RIGHT};
    /**
     * Clears the user account list.
     */
    @After
    public void tearDown() {
        testUserAccountList = new ArrayList<>();
    }

    /**
     * test for updateUserAccounts
     */
    @Test

    public void testUpdateUserAccounts(){
        setUp();
        SnakeMenuController.updateUserAccounts(account, savedData, testUserAccountList);
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        assertTrue(testUserAccountList.contains(account)&&
                account.getSnakeGame(datetime).equals(savedData)) ;
        tearDown();
    }

    /**
     * test that the saved games list has all the names of previously saved games and an autosave
     */
    @Test
    public void testSavedGamesList(){
        setUp();
        int[] snakeXs = {0};
        int[] snakeYs = {0};
        String gameName1 = "Game 1";
        Object[] gameSavedData1 = {snakeXs, snakeYs, 2, 2, 1, 0, "Snake Easy Mode",
                SnakeView.Direction.RIGHT, 10, 5, 5};
        String gameName2 = "Game 2";
        Object[] gameSavedData2 = {snakeXs, snakeYs, 3, 3, 1, 0, "Snake Hard Mode",
                SnakeView.Direction.LEFT, 14, 5, 5};
        String gameName3 = "Game 3";
        Object[] gameSavedData3 = {snakeXs, snakeYs, 4, 4, 1, 0, "Snake Easy Mode",
                SnakeView.Direction.UP, 10, 5, 5};
        account.addSnakeGame(gameName1, gameSavedData1);
        account.addSnakeGame(gameName2, gameSavedData2);
        account.addSnakeGame(gameName3, gameSavedData3);
        String[] gameNames = {gameName1, gameName2, gameName3};
        assertEquals(gameNames, SnakeMenuController.savedGamesList(account));
        tearDown();
    }
}