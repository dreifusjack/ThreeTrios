package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;

public class ReverseCardDecorator extends PassThroughCardDecorator {
  private final Card decoratedCard;

  public ReverseCardDecorator(Card delegate) {
    super(delegate);
    decoratedCard = createBaseCard(delegate);
  }

  @Override
  public boolean compare(Card other, Direction direction) {
    Card otherCard = createBaseCard(other);
    if (other.getValue(direction.getOppositeDirection()) == decoratedCard.getValue(direction)) {
      return false;
    }
    return !decoratedCard.compare(otherCard, direction);
  }
}