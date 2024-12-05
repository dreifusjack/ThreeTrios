package cs3500.threetrios.model.decorators.level2;

import java.util.HashMap;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.BaseThreeTriosModelDecorator;

public class SameModelDecorator extends BaseThreeTriosModelDecorator {
  public SameModelDecorator(ThreeTriosModel baseModel) {
    super(baseModel);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    baseModel.playToGrid(row, col, handIdx);
    baseModel.notifyPlayerTurnChange();
    System.out.println("notifyPlayerTurnChange 2nd");
    applySameRule(row, col);
  }

  private void applySameRule(int row, int col) {
    System.out.println("applySameRule triggered");
    Card placedCard = baseModel.getCell(row, col).getCardCopy();
    TeamColor currentPlayerColor = baseModel.getCurrentPlayer().getColor();

    Map<Direction, Card> opponentCards = new HashMap<>();

    for (Direction dir : Direction.values()) {
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);
      if (isValidCoordinate(adjRow, adjCol)) {
        try {
          ReadOnlyGridCell adjCell = baseModel.getCell(adjRow, adjCol);
          Card adjCard = adjCell.getCardCopy();
          if (adjCard != null) {
            Direction oppositeDir = dir.getOppositeDirection();
            if (placedCard.getValue(dir) == adjCard.getValue(oppositeDir)) {
              opponentCards.put(dir, adjCard);
              System.out.println("Add card to opponentCards");
            }
          }
        } catch (IllegalStateException e) {
          // ignored, hole case
        }
      }
    }

    if (opponentCards.size() >= 2) {
      for (Direction dir : opponentCards.keySet()) {
        int adjRow = row + Direction.getRowHelper(dir);
        int adjCol = col + Direction.getColHelper(dir);
        if (currentPlayerColor == baseModel.getCell(adjRow, adjCol).getColor()) {
          GridCell opposingCell = (GridCell) baseModel.getCell(adjRow, adjCol);
          opposingCell.toggleColor();
          System.out.println("same flip triggered");
        }
      }
    }
    baseModel.notifyPlayerTurnChange();
  }

  private boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < baseModel.numRows() && col >= 0 && col < baseModel.numCols();
  }
}