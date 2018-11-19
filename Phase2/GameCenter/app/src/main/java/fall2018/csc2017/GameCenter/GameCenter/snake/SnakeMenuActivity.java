package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesGameActivity;

public class SnakeMenuActivity extends AppCompatActivity {
    /**
     * The current user account obtained from the game select screen.
     */
    private UserAccount currentUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_menu);
        currentUserAccount =
                (UserAccount) getIntent().getSerializableExtra("currentUserAccount");
//        addLoadButtonListener();
//        addSaveButtonListener();
//        addLoadAutoSaveButtonListener();
          addNewGameListener();
    }
    private void addNewGameListener(){
        Intent tmp = new Intent(this, SnakeMenuActivity.class);
        tmp.putExtra("currentUserAccount", currentUserAccount);
        startActivity(tmp);
    }
}
