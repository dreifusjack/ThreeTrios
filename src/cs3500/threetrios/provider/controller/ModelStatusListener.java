package cs3500.threetrios.provider.controller;

import cs3500.threetrios.provider.model.PlayerType;

/**
 * The ModelStatusListener interface represents a listener for model state changes in the
 * ThreeTrios game. It provides methods that are called when the current player changes or when
 * the game is over.
 */
public interface ModelStatusListener {

  /**
   * Called when the current player changes in the game.
   *
   * @param newCurrentPlayer the new current player
   */
  void currentPlayerChanged(PlayerType newCurrentPlayer);

  /**
   * Called when the game is over.
   *
   * @param winner    the winner of the game
   * @param redScore  the final score of the red player
   * @param blueScore the final score of the blue player
   */
  void gameOver(PlayerType winner, int redScore, int blueScore);
}
