package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class MaximizeFlipsStrategy implements ThreeTriosStrategy {

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    int maxFlips = 0;
    PlayedMove bestMove = null;

    // loop through each of the card and for each card apply double for loops to go over each cell on the gird
    for (int index = 0; index < player.getHand().size(); index++) {
      for (int row = 0; row < model.getGridReadOnly().size(); row++) {
        for (int col = 0; col < model.getGridReadOnly().get(row).size(); col++) {
          ReadOnlyGridCell cell = model.getGridReadOnly().get(row).get(col);

          if (!cell.toString().equals("_")) {
            continue;
          }

          // calculate the number of flips for this card at this position
          int flips = model.numCardFlips(player.getHand().get(index), row, col, player);

          if (flips > maxFlips || (flips == maxFlips && (bestMove == null || (row < bestMove.getRow() && col <= bestMove.getCol() || (row == bestMove.getRow() && col < bestMove.getCol()))))) {
            maxFlips = flips;
            bestMove = new BasicMove(index, row, col);
          }
        }
      }
    }

    // if no bestMove is found then we do this
    if(bestMove == null && !player.getHand().isEmpty()) {
      for(int row = 0; row < model.numRows(); row++) {
        for(int col = 0; col < model.numCols(); col++) {
          ReadOnlyGridCell gridCell = model.getCell(row, col);
          if(gridCell.toString().equals("_")) {
            return new BasicMove(0, row, col);
          }
        }
      }
    }

    return bestMove;
  }
}