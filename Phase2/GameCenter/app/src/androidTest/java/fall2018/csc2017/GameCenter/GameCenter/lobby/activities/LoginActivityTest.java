package fall2018.csc2017.GameCenter.GameCenter.lobby.activities;

import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import fall2018.csc2017.GameCenter.GameCenter.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void onCreate() {
    }

    @Test
    public void onClick() {
        onView(withId(R.id.Username))
                .perform(typeText("Username"), closeSoftKeyboard());
        onView(withId(R.id.Password))
                .perform(typeText("Password"), closeSoftKeyboard());
    }
}