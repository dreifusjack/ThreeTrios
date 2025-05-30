package cs3500.threetrios.provider.model;

import cs3500.threetrios.provider.controller.ModelStatusListener;

/**
 * This class covers the model operations of the Three Trios Game.
 * The goal is this game is to own the most cards on the board.
 */
public interface ThreeTriosModel extends ReadOnlyThreeTriosModel {

  /**
   * This method plays a card to the board and handles the placing phase and the
   * battle phase.
   *
   * @param cardIndex The index of the card to place from the current player's
   *                  hand.
   * @param row       The row to place the card. Row is 0-indexed.
   * @param col       The column to place the card. Col is 0-indexed.
   * @throws IllegalStateException    if the game is over.
   * @throws IllegalArgumentException if the row or column is invalid. That is
   *                                  less than 0 or
   *                                  greater than the len - 1.
   * @throws IllegalArgumentException if the card index is invalid.
   * @throws IllegalArgumentException if the move is illegal.
   */
  void playCard(int cardIndex, int row, int col);

  /**
   * Registers a ModelStatusListener to receive updates about model state changes.
   *
   * @param listener the listener to be added
   */
  void addModelStatusListener(ModelStatusListener listener);

  /**
   * Starts the game and notifies listeners of the initial game state.
   *
   * @throws IllegalArgumentException if the number of card cells is not odd.
   * @throws IllegalArgumentException if the cards provided is too small.
   */
  void startGame();
}
