package cs3500.threetrios.model;

/**
 * Responsible for all read only interacts for a grid cell. These behaviors will be used
 * when the client handles a grid cell, or the view class handles the grid cell.
 */
public interface ReadOnlyGridCell {
  /**
   * Textual representation of this cell.
   * Hole: ' '
   * EmptyCardCell: '_'
   * FilledCardCell: 'R' or 'B'
   *
   * @return string format of this cell
   */
  String toString();

  String cardToString();

  TeamColor getCardColor();
}
