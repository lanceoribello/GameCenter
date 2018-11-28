package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeMenuController;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeView;

import static org.junit.Assert.*;

public class SlidingTilesMenuControllerTest {
    /**
     * an Instance of UserAccount for testing purposes.
     */
    private UserAccount account = new UserAccount("testName", "testPassword");

    /**
     * The user account list for testing.
     */
    private ArrayList<UserAccount> testUserAccountList = new ArrayList<>();

    /**
     * Board Manager
     */
    private BoardManager boardManager;

    /**
     * Drawable tile ID's for 3x3 board.
     */
    private Integer[] tileIds3x3 = {2131099773, 2131099784, 2131099790,
            2131099791, 2131099792, 2131099793,
            2131099794, 2131099795, 2131099796};

    /**
     * Drawable tile ID's for 3x3 board.
     */
    private Integer[] tileIds4x4 = {2131099773, 2131099784, 2131099790, 2131099791,
            2131099792, 2131099793, 2131099794, 2131099795,
            2131099796, 2131099774, 2131099775, 2131099776,
            2131099777, 2131099778, 2131099779, 2131099780};

    /**
     * Drawable tile ID's for 3x3 board.
     */
    private Integer[] tileIds5x5 = {2131099773, 2131099784, 2131099790, 2131099791, 2131099792,
            2131099793, 2131099794, 2131099795, 2131099796, 2131099774,
            2131099775, 2131099776, 2131099777, 2131099778, 2131099779,
            2131099780, 2131099781, 2131099782, 2131099783, 2131099785,
            2131099786, 2131099787, 2131099788, 2131099789, 0};

    @Before
    public void setUp() {
        testUserAccountList.add(account);
    }

    @After
    public void tearDown() {
        testUserAccountList = new ArrayList<>();
    }

    @Test
    public void updateUserAccounts() {
        setUp();
        ArrayList<Integer> gameTileIds = new ArrayList<>(Arrays.asList(this.tileIds3x3));
        boardManager = new
                BoardManager(3, gameTileIds);
        SlidingTilesMenuController.updateUserAccounts(account, boardManager, testUserAccountList);
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        assertTrue(testUserAccountList.contains(account)&&
                account.getSlidingTilesGame(datetime).equals(boardManager)) ;
        tearDown();
    }

    @Test
    public void savedGamesList() {
        setUp();
        String gameName1 = "Game 1";
        ArrayList<Integer> gameTileIds3x3 = new ArrayList<>(Arrays.asList(this.tileIds3x3));
        BoardManager gameBoardManager1 = new BoardManager(3, gameTileIds3x3);
        String gameName2 = "Game 2";
        ArrayList<Integer> gameTileIds4x4 = new ArrayList<>(Arrays.asList(this.tileIds4x4));
        BoardManager gameBoardManager2 = new BoardManager(4, gameTileIds4x4);
        String gameName3 = "Game 3";
        ArrayList<Integer> gameTileIds5x5 = new ArrayList<>(Arrays.asList(this.tileIds5x5));
        BoardManager gameBoardManager3 = new BoardManager(5, gameTileIds5x5);
        account.addSlidingTilesGame(gameName1, gameBoardManager1);
        account.addSlidingTilesGame(gameName2, gameBoardManager2);
        account.addSlidingTilesGame(gameName3, gameBoardManager3);
        String[] gameNames = {gameName1, gameName2, gameName3};
        assertArrayEquals(gameNames, SlidingTilesMenuController.savedGamesList(account));
        tearDown();
    }
}