package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class MaximizeFlipsStrategy implements ThreeTriosStrategy {

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    int maxFlips = -1;
    PlayedMove bestMove = null;

    // loop through each of the card and for each card apply double for loops to go over each cell on the gird
    for (int index = 0; index < player.getHand().size(); index++) {
      for (int row = 0; row < model.getGridReadOnly().size(); row++) {
        for (int col = 0; col < model.getGridReadOnly().get(row).size(); col++) {
          ReadOnlyGridCell cell = model.getCell(row, col);

          // skip cells that are not empty (hole / occupied)
          if (cell.isOccupied()) {
            continue;
          }

          // calculate the number of flips for this card at this position
          int flips = model.numCardFlips(player.getHand().get(index), row, col, player);

          if (flips > maxFlips) {
            maxFlips = flips;
            bestMove = new BasicMove(index, row, col);
          }
        }
      }
    }
    return bestMove;
  }
}