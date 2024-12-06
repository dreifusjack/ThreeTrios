package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTrioCard;

public class PassThroughCardDecorator implements Card {
  private final Card delegate;

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
  public ThreeTrioCard.AttackValue getEast() {
    return delegate.getEast();
  }

  @Override
  public ThreeTrioCard.AttackValue getWest() {
    return delegate.getWest();
  }

  @Override
  public ThreeTrioCard.AttackValue getSouth() {
    return delegate.getSouth();
  }

  @Override
  public ThreeTrioCard.AttackValue getNorth() {
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
  protected ThreeTrioCard createBaseCard(Card card) {
    return new ThreeTrioCard(
            card.getName(), card.getNorth(), card.getEast(), card.getSouth(), card.getWest());
  }
}
