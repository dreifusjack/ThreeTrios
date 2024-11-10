package cs3500.threetrios.model;

/**
 * Represents cells in the ThreeTrio grid that can have cards placed on.
 */
public class CardCell implements GridCell {
  private Card card;
  private TeamColor color;

  /**
   * Constructs a CardCell with no present card added to its cell. Initialized to null because
   * cards will be added to the cell through game play in the model.
   */
  public CardCell() {
    card = null;
    color = null;
  }

  public CardCell(Card card, TeamColor color) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    this.card = card;
    this.color = color;
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
  public void toggleColor() {
    if (card == null) {
      throw new IllegalStateException("This cell does not have a card added.");
    }
    color = color == TeamColor.RED ? TeamColor.BLUE : TeamColor.RED;
  }

  @Override
  public void setColor(TeamColor color) {
    if (this.color != null) {
      throw new IllegalStateException("This cell already has a color added.");
    }
    this.color = color;
  }

  @Override
  public Card getCard() {
    return card;
  }

  @Override
  public String cardToString() {
    if (card == null) {
      throw new IllegalStateException("This cell does not have a card added.");
    }
    return this.card.toString();
  }

  @Override
  public TeamColor getColor() {
    if (color == null) {
      throw new IllegalStateException("This cell does not have a color added.");
    }
    return color;
  }

  @Override
  public boolean isEmpty() {
    return card == null;
  }

  @Override
  public String toString() {
    if (card == null) {
      return "_";
    }
    if (color == null) {
      throw new IllegalStateException("Card occupies cell but no color has been set.");
    }
    return color.toStringAbbreviation();
  }
}
