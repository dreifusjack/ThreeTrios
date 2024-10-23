package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GridFileReader implements GridReader {
  private final String filename;
  private final List<Integer> gridCords; // first int is row second int is col
  private final List<List<GridCell>> gridCells;
  private int numOfCardCells;

  public GridFileReader(String filename) {
    this.filename = filename;
    this.gridCords = new ArrayList<>();
    this.gridCells = new ArrayList<>();
    this.numOfCardCells = 0;
  }

  @Override
  public void readFile() throws IOException {
    try {
      File file = new File(filename);
      Scanner scanner = new Scanner(file);

      if (scanner.hasNextLine()) {
        String[] firstLineInFile = scanner.nextLine().split(" ");
        if (firstLineInFile.length != 2) {
          throw new IllegalArgumentException("Invalid grid file format");
        }

        int row = Integer.parseInt(firstLineInFile[0]);  // Similar like what I did in CardFileReader, I think this would be better since if we stick to the old logic and assume that we will always care about the first 3 characters. This won't be correct if we have like for example 13 15 for the list line.
        int col = Integer.parseInt(firstLineInFile[1]);
        gridCords.add(row);
        gridCords.add(col);
      } else {
        throw new IllegalArgumentException("Grid file is empty");
      }

      int rowCount = 0;
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine(); // this is the second line in the file
        if (line.length() != gridCords.get(1)) {
          throw new IllegalArgumentException("Invalid row length");
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

      if (rowCount != gridCords.get(0)) { // I also used a similar approach I fixed in CardFileReader. Check if the number of rows in the file matches the rows in the first line of the file
        throw new IllegalArgumentException("Number of rows doesn't match the specified grid size in the file"); // we missed this so i just added this condition
      }

    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(e.getMessage());
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
  public String getFilename() {
    return this.filename;
  }

  @Override
  public int getNumberOfCards() {
    return this.numOfCardCells;
  }
}