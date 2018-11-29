package fall2018.csc2017.GameCenter.GameCenter.blocks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;

import static org.junit.Assert.*;

public class BlockMenuControllerTest {
    /**
     * An instance of UserAccount for testing purposes.
     */
    private UserAccount account = new UserAccount("testName", "testPassword");

    /**
     * The user account list for testing.
     */
    private ArrayList<UserAccount> testUserAccountList = new ArrayList<>();

    /**
     * Some data to write and load.
     */
    private GridManager gridManager;

    @Before
    public void setUp() {
        testUserAccountList.add(account);
    }

    /**
     * Clears the user account list.
     */
    @After
    public void tearDown() {
        testUserAccountList = new ArrayList<>();
    }


    @Test
    public void updateUserAccounts() {
        gridManager = new GridManager();
        BlockMenuController.updateUserAccounts(account, gridManager, testUserAccountList);
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        assertTrue(testUserAccountList.contains(account)&&
                account.getBlocksGame(datetime).equals(gridManager));

    }

    @Test
    public void savedGamesList() {
        String gameName1 = "Game 1";
        GridManager gameGridManager1 = new GridManager();
        String gameName2 = "Game 2";
        GridManager gameGridManager2 = new GridManager();
        String gameName3 = "Game 3";
        GridManager gameGridManager3 = new GridManager();
        account.addBlocksGame(gameName1, gameGridManager1);
        account.addBlocksGame(gameName2, gameGridManager2);
        account.addBlocksGame(gameName3, gameGridManager3);
        String[] gameNames = {gameName1, gameName2, gameName3};
        assertArrayEquals(gameNames, BlockMenuController.savedGamesList(account));
    }
}