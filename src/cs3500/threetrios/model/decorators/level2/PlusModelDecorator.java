package cs3500.threetrios.model.decorators.level2;

import java.util.HashMap;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.BaseThreeTriosModelDecorator;

public class PlusModelDecorator extends BaseThreeTriosModelDecorator {

  public PlusModelDecorator(ThreeTriosModel baseModel) {
    super(baseModel);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    baseModel.playToGrid(row, col, handIdx);
    baseModel.notifyPlayerTurnChange();
    applyPlusRule(row, col);
  }

  private void applyPlusRule(int row, int col) {
    Card placedCard = baseModel.getCell(row, col).getCardCopy();
    Player currentPlayer = baseModel.getCurrentPlayer();
    TeamColor currentPlayerColor = baseModel.getCurrentPlayer().getColor();

    Map<Direction, Integer> opponentCards = new HashMap<>();

    for (Direction dir : Direction.values()) {
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);

      if (isValidCoordinate(adjRow, adjCol)) {
        try {
          ReadOnlyGridCell adjCell = baseModel.getCell(adjRow, adjCol);
          Card adjCard = adjCell.getCardCopy();
          if (adjCard != null) {
            Direction oppositeDir = dir.getOppositeDirection();
            int sum = placedCard.getValue(dir) + adjCard.getValue(oppositeDir);
            opponentCards.put(dir, sum);
            System.out.println("Add card to opponentCards");
          }
        } catch (IllegalStateException e) {
          // ignored, hole case
        }
      }
    }

    for (Integer sum : opponentCards.values()) {
      int count = 0;
      for (Integer cardSum : opponentCards.values()) {
        if (cardSum.equals(sum)) {
          count++;
        }
      }
      if (count >= 2) {
        for (Map.Entry<Direction, Integer> entry : opponentCards.entrySet()) {
          if (entry.getValue().equals(sum)) {
            Direction dir = entry.getKey();
            int adjRow = row + Direction.getRowHelper(dir);
            int adjCol = col + Direction.getColHelper(dir);
            if(currentPlayerColor == baseModel.getCell(adjRow, adjCol).getColor()) {
              GridCell flippedCell = (GridCell) baseModel.getCell(adjRow, adjCol);
              flippedCell.toggleColor();
              System.out.println("plus flip triggered");
            }
          }
        }
      }
    }
    this.baseModel.notifyPlayerTurnChange();
  }

  private boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < baseModel.numRows() && col >= 0 && col < baseModel.numCols();
  }

}

