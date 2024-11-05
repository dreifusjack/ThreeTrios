package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;
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
      for (int row = 0; row < model.getGrid().size(); row++) {
        for (int col = 0; col < model.getGrid().get(row).size(); col++) {
          ReadOnlyGridCell cell = model.getGrid().get(row).get(col);

          if (!cell.toString().equals("_")) {
            continue;
          }

          // calculate the number of flips for this card at this position
          int flips = model.numCardFlips(player.getHand().get(index), row, col);

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