package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GridFileReader implements GridReader {
  String filename;
  List<Integer> gridCords; // first int is row second int is col
  List<List<GridCell>> gridCells;
  int numOfCardCells;

  public GridFileReader(String filename) {
    this.filename = filename;
    gridCords = new ArrayList<>();
    gridCells = new ArrayList<>();
    numOfCardCells = 0;
  }

  @Override
  public void readFile() throws IOException {
    try {
      File file = new File(filename);
      Scanner scanner = new Scanner(file);
      boolean firstLine = true;
      int row = 0;
      int col = 0;
      int rowCount = 1;
      while (scanner.hasNextLine()) {
        if (firstLine) {
          String line = scanner.nextLine();
          if (line.length() == 3) {
            row = Integer.parseInt(line.substring(0, 1));
            col = Integer.parseInt(line.substring(2, 3));
            gridCords.add(row);
            gridCords.add(col);
            firstLine = false;
          }
        }
        String line = scanner.nextLine();
        if (line.length() != col || rowCount > row) {
          throw new IllegalArgumentException("Invalid grid file format");
        }
        List<GridCell> cellRow = new ArrayList<>();
        for (int index = 0; index < line.length(); index++) {
          if (line.charAt(index) == 'C') {
            cellRow.add(new CardCell());
            numOfCardCells++;
          } else if (line.charAt(index) == 'X') {
            cellRow.add(new Hole());
          } else {
            throw new IllegalArgumentException("Invalid cell type");
          }
        }
        gridCells.add(cellRow);
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
  public List<List<GridCell>> cells() {
    List<List<GridCell>> result = new ArrayList<>();
    for (List<GridCell> cellRow : gridCells) {
      result.add(new ArrayList<>(cellRow));
    }
    return result;
  }
}
