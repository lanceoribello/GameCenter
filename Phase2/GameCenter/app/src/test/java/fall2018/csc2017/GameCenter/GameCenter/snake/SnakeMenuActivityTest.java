package fall2018.csc2017.GameCenter.GameCenter.snake;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.lobby.activities.LoginActivity;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeView.Direction;
import fall2018.csc2017.GameCenter.GameCenter.snake.activities.SnakeMenuActivity;

import static fall2018.csc2017.GameCenter.GameCenter.snake.SnakeView.Direction.*;
import static org.junit.Assert.*;

//Find a way to test if switch to game initiates the correct board.
//Test saving and loading by serialising and deserialising
//Test helper methods for saving and loading.



public class SnakeMenuActivityTest {
    /**
     * Useraccount for testing.
     */
    private UserAccount userAccount = new UserAccount("testName", "testPassword");
    /**
     * test saving file
     */
    public static final String TEMP_SAVE_FILENAME = "snakeTest_save_file_tmp.ser";

    /**
     * SnakeMenuActivity instance to test its methods
     */
    @Mock
    SnakeMenuActivity menu;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    /**
     * check if userAccountList was updated when a game was saved.
     */
    public void listIsUpdated(){
//        Object[] savedData = [[1,2,3]
    }



}