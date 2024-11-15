package cs3500.threetrios.player.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

import java.util.List;

/**
 * Represent the ChainStrategy as a way to recombine many strategies to offer more sophisticated
 * ways to pick the best move from the list of strategies. We will run findBestMove on every
 * ThreeTriosStrategy in the list of ThreeTriosStrategy given to the ChainStrategy. If a
 * ThreeTriosStrategy return null which it cannot find a best move then we move on with the next
 * ThreeTriosStrategy in the list. If none of the ThreeTriosStrategy in the list provide a valid
 * move then we choose the upper-most, left-most open position and the card at index 0. For the
 * input list, it is best to not put MaximizeFlip at the front of the list since it won't return
 * null as our grid must have an odd number of card cells (which always guarantee at least 1
 * CardCell for every game board). And this ensures that this strategy won't ever return null
 * for its best move. After examine the strategies more carefully, the MinimizeStrategy won't
 * return null as it's mechanism is similarly to MaximizeFlipStrategy. So if this is being placed
 * at the front of the input list, then other strategies after it won't ever be processed.
 **/

public class ChainStrategy implements ThreeTriosStrategy {
  private final List<ThreeTriosStrategy> strategies;

  public ChainStrategy(List<ThreeTriosStrategy> strategies) {
    this.strategies = strategies;
  }

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    for (ThreeTriosStrategy strategy : strategies) {
      PlayedMove move = strategy.findBestMoveForChain(model, player);
      if (move != null) {
        return move;
      }
    }
    // if no strategies provide a valid move, choose the upper-most,
    // left-most open position and the card at index 0.
    if (!player.getHand().isEmpty()) {
      for (int row = 0; row < model.numRows(); row++) {
        for (int col = 0; col < model.numCols(); col++) {
          if (model.getCell(row, col).toString().equals("_")) {
            return new BasicMove(0, row, col);
          }
        }
      }
    }
    return null;
  }

  @Override
  public PlayedMove findBestMoveForChain(ReadOnlyThreeTriosModel model, Player player) {
    return this.findBestMove(model, player);
  }
}
