## Overview

The structure of this codebase is planned out to use a MVC format. 
In our implementation of ThreeTrios so far, our main goal is to create a modular model 
that encapulates a flexible implementation while still having meaning an intent. 


### Key-assumptions:
* There are only two players, a Red and Blue player. 
* Red player always goes first.
* After each move the player turn switches.
* The board is a two-dimension rectangle of grid-cells.

### Background knowledge required/prerequisites: 
* Requires knowledge of OO principles, how various classes can interact with eachother through a model. 
* Requires java/programming knowledge such as forms of interation, data structure, access modifiers, etc. 
* Familar with JUnit testing library. 

## Quick Start (for a user that wants to quickly view the progress of our ThreeTrios game so far)
* ```Open ThreeTrios project in IntelliJ```
* ```Navigate to Main file in src directory```
* ```Run Main file```


## High-level Components
**1 Model:**
* The model contains the game logic, grid setup, player actions (playToGrid), turn switching, and battle cards logic.
* It includes configuration file support, reading the initial board and card setups from external files via file readers
* This component ensures that each move is playable according to the game rules, it also manages player turns, and updates the game state.

**2 View:**
* The view component displays the game state to the user in a textual format, providing real-time feedback on the game after each play is completed.
* It interacts with a read-only version of the model/gridCell to access necessary data without modifying it, preserving model integrity.
* Its role is to represent the current grid, player hands after each turn is done. 

**3 File readers:**
* Handles file reading and parsing of configuration files to set up the initial grid and card information.
* It provides the model with structured data from external files, making it possible to reconfigure / allow the player to play.

## Key Subcomponents

**1 Player:**

* Manages the state and actions of each player, including their team color and hand.
* Provides actions needed for a player like addCard/removeCard and toString to display the current hand.

**2 GridCell (Hole / CardCell):**
* GridCell represents all cells for the game board and be either a Hole or CardCell.
* It has an ability to add a card to the cell, unless restricted (when there is a Hole cell)
* It can toggle between colors and have access to the card through getCard 
* Card: holds a card and supports interactions like adding a card and toggling its color. 
* Hole: Represents an empty spot on the board, where cards cannot be placed.

**3 Direction:**
* Enum class that handles directions for movement and battles (North, South, East, West).
* Also has helper methods like getRowHelper/getColHelper which help to interpret directional effects on the grid layout.

**4 TeamColor:**
* The TeamColor is an enum class that defines the two team colors in the game (Red and Blue)
* It has a method toStringAbbreviation to provides a single-character abbreviation ("R" for Red and "B" for Blue).
* This abbreviation method is important as it is being used in ThreeTrioTextView to display the game’s textual representation.

**5: Card (ThreeTrioCard):**
* The Card interfaces represents the structure of a playing card in the game.
* A ThreeTrioCard has a TeamColor to determine the team the card belongs to and can be toggled when necessary. 
* It has additional methods to compare its value with another card to support the battling mechanism of the game.

## Codebase Organization

#### The codebase is divided into the following three directories.

**docs:** 
* README user documentation file with docs. 
* Player.txt includes our design approach to human and AI player interations with the model. 
* card-configurations sub-directory within docs. This sub-directory holds all card configuration files for our 
model to use in setting up the player cards for the Blue and Red players. 
* grid-configurations sub-directory within docs. This sub-directory hold all grid configuration files 
for our model to use in setting up the playing board for the players to play cards onto. 
  
**src:** 
* all implementations are in the cs3500.threetrios package within the src directory. 
* Main file is used for quick testing of model and view interacts, does not affect any implementation or reduce any unit testing. 
* model package within cs3500.threetrios includes public interface describing the behaviors of the model
* model package additionally contains objects that the model uses (Player, Card, etc.), these objects have both classes and interfaces. Allowing an extendable design to replace the implementations while still using the interfaces. 
* model package also contains file readers both for card configs and grid confgis with seperate classes and interfaces. Allowing this grid file readers with new implementations to be extendable in the future.
* view package within cs3500.threetrios includes public interface describing the current behaviors of the view, as well as a basic implementation of that interface. 

**test:** 
* all testing files are in the cs3500.threetrios package within the test directroy to match the src directory. 
* tests for public interfaces (model, view) in cs3500.threetrios, testing the properties of client used interfaces. 
* tests for model package-private implementations within a new model package in cs3500.threetrios package

## Class Invariant
**playerTurn is one of redPlayer or bluePlayer**
* This is a logical statement, true or false.
* This can always be checked in real-time: simply check if playerTurn is either redPlayer or bluePlayer
* Ensured by the constructor by initializing playerTurn to redPlayer (since Red team always goes first)
* Preserved by the methods because the only method that re-assigns playerTurn is playToGrid. In this method we re-assign playerTurn with the following logic. 
If the player has successfully played to grid, the battle phase is complete, and the game is not over "playerTurn = playerTurn == redPlayer ? bluePlayer : redPlayer;". 
This condition only allows playerTurn to be assigned to redPlayer or bluePlayer, it effectively toggles between the players after each move. 

## Changes for part 2
* Added observation methods for grid sizes, we chose not to add this in part 1 because any class that accesses a read only model can
get a copy of the grid and access the size configurations through that copy. However these methods makes it easier to access grid sizing's. 
* Added observation method for getting the contents of the grid at a specific coordinate. Once again we didn't add this originally because
other classes can get the a copy of the whole grid which would allow them to get any cell at a specific coordinate. However this method makes 
this process easier for specific cells. 
* Added observation method for getting a players current score. We had this as a private helper in part 1, but now it is 
in our read only model interface. 
* Added method that is given a card and two coordinates, determines how many flips that card would make
in the battle phase. We did not add this because we didn't find the functionality necessary in part 1, but now that we are designing
strategies, it is needed. 
* We created a controller to start the model for us. The controller does not have any functionality 
yet other than the playGame() method that takes in 2 Strings, first one is the direction to get 
to get the world(grid) configurations and the second String is about the direction of the cards 
configurations we want to use for our game. The main constructor our model now does not take in 
anything since the controller process the information in the configuration files for us.

## Extra credits for part 2
* We attempted to do design all the strategies provided for extra credit, which are strategy 3 and strategy 4 and the chain strategy to combine multiple strategies. 
* The third strategy class's name is MinimizeFlipStrategy and the fourth strategy class's name is MinimaxStrategy and the chain strategy name is ChainStrategy. You can find the implementations of these 2 strategies following this path "src/cs3500/threetrios/strategy".
* Then, the test classes for these extra credit strategies are located in "test/cs3500/threetrios/strategy", the name for the test class of MinimizeFlipStrategy is MinimizeFlipStrategyTest 
and the name for the test class of the MinimaxStrategy is MinimaxStrategyTest and the test class's name of the ChainStrategy is ChainStrategyTest. 