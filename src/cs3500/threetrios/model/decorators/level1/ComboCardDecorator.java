package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTrioCard;



public class ComboCardDecorator extends ThreeTrioCard {
  private final ThreeTrioCard decoratedCard;


  public ComboCardDecorator(ThreeTrioCard decoratedCard) {
    super(decoratedCard.getName(), decoratedCard.getNorth(), decoratedCard.getEast(), decoratedCard.getSouth(), decoratedCard.getWest());
    this.decoratedCard = decoratedCard;
  }



  @Override
  public boolean compare(Card other, Direction direction) {

    if (other == null || direction == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    if (!(other instanceof ThreeTrioCard)) {
      return false;
    }

    ThreeTrioCard otherCard = (ThreeTrioCard) other;

    int thisValue = getValue(decoratedCard, direction);
    int otherValue = getValue(otherCard, direction.getOppositeDirection());

    if (thisValue == 10 && otherValue == 1) {
      return true;
    }

    else {

      System.out.println("Triggered both of the rule");
      return !super.compare(other, direction);
    }
  }


  private int getValue(ThreeTrioCard card, Direction direction) {
    switch (direction) {
      case NORTH:
        return card.getNorth().getValue();
      case SOUTH:
        return card.getSouth().getValue();
      case EAST:
        return card.getEast().getValue();
      case WEST:
        return card.getWest().getValue();
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }
}
