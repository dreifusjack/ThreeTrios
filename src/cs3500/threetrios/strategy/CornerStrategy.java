package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class CornerStrategy implements ThreeTriosStrategy {

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    PlayedMove bestMove = null;

    // find all the corner postision
    List<int[]> corners = new ArrayList<>();
    corners.add(new int[]{0, 0});
    corners.add(new int[]{0, model.numCols() - 1});
    corners.add(new int[]{model.numRows() - 1, 0});
    corners.add(new int[]{model.numRows() - 1, model.numCols() - 1});

    // loop through each card for each corner position
    for (Card card : player.getHand()) {
      for (int[] corner : corners) {
        int row = corner[0];
        int col = corner[1];
        ReadOnlyGridCell cell = model.getCell(row, col);

        if (!cell.toString().equals("_")) {
          continue;
        }

        // add condition for hardest card to flip (adds 2 of the sides)

        if (bestMove == null || row < bestMove.getRow() || (row == bestMove.getRow() && col < bestMove.getCol())) {
          bestMove = new BasicMove(card, row, col);
        }
      }
    }

    return bestMove;
  }
}