package cs3500.threetrios.model;

/**
 * Represents cells in the ThreeTrio grid that can have cards placed on.
 */
public class CardCell implements GridCell {
  private Card card;

  /**
   * Constructs a CardCell with no present card added to its cell. Initialized to null because
   * cards will be added to the cell through game play in the model.
   */
  public CardCell() {
    card = null;
  }

  @Override
  public void addCard(Card card) {
    if (this.card != null) {
      throw new IllegalStateException("This cell already has a card added.");
    }
    if (card == null) {
      throw new IllegalArgumentException();
    }
    this.card = card;
  }

  @Override
  public void changeCardColor() {
    card.toggleColor();
  }

  @Override
  public Card getCard() {
    return card;
  }

  @Override
  public String toString() {
    if (card == null) {
      return "_";
    } else {
      return card.getColor().toStringAbbreviation();
    }
  }
}
