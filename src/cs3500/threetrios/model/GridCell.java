package cs3500.threetrios.model;

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
   * @throws IllegalArgumentException if the given card is null
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
   * throws an exception. Needs to return the direct card because that instance is the card
   * that is occupied on the grid.
   *
   * @return Card that occupies this cell.
   * @throws IllegalStateException if this cell is a hole.
   */
  public Card getCard();

  /**
   * Textual representation of this cell.
   * Hole: ' '
   * EmptyCardCell: '_'
   * FilledCardCell: 'R' or 'B'
   * @return string format of this cell
   */
  public String toString();
}
