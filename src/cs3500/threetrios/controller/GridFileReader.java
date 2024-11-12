package cs3500.threetrios.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Hole;

/**
 * Implements the behaviors of the GridReader. Reads from a grid configuration file
 * and if successful, saves data about the grid, the sizes, and the number of empty card cells.
 */
public class GridFileReader implements GridReader {
  private final Scanner fileScan;
  private int rows;
  private int cols;
  private final GridCell[][] grid;
  private int numOfCardCells;

  /**
   * Constructs a GridFileReader in terms of the given filename. Creates a file path in a specified
   * location using the file nam. Assuming all grid configs are in docs/gridconfiguration/.
   *
   * @param filename String filename
   * @throws IllegalArgumentException if filename is null
   * @throws IllegalArgumentException if file is not found in assumed location
   */
  public GridFileReader(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("filename cannot be null");
    }
    try {
      String path = "docs" + File.separator + "gridconfigurations" + File.separator + filename;
      File file = new File(path);
      fileScan = new Scanner(file);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + filename);
    }
    numOfCardCells = 0;
    rows = 0;
    cols = 0;
    getBoardSize(); // updates the row and cols from the configuration file
    grid = new GridCell[rows][cols];
  }

  @Override
  public void readFile() {
    int rowCount = 0;
    while (fileScan.hasNextLine()) {
      String line = fileScan.nextLine(); // the second line in the file
      if (line.length() != cols) {
        throw new IllegalArgumentException("Col length fails to match file specified col length.");
      }
      GridCell[] cellRow = new GridCell[cols];
      for (int index = 0; index < line.length(); index++) {
        char cellType = line.charAt(index);
        if (cellType == 'C') {
          cellRow[index] = new CardCell();
          numOfCardCells++;
        } else if (cellType == 'X') {
          cellRow[index] = new Hole();
        } else {
          throw new IllegalArgumentException("Invalid cell type");
        }
      }
      grid[rowCount] = cellRow;
      rowCount++;
    }
    if (rowCount != rows) {
      throw new IllegalArgumentException("Row length fails to match file specified row length.");
    }
    if (numOfCardCells % 2 == 0) {
      throw new IllegalArgumentException("Number of card cells must be odd.");
    }
  }

  /**
   * Reads from the scanner to add the specified row and col to this GridFileReader's gridCords
   * list. The order must be [row, col] and must be size 2.
   *
   * @throws IllegalArgumentException if the format is not a specified row then column
   * @throws IllegalArgumentException if the file is empty
   */
  private void getBoardSize() {
    if (fileScan.hasNextLine()) {
      String[] firstLineInFile = fileScan.nextLine().split(" ");
      if (firstLineInFile.length != 2) {
        throw new IllegalArgumentException("Invalid grid file format");
      }
      rows = Integer.parseInt(firstLineInFile[0]);
      cols = Integer.parseInt(firstLineInFile[1]);
    } else {
      throw new IllegalArgumentException("Grid file is empty");
    }
  }

  @Override
  public GridCell[][] getGrid() {
    if (grid == null) {
      return null;
    }

    GridCell[][] copy = new GridCell[rows][cols];
    for (int index = 0; index < grid.length; index++) {
      copy[index] = grid[index].clone(); // clone each row for a deep copy
    }
    return copy;
  }

  @Override
  public int getNumberOfCardCells() {
    return numOfCardCells;
  }
}