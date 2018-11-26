package fall2018.csc2017.GameCenter.GameCenter.snake;

import java.text.DateFormat;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;

public class SnakeMenuController {
    /**
     * Update the user Account List.
     * @Param: UserAccount logged in
     * @Param: Saved point data for the current game.
     */
    public static void updateUserAccounts(UserAccount ua, Object[] savedData) {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        LoginActivity.userAccountList.remove(ua);
        ua.addSnakeGame(datetime, savedData);
        LoginActivity.userAccountList.add(ua);

    }



    /**
     * Make a list of games names for displaying in load games.
     * @Param: ua: UserAccount logged in.
     */
    public static String[] savedGamesList(UserAccount ua) {
        String[] games = new String[(ua.getSnakeGameNames().size())];
        int i = 0;
        for (String s : ua.getSnakeGameNames()) {
            games[i++] = s;
        }
        return games;
    }
}