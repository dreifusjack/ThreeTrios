package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardFileReader implements CardReader {
  String filename;
  List<Card> cards;

  public CardFileReader(String filename) {
    this.filename = filename;
    cards = new ArrayList<>();
  }

  @Override
  public void readFile() throws IOException {
    try {
      File file = new File(filename);
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String[] line = scanner.nextLine().split(" ");
        if (line.length != 5) {
          throw new IllegalArgumentException("Invalid card file format");
        } else {
          String cardName = scanner.next();
          ThreeTrioCard.CardValue north = ThreeTrioCard.CardValue.fromString(scanner.next());
          ThreeTrioCard.CardValue south = ThreeTrioCard.CardValue.fromString(scanner.next());
          ThreeTrioCard.CardValue east = ThreeTrioCard.CardValue.fromString(scanner.next());
          ThreeTrioCard.CardValue west = ThreeTrioCard.CardValue.fromString(scanner.next());
          ThreeTrioCard newCard = new ThreeTrioCard(cardName, null, north, south, east, west);
          cards.add(newCard);
        }
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(e.getMessage());
    }
  }

  @Override
  public List<Card> cards() {
    return new ArrayList<>(cards);
  }
}
