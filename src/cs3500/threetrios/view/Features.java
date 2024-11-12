package cs3500.threetrios.view;

/**
 * Feature class for future usage.
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