package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.IconView;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class CornerStrategy implements ThreeTriosStrategy {

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    PlayedMove bestMove = null;
    int maxComboSoFar = -1;

    // find all the corner position
    Map<String, int[]> corners = new LinkedHashMap<>();
    corners.put("TL", new int[]{0, 0}); // (top-left)
    corners.put("TR", new int[]{0, model.numCols() - 1}); // top-right
    corners.put("BL", new int[]{model.numRows() - 1, 0}); //bottom-left
    corners.put("BR", new int[]{model.numRows() - 1, model.numCols() - 1}); // bottom-right

    // loop through each card for each corner position
    for (Map.Entry<String, int[]> entry : corners.entrySet()) {
      int[] corner = entry.getValue();
      int row = corner[0];
      int col = corner[1];
      ReadOnlyGridCell cell = model.getCell(row, col);

      if (!cell.toString().equals("_")) {
        continue;
      }

      Map<Integer, Integer> map = getBestCardIndex(entry.getKey(), model, player);
      int combo = map.entrySet().iterator().next().getValue();

      if (combo > maxComboSoFar) {
        maxComboSoFar = combo;
        int bestIndexSoFar = map.entrySet().iterator().next().getKey();
        bestMove = new BasicMove(bestIndexSoFar, row, col);
      }
    }

    // if no bestMove is found then we do this
    if (bestMove == null && !player.getHand().isEmpty()) {
      for (int row = 0; row < model.numRows(); row++) {
        for (int col = 0; col < model.numCols(); col++) {
          ReadOnlyGridCell cell = model.getCell(row, col);
          if (cell.toString().equals("_")) {
            return new BasicMove(0, row, col);
          }
        }
      }
    }

    //call helper method to handle tie breaker (iterate from (0, 1))
    return bestMove;
    // if all corners are filled then trigger the next strategy (...) {
    // trigger findQuickestMove();
    //
    // }
  }

  private Map<Integer, Integer> getBestCardIndex(String key, ReadOnlyThreeTriosModel model, Player player) {
    switch (key) {
      case "TL":
         return player.bestCardInTopLeft();
      case "TR":
        return player.bestCardInTopRight();
      case "BL":
        return player.bestCadInBottomLeft();
      case "BR":
        return player.bestCardInBoomRight();
      default:
        return null;
    }
  }
}