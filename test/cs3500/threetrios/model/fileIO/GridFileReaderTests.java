package cs3500.threetrios.model.fileIO;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Hole;

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
    assertEquals(List.of(), gridFileReader.specifiedSizes());
    assertEquals(List.of(), gridFileReader.getGrid());

    gridFileReader.readFile();

    assertEquals(3, gridFileReader.getNumberOfCardCells());
    assertEquals(List.of(2, 2), gridFileReader.specifiedSizes());

    List<List<GridCell>> expected = List.of(List.of(new CardCell(), new Hole()),
            List.of(new CardCell(), new CardCell()));
    for (int row = 0; row < expected.size(); row++) {
      for (int col = 0; col < expected.get(row).size(); col++) {
        assertEquals(expected.get(row).get(col).getClass(),
                gridFileReader.getGrid().get(row).get(col).getClass());
      }
    }
  }

  @Test
  public void testGetGridMutation() {
    assertEquals(List.of(), gridFileReader.getGrid());
    List<List<GridCell>> gridCopy = gridFileReader.getGrid();
    gridCopy.add(null);
    assertEquals(List.of(), gridFileReader.getGrid());
  }

  @Test
  public void testSpecifiedSizesMutation() {
    assertEquals(List.of(), gridFileReader.specifiedSizes());
    List<Integer> sizesCopy = gridFileReader.specifiedSizes();
    sizesCopy.add(null);
    assertEquals(List.of(), gridFileReader.specifiedSizes());
  }
}
