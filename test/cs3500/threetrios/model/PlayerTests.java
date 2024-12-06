package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of the Player interface within the model.
 */
public class PlayerTests {
  private Player red;
  private Player blue;
  private Card king;


  @Before
  public void setUp() {
    red = new ThreeTriosPlayer(TeamColor.RED);
    blue = new ThreeTriosPlayer(TeamColor.BLUE);
    king = new ThreeTriosCard("King",
            ThreeTriosCard.AttackValue.SEVEN,
            ThreeTriosCard.AttackValue.THREE,
            ThreeTriosCard.AttackValue.NINE,
            ThreeTriosCard.AttackValue.A);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTeam() {
    new ThreeTriosPlayer(null);
  }

  @Test
  public void testAddToHand() {
    assertEquals(List.of(), red.getHand());
    red.addToHand(king);
    assertEquals(List.of(king), red.getHand());
  }

  @Test
  public void testGetHandMutation() {
    red.addToHand(king);
    assertEquals(List.of(king), red.getHand());
    List<Card> cards = red.getHand();
    cards.remove(king);
    assertEquals(List.of(king), red.getHand());
  }

  @Test
  public void testRemoveFromHand() {
    red.addToHand(king);
    assertEquals(List.of(king), red.getHand());
    red.removeCard(0);
    assertEquals(List.of(), red.getHand());
  }

  @Test
  public void testGetColor() {
    assertEquals(TeamColor.RED, red.getColor());
    assertEquals(TeamColor.BLUE, blue.getColor());
  }

  @Test
  public void testCloneMutation() {
    red.addToHand(king);
    assertEquals(List.of(king), red.getHand());
    Player clone = red.clone();
    clone.addToHand(king);
    assertEquals(List.of(king, king), clone.getHand());
    assertEquals(List.of(king), red.getHand());
  }

  @Test
  public void testToString() {
    String expected = "Player: BLUE\n";
    assertEquals(expected, blue.toString());
    String expected2 = "Player: RED\n";
    assertEquals(expected2, red.toString());
  }
}
