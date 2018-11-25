package fall2018.csc2017.GameCenter.GameCenter.lobby.activities;

import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GameSelectActivityTest {

    @Rule
    public ActivityTestRule<GameSelectActivity> gameSelectActivityRule =
            new ActivityTestRule<>(GameSelectActivity.class);

    @Test
    public void onCreate() {
    }

    @Test
    public void setCurrentUserAccount() {
    }

    @Test
    public void onResume() {
    }

    /**
     * Check if the user account list in the file matches the set one.
     */
    @Test
    public void testSetCurrentUserAccountMatch() {
    }
}