package cs3500.threetrios.model.decorators.level1;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTrioCard;

public class ReverseCardDecorator extends ThreeTrioCard {

  public ReverseCardDecorator(ThreeTrioCard decoratedCard) {
    super(decoratedCard.getName(), decoratedCard.getNorth(), decoratedCard.getEast(), decoratedCard.getSouth(), decoratedCard.getWest());
  }

    @Override
    public boolean compare(Card other, Direction direction) {
      System.out.println("compare triggered");
      return !super.compare(other, direction);
    }
}