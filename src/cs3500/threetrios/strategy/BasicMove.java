package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;

/**
 * The BasicMove class to represent a best move return from the strategies.
 */
public class BasicMove implements PlayedMove {
  private final int handInx;
  private final int row;
  private final int col;

  /**
   * Construct a BasicMove with a handInx, row and col to play the next move.
   * @param handInx is an int represents the hand index.
   * @param row is an int to represent the row being picked.
   * @param col is an int to represent the col being picked.
   */
  public BasicMove(int handInx, int row, int col) {
    this.handInx = handInx;
    this.row = row;
    this.col = col;
  }

  @Override
  public int getHandInx() {
    return handInx; }

  @Override
  public int getRow() { return row; }

  @Override
  public int getCol() { return col; }
}
