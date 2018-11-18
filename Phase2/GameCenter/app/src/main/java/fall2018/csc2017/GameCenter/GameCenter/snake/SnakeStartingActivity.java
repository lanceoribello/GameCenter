package fall2018.csc2017.GameCenter.GameCenter.snake;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.Display;
import android.widget.Button;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import fall2018.csc2017.GameCenter.GameCenter.R;
import fall2018.csc2017.GameCenter.GameCenter.lobby.LoginActivity;
import fall2018.csc2017.GameCenter.GameCenter.lobby.UserAccount;
import android.graphics.Point;
/*
Adapted from: https://androidgameprogramming.com/programming-a-snake-game/
 */

public class SnakeStartingActivity extends AppCompatActivity{

    // Declare an instance of SnakeView
    SnakeGameActivity snakeView;
    // We will initialize it in onCreate
    // once we have more details about the Player's device

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //find out the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new View based on the SnakeView class
        snakeView = new SnakeGameActivity(this, size);

        // Make snakeView the default view of the Activity
        setContentView(snakeView);
    }

    // Start the thread in snakeView when this Activity
    // is shown to the player
    @Override
    protected void onResume() {
        super.onResume();
        snakeView.resume();
    }

    // Make sure the thread in snakeView is stopped
    // If this Activity is about to be closed
    @Override
    protected void onPause() {
        super.onPause();
        snakeView.pause();
    }
}
