package cs3500.threetrios.model.decorators.level1;

import java.util.List;
import java.util.stream.Collectors;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.ThreeTrioCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.BaseThreeTriosModelDecorator;

public class ComboModelDecorator extends BaseThreeTriosModelDecorator {

  public ComboModelDecorator(ThreeTriosModel baseModel) {
    super(baseModel);
    System.out.println("FallenAceModelDecorator created");
  }

  @Override
  public void startGame(GridCell[][] grid, List<Card> deck, int numOfCardCells) {
    List<Card> decoratedDeck = deck.stream()
            .map(card -> new ComboCardDecorator((ThreeTrioCard) card.copy()))
            .collect(Collectors.toList());
    super.startGame(grid, decoratedDeck, numOfCardCells);
  }



}

