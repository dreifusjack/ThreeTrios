package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;

/**
 * Interface for a move to play on the grid as the best move for the strategies.
 */
public interface PlayedMove {

  /**
   * Get the hand index from the best move.
   * @return an int represents the hand index of the best move.
   */
  int getHandInx();

  /**
   * Get the row from the best move.
   * @return and int represents the row of the best move.
   */
  int getRow();

  /**
   * Get the col from the best move.
   * @return and int represents the col of the best move.
   */
  int getCol();
}
