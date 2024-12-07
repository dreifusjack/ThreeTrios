package cs3500.threetrios.model.decorators.level1;

import java.util.List;
import java.util.stream.Collectors;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.PassThroughModelDecorator;

/**
 * Class is responsible being a model implementation, that allows for variant card rules, such that
 * the battle phase of Three Trios changes.
 */
public class VariantCardModelDecorator extends PassThroughModelDecorator {
  private final List<PassThroughCardDecorator> decorators;

  /**
   * Constructs a VariantCardModelDecorator in terms of the given model to add functionality to and
   * the list of variant card rules.
   *
   * @param baseModel  model to add functionality to
   * @param decorators list of card rule changes
   */
  public VariantCardModelDecorator(
          ThreeTriosModel baseModel, List<PassThroughCardDecorator> decorators) {
    super(baseModel);
    this.decorators = decorators;
  }

  @Override
  public void startGame(GridCell[][] grid, List<Card> deck, int numOfCardCells) {
    List<Card> decoratedDeck = deck.stream()
            .map(card -> new ComposableCardDecorator(card.copy(), decorators))
            .collect(Collectors.toList());
    // deck potentially now has cards with variant rules
    super.startGame(grid, decoratedDeck, numOfCardCells);
  }
}


