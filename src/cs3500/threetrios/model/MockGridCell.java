package cs3500.threetrios.model;

/**
 * Mock implementation of a ReadOnlyGridCell for testing. This mock is responsible for
 * simply telling the model is this GridCell a hole or card cell.
 */
public class MockGridCell implements ReadOnlyGridCell {
  private final boolean isHole;

  /**
   * Construct a MockGridCell with a boolean value to represents what it holds (cell/hole).
   *
   * @param isHole boolean value to represents this MockGridCell is a hole.
   */
  public MockGridCell(boolean isHole) {
    this.isHole = isHole;
  }

  @Override
  public String cardToString() {
    if (isHole) {
      return " ";
    } else {
      return "_";
    }
  }

  @Override
  public TeamColor getColor() {
    return null;
  }

  @Override
  public boolean isOccupied() {
    return isHole;
  }

  @Override
  public Card getCardCopy() {
    return null;
  }

  @Override
  public String toString() {
    if (isHole) {
      return " ";
    } else {
      return "_";
    }
  }
}
