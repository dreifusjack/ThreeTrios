package cs3500.threetrios.model.decorators.level2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.controller.ModelStatusListener;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
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
    applyPlusRule(row, col);
  }

  private void applyPlusRule(int row, int col) {
    Card placedCard = baseModel.getCell(row, col).getCardCopy();
    Player currentPlayer = baseModel.getCurrentPlayer();

    Map<Direction, Integer> opponentCards = new HashMap<>();

    for (Direction dir : Direction.values()) {
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);

      if (isValidCoordinate(adjRow, adjCol)) {
        ReadOnlyGridCell adjCell = baseModel.getCell(adjRow, adjCol);
        if (adjCell.getCardCopy() != null && adjCell.getColor() != currentPlayer.getColor()) {
          Card adjCard = adjCell.getCardCopy();
          Direction oppositeDir = dir.getOppositeDirection();
          int sum = placedCard.getValue(dir) + adjCard.getValue(oppositeDir);
          opponentCards.put(dir, sum);
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
          //  baseModel.getCell(adjRow, adjCol).toggleColor();
          }
        }
      }
    }
  }

  private boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < baseModel.numRows() && col >= 0 && col < baseModel.numCols();
  }
}






