package cs3500.threetrios.model;

/**
 * Mock implementation of a ReadOnlyGridCell for testing.
 */

public class MockGridCell implements ReadOnlyGridCell {
  private final boolean isHole;

  /**
   * Construct a MockGridCell with a boolean value to represents what it holds (cell/hole)
   * @param isHole boolean value to represents this MockGridCell is a hole.
   */
  public MockGridCell(boolean isHole) {
    this.isHole = isHole;
  }

  @Override
  public String cardToString() {
    if(isHole) {
      return " ";
    }
    else {
      return "_";
    }
  }

  @Override
  public TeamColor getColor() {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return !isHole;
  }

  @Override
  public String toString() {
    if(isHole) {
      return " ";
    }
    else {
      return "_";
    }
  }
}
