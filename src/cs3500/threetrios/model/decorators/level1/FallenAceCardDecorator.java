package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;

public class FallenAceCardDecorator extends PassThroughCardDecorator {
  public FallenAceCardDecorator() {
    super(null); // delegate is never used in this class
  }

  @Override
  boolean modifyComparison(boolean currentResult, Card self, Card other, Direction direction) {
    int selfValue = self.getValue(direction);
    int otherValue = other.getValue(direction.getOppositeDirection());
    // Apply Fallen Ace rule: A "1" can beat an "A" (10).
    if (selfValue == 1 && otherValue == 10) {
      return true;
    }
    if (selfValue == 10 && otherValue == 1) {
      return false;
    }
    return currentResult;
  }
}