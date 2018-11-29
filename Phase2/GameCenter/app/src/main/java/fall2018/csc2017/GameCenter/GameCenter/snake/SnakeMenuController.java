package fall2018.csc2017.GameCenter.GameCenter.snake;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;

public class SnakeMenuController {
    /**
     * Update the user Account List.
     */
    public static void updateUserAccounts(UserAccount ua, Object[] savedData, ArrayList<UserAccount>
            ual) {
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        ual.remove(ua);
        ua.addSnakeGame(datetime, savedData);
        ual.add(ua);

    }

    /**
     * Make a list of games names for displaying in load games.
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