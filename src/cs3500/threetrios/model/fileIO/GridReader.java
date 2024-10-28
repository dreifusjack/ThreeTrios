package cs3500.threetrios.model.fileIO;

import java.io.IOException;
import java.util.List;

import cs3500.threetrios.model.GridCell;

/**
 * Reads data from a textual file. Is responsible for returning that data to the model to
 * initialize its fields.
 */
public interface GridReader {

  public void readFile() throws IOException;

  public List<Integer> coordinates();

  public List<List<GridCell>> getCells();

  public int getNumberOfCardCells();
}
