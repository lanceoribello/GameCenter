package fall2018.csc2017.GameCenter.GameCenter.snake.activities;

import android.content.Context;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashSet;
import java.util.Set;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeView;

import static org.junit.Assert.*;

public class SnakeMenuActivityTest {
    //Test that the object loaded is correct.
    //Test that an overwritten object was loaded correctly.
    //Test that the userAccounts to File was saved with proper changes.
    /**
     * an Instance of UserAccount for testing purposes.
     */
    private UserAccount account = new UserAccount("testName", "testPassword");
    /**
     *  A dummy userAccountList
     */
    private ArrayList<UserAccount> testUserAccountList = new ArrayList<>();
    /**
     * Temp File for data reading and writing
     */
    public static final String TEMP_SAVE_FILENAME = "snakeTest_save_file_tmp.ser";
    /**
     * Temp File for data reading and writing
     */
    public static final String ACCOUNTS_SAVE_FILENAME = "snakeTest_accounts_file_tmp.ser";
    /**
     * An instance of SnakeMenuActivity
     */
    public Context menu = new SnakeMenuActivity();
    /**
     * int arrays specifying a snakes position. Used as data in saved Data
     */
    private  int[] snakeXs = {0};
    private  int[] snakeYs = {0};
    /**
     * Some data to write and load
     */
    private Object[] savedData = {snakeXs, snakeYs, 2, 2, 1, 0, "Snake Easy Mode",
            SnakeView.Direction.RIGHT, 10, 5, 5};

    /**
     * Initial file with some Data.
     */
    /**
     * Sets up user account list to begin with tester.
     */
    @Before
    public void setUp() {
        testUserAccountList.add(account);
    }
    @Before
    public void fillTempFilewithData(){
        FileOutputStream fos;
        try {
            fos = menu.openFileOutput(TEMP_SAVE_FILENAME, menu.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(savedData);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void loadFromTempFile(){
        FileInputStream fis;
        try {
            fis = menu.openFileInput(TEMP_SAVE_FILENAME);


            ObjectInputStream ois = new ObjectInputStream(fis);
            savedData = (Object[]) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        }

    /**
     * Test for update User Accounts list
     */
    @Test
    public void testUserAccountsToFile(){

    }
    @Test
    public void testLoadFromTempFile(){
        fillTempFilewithData();
        FileInputStream fis;
        Object[] got;
        try {
            fis = menu.openFileInput(TEMP_SAVE_FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            got = (Object[]) ois.readObject();
            ois.close();
            assertEquals(savedData, got);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}