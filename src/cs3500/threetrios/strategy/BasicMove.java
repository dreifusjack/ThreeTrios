package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;

public class BasicMove implements PlayedMove {
  private final int handInx;
  private final int row;
  private final int col;

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
