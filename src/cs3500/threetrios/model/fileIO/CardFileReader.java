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

public class CardFileReader implements CardReader {
  private final Scanner fileScan;
  private final List<Card> cards;

  public CardFileReader(String filename) {
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

  @Override
  public Card removeCard() {
    if (cards.isEmpty()) {
      throw new IllegalArgumentException("No cards to remove");
    }
    return cards.remove(0);
  }
}
