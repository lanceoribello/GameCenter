package fall2018.csc2017.GameCenter.GameCenter.lobby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesMenuActivity;
import fall2018.csc2017.GameCenter.GameCenter.snake.SnakeMenuActivity;

/**
 * The game select screen shown after login screen.
 */
public class GameSelectActivity extends AppCompatActivity {

    /**
     * The current user account obtained from the login screen.
     */
    private UserAccount currentUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserAccount = (UserAccount) getIntent().getSerializableExtra(
                "currentUserAccount");
        setContentView(R.layout.activity_game_select);
        addSlidingTilesButtonListener();
        addSnakeButtonListener();
        addScoreboardButtonListener();
    }

    /**
     * Activate the sliding tiles button.
     */
    private void addSlidingTilesButtonListener() {
        Button slidingTilesButton = findViewById(R.id.slidingTilesButton);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SlidingTilesMenuActivity.class);
                intent.putExtra("currentUserAccount", currentUserAccount);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the snake button.
     */

    private void addSnakeButtonListener() {
        Button slidingTilesButton = findViewById(R.id.snake);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SnakeMenuActivity.class);
                intent.putExtra("currentUserAccount", currentUserAccount);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the View ScoreboardActivity button.
     */
    private void addScoreboardButtonListener() {
        Button scoreboardButton = findViewById(R.id.ScoreboardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboard();
            }
        });
    }

    /**
     * Switch to the ScoreboardActivity view to view the per-user and per-game scoreboards.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        tmp.putExtra("currentUserAccount", currentUserAccount);
        startActivity(tmp);
    }
}
