package cs3500.threetrios.controller;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ThreeTriosCard;

import static org.junit.Assert.assertEquals;

/**
 * To test the functionality of our card file reader that gathers data about the configurations
 * for the model to use in game setting up.
 */
public class CardFileReaderTests {
  private CardFileReader cardFileReader;
  private CardFileReader pendingFilename;

  @Before
  public void setUp() {
    cardFileReader = new CardFileReader("3cardsonly.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilename() {
    new CardFileReader(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonCreatedFile() {
    new CardFileReader("non-existing-file");
  }

  @Test
  public void testGetCardMutation() {
    assertEquals(List.of(), cardFileReader.getCards());
    List<Card> pointer = cardFileReader.getCards();
    pointer.add(null);
    assertEquals(List.of(), cardFileReader.getCards());
  }

  @Test
  public void testReadFileSuccessfully() {
    cardFileReader.readFile();
    ArrayList<Card> expected = new ArrayList<>();
    expected.add(new ThreeTriosCard("CorruptKing",
            ThreeTriosCard.AttackValue.SEVEN, ThreeTriosCard.AttackValue.NINE,
            ThreeTriosCard.AttackValue.THREE, ThreeTriosCard.AttackValue.A));
    expected.add(new ThreeTriosCard("AngryDragon",
            ThreeTriosCard.AttackValue.TWO, ThreeTriosCard.AttackValue.EIGHT,
            ThreeTriosCard.AttackValue.NINE, ThreeTriosCard.AttackValue.NINE));
    expected.add(new ThreeTriosCard("WindBird",
            ThreeTriosCard.AttackValue.SEVEN, ThreeTriosCard.AttackValue.FIVE,
            ThreeTriosCard.AttackValue.TWO, ThreeTriosCard.AttackValue.THREE));
    // testing get cards method
    assertEquals(expected, cardFileReader.getCards());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingFromCardFileInvalidFormat() {
    pendingFilename = new CardFileReader("badformat.txt");
    pendingFilename.readFile();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingFromCardFileInvalidCardValue() {
    pendingFilename = new CardFileReader("invalidcardvalue.txt");
    pendingFilename.readFile();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingFromCardFileDuplicateCardNames() {
    pendingFilename = new CardFileReader("duplicatenames.txt");
    pendingFilename.readFile();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingFromEmptyCardFile() {
    pendingFilename = new CardFileReader("empty.txt");
    pendingFilename.readFile();
  }
}
