package cs3500.threetrios;

/**
 * Represents cells in the ThreeTrio grid that can have cards placed on.
 */
public class CardCell implements GridCell {
  private Card card;

  /**
   * Constructs a CardCell with no present card added to its cell.
   */
  public CardCell() {
    card = null;
  }

  @Override
  public void addCard(Card card) {
    if (this.card != null) {
      throw new IllegalStateException("This cell already has a card added.");
    }
    this.card = card;
  }

  @Override
  public void changeCardColor() {
    card.changeColor();
  }

  @Override
  public Card getCard() {
    if (card == null) {
      throw new IllegalStateException("This cell does not have a card added.");
    }
    return card.clone();
  }

  @Override
  public String toString() {
    if (card == null) {
      return " ";
    } else {
      return card.getColor().toStringAbbreviation();
    }
  }

  @Override
  public boolean isHole() {
    return false;
  }
}
