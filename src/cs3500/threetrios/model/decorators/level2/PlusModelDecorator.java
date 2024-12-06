package cs3500.threetrios.model.decorators.level2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.PassThroughModelDecorator;

public class PlusModelDecorator extends PassThroughModelDecorator {

  public PlusModelDecorator(ThreeTriosModel baseModel) {
    super(baseModel);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    super.playToGrid(row, col, handIdx);
    notifyPlayerTurnChange();
    applyPlusRule(row, col);
  }

  private void applyPlusRule(int row, int col) {
    Card placedCard = getCell(row, col).getCardCopy();
    TeamColor currentPlayerColor = getCurrentPlayer().getColor();

    Map<Direction, Integer> directionSums = calculateDirectionSums(row, col, placedCard);

    Set<Direction> matchingDirections = findMatchingSums(directionSums);
    for (Direction dir : matchingDirections) {
      flipAdjacentCardIfMatchesColor(row, col, dir, currentPlayerColor);
    }
    notifyPlayerTurnChange();
  }

  private Map<Direction, Integer> calculateDirectionSums(int row, int col, Card placedCard) {
    Map<Direction, Integer> directionSums = new HashMap<>();

    for (Direction dir : Direction.values()) {
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);

      if (isValidCoordinate(adjRow, adjCol)) {
        try {
          Card adjCard = getCell(adjRow, adjCol).getCardCopy();
          if (adjCard != null) {
            int sum = placedCard.getValue(dir) + adjCard.getValue(dir.getOppositeDirection());
            directionSums.put(dir, sum);
          }
        } catch (IllegalStateException e) {
          // ignore, hole case
        }
      }
    }
    return directionSums;
  }

  private Set<Direction> findMatchingSums(Map<Direction, Integer> directionSums) {
    // Count occurrences of each sum
    Map<Integer, Integer> sumFrequencies = new HashMap<>();
    for (int sum : directionSums.values()) {
      sumFrequencies.put(sum, sumFrequencies.getOrDefault(sum, 0) + 1);
    }
    // Find directions with sums appearing 2 or more times
    Set<Direction> matchingDirections = new HashSet<>();
    for (Map.Entry<Direction, Integer> entry : directionSums.entrySet()) {
      if (sumFrequencies.get(entry.getValue()) >= 2) {
        matchingDirections.add(entry.getKey());
      }
    }
    return matchingDirections;
  }
}
