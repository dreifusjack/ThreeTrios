package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GridFileReader implements GridReader {
  private final Scanner fileScan;
  private final List<Integer> gridCords; // first int is row second int is col
  private final List<List<GridCell>> gridCells;
  private int numOfCardCells;

  public GridFileReader(String filename) {
    try {
      File file = new File(filename);
      fileScan = new Scanner(file);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + filename);
    }
    this.gridCords = new ArrayList<>();
    this.gridCells = new ArrayList<>();
    this.numOfCardCells = 0;
  }

  @Override
  public void readFile() throws IOException {
    if (fileScan.hasNextLine()) {
      String[] firstLineInFile = fileScan.nextLine().split(" ");
      if (firstLineInFile.length != 2) {
        throw new IllegalArgumentException("Invalid grid file format");
      }
      int row = Integer.parseInt(firstLineInFile[0]);
      int col = Integer.parseInt(firstLineInFile[1]);
      gridCords.add(row);
      gridCords.add(col);
    } else {
      throw new IllegalArgumentException("Grid file is empty");
    }

    int rowCount = 0;
    while (fileScan.hasNextLine()) {
      String line = fileScan.nextLine(); // the second line in the file
      if (line.length() != gridCords.get(1)) {
        throw new IllegalArgumentException("Col length fails to match file specified col length.");
      }
      List<GridCell> cellRow = new ArrayList<>();
      for (int index = 0; index < line.length(); index++) {
        char cellType = line.charAt(index);
        if (cellType == 'C') {
          cellRow.add(new CardCell());
          numOfCardCells++;
        } else if (cellType == 'X') {
          cellRow.add(new Hole());
        } else {
          throw new IllegalArgumentException("Invalid cell type");
        }
      }
      gridCells.add(cellRow);
      rowCount++;
    }
    if (rowCount != gridCords.get(0)) {
      throw new IllegalArgumentException("Row length fails to match file specified row length.");
    }
  }


  @Override
  public List<Integer> coordinates() {
    return new ArrayList<>(gridCords);
  }

  @Override
  public List<List<GridCell>> getCells() {
    List<List<GridCell>> result = new ArrayList<>();
    for (List<GridCell> cellRow : gridCells) {
      result.add(new ArrayList<>(cellRow));
    }
    return result;
  }

  @Override
  public int getNumberOfCardCells() {
    return this.numOfCardCells;
  }
}