# Three Trios #
## Overview ##
Two player card game that involves placing cards to a board and battling with the opposing teams cards based off attack values. The winner of the game is determined when the board has been filled and the player with the most cards flipped to their team color. To flip a card, a player must place a card adjacent to an opposing teams card and the attack value in the corresponing direction must be higher (in default variant rules).

## Game Settings ##
Three Trios can be played with the following player-types: Human v Human, Human v AI, AI v AI, with specifed AI strategies.
There are also game variants that can be applied to change how flips occur in the battle phase of the gameplay.
Both of these game settings are determined via the command-line when the game starts. 

## Game Interaction ## 
When the game starts two windows will appear, one for each player. To make a move a user must click a card from their and hand a valid board cell to play to. If playing against AI, a move will automatically be played, else the user must wait for the other player to make a move. 

## Running Three Trios ##
1. Download ThreeTrios.jar, found in src/cs3500/threetrios/
2. Open terminal
3. cd into directory with ThreeTrios.jar
4. run the following command `java -jar ThreeTrios.jar`

