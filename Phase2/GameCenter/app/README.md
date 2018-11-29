# Setup Instructions

Open Android Studio

Select Checkout from version control --> select GIT and paste this URL and clone directory:

URL: https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0633

Select yes when you are prompted to create an android studio project for the sources

You will then be prompted to Import Project. Select the option to import project from
external model.

There will be 2 options, Android Gradle and Gradle --> Select gradle and press next

Select the "Use local gradle distribution", and set your Gradle Home: to your gradle directory
An example of the directory if you're having trouble finding it may be:
 \Program files\Android\Android Studio\gradle\gradle-4.6

Now, go to file --> Close project

You should now be back to the Android studio home page. Select Open an existing Android studio
project

Open up group_0633 -> Phase 2 -> GameCentre

Once the project is open, File -> Sync Project With Gradle Files

Create new virtual device: Pixel 2, Oreo API 27 Android 8.1

You can now run the app by clicking Run -> Run 'app'

# Using the Application

## Login Screen
The first screen of the application prompts for a username and password.
Here you may sign up a new user account to keep track of your scores and saved games.
Input your desired username and password and then press signup; a toast is shown whether the sign
up is successful. Provided that an account with that username does not already exist, you may now
press the login button to continue to the game select screen.

## Game Select Screen
Here you can find buttons that lead you to the menu screens of our three games: Sliding Tiles,
Snake, and our original game Blocks. There is also a button here that allows you to view the
scoreboard of the games.

## Scoreboard Screen
The scoreboard displays the top-scoring user and their score for any one of the various modes of
our games (under Per-User), as well as the current user's top scores for each of those game modes
(under Per-Game).

## Sliding Tiles
Pressing the Sliding Tiles button in the Game Select Screen leads you to the Sliding Tiles menu.
Here you can find buttons for loading and saving the game, loading an auto save, undoing the moves
of the current game, and starting a new game.

Pressing the new game button prompts you to choose from three complexities: 3x3, 4x4, and 5x5,
each corresponding to a differently-sized game board with a different number of tiles.

The game itself is simple: the board contains numerous tiles, each with a number on them except
for one blank tile. To win the game, you must slide the tiles into row-major order with the numbers
ascending and the blank tile at the bottom right. You swap tiles by pressing any of the tiles
adjacent to the blank tile, which results in that tile switching with the blank tile. A single tile
swap constitutes one move. Your final score is determined by the number of moves you take to win:
the lower the better! Your high score is updated upon finishing the game if you beat your old one.

Pressing the back button gives you access to the menu once again, where you can save the
game in progress or undo a number of moves (you input how many you wish to undo).
When games are saved manually, they are labelled with the exact time and date of saving.

If you have any past saved games, pressing load game will bring up a list of all your previous
saves; clicking on any of them automatically brings up that game to play. An autoSave is
automatically created in your account whenever you exit the game screen; pressing the Load Auto
Save button in the menu loads that autoSave game back up.

## Snake
Pressing the Snake button in the Game Select Screen leads you to the Snake game menu.
Here you can find buttons for loading and saving the game, loading an auto save, and starting a new
game.

Pressing the new game button prompts you to select a difficulty level: easy or hard, the difference
in the two being in how fast your snake moves.

The game itself is very much like classic Snake: maneuver the snake around the screen eating
apples, which cause the snake to grow in length and your score to increase.
Also present in the game are bombs, which end the game when eaten. Every time an apple is eaten,
a new apple is spawned in a random location, and the bomb's location is randomly changed.
Every three apples eaten, the game creates an automatic save point, which can be loaded from the
menu. The game ends when a bomb is eaten, the snake runs into the border of the play area, or the
snake eats one of its own body segments.

Whenever the snake reaches a certain length, the game's difficulty increases! Your snake's length
is reset to 1 and the game runs a little bit faster.

The snake is controlled with four buttons at the bottom of the screen, corresponding to up, down,
left, and right. Note you cannot change your direction to the direction opposite to the one you
are currently moving in (lest you turn back into yourself!).

A pause button is also present at the bottom right of the screen, which allows you to stop the
snake's movement until you press it again.

Pressing the back button gives you access to the menu once again, where you can save the
game in progress, load any old saved games (the Load Game button also gives you access to any
save points created), or load an auto save.
When games are saved manually, they are labelled with the exact time and date of saving.

Similarly to Sliding Tiles, an autoSave is automatically created in your account whenever you exit
the game screen; pressing the Load Auto Save button in the menu loads that autoSave game back up.

Your high score is also updated whenever you exit the game screen if you beat your old one.

## Blocks
Pressing the Blocks button in the Game Select Screen leads you to the Blocks game menu.
Here you can find buttons for loading and saving the game, loading an auto save, undoing the moves
of the current game, and starting a new game.

Pressing the new game button begins a new game of Blocks, a game of our original creation.
The game is played on a 9x9 grid which are populated by blocks, food, and the player.
The borders of the grid are taken up by blocks. Starting a new game places the player in the middle
of the grid and spawns 4 foods in random locations around the grid.

The aim of the game is to collect as much food as you can until your movement is completely
hindered by (possibly your own) blocks, upon which it is game over!

A turn in this game constitutes doing one of two things: moving the player or placing a block.
When the player is moved, it must move the maximum number of squares in the up, down, left, or
right direction until it encounters a food or a block. A player stops at the food's location and
eats it, and the player stops before blocks. Eating food causes another food to spawn at random
location; there is always 4 food on the screen at a time.

Movement is controlled using the directional input buttons at the bottom of the screen.
The player places blocks by tapping the screen; blocks may only be placed in empty squares.
Blocks are used to purposefully impede your own movement to obtain any food that is out of your
reach.

As with Sliding Tiles, pressing the back button gives you access to the menu once again, where you
can save the game in progress or undo a number of moves (you input how many you wish to undo).
When games are saved manually, they are labelled with the exact time and date of saving.

If you have any past saved games, pressing load game will bring up a list of all your previous
saves; clicking on any of them automatically brings up that game to play. An autoSave is
automatically created in your account whenever you exit the game screen; pressing the Load Auto
Save button in the menu loads that autoSave game back up.

Your high score is also updated whenever you exit the game screen if you beat your old one.
