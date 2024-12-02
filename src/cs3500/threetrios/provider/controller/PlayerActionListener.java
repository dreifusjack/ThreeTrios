package cs3500.threetrios.provider.controller;

import cs3500.threetrios.provider.model.PlayerType;

/**
 * The PlayerActionListener interface represents a listener for player actions in the
 * ThreeTrios game. It provides methods that are called when a player selects a card or clicks on
 * a cell on the grid.
 */
public interface PlayerActionListener {

  /**
   * Called when a player selects a card from their hand.
   *
   * @param playerType the player who selected the card
   * @param cardIndex  the index of the selected card in the player's hand
   */
  void cardSelected(PlayerType playerType, int cardIndex);

  /**
   * Called when a player clicks on a cell on the grid.
   *
   * @param row the row (0-index) of the clicked cell
   * @param col the column (0-index) of the clicked cell
   */
  void cellClicked(int row, int col);
}
