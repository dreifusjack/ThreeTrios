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
    king = new ThreeTrioCard("King",
            TeamColor.RED,
            ThreeTrioCard.CardValue.SEVEN,
            ThreeTrioCard.CardValue.THREE,
            ThreeTrioCard.CardValue.NINE,
            ThreeTrioCard.CardValue.A);
    dragon = new ThreeTrioCard("Dragon",
            TeamColor.RED,
            ThreeTrioCard.CardValue.TWO,
            ThreeTrioCard.CardValue.NINE,
            ThreeTrioCard.CardValue.EIGHT,
            ThreeTrioCard.CardValue.NINE);
    bird = new ThreeTrioCard("Bird",
            TeamColor.BLUE,
            ThreeTrioCard.CardValue.SEVEN,
            ThreeTrioCard.CardValue.TWO,
            ThreeTrioCard.CardValue.FIVE,
            ThreeTrioCard.CardValue.THREE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullNameConstruction() {
    new ThreeTrioCard(null,
            TeamColor.BLUE,
            ThreeTrioCard.CardValue.SEVEN,
            ThreeTrioCard.CardValue.TWO,
            ThreeTrioCard.CardValue.FIVE,
            ThreeTrioCard.CardValue.THREE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullValueConstruction() {
    new ThreeTrioCard("hello",
            TeamColor.BLUE,
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

  @Test
  public void testGetColor() {
    assertEquals(TeamColor.RED, king.getColor());
    assertEquals(TeamColor.BLUE, bird.getColor());
  }

  @Test
  public void testSetColor() {
    Card noColor = new ThreeTrioCard("Card", null,
            ThreeTrioCard.CardValue.EIGHT,
            ThreeTrioCard.CardValue.EIGHT,
            ThreeTrioCard.CardValue.EIGHT,
            ThreeTrioCard.CardValue.EIGHT);
    noColor.setColor(TeamColor.RED);
    assertEquals(TeamColor.RED, noColor.getColor());
    noColor.setColor(TeamColor.BLUE);
    assertEquals(TeamColor.BLUE, noColor.getColor());
  }

  @Test
  public void testToggleColor() {
    assertEquals(TeamColor.RED, king.getColor());
    king.toggleColor();
    assertEquals(TeamColor.BLUE, king.getColor());
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
}
