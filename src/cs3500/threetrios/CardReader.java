package cs3500.threetrios;

import java.io.IOException;
import java.util.List;

public interface CardReader {
  public void readFile() throws IOException;
  public List<Card> getCards();
  public String getFilename();
}
