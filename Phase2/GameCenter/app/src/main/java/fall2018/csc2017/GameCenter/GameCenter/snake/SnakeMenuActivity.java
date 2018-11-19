package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.SlidingTilesBoardManager;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesGameActivity;
import fall2018.csc2017.GameCenter.GameCenter.slidingtiles.activities.SlidingTilesMenuActivity;
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
//        addLoadButtonListener();
//        addSaveButtonListener();
//        addLoadAutoSaveButtonListener();
    }

    /**
     * Switch to SnakeStartingActivity to play the game.
     */
    private void switchToGame(String difficulty) {
        Intent intent = new Intent(this, SnakeStartingActivity.class);
        intent.putExtra("currentUserAccount", currentUserAccount);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    /**
     * Activate the start button. Once the start button is pressed, a new alert dialog
     * prompts the user to choose between the 2 difficulties: easy, hard.
     *
     * @param view the current view.
     */
    public void newGame(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                SnakeMenuActivity.this);
        builder.setTitle("Choose a Difficulty Level");
        String[] difficulties = {"Easy", "Hard"};
        builder.setItems(difficulties, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        switchToGame("easy");
                        break;
                    case 1:
                        switchToGame("hard");
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
