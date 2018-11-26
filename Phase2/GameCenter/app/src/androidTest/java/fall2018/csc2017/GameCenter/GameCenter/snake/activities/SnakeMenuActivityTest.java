package fall2018.csc2017.GameCenter.GameCenter.snake.activities;

import android.content.Context;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeView;

import static org.junit.Assert.*;

public class SnakeMenuActivityTest {
    /**
     * an Instance of UserAccount for testing purposes.
     */
    private UserAccount account = new UserAccount("testName", "testPassword");
    /**
     *  A dummy userAccountList
     */
    private ArrayList<UserAccount> userAccounts = new ArrayList<>();
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
    private Context menu = new SnakeMenuActivity().getApplicationContext();
    /**
     * Some data to write and load
     */
    private Object[] savedData = {1, 1, 2, 2, 1, 0, "Snake Easy Mode", SnakeView.Direction.RIGHT};

    /**
     * Initial file with some Data.
     */
    @Before
    private void fillFilewithData(){
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
     * Fill up the list.
     */
    @Before
    private void setUserAccounts() {
        userAccounts.add(new UserAccount("Paul", "yougetanA"));
        userAccounts.add(new UserAccount("Lindsey", "seconDED"));
        userAccounts.add(new UserAccount("David", "groupOfTheYear"));
        userAccounts.add(account);
    }
    /**
     * Test for update User Accounts list
     */
    @Test
    private void testUpdateUserAccounts(){
        boolean updated = false;
        fillFilewithData();
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String datetime = dateFormat.format(c.getTime());
        userAccounts.remove(account);
        loadFromTempFile();
        account.addSnakeGame(datetime, savedData);
        userAccounts.add(account);
        //check if the game was added with the proper data
        if (userAccounts.contains(account) && account.getSnakeGame(datetime).equals(savedData)){
            updated = true;
        }
        assertTrue(updated);
    }
    @Test
    private void testListOfSavedGames(){
        Object[] savedData1 = {2, 2, 3, 3, 2, 0, "Snake Easy Mode", SnakeView.Direction.RIGHT};
        Object[] savedData2 = {3, 3, 4, 4, 3, 0, "Snake Easy Mode", SnakeView.Direction.RIGHT};
        account.addSnakeGame("game1", savedData);
        account.addSnakeGame("game2", savedData1);
        account.addSnakeGame("game3", savedData2);
        Set<String> expected = new HashSet<>(Arrays.asList("game1", "game2", "game3"));
        assertEquals(account.getSnakeGameNames(),expected );
    }

}