package cs3500.threetrios.provider.model;

import java.util.List;

import cs3500.threetrios.model.Card;

/**
 * Interface for Read Only of the Three Trios Model.
 */
public interface ReadOnlyThreeTriosModel {

  /**
   * This method checks if the game is over or not.
   *
   * @return true if the game is over, false if it is still running.
   */
  boolean isGameOver();

  /**
   * Calculates the score of a given player.
   *
   * @param player The player to see the current score for.
   * @return the score of a player.
   */
  int getPlayerScore(PlayerType player);

  /**
   * This method checks to see if a move from the player's hand to the grid is legal.
   *
   * @param cardIndex The index of the card to place from the current player's hand.
   * @param row       The row to place the card. Row is 0-indexed.
   * @param col       The column to place the card. Col is 0-indexed.
   * @return True if the move is allowed on the grid, false if not.
   */
  boolean isMoveLegal(int cardIndex, int row, int col);

  /**
   * Calculates the winner of the game once it is finished.
   *
   * @return the winner of the game, if it is a tie returns PlayerType.OVER.
   * @throws IllegalStateException if the game is still in progress
   */
  PlayerType getWinner();

  /**
   * Gets a copy of the current grid.
   *
   * @return the grid.
   */
  Cell[][] getGrid();

  /**
   * Gets a copy of a Player's hand.
   *
   * @param player Which player's hand to get.
   * @return the player's hand
   */
  List<ICard> getHand(PlayerType player);

  /**
   * Gets the current player's turn.
   *
   * @return the current player.
   */
  PlayerType getCurrentPlayer();
}
