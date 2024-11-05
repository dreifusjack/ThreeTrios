package cs3500.threetrios.model;

import java.awt.*;

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
    color = color == TeamColor.RED? TeamColor.BLUE : TeamColor.RED;
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
    return this.card.toString();
  }

  @Override
  public TeamColor getColor() {
    return color;
  }

  @Override
  public String toString() {
    if (card == null) {
      return "_";
    } else {
      return color.toStringAbbreviation();
    }
  }
}
