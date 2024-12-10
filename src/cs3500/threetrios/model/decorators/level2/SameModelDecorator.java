package cs3500.threetrios.model.decorators.level2;

import java.util.HashMap;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.PassThroughModelDecorator;

/**
 * A model decorator that implements the "Same Rule".
 * When a card is placed, it evaluates adjacent cards to determine if they have matching opposing
 * values. If this happens two or more times, new flipping functionality is added.
 */
public class SameModelDecorator extends PassThroughModelDecorator {
  /**
   * Constructs a SameModelDecorator with the given base model.
   *
   * @param baseModel the base model to decorate
   */
  public SameModelDecorator(ThreeTriosModel baseModel) {
    super(baseModel);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    super.playToGrid(row, col, handIdx);
    notifyPlayerTurnChange();
    applySameRule(row, col);
  }

  /**
   * Applies the "Same Rule" to the card placed at the specified grid cell.
   * This rule checks for adjacent cards with matching opposing values and flips them if opposing.
   *
   * @param row given row
   * @param col given col
   */
  private void applySameRule(int row, int col) {
    Card placedCard = getCell(row, col).getCardCopy();
    TeamColor currentPlayerColor = getCurrentPlayer().getColor();

    Map<Direction, Card> matchingOpposingCards =
            calculateMatchingOpposingCards(row, col, placedCard);

    if (matchingOpposingCards.size() >= 2) {
      for (Direction dir : matchingOpposingCards.keySet()) {
        flipAdjacentCardIfMatchesColor(row, col, dir, currentPlayerColor);
      }
    }
    notifyPlayerTurnChange();
  }

  /**
   * Calculates the adjacent cards with matching opposing values
   * for the card at the specified grid cell.
   *
   * @param row        given row
   * @param col        given col
   * @param placedCard the card placed
   * @return a map of directions to the adjacent cards with matching opposing values
   */
  private Map<Direction, Card> calculateMatchingOpposingCards(int row, int col, Card placedCard) {
    Map<Direction, Card> opponentMatchingCards = new HashMap<>();
    for (Direction dir : Direction.values()) {
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);
      if (isValidCoordinate(adjRow, adjCol)) {
        try {
          ReadOnlyGridCell adjCell = getCell(adjRow, adjCol);
          Card adjCard = adjCell.getCardCopy();
          if (adjCard != null) {
            Direction oppositeDir = dir.getOppositeDirection();
            if (placedCard.getValue(dir) == adjCard.getValue(oppositeDir)) {
              opponentMatchingCards.put(dir, adjCard);
            }
          }
        } catch (IllegalStateException ignored) { // hole case
        }
      }
    }
    return opponentMatchingCards;
  }
}