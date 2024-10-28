package cs3500.threetrios.model;

public enum Direction {
  NORTH, EAST, SOUTH, WEST;

  /**
   * Returns the change in row for the given direction.
   *
   * @param dir the direction to calculate the row change
   * @return -1 for NORTH, 1 for SOUTH, other directions have no effect
   */
  public static int getRowHelper(Direction dir) {
    switch (dir) {
      case NORTH:
        return -1;
      case SOUTH:
        return 1;
      default:
        return 0;
    }
  }

  /**
   * Returns the change in column for the given direction.
   *
   * @param dir the direction for calculate the column change
   * @return 1 for EAST, -1 for WEST, other directions have no effect
   */
  public static int getColHelper(Direction dir) {
    switch (dir) {
      case EAST:
        return 1;
      case WEST:
        return -1;
      default:
        return 0;
    }
  }
}
