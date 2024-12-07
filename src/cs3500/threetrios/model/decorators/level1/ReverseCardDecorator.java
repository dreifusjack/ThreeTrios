package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;

/**
 * This decorator reverses all comparisons of cards that were originally implemented. For example,
 * now 1 beats 5, 5 beats 9. All other original card implements are the same.
 */
public class ReverseCardDecorator extends PassThroughCardDecorator {
  /**
   * Constructs a reverse card decorator by making a super call to the PassThroughCardDecorator.
   * Set param to be null because the delegate is never needed for any operations of this class.
   */
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