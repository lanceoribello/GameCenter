package fall2018.csc2017.GameCenter.GameCenter.lobby;

import java.util.ArrayList;

/**
 * The control of the login screen shown upon initial startup of the game.
 * Processes sign ups and log ins of userAccounts.
 */
public class LoginController {

    /**
     * Logs in (returns) the UserAccount if it exists and the username and password are correct.
     *
     * @param username the username of the userAccount to be logged in
     * @param password the password of the userAccount to be logged in
     * @param userAccountList the list of user accounts
     * @return currentUserAccount if successful login, null otherwise
     */
    public static UserAccount loginUserAccount(String username, String password,
                                        ArrayList<UserAccount> userAccountList) {
        for (UserAccount userAccount : userAccountList) {
            if (userAccount.getUsername().equals(username)
                    && userAccount.getPassword().equals(password)) {
                return userAccount;
            }
        }
        return null;
    }

    /**
     * Signs up (returns) new UserAccount if it does not already exist.
     *
     * @param username the username of the new UserAccount
     * @param password the password of the new UserAccount
     * @param userAccountList the list of user accounts
     * @return currentUserAccount if successful sign up, null otherwise
     */
    public static UserAccount signUpUserAccount(String username, String password,
                                     ArrayList<UserAccount> userAccountList) {
        UserAccount newUserAccount = new UserAccount(username, password);
        for (UserAccount userAccount : userAccountList) {
            if (userAccount.getUsername().equals(username)) {
                return null;
            }
        }
        return newUserAccount;
    }
}
