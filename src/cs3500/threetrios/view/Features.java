package cs3500.threetrios.view;

/**
 * Features class for future usage in which the controller and view will communicate through
 * event listeners.
 */
public interface Features {
  /**
   * Handles the logic for selecting a card.
   *
   * @param card the card to select
   */
  void selectCard(String card);

  /**
   * Handles the logic for placing a card on the grid.
   *
   * @param row the row to place the card
   * @param col the column to place the card
   */
  void placeCard(int row, int col);
}