package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the GridCell interface.
 */
public class GridCellTests {
  private GridCell hole;
  private GridCell emptyCell;
  private GridCell fullCell;
  private Card king;

  @Before
  public void setUp() {
    hole = new Hole();
    emptyCell = new CardCell();
    fullCell = new CardCell();
    king = new ThreeTriosCard("King",
            ThreeTriosCard.AttackValue.SEVEN,
            ThreeTriosCard.AttackValue.THREE,
            ThreeTriosCard.AttackValue.NINE,
            ThreeTriosCard.AttackValue.A);
    fullCell.addCard(king);
  }

  //tests for holes
  @Test(expected = IllegalStateException.class)
  public void testAddCardToHole() {
    hole.addCard(king);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardInHole() {
    hole.getCard();
  }

  @Test(expected = IllegalStateException.class)
  public void testChangeCardColorInHole() {
    hole.toggleColor();
  }

  @Test
  public void testToStringInHole() {
    assertEquals(" ", hole.toString());
  }

  //tests for card cells
  @Test(expected = IllegalStateException.class)
  public void testAddCardToOccupiedCell() {
    fullCell.addCard(king);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardWithNullCard() {
    emptyCell.addCard(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testToStringWhenOccupiedCellDoesNotHaveColor() {
    fullCell.toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testCardToStringInHole() {
    hole.cardToString();
  }

  @Test(expected = IllegalStateException.class)
  public void testCardToStringInNonOccupiedCell() {
    emptyCell.cardToString();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetColorInHole() {
    hole.getColor();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetColorInCellWithNoColor() {
    fullCell.getColor();
  }

  @Test
  public void testGetAndSetColor() {
    fullCell.setColor(TeamColor.BLUE);
    assertEquals(TeamColor.BLUE, fullCell.getColor());
  }

  @Test
  public void testCardToString() {
    assertEquals("King 7 9 3 A", fullCell.cardToString());
  }

  @Test
  public void testToString() {
    fullCell.setColor(TeamColor.RED);
    assertEquals("R", fullCell.toString());
    assertEquals("_", emptyCell.toString());
    assertEquals(" ", hole.toString());
  }

  @Test
  public void testAddAndGetCard() {
    emptyCell.addCard(king);
    assertEquals(king, emptyCell.getCard());
  }

  @Test
  public void testToggleColor() {
    fullCell.setColor(TeamColor.BLUE);
    assertEquals(TeamColor.BLUE, fullCell.getColor());
    fullCell.toggleColor();
    assertEquals(TeamColor.RED, fullCell.getColor());
  }
}
