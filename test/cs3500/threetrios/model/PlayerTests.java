package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of the Player interface within the model.
 */
public class PlayerTests {
  private Player red;
  private Player blue;
  private Card king;
  private Card worldDragon;
  private Card heroKnight;
  private Card corruptKing;
  private Card firePhoenix;


  @Before
  public void setUp() {
    red = new ThreeTriosPlayer(TeamColor.RED);
    blue = new ThreeTriosPlayer(TeamColor.BLUE);
    king = new ThreeTrioCard("King",
            ThreeTrioCard.CardValue.SEVEN,
            ThreeTrioCard.CardValue.THREE,
            ThreeTrioCard.CardValue.NINE,
            ThreeTrioCard.CardValue.A);

     worldDragon =
            new ThreeTrioCard("WorldDragon", ThreeTrioCard.CardValue.ONE,
                    ThreeTrioCard.CardValue.FIVE, ThreeTrioCard.CardValue.SIX,
                    ThreeTrioCard.CardValue.ONE);
     heroKnight =
            new ThreeTrioCard("HeroKnight", ThreeTrioCard.CardValue.A,
                    ThreeTrioCard.CardValue.FOUR,
                    ThreeTrioCard.CardValue.FOUR, ThreeTrioCard.CardValue.ONE);

     corruptKing =
            new ThreeTrioCard("CorruptKing", ThreeTrioCard.CardValue.THREE,
                    ThreeTrioCard.CardValue.ONE, ThreeTrioCard.CardValue.ONE,
                    ThreeTrioCard.CardValue.TWO);
     firePhoenix =
            new ThreeTrioCard("FirePhoenix",
                    ThreeTrioCard.CardValue.TWO, ThreeTrioCard.CardValue.FOUR,
                    ThreeTrioCard.CardValue.THREE, ThreeTrioCard.CardValue.TWO);
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

  @Test
  public void testBestCardInTopLeft() {
    Map<Integer, Integer> result = new HashMap<Integer, Integer>();
    result.put(0, 11);
    red.addToHand(worldDragon);
    red.addToHand(heroKnight);
    red.addToHand(corruptKing);
    red.addToHand(firePhoenix);

    assertEquals(result, red.bestCardInTopLeft());
  }

  @Test
  public void testBestCardInTopRight() {
    Map<Integer, Integer> result = new HashMap<Integer, Integer>();
    result.put(0, 7);
    red.addToHand(worldDragon);
    red.addToHand(heroKnight);
    red.addToHand(corruptKing);
    red.addToHand(firePhoenix);

    assertEquals(result, red.bestCardInTopRight());
  }

  @Test
  public void testBestCardInBottomLeft() {
    Map<Integer, Integer> result = new HashMap<Integer, Integer>();
    result.put(1, 14);
    red.addToHand(worldDragon);
    red.addToHand(heroKnight);
    red.addToHand(corruptKing);
    red.addToHand(firePhoenix);

    assertEquals(result, red.bestCadInBottomLeft());
  }

  @Test
  public void testBestCardInBottomRight() {
    Map<Integer, Integer> result = new HashMap<Integer, Integer>();
    result.put(1, 11);
    red.addToHand(worldDragon);
    red.addToHand(heroKnight);
    red.addToHand(corruptKing);
    red.addToHand(firePhoenix);

    assertEquals(result, red.bestCardInBoomRight());
  }
}
