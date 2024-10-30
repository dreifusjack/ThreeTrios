package cs3500.threetrios.model;

public interface ReadOnlyGridCell {

  /**
   * Textual representation of this cell.
   * Hole: ' '
   * EmptyCardCell: '_'
   * FilledCardCell: 'R' or 'B'
   * @return string format of this cell
   */
  public String toString();

}
