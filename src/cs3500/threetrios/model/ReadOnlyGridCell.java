package cs3500.threetrios.model;

/**
 * Responsible for all read only interacts for a grid cell. These behaviors will be used
 * when the client handles a grid cell, or the view class handles the grid cell.
 * A GridCell is responsible for representing a cell on the board. It can be a hole or a card cell.
 * Card Cells allow a card to be placed on them, they will know their team color when a card is
 * placed. Holes cannot have anything placed on them.
 */
public interface ReadOnlyGridCell {
  /**
   * Textual representation of this cell.
   * Hole: ' '
   * EmptyCardCell: '_'
   * FilledCardCell: 'R' or 'B'
   *
   * @return string format of this cell
   * @throws IllegalStateException if cell has a card but does not have a color
   */
  String toString();

  /**
   * Returns the string value of this cells card if the cell is occupied by a card.
   * Format: "Name NorthValue SouthValue EastValue WestValue".
   *
   * @return textual representation of this cells card.
   * @throws IllegalStateException if this cell is a hole
   * @throws IllegalStateException if this cell is not occupied by a card
   */
  String cardToString();

  /**
   * Returns the team color (red or blue) of this cell, if it's an occupied card cell.
   *
   * @return TeamColor enum representing this cells team
   * @throws IllegalStateException if this cell is a hole
   * @throws IllegalStateException if this cell does not have a color
   */
  TeamColor getColor();

  boolean isEmpty();

}
