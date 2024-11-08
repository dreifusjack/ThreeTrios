package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple mock ThreeTriosModel for testing purposes.
 */
public class MockThreeTriosModel implements ReadOnlyThreeTriosModel {
  private final List<String> mocklog;
  private final int numRows;
  private final int numCols;
  private final int predeterminedRow;
  private final int predeterminedCol;
  private final boolean validMoveOnly;
  private final List<List<ReadOnlyGridCell>> grid;


  /**
   * Constructs a mock ThreeTriosModel.
   *
   * @param numRows          number of rows in the grid
   * @param numCols          number of columns in the grid
   * @param predeterminedRow chosen row to lie about.
   * @param predeterminedCol chosen col to lie about.
   * @param validMoveOnly    boolean to show if we want to lie or not.
   */
  public MockThreeTriosModel(int numRows, int numCols, int predeterminedRow, int predeterminedCol, boolean validMoveOnly) {
    this.mocklog = new ArrayList<>();
    this.numRows = numRows;
    this.numCols = numCols;
    this.grid = createMockGrid(numRows, numCols, new boolean[numRows][numCols]);
    this.predeterminedRow = predeterminedRow;
    this.predeterminedCol = predeterminedCol;
    this.validMoveOnly = validMoveOnly;
  }

  /**
   * Creates a mock grid with the given dimensions.
   *
   * @param numRows number of rows in the grid
   * @param numCols number of columns in the grid
   * @param holes   a 2D boolean array where true represents a hole at that position
   * @return a list of lists representing the mock grid
   */
  public List<List<ReadOnlyGridCell>> createMockGrid(int numRows, int numCols, boolean[][] holes) {
    List<List<ReadOnlyGridCell>> mockGrid = new ArrayList<>();
    for (int row = 0; row < numRows; row++) {
      List<ReadOnlyGridCell> gridRow = new ArrayList<>();
      for (int col = 0; col < numCols; col++) {
        if (holes[row][col]) {
          gridRow.add(new MockGridCell(true));
        } else {
          gridRow.add(new MockGridCell(false));
        }
      }
      mockGrid.add(gridRow);
    }
    return mockGrid;
  }

  @Override
  public int numRows() {
    mocklog.add("checking for number of rows");
    return numRows;
  }

  @Override
  public int numCols() {
    mocklog.add("checking for number of columns");
    return numCols;
  }

  @Override
  public ReadOnlyGridCell getCell(int row, int col) {
    mocklog.add("checking at cell (" + row + ", " + col + ")");
    if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
      if (validMoveOnly && !(row == predeterminedRow && col == predeterminedCol)) {
        return new MockGridCell(true);
      }
      return grid.get(row).get(col);
    } else {
      throw new IllegalArgumentException("invalid cell coordinates");
    }
  }

  @Override
  public List<List<ReadOnlyGridCell>> getGridReadOnly() {
    mocklog.add("accessing entire grid");
    return grid;
  }

  @Override
  public boolean isGameOver() {
    mocklog.add("checking if the game is over");
    return false;
  }

  @Override
  public Player getWinner() {
    mocklog.add("checking for the winner");
    return null;
  }

  @Override
  public Player getCurrentPlayer() {
    mocklog.add("getting current player");
    return null;
  }

  @Override
  public Player getRedPlayer() {
    mocklog.add("getting red player");
    return null;
  }

  @Override
  public Player getBluePlayer() {
    mocklog.add("getting blue player");
    return null;
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {
    mocklog.add("getting player score for " + teamColor + " team");
    return 0;
  }

  @Override
  public int numCardFlips(Card card, int row, int col, Player player) {
    mocklog.add("checking number of card flips for card at cell (" + row + ", " + col + ")");
    return 0;
  }

  /**
   * Gets the mock log for testing.
   *
   * @return the interaction log
   */
  public List<String> getMockLog() {
    return new ArrayList<>(mocklog);
  }
}
