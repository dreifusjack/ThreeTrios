package cs3500.threetrios.model.fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ThreeTrioCard;

/**
 * Responsible for reading card configuration files and saving data about the cards for the
 * model to use in setting up player cards for Red and Blue teams.
 */
public class CardFileReader implements CardReader {
  private final Scanner fileScan;
  private final List<Card> cards;

  /**
   * Constructs a CardFileReader in terms of the filename it should read from. Assuming that
   * all card config files are in docs/cardcongiurations/.
   *
   * @param filename name of the file
   * @throws IllegalArgumentException if filename is null
   * @throws IllegalArgumentException if filename is not found in assumed location
   */
  public CardFileReader(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("The filename cannot be null.");
    }
    try {
      String path = "docs" + File.separator + "cardconfigurations" + File.separator + filename;
      File file = new File(path);
      fileScan = new Scanner(file);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + filename);
    }
    this.cards = new ArrayList<>();
  }

  @Override
  public void readFile() {
    Set<String> cardNames = new HashSet<>();

    if (!fileScan.hasNextLine()) {
      throw new IllegalArgumentException("Card file is empty.");
    }

    while (fileScan.hasNextLine()) {
      String[] line = fileScan.nextLine().split(" ");

      if (line.length != 5) {
        throw new IllegalArgumentException("Invalid card file format");
      }

      String cardName = line[0];

      if (!cardNames.add(cardName)) {
        throw new IllegalArgumentException("Duplicate card name: " + cardName);
      }

      ThreeTrioCard.CardValue north = ThreeTrioCard.CardValue.fromString(line[1]);
      ThreeTrioCard.CardValue south = ThreeTrioCard.CardValue.fromString(line[2]);
      ThreeTrioCard.CardValue east = ThreeTrioCard.CardValue.fromString(line[3]);
      ThreeTrioCard.CardValue west = ThreeTrioCard.CardValue.fromString(line[4]);

      ThreeTrioCard newCard = new ThreeTrioCard(cardName, null, north, east, south, west);
      cards.add(newCard);
    }
  }

  @Override
  public List<Card> getCards() {
    return new ArrayList<>(cards);
  }
}
