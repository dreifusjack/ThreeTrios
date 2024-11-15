package cs3500.threetrios.view;

import cs3500.threetrios.model.TeamColor;

/**
 * Features class for future usage in which the controller and view will communicate through
 * event listeners.
 */
public interface Features {


  void selectCard(TeamColor playerColor, int cardIndex);

  /**
   * Handles the logic for placing a card on the grid.
   *
   * @param row the row to place the card
   * @param col the column to place the card
   */
  void placeCard(TeamColor playerColor, int row, int col);

  void refreshView();

  void startGame();

}