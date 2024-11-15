package cs3500.threetrios.player;

import cs3500.threetrios.model.TeamColor;

/**
 * Responsible for passing features of player actions to player based controllers. Actions include
 * selecting a card from a player hand and selecting a grid cell to play that card to, ultimately
 * playing a move.
 */
public interface PlayerActionFeatures {
  /**
   * When a card has been selected, get the selected card index to be used after the user selects
   * coordinates to play that card to.
   *
   * @param playerColor TeamColor of the player that selected the card
   * @param cardIndex   index from hand of the selected card
   */
  void onCardSelected(TeamColor playerColor, int cardIndex);

  /**
   * When a position on the grid has been selected, attempt to place the previously selected card
   * if present, to that selected location. GUI will provide a pop-up with an error message if
   * invalid placement.
   *
   * @param playerColor TeamColor of the player that selected the position on the grid
   * @param row row on the grid
   * @param col column on the grid
   */
  void onCardPlaced(TeamColor playerColor, int row, int col);
}