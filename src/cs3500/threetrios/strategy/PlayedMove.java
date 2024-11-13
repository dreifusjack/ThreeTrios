package cs3500.threetrios.strategy;

/**
 * Represents a move that can be played based off what a strategy returns. These PlayedMoves can
 * be implemented by a computer player.
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
