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
    king = new ThreeTrioCard("King",
            ThreeTrioCard.CardValue.SEVEN,
            ThreeTrioCard.CardValue.THREE,
            ThreeTrioCard.CardValue.NINE,
            ThreeTrioCard.CardValue.A);
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

  @Test
  public void testAddCardToEmptyCell() {
    emptyCell.addCard(king);
    assertEquals(king, emptyCell.getCard());
  }

  @Test
  public void testToggleColor() {
    assertEquals(null, fullCell.getColor());
    fullCell.toggleColor();
    assertEquals(TeamColor.RED, fullCell.getColor());
  }

  @Test
  public void testToString() {
    assertEquals("R", fullCell.toString());
    assertEquals("_", emptyCell.toString());
  }

  @Test
  public void testSetColor() {
    fullCell.setColor(TeamColor.RED);
    assertEquals(TeamColor.RED, fullCell.getColor());
  }

  @Test
  public void testGetColor() {
    assertEquals(null, fullCell.getColor());
    fullCell.setColor(TeamColor.BLUE);
    assertEquals(TeamColor.BLUE, fullCell.getColor());
  }

  @Test
  public void testGetCard() {
    assertEquals(king, fullCell.getCard());
  }

  @Test
  public void testCardToString() {
    assertEquals("King 7 9 3 A", fullCell.cardToString());
  }

}
