package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesGameActivity;
import fall2018.csc2017.GameCenter.GameCenter.snake.activities.SnakeStartingActivity;

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
        addLoadButtonListener();
        addSaveButtonListener();
        addLoadAutoSaveButtonListener();
        addNewGameListener();
    }

    /**
     * Activate the new game button.
     */
    private void addNewGameListener() {
        Button newGame = findViewById(R.id.NewSnake);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(getApplicationContext(), SnakeStartingActivity.class);
                tmp.putExtra("currentUserAccount", currentUserAccount);
                startActivity(tmp);
            }
        });
    }

    /**
     * Activate the load game button.
     */
    private void addLoadButtonListener() {
        Button newGame = findViewById(R.id.LoadSnake);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    /**
     * Activate the load game button.
     */
    private void addSaveButtonListener() {
        Button newGame = findViewById(R.id.SaveSnake);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    /**
     * Activate the load game button.
     */
    private void addLoadAutoSaveButtonListener() {
        Button newGame = findViewById(R.id.AutoSnake);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
