package fall2018.csc2017.GameCenter.GameCenter.slidingtiles;

import java.text.DateFormat;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;

public class SlidingTilesMenuController {
    /**
     * Saves a new game to the currentUserAccount with game name as date and time.
     */
    public static void updateUserAccounts(UserAccount ua, BoardManager bm) {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        LoginActivity.userAccountList.remove(ua);
        ua.addSlidingTilesGame(datetime, bm);
        LoginActivity.userAccountList.add(ua);
    }
    /**
     * Makes a list of previously saved Games to set for view in load Game.
     */

    /**
     * Make a list of games names for displaying in load games.
     * @Param: ua: UserAccount logged in.
     */
    public static String[] savedGamesList(UserAccount ua) {
        String[] games = new String[(ua.getSlidingTilesGameNames().size())];
        int i = 0;
        for (String s : ua.getSlidingTilesGameNames()) {
            games[i++] = s;
        }
        return games;
    }

}
