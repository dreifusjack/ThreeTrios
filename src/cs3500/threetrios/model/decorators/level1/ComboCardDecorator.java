package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTrioCard;

public class ComboCardDecorator extends PassThroughCardDecorator {
  private final ThreeTrioCard decoratedCard;

  public ComboCardDecorator(Card delegate) {
    super(delegate);
    this.decoratedCard = createBaseCard(delegate);
  }

  @Override
  public boolean compare(Card other, Direction direction) {
    ThreeTrioCard otherCard = createBaseCard(other);
    int otherValue = otherCard.getValue(direction.getOppositeDirection());
    int thisValue = getValue(direction);

    if (thisValue != 10 && otherValue == 10) {
      return false;
    }
    if (thisValue == 10 && otherValue != 10) {
      return true;
    }
    if (other.getValue(direction.getOppositeDirection()) == decoratedCard.getValue(direction)) {
      return false;
    }
    return !decoratedCard.compare(otherCard, direction);
  }
}
