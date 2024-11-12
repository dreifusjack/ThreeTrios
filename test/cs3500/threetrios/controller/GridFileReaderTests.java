package cs3500.threetrios.controller;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Hole;

import static org.junit.Assert.assertArrayEquals;
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

  @Test(expected = IllegalArgumentException.class)
  public void testBadGridSizingConfigs() {
    pendingFilename = new GridFileReader("badsizingconfigs.txt");
    pendingFilename.readFile();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyBoard() {
    pendingFilename = new GridFileReader("empty.txt");
    pendingFilename.readFile();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMismatchCols() {
    pendingFilename = new GridFileReader("mismatchcol.txt");
    pendingFilename.readFile();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMismatchRows() {
    pendingFilename = new GridFileReader("mismatchrow.txt");
    pendingFilename.readFile();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testContentNotHoleOrCardCell() {
    pendingFilename = new GridFileReader("badcontents.txt");
    pendingFilename.readFile();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvenNumCardCells() {
    pendingFilename = new GridFileReader("evencardcell.txt");
    pendingFilename.readFile();
  }

  @Test
  public void testSuccessfullyReadingFromFileAndSavingData() {
    assertEquals(0, gridFileReader.getNumberOfCardCells());
    assertArrayEquals(new GridCell[0][0], gridFileReader.getGrid());

    gridFileReader.readFile();

    assertEquals(3, gridFileReader.getNumberOfCardCells());

    GridCell[][] expected = {
            {new CardCell(), new Hole()},
            {new CardCell(), new CardCell()}
    };

    assertArrayEquals(expected, gridFileReader.getGrid());
  }

  @Test
  public void testGetGridMutation() {
    assertEquals(new GridCell[0][0], gridFileReader.getGrid());
    GridCell[][] gridCopy = gridFileReader.getGrid();
    gridCopy[0][0] = new CardCell();
    assertEquals(new GridCell[0][0], gridFileReader.getGrid());
  }
}
