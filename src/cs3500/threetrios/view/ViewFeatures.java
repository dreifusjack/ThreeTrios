package cs3500.threetrios.view;

import cs3500.threetrios.model.TeamColor;

/**
 * Features to be used for the view to communicate to the controller with. These features include
 * allowing a human player to select and place cards onto the board.
 */
public interface ViewFeatures {
  /**
   * Handles the logic for a human player to select a card from their hand.
   *
   * @param playerColor the team color of the player that selects the card
   * @param cardIndex index of card in the players hand
   */
  void selectCardOnGUI(TeamColor playerColor, int cardIndex, ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard);

  /**
   * Handles the logic for placing a card on the grid.
   *
   * @param row the row to place the card
   * @param col the column to place the card
   */
  void placeCardOnGUI(int row, int col);

  /**
   * Handles the logic for setting correct titles to GUI.
   */
  void startGame();
}