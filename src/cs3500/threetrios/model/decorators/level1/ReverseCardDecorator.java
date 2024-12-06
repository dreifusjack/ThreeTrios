package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;

public class ReverseCardDecorator extends PassThroughCardDecorator {
  public ReverseCardDecorator() {
    super(null); // delegate is never used in this class
  }

  @Override
  boolean modifyComparison(boolean currentResult, Card self, Card other, Direction direction) {
    if (other.getValue(direction.getOppositeDirection()) == self.getValue(direction)) {
      return false;
    }
    return !currentResult;
  }
}