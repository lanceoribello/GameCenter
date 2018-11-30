# Contact Information

## Nancy Zhao
n.zhao@mail.utoronto.ca
416-819-5486
Username in git log: zhaonancy

## Neel Patel
neelu.patel@mail.utoronto.ca
905-616-9292
Username in git log: Neel

## Lance Oribello
lance.oribello@mail.utoronto.ca
778-231-6563
Username in git log: Lance

## Ishan Sharma
ish.sharma@mail.utoronto.ca
647-771-3440
Username in git log: shar1178

# Communication Tool

Facebook

# Team Contract

* I will get my allotted work done on time.
* I will attend every team meeting and actively participate.
* Should an emergency arise that prevents me from attending a team meeting, I will notify my team immediately.
* The work will be divided roughly equally among all team members.
* I will help my team to understand every concept in the project.
* If I do not understand a concept or code, I will immediately ask my team for help.

# Work Division

## Neel Patel

## Lobby
Implemented the login button onClick logic which checks if a user
which is trying to log in is a valid user or not, as well as switches to GameSelect if valid.

## Sliding Tiles
Completely reworked the Tile class in sliding tiles to a newer model which hadn't abused switch
statements for the many arbitrary cases of sliding tiles. In the new version of Tile, I developed
a new constructor which allows the Board to take in any arbitrary dimensions of tiles, rather than
the hard coded switch statement which originally just had cases for 3x3, 4x4 and 5x5 boards.
Implemented the 3 difficulties in Sliding tiles. Originally a 3x3 games, also includes 4x4 and 5x5
options for the player. Also implemented the prompt for the user to be able to choose between
these different modes in the GUI of the starting activity.
Created unit tests with 100% coverage for Board, BoardManager and Tiles

## Snake
Implemented the directional input which actually allows the user to control the Snake in a simple
manner of pressing directions on a D-pad set at the bottom of the SnakeView. Also made the GUI
of the D-pad itself.
Implemented the bomb function of snake, where a random bomb spawns every time a point is scores.
This adds an extra loss condition aside from just crashing into the wall.
Worked on the general logic of the game

## Blocks
Implemented the directional input which actually allows the user to Player block a similar manner
to how I implemented it within Snake: pressing directions on a D-pad set at the bottom of the
BlocksView. Also made the GUI of the D-pad itself and the logic behind converting button presses
into knowing they were in the constraints of the different parts of the D-pad.
Implemented the actual placement of block objects in the game blocks based on user input. (Made
it possible for the user to place blocks on the generated grid based on where they tap the screen)
Created unit tests with 100% coverage for Grid
Helped develop logic in both Grid and GridManager to get the game working in its current final state
bug free.

## Nancy Zhao

### Lobby
Implemented file writing and reading for accounts.ser, which contains the user account list.
Developed LoginActivity and LoginController (display and logic), and optimized UserAccount for 
adding more games.
Developed GameSelectActivity and GameSelectController (display and logic).
Developed ScoreboardActivity and ScoreboardController (display and logic), using Intents and 
accounts.ser to update top scores immediately after a top score is achieved in a game.
Implemented the Model View Controller (MVC) design pattern in all lobby classes to allow for 
easier testing of the logic for each screen.
Achieved 100% code coverage for all lobby controller classes and UserAccount.
Configured the .xml files for lobby activities for display.

### Sliding Tiles
Implemented Sliding Tiles BoardManager so it always initializes a solvable game, by starting with
a solved board and making 1000 valid taps to shuffle the board.
Implemented update high scores so it updates the user account list for the scoreboard.
Implemented try catch in undo for invalid number of moves to undo inputs.

### Snake
Implemented update high scores so it updates the user account list for the scoreboard.
Developed the dialog box for selecting difficulty level (easy/hard) and game play for each level.
Implemented drawing text for the Snake view, for score and game over.
Achieved 100% code coverage for SnakeController tests.

### Blocks
Implemented undo functionality (undo moving the player and placing blocks) and undo display.
Implemented update high scores so it updates the user account list for the scoreboard.
Implemented loading, saving, and loading auto saved games.
Achieved 100% code coverage for GridManager tests.

## Lance Oribello

### Lobby
Developed UserAccount.

### Sliding Tiles
Implemented the logic behind the copying of Boards for use in autoSaving and undo functionality.
Developed undo functionality to work in a way where an arbitrary number of moves can be undone.
Implemented the autoSave functionality to write onto file upon exiting the game.

### Snake
Developed the SnakeController class after splitting it from the SnakeView class.
Implemented the initial setting of difficulty levels in a Snake game.
Implemented the increasing difficulty functionality after a certain snake length is reached.
Implemented the automatic creation of save points whenever certain scores are reached.
Developed the logic in which game data is saved and loaded within an Object array.

### Blocks
Developed the underlying logic for both the Grid and GridManager classes.
Encompasses any methods that modify the 2d int array that represents the states of each grid square:
moving the player, placing objects on the grid, eating food, etc.
Implemented the logic behind the copying and loading of Grids for use in undoing and autoSaving.
Developed how the Grid is displayed within BlocksView.

