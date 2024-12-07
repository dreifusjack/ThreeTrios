package cs3500.threetrios.model.decorators.level1;

import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTriosCard;

/**
 * This Decorator allows for multiple decorators to be passed in, composes them together to modify
 * the compare method however specified.
 */
public class ComposableCardDecorator extends PassThroughCardDecorator {
  private final List<PassThroughCardDecorator> decoratedCards;
  private final ThreeTriosCard decoratedCard;

  /**
   * Constructs a ComposableCardDecorator in terms of the delegate to call methods on and the list
   * of decorates to change the functionality of compare.
   *
   * @param delegate   given original card
   * @param decorators added functionality to call
   */
  public ComposableCardDecorator(Card delegate, List<PassThroughCardDecorator> decorators) {
    super(delegate);
    decoratedCard = createBaseCard(delegate);
    decoratedCards = decorators;
  }

  @Override
  public boolean compare(Card other, Direction direction) {
    Card otherCard = createBaseCard(other);
    boolean result = decoratedCard.compare(otherCard, direction);
    // Apply each decorator's modifyComparison method in sequence
    for (PassThroughCardDecorator decorator : decoratedCards) {
      result = decorator.modifyComparison(result, this, other, direction);
    }
    return result;
  }

  @Override
  boolean modifyComparison(boolean currentResult, Card self, Card other, Direction direction) {
    return false; // never will be called
  }
}
