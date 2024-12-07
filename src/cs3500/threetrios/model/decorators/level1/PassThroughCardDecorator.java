package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTriosCard;

/**
 * "Pass through" card, where this class simply takes a Card delegate to  call
 * all of its methods for each method. Will be extended by decorated card variants.
 */
public abstract class PassThroughCardDecorator implements Card {
  private final Card delegate;

  /**
   * Constructs this pass through card in terms of the delegate that it will call on all methods.
   *
   * @param delegate card that handles all implementations.
   */
  public PassThroughCardDecorator(Card delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean compare(Card other, Direction direction) {
    return delegate.compare(other, direction);
  }

  @Override
  public Card copy() {
    return delegate.copy();
  }

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public ThreeTriosCard.AttackValue getEast() {
    return delegate.getEast();
  }

  @Override
  public ThreeTriosCard.AttackValue getWest() {
    return delegate.getWest();
  }

  @Override
  public ThreeTriosCard.AttackValue getSouth() {
    return delegate.getSouth();
  }

  @Override
  public ThreeTriosCard.AttackValue getNorth() {
    return delegate.getNorth();
  }

  @Override
  public String getName() {
    return delegate.getName();
  }

  @Override
  public int getValue(Direction dir) {
    return delegate.getValue(dir);
  }

  /**
   * Given a card of Interface type creates an instance of ThreeTrioCard, the base implementation.
   *
   * @param card given card of interface type
   * @return three trios card with the same internal data
   */
  protected ThreeTriosCard createBaseCard(Card card) {
    return new ThreeTriosCard(
            card.getName(), card.getNorth(), card.getEast(), card.getSouth(), card.getWest());
  }

  /**
   * Adjust the comparison logic to implement the inherited rule.
   * Default protection because only this package needs access to this method.
   *
   * @param currentResult the current comparison result from previous logic
   * @param self          the card being compared (this card)
   * @param other         the card to compare against
   * @param direction     the direction of comparison
   * @return the adjusted comparison result
   */
  abstract boolean modifyComparison(
          boolean currentResult, Card self, Card other, Direction direction);
}