## Ishan Sharma

### Sliding Tiles Menu Activity and test
Made The sliding Tiles menu activity page, specifically the saving of a game and loading a game from
a list of games.
Also added tests for the controller class

### Snake Menu Activity and test
Made the snake game button switch in GameSelectActivity and the snake menu activity page with 
more focus on saving new snake games and loading from a list of saved games.
Also added tests for the controller class.

### Blocks Menu Activity
Similar to Snake and Sliding tiles, made the menu activity page of this class with more focus on 
saving a game and loading a game.
Added tests for the controller class.

### Snake Pause
Implemented the Pause feature in Snake.

### Snake Controller tests
Added tests for the snake controller class, which is a controller class for snakeview.

# Meeting Notes

## October 21, 2018
Worked on class design.
Attempted to copy sliding tile files into repository.

## October 23, 2018
Worked on class design.

## October 24, 2018
Completed CRC cards - modified current classes and added two new classes.
Cloning troubleshooting.

## October 28, 2018
Sliding tile files successfully pushed.
Started README file with instructions on cloning.
Created Account class, updated BoardManager class, started on LoginScreen class.

## October 31, 2018
Worked on LoginScreen class, added display functionality.
Edited AndroidManifest to include LoginScreen activity.
LoginScreen is now a separate activity from StartingActivity.

## November 3, 2018
Worked on LoginScreen class, it is now able to display and users can login
BoardManager class can now display different complexities.
Implemented Serializable in UserAccount.
Working on Scoreboard display.
Created all the tile objects for different complexities, as well as changes to the tile
class in accordance to the new cases for 3x3 and 5x5.

## November 4, 2018
Edited AndroidManifest to include Scoreboard activity.
StartingActivity now has View Scoreboards button which leads to Scoreboard display.
SignUp and Login buttons now work successfully. 
Fixed some issues with 
Completed Scoreboard implementation. 

## November 5, 2018
Completed Scoreboard display.
Completed undo functionality.
Added the ability for the user to choose complexity.
Added a game centre menu to choose games (for phase 2).
Added a class for managing the user accounts file.
Added the ability for the user to select and load previously saved games.

## November 6, 2018
Added the autoSave functionality.
Added jorjani mode that changes the background images of the tiles for all complexities.
Finalized docstring.
Finished everything else that needs doing for Phase 1!

## November 13, 2018
Set up repository for Phase 2.

## November 18, 2018
Removed Jorjani mode.
Fixed many code smells - public to private, etc.
Fixed undo for Sliding Tiles.
Renamed files and organized into packages for clarity.
Added Snake, updated ScoreboardActivity to show Snake high scores and edited display.
Added Snake Menu, can choose difficulty level.

## November 19, 2018
Fixed scoreboard - per-game now does not arbitrarily show None.
Changed UserAccount to store top scores in a Map rather than individual class variables.
Edited Tile to fix smelly code.

## November 20, 2018
Completely reworked the Tile class, removing the ugly switch constructor.
Implemented the save and load functionality for Snake.
Added README.md and TEAM.md to the repository.
Changed boards in Sliding Tiles to be always solvable.
Implemented the autoSave functionality in Snake.
Beautified the Snake menu.

## November 21, 2018
Fixed snake death and high score updating issue.

## November 23, 2018
Fixed tile id error.
Set up unit testing.
Wrote unit tests for lobby and sliding tiles classes.
Updated docstring for snakeView.

## November 24, 2018
Testing for lobby classes, Board, BoardManager.
Set up testing for activities, Mockito.
Formatting fixes to SnakeView.
Created the Grid class for Blocks.
Added undo, save, load functionality to Blocks. Improved movement in Blocks.

## November 25, 2018
Added Controllers for Login, Scoreboard, GameSelect (MVC).
Created a D-pad for snake.
Created the GridManager class for Blocks.
Scoring completed in Blocks.
Work on BlocksStartingActivity, BlocksView. Game can be displayed.
Added apples and bombs to Snake.
Testing on SnakeMenu.

## November 26, 2018
Created SnakeMenuController, SlidingTilesMenuController.
Edited grid display in Blocks.
Fixed Blocks bugs (eating food, placing blocks). Blocks can now be placed.

## November 27, 2018
Added pause functionality to Snake.
Increased code coverage in lobby and sliding tiles tests.
Code optimization.
Fixed food spawn bug in Blocks.
Fixed load auto save issue in games for new users.
Testing on Grid and GridManager.
Save point functionality added to Snake.

## November 28, 2018
Go into more detail here on design decisions (from phase 1 feedback).
Docstring updates.
Write instructions on application usage on README.
Write WALKTHROUGH.pdf.

## Novmember 29, 2018
Finish testing.
List contributions of each team member.
Complete WALKTHROUGH.pdf.
C'est fini!

We did it!
