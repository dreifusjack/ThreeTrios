package cs3500.threetrios;

/**
 * Representing a single cell in the grid of ThreeTrios game.
 */
public interface GridCell {
  /**
   * Adds the given card to this GridCell.
   *
   * @param card Card to be added to the cell
   * @throws IllegalStateException if this cell is a hole.
   * @throws IllegalStateException if this cell already has a card.
   */
  public void addCard(Card card);

  /**
   * Changes the given card color on this cell if a card is present. If no card is present
   * do nothing.
   * @throws IllegalStateException if this cell is a hole.
   */
  public void changeCardColor();

  /**
   * Returns the card occupied by this cell. If no card is present return null, if cell is a hole
   * throws an exception.
   *
   * @return Card that occupies this cell.
   * @throws IllegalStateException if this cell is a hole.
   */
  public Card getCard();

  /**
   * Check if this cell is a hole where no card can be placed.
   * @return true if this cell is a hole and false if it is not.
   */
  boolean isHole();
}
