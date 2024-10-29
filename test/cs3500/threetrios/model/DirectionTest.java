package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for directions.
 */
public class DirectionTest {

  // Test getRowHelper for each direction.
  @Test
  public void testGetRowHelper() {
    // NORTH should decrease row by 1
    Assert.assertEquals(-1, Direction.getRowHelper(Direction.NORTH));

    // SOUTH should increase row by 1
    Assert.assertEquals(1, Direction.getRowHelper(Direction.SOUTH));

    // EAST should have no row change
    Assert.assertEquals(0, Direction.getRowHelper(Direction.EAST));

    // WEST should have no row change
    Assert.assertEquals(0, Direction.getRowHelper(Direction.WEST));
  }

  // Test getColHelper for each direction.
  @Test
  public void testGetColHelper() {
    // NORTH should have no column change
    Assert.assertEquals(0, Direction.getColHelper(Direction.NORTH));

    // SOUTH should have no column change
    Assert.assertEquals(0, Direction.getColHelper(Direction.SOUTH));

    // EAST should increase column by 1
    Assert.assertEquals(1, Direction.getColHelper(Direction.EAST));

    // WEST should decrease column by 1
    Assert.assertEquals(-1, Direction.getColHelper(Direction.WEST));
  }

}