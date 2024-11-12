package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.strategy.PlayedMove;

/**
 * Interface to present a strategy for the ThreeTrios game.
 */
public interface ThreeTriosStrategy {
  /**
   * Find the best move for the given player.
   *
   * @param model is the read-only game model
   * @param player the player for whom to determine the move
   * @return a PlayedMove.
   */
  PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player);

  /**
   * This is the version of findBestMove but being used for ChainStrategy, now if a
   * ThreeTriosStrategy returns null, we won't process it further with the condition
   * "If there are no valid moves, your player should pass choose the upper-most,
   * left-most open position and the card at index 0."
   * @param model is the read-only game model
   * @param player the player for whom to determine the move
   * @return a PlayedMove or null if cannot find the best move.
   */
  PlayedMove findBestMoveForChain(ReadOnlyThreeTriosModel model, Player player);
}
