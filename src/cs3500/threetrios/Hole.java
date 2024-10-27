package cs3500.threetrios;

/**
 * Represents a "Hole" in the grid meaning no card can be placed on this cell.
 */
public class Hole implements GridCell {
  @Override
  public void addCard(Card card) {
    throw new IllegalStateException("Cannot place cards on a hole");
  }

  @Override
  public void changeCardColor() {
    throw new IllegalStateException("Holes have no cards");
  }

  @Override
  public Card getCard() {
    throw new IllegalStateException("Holes have no cards");
  }

  @Override
  public String toString() {
    return  "_";
  }

  @Override
  public boolean isHole() {
    return true;
  }
}
