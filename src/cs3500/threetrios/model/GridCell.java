package cs3500.threetrios.model;

/**
 * Responsible for all the operations of a grid cell. These behaviors will be used
 * by the model. A GridCell is responsible for representing a cell on the board.
 * It can be a hole or a card cell. Card Cells allow a card to be placed on them,
 * they will know their team color when a card is placed. Holes cannot have anything placed on them.
 */
public interface GridCell extends ReadOnlyGridCell {
  /**
   * Adds the given card to this GridCell.
   *
   * @param card Card to be added to the cell
   * @throws IllegalStateException    if this cell is a hole
   * @throws IllegalStateException    if this cell already has a card
   * @throws IllegalArgumentException if the given card is null
   */
  void addCard(Card card);

  /**
   * Changes the given card color on this cell if a card occupies this cell.
   *
   * @throws IllegalStateException if this cell is a hole
   * @throws IllegalStateException if this cell is not occupied by a card
   */
  void toggleColor();

  /**
   * Sets the given TeamColor to this cell. This will occur when a player plays a card
   * grid.
   *
   * @param color RED or BLUE
   * @throws IllegalStateException if this cell is a hole
   * @throws IllegalStateException if this cell already has a color added (should call toggleColor
   *                               in that case)
   */
  void setColor(TeamColor color);


}
