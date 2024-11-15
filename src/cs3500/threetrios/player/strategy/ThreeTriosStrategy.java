package cs3500.threetrios.player.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represents a strategy for playing moves in a ThreeTrios game. Implementations will be responsible
 * for implementing various strategies which will rerun moves to play for computer players.
 */
public interface ThreeTriosStrategy {
  /**
   * Based on this strategy, find the best possible move for the given player. Always prioritize
   * moves that are lower card index in hand and are more upper-most-left-most for tie-breakers. If
   * no moves are found based on this strategy then play the lowest index card in the
   * most upper-most-left-most location of the grid.
   *
   * @param model  is the read-only game model
   * @param player the player for whom to determine the move
   * @return a PlayedMove.
   */
  PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player);

  /**
   * This is the version of findBestMove but being used for ChainStrategy, now if a
   * ThreeTriosStrategy returns null, we won't process it further with the condition
   * "If there are no valid moves, your player should pass choose the upper-most,
   * left-most open position and the card at index 0."
   *
   * @param model  is the read-only game model
   * @param player the player for whom to determine the move
   * @return a PlayedMove or null if cannot find the best move.
   */
  PlayedMove findBestMoveForChain(ReadOnlyThreeTriosModel model, Player player);
}
