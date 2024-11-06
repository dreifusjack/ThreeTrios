package cs3500.threetrios.controller;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ThreeTrioCard;

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
    expected.add(new ThreeTrioCard("CorruptKing",
            ThreeTrioCard.CardValue.SEVEN, ThreeTrioCard.CardValue.NINE,
            ThreeTrioCard.CardValue.THREE, ThreeTrioCard.CardValue.A));
    expected.add(new ThreeTrioCard("AngryDragon",
            ThreeTrioCard.CardValue.TWO, ThreeTrioCard.CardValue.EIGHT,
            ThreeTrioCard.CardValue.NINE, ThreeTrioCard.CardValue.NINE));
    expected.add(new ThreeTrioCard("WindBird",
            ThreeTrioCard.CardValue.SEVEN, ThreeTrioCard.CardValue.FIVE,
            ThreeTrioCard.CardValue.TWO, ThreeTrioCard.CardValue.THREE));
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
