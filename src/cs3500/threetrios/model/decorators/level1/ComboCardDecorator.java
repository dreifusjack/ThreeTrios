package cs3500.threetrios.model.decorators.level1;

import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTriosCard;

public class ComboCardDecorator extends PassThroughCardDecorator {
  private final List<PassThroughCardDecorator> decoratedCards;
  private final ThreeTriosCard decoratedCard;

  public ComboCardDecorator(Card delegate, List<PassThroughCardDecorator> decorators) {
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
    return false;
  }
}
