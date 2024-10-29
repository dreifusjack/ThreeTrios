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


## High-level Components come back to! 

## Key Subcomponents come back to!


## Codebase Organization

#### The codebase is divided into the following three directories.

**docs:** 
* README user documentation file with docs. 
* cardconfigurations sub-directory within docs. This sub-directory holds all card configuration files for our 
model to use in setting up the player cards for the Blue and Red players. 
* gridconfigurations sub-directory within docs. This sub-directroy hold all grid configuration files 
for our model to use in setting up the playing board for the players to play cards onto. 
  
**src:** 
* all implementations are in the cs3500.threetrios package within the src directory. 
* Main file is used for quick testing of model and view interacts, does not affect any implementation or reduce any unit testing. 
* model package within cs3500.threetrios includes public interface describing the behaviors of the model, as well as a basic implementation of that interface. The rest of the files are package-private 
classes that are used within the model, leading to a more modular design. 
* fileIO package within model package, handels logic of reading from configuration files and passing that data to the model. 
* view package within cs3500.threetrios includes public interface describing the current behaviors of the view, as well as a basic implementation of that interface. 

**test:** 
* all testing files are in the cs3500.threetrios package within the test directroy to match the src directory. 
* tests for public interfaces (model, view) in cs3500.threetrios, testing the properties of client used interfaces. 
* tests for model package-private implementations within a new model package in cs3500.threetrios package

## Class Invariant
**playerTurn is one of redPlayer or bluePlayer**
* This is a logical statment, true or false.
* This can always be checked in real-time: simply check if playerTurn is either redPlayer or bluePlayer
* Ensured by the constructor by initializing playerTurn to redPlayer (since Red team always goes first)
* Preserved by the methods because the only method that re-assignes playerTurn is playToGrid. In this method we re-assign playerTurn with the following logic. 
If the player has successfully played to grid, the battle phase is complete, and the game is not over "playerTurn = playerTurn == redPlayer ? bluePlayer : redPlayer;". 
This condition only allows playerTurn to be assigned to redPlayer or bluePlayer, it effectively toggles between the players after each move. 
