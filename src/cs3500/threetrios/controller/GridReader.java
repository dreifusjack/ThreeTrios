package cs3500.threetrios.controller;

import cs3500.threetrios.model.GridCell;

/**
 * Behaviors for a grid configuration reader. Responsible for reading from a grid configuration,
 * validating the format of the file, constructing the grid if valid format from the file.
 */
public interface GridReader {
  /**
   * Reads from this GridReader's scanner that has be initialized to a specified grid configuration.
   * Creates an instance of a 2DGrid of GridCell's for the model to use as its internal grid.
   *
   * @throws IllegalArgumentException if the first line of the grid config fails to specify
   *                                  row and cols, first line must be rows(int) cols(int)
   * @throws IllegalArgumentException if the card config is empty
   * @throws IllegalArgumentException if at any point the configuration does not match its specified
   *                                  rows or columns
   * @throws IllegalArgumentException if the configuration does not specify a cell is either a hole
   *                                  or empty card cell
   * @throws IllegalArgumentException if the number of empty card cells is even
   */
  void readFile();

  /**
   * Return a copy of this GridReader's grid. This grid is only initialized after it is read
   * from the grid configuration file. Modifying this 2dGrid has no effect on this
   * GridReader's grid.
   *
   * @return a 2D array of grid cells for the model to operate on
   */
  GridCell[][] getGrid();

  /**
   * Returns the number of card cells in the grid. This is updated after the GridReader reads from
   * its grid file.
   *
   * @return number of empty card cells in this GridReader's grid.
   */
  int getNumberOfCardCells();
}
