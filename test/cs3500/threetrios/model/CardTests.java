package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality of the Card interface and CardValue enum within the model.
 */
public class CardTests {
  private Card king;
  private Card dragon;
  private Card bird;

  @Before
  public void setUp() {
    king = new ThreeTriosCard("King",
            ThreeTriosCard.AttackValue.SEVEN,
            ThreeTriosCard.AttackValue.THREE,
            ThreeTriosCard.AttackValue.NINE,
            ThreeTriosCard.AttackValue.A);
    dragon = new ThreeTriosCard("Dragon",
            ThreeTriosCard.AttackValue.TWO,
            ThreeTriosCard.AttackValue.NINE,
            ThreeTriosCard.AttackValue.EIGHT,
            ThreeTriosCard.AttackValue.NINE);
    bird = new ThreeTriosCard("Bird",
            ThreeTriosCard.AttackValue.SEVEN,
            ThreeTriosCard.AttackValue.TWO,
            ThreeTriosCard.AttackValue.FIVE,
            ThreeTriosCard.AttackValue.THREE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullNameConstruction() {
    new ThreeTriosCard(null,
            ThreeTriosCard.AttackValue.SEVEN,
            ThreeTriosCard.AttackValue.TWO,
            ThreeTriosCard.AttackValue.FIVE,
            ThreeTriosCard.AttackValue.THREE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullValueConstruction() {
    new ThreeTriosCard("hello",
            null,
            null,
            null,
            null);
  }

  @Test
  public void testToString() {
    String expected = "King 7 9 3 A";
    assertEquals(expected, king.toString());
    String expected2 = "Dragon 2 8 9 9";
    assertEquals(expected2, dragon.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompareNullDirection() {
    dragon.compare(bird, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompareNullOtherCard() {
    dragon.compare(null, Direction.NORTH);
  }

  @Test
  public void testCompare() {
    assertFalse("cards are on the same team", dragon.compare(king, Direction.EAST));
    assertFalse("direction values are equal", dragon.compare(bird, Direction.NORTH));
    assertTrue("dragon south > bird north", dragon.compare(bird, Direction.SOUTH));
    assertTrue("dragon east > bird west", dragon.compare(bird, Direction.EAST));
    assertTrue("dragon west > bird east", dragon.compare(bird, Direction.WEST));
  }

  @Test
  public void testGetEast() {
    assertEquals(ThreeTriosCard.AttackValue.NINE, dragon.getEast());
  }

  @Test
  public void testGetWest() {
    assertEquals(ThreeTriosCard.AttackValue.NINE, dragon.getWest());
  }

  @Test
  public void testGetSouth() {
    assertEquals(ThreeTriosCard.AttackValue.EIGHT, dragon.getSouth());
  }

  @Test
  public void testGetNorth() {
    assertEquals(ThreeTriosCard.AttackValue.TWO, dragon.getNorth());
  }
}
