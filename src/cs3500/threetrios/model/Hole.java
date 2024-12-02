package cs3500.threetrios.model;

/**
 * Represents a "Hole" in the grid meaning no card can be placed on this cell.
 */
public class Hole implements GridCell {
  @Override
  public void addCard(Card card) {
    throw new IllegalStateException("Cannot place cards on a hole");
  }

  @Override
  public void toggleColor() {
    throw new IllegalStateException("Holes do not have colors");
  }

  @Override
  public void setColor(TeamColor color) {
    throw new IllegalStateException("Holes do not have colors");
  }

  @Override
  public Card getCard() {
    throw new IllegalStateException("Holes have no cards");
  }

  @Override
  public String cardToString() {
    throw new IllegalStateException("Holes have no cards");
  }

  @Override
  public TeamColor getColor() {
    throw new IllegalStateException("Hole do not have colors");
  }

  @Override
  public boolean isOccupied() {
    return true;
  }

  @Override
  public Card getCardCopy() {
    return getCard();
  }

  @Override
  public String toString() {
    return " ";
  }
}
