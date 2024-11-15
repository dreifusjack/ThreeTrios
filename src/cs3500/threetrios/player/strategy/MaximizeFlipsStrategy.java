package cs3500.threetrios.player.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represent a MaximizeFlipsStrategy as one of the ThreeTriosStrategy to choose the best card for
 * the ThreeTrios game. This strategy will loop through all the cards in the player's hand. For
 * every card, we will loop through all the ReadOnlyGridCell on the board. If a ReadOnlyGridCell
 * is a cell that holds a card or a hole then we will skip this ReadOnlyGridCell. The best move
 * will be the move that can flip as much of the opponent card as possible. If there
 * is a tie with many best move then break the tie by choosing the move with the uppermost-leftmost
 * coordinate for the position and then choose the best card for that position with an index
 * closest to 0 in the hand. This strategy won't return null as our grid must have an odd number of
 * card cells (which always guarantee at least 1 CardCell for every game board). And this ensures
 * that this strategy won't ever return null for its best move.
 */
public class MaximizeFlipsStrategy implements ThreeTriosStrategy {

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    int maxFlips = -1;
    PlayedMove bestMove = null;

    // loop through each of the card and for each card apply double
    // for loops to go over each cell on the gird
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

  @Override
  public PlayedMove findBestMoveForChain(ReadOnlyThreeTriosModel model, Player player) {
    return this.findBestMove(model, player);
  }
}