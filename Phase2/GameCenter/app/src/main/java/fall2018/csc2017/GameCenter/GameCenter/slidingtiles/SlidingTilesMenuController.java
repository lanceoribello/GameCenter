package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;

/**
 * The controller for the Sliding Tiles Menu.
 */
public class SlidingTilesMenuController {

    /**
     * Saves a new game to the currentUserAccount with game name as date and time.
     */
    public static void updateUserAccounts(UserAccount userAccount, BoardManager boardManager,
                                          ArrayList<UserAccount> userAccountArrayList) {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        userAccountArrayList.remove(userAccount);
        userAccount.addSlidingTilesGame(datetime, boardManager);
        userAccountArrayList.add(userAccount);
    }

    /**
     * Make a list of games names for displaying in load games.
     */
    public static String[] savedGamesList(UserAccount userAccount) {
        String[] games = new String[(userAccount.getSlidingTilesGameNames().size())];
        int i = 0;
        for (String s : userAccount.getSlidingTilesGameNames()) {
            games[i++] = s;
        }
        return games;
    }
}
