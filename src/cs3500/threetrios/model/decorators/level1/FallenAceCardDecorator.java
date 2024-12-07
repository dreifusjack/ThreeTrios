package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;

/**
 * This decorator forces A to lose to 1 in battles. When comparing a 1 with an A, the 1 wins. All
 * other cases are the same as the original Card implementation.
 */
public class FallenAceCardDecorator extends PassThroughCardDecorator {
  /**
   * Constructs a fallen ace card decorator by making a super call to the PassThroughCardDecorator.
   * Set param to be null because the delegate is never needed for any operations of this class.
   */
  public FallenAceCardDecorator() {
    super(null); // delegate is never used in this class
  }

  @Override
  boolean modifyComparison(boolean currentResult, Card self, Card other, Direction direction) {
    int selfValue = self.getValue(direction);
    int otherValue = other.getValue(direction.getOppositeDirection());
    if (selfValue == 1 && otherValue == 10) {
      return true;
    }
    if (selfValue == 10 && otherValue == 1) {
      return false;
    }
    return currentResult;
  }
}