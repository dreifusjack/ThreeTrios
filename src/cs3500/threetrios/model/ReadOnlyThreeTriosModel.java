package cs3500.threetrios.model;

import java.util.List;

/**
 * All observation behaviors of the Three Trios Model. These behaviors are to be used
 * in the view/client side, so it has no access to the models operations (internal state).
 * The model represents a rules keeper for ThreeTrios. It is constructed in a way that validates
 * inputs however is not instantly ready to start a game. A method is required to be called to start
 * a game. The model ensures valid game play occurs and keeps track of the internal game-state.
 */
public interface ReadOnlyThreeTriosModel {
  /**
   * Determines if the game is over. The game is over if all card cells on the board have been
   * occupied by player placed cards.
   *
   * @return true iff the game is over
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();

  /**
   * Returns a copy winning player after the game is over. Modifying this returning object will
   * have no effect on the game. If the game ends in a tie returns null.
   *
   * @return the winning player
   * @throws IllegalStateException if the game has not started or the game is not over
   */
  Player getWinner();

  /**
   * Returns a copy of the current player. Modifying this player will have no effect on the game
   * state.
   *
   * @return the player whose turn it is, or the last player to play if the game is over
   * @throws IllegalStateException if the game has not started
   */
  Player getCurrentPlayer();

  /**
   * Returns a copy of the player on the red team.
   * Modifying this player will have no effect on the game state.
   *
   * @return the player who is on the red team
   * @throws IllegalStateException if the game has not started
   */
  Player getRedPlayer();

  /**
   * Returns a copy of the player on the blue team.
   * Modifying this player will have no effect on the game state.
   *
   * @return the player who is on the blue team
   * @throws IllegalStateException if the game has not started
   */
  Player getBluePlayer();

  /**
   * Returns a state of the Grid that is read only for the user to view the current state of the
   * grid. The grid is 0 index based.
   *
   * @return a 2D array list representing the current playing grid.
   * @throws IllegalStateException if the game has not started
   */
  List<List<ReadOnlyGridCell>> getGridReadOnly();

  /**
   * Returns the number of rows in the playing grid.
   *
   * @return int representing the number of rows
   * @throws IllegalStateException if the game has not started
   */
  int numRows();

  /**
   * Returns the number of columns in the playing grid.
   *
   * @return int representing the number of cols
   * @throws IllegalStateException if the game has not started
   */
  int numCols();

  /**
   * Returns a read only version of the cell at the given coordinates.
   * The contents are one of: hole, empty card cell, or card cell containing a card.
   *
   * @return ReadOnlyGridCell of the given coordinates
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if row < 0 or >= number of rows
   * @throws IllegalArgumentException if col < 0 or >= number of columns
   */
  ReadOnlyGridCell getCell(int row, int col);

  /**
   * Returns the current score of the player with the corresponding team color. Score is calculated
   * by all the cards of that players team color on the board and in their hand.
   *
   * @param teamColor blue or red
   * @return player score of the given team color
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if team is null
   */
  int getPlayerScore(TeamColor teamColor);

  /**
   * Determines at the given coordinates, how many surrounding cards will be flipped if the given
   * player were to place the given card at the given coordinates. This method will not play any
   * card to the grid, rather it checks if a move would have a high number of flips.
   *
   * @param card Card to be placed
   * @param row  row coordinate of placed card
   * @param col  column coordinate of placed card
   * @return number surrounding cards that would be flipped
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if the given row or col are out of grid bounds
   * @throws IllegalArgumentException if the given card or player is null
   * @throws IllegalArgumentException if coordinates map to a hole
   */
  int numCardFlips(Card card, int row, int col, Player player);

  ReadOnlyThreeTriosModel simulateMove(int row, int col, int handIdx);
}
