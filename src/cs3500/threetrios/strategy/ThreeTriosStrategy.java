package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.strategy.PlayedMove;

public interface ThreeTriosStrategy {
  /**
   * Find the best move for the given player.
   *
   * @param model is the read-only game model
   * @param player the player for whom to determine the move
   * @return a PlayedMove.
   */
  PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player);
}
