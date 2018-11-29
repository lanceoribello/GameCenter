package fall2018.csc2017.GameCenter.GameCenter.lobby;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test Class for LoginController.
 */
public class LoginControllerTest {

    /**
     * The user accounts for testing.
     */
    private UserAccount tester = new UserAccount("nancyishaneelance", "207");

    /**
     * The user account list for testing.
     */
    private ArrayList<UserAccount> testUserAccountList = new ArrayList<>();

    /**
     * Sets up user account list to begin with tester.
     */
    @Before
    public void setUp() {
        testUserAccountList.add(tester);
    }

    /**
     * Clears the user account list.
     */
    @After
    public void tearDown() {
        testUserAccountList = new ArrayList<>();
    }

    /**
     * Tests if login returns the correct account after successful login.
     */
    @Test
    public void loginUserAccountSuccess() {
        assertEquals(tester, LoginController.loginUserAccount("nancyishaneelance",
                "207", testUserAccountList));
    }

    /**
     * Tests if login returns null after failed login (username points to user who doesn't exist).
     */
    @Test
    public void loginUserAccountWrongUsername() {
        assertNull(LoginController.loginUserAccount("group_0633", "207",
                testUserAccountList));
    }

    /**
     * Tests if login returns null after failed login (password is incorrect for user).
     */
    @Test
    public void loginUserAccountWrongPassword() {
        assertNull(LoginController.loginUserAccount("nancyishaneelance", "236",
                testUserAccountList));
    }

    /**
     * Tests if sign up returns the correct account after successful sign up.
     */
    @Test
    public void signUpUserAccountSuccess() {
        UserAccount newUserAccount = new UserAccount("new", "207");
        assertEquals(newUserAccount, LoginController.signUpUserAccount(
                "new", "207", testUserAccountList));

    }

    /**
     * Tests if sign up returns null after failed sign up (username points to user who
     * already exists).
     */
    @Test
    public void signUpUserAccountFail() {
        assertNull(LoginController.signUpUserAccount("nancyishaneelance", "236",
                testUserAccountList));
    }
}