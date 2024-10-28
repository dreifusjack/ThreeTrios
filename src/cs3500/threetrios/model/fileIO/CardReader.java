package cs3500.threetrios.model.fileIO;

import java.io.IOException;
import java.util.List;

import cs3500.threetrios.model.Card;

public interface CardReader {
  public void readFile() throws IOException;
  public List<Card> getCards();
  public Card removeCard();
}
