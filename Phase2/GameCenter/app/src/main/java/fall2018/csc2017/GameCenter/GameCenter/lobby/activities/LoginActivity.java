package fall2018.csc2017.GameCenter.GameCenter.lobby.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.LoginController;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;

/**
 * The login screen shown upon initial startup of the game.
 * Processes sign ups and log ins of userAccounts.
 * Passes on the current signed-in userAccount to GameSelectActivity.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * The file containing an arrayList of all created instances of UserAccounts.
     */
    public static final String USER_ACCOUNTS_FILENAME = "accounts.ser";

    /**
     * An ArrayList of UserAccounts, read and stored in a file.
     */
    public static ArrayList<UserAccount> userAccountList;

    /**
     * The UserAccount that will be logged in; will be passed onto GameSelectActivity.
     */
    private UserAccount currentUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userAccountList = new ArrayList<>();
        setUserAccountList();
        setContentView(R.layout.activity_login);
        addSignUpButtonListener();
    }

    /**
     * Called when the user taps the login button.
     * If the login credentials are valid, sends the currentUserAccount to GameSelectActivity
     * then sets the view to GameSelectActivity.
     */
    public void onClick(View view) {
        EditText usernameView = findViewById(R.id.Username);
        EditText passwordView = findViewById(R.id.Password);
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
        Intent intent = new Intent(view.getContext(), GameSelectActivity.class);
        setUserAccountList();
        currentUserAccount = LoginController.loginUserAccount(username, password, userAccountList);
        if (currentUserAccount == null) {
            makeToastFailedText();
        } else {
            makeToastAcceptedText();
            intent.putExtra("currentUserAccount", currentUserAccount);
            startActivity(intent);
        }
    }

    /**
     * Called when the user taps the sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = findViewById(R.id.Signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameView = findViewById(R.id.Username);
                EditText passwordView = findViewById(R.id.Password);
                String username = usernameView.getText().toString();
                String password = passwordView.getText().toString();
                setUserAccountList();
                currentUserAccount = LoginController.signUpUserAccount(username, password,
                        userAccountList);
                if (currentUserAccount == null) {
                    makeToastExistsText();
                } else {
                    userAccountList.add(currentUserAccount);
                    userAccountsToFile(USER_ACCOUNTS_FILENAME);
                    makeToastSignUpAcceptedText();
                }
            }
        });
    }

    /**
     * Display that the login was successful.
     */
    private void makeToastAcceptedText() {
        Toast.makeText(this, "Credentials accepted", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the login was unsuccessful.
     */
    private void makeToastFailedText() {
        Toast.makeText(this, "Try Again or Sign up", Toast.LENGTH_LONG).show();
    }

    /**
     * Display that a sign up was successful.
     */
    private void makeToastSignUpAcceptedText() {
        Toast.makeText(this, "Sign Up successful. Please Login to continue.",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Display that a sign up failed because user already exists.
     */
    private void makeToastExistsText() {
        Toast.makeText(this, "Account already exists.", Toast.LENGTH_LONG).show();
    }

    /**
     * Saves the userAccountList to a file.
     *
     * @param fileName the name of the file
     */
    public void userAccountsToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userAccountList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Sets the userAccountList to the list of UserAccounts read from
     * the file USER_ACCOUNTS_FILENAME.
     */
    private void setUserAccountList() {
        try {
            InputStream inputStream = this.openFileInput(USER_ACCOUNTS_FILENAME);
            if (inputStream == null) {
                userAccountsToFile(USER_ACCOUNTS_FILENAME);
            } else {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userAccountList = (ArrayList<UserAccount>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }
}
