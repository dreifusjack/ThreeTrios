package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardFileReader implements CardReader {
  private final Scanner fileScan;
  private final List<Card> cards;

  public CardFileReader(String filename) {
    try {
      File file = new File(filename);
      fileScan = new Scanner(file);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + filename);
    }
    this.cards = new ArrayList<>();
  }

  @Override
  public void readFile() {
    while (fileScan.hasNextLine()) {
      String[] line = fileScan.nextLine().split(" ");

      if (line.length != 5) {
        throw new IllegalArgumentException("Invalid card file format");
      }

      String cardName = line[0];
      ThreeTrioCard.CardValue north = ThreeTrioCard.CardValue.fromString(line[1]); //i check the documentation so after you slit a line, you can access each slitted component in that line with [index you want to access].
      ThreeTrioCard.CardValue south = ThreeTrioCard.CardValue.fromString(line[2]);
      ThreeTrioCard.CardValue east = ThreeTrioCard.CardValue.fromString(line[3]);
      ThreeTrioCard.CardValue west = ThreeTrioCard.CardValue.fromString(line[4]);

      ThreeTrioCard newCard = new ThreeTrioCard(cardName, null, north, south, east, west);
      cards.add(newCard);
    }
  }


  @Override
  public List<Card> getCards() {
    return new ArrayList<>(cards);
  }
}
