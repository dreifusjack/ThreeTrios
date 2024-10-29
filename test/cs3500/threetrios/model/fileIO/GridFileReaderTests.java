package cs3500.threetrios.model.fileIO;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.ThreeTrioCard;

import static org.junit.Assert.assertEquals;

/**
 * To test the functionality of our card file reader that gathers data about the configurations
 * for the model to use in game setting up.
 */
public class GridFileReaderTests {
  private GridFileReader gridFileReader;
  private GridFileReader pendingFilename;

  @Before
  public void setUp() {
    gridFileReader = new GridFileReader("world2x2.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilename() {
    new GridFileReader(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonCreatedFile() {
    new GridFileReader("non-existing-file");
  }
}
