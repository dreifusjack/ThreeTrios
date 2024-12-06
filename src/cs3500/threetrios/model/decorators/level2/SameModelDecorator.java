package cs3500.threetrios.model.decorators.level2;

import java.util.HashMap;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.PassThroughModelDecorator;

public class SameModelDecorator extends PassThroughModelDecorator {
  public SameModelDecorator(ThreeTriosModel baseModel) {
    super(baseModel);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    super.playToGrid(row, col, handIdx);
    notifyPlayerTurnChange();
    applySameRule(row, col);
  }

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
        } catch (IllegalStateException e) {
          // ignored, hole case
        }
      }
    }
    return opponentMatchingCards;
  }
}