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

/**
 * A model decorator that adds the "Plus Rule" functionality.
 * When a card is placed, it evaluates adjacent cards based on their directional values.
 * Adds new flipping cases when there are directions with matching sums appearing two or more times.
 */
public class PlusModelDecorator extends PassThroughModelDecorator {
  /**
   * Constructs a PlusModelDecorator with the given base model.
   *
   * @param baseModel the base model to decorate
   */
  public PlusModelDecorator(ThreeTriosModel baseModel) {
    super(baseModel);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    super.playToGrid(row, col, handIdx);
    notifyPlayerTurnChange();
    applyPlusRule(row, col);
  }

  /**
   * Applies the "Plus Rule" to the card placed at the specified grid cell.
   * This checks adjacent cards for matching directional sums and flips them if their colors match.
   *
   * @param row given row
   * @param col given col
   */
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

  /**
   * Calculates the sums of directional values for the card at the specified grid cell
   * and its adjacent cards.
   *
   * @param row        given row
   * @param col        given col
   * @param placedCard the card placed
   * @return a map of directions to their corresponding sums
   */
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
        } catch (IllegalStateException ignored) {// hole case
        }
      }
    }
    return directionSums;
  }

  /**
   * Finds directions with matching sums appearing two or more times.
   *
   * @param directionSums a map of directions to their corresponding sums
   * @return a set of directions with matching sums
   */
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
