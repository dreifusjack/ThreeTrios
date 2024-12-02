package cs3500.threetrios.controller;

import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.view.ThreeTriosCardPanel;

/**
 * Responsible for passing features of player actions to player based controllers. Actions include
 * selecting a card from a player hand and selecting a grid cell to play that card to, ultimately
 * playing a move.
 */
public interface PlayerActionListener {
  /**
   * When a card has been selected, get the selected card index to be used after the user selects
   * coordinates to play that card to.
   *
   * @param playerColor TeamColor of the player that selected the card
   * @param cardIndex   index from hand of the selected card
   */
  void handleCardSelection(TeamColor playerColor, int cardIndex,
                           ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard);

  /**
   * When a position on the grid has been selected, attempt to place the previously selected card
   * if present, to that selected location. GUI will provide a pop-up with an error message if
   * invalid placement.
   *
   * @param row row on the grid
   * @param col column on the grid
   */
  void handleBoardSelection(int row, int col);
}