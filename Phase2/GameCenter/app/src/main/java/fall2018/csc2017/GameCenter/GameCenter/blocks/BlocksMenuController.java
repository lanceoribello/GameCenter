package fall2018.csc2017.GameCenter.GameCenter.blocks;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;

/**
 * The controller for the Blocks Menu.
 */
public class BlocksMenuController {

    /**
     * Saves a new game to the currentUserAccount with game name as date and time.
     */
    public static void updateUserAccounts(UserAccount userAccount, GridManager gridManager,
                                          ArrayList<UserAccount> userAccountArrayList) {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        userAccountArrayList.remove(userAccount);
        userAccount.addBlocksGame(datetime, gridManager);
        userAccountArrayList.add(userAccount);
    }

    /**
     * Make a list of games names for displaying in load games.
     */
    public static String[] savedGamesList(UserAccount userAccount) {
        String[] games = new String[(userAccount.getBlocksGameNames().size())];
        int i = 0;
        for (String s : userAccount.getBlocksGameNames()) {
            games[i++] = s;
        }
        return games;
    }
}
