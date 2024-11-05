package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;

public class BasicMove implements PlayedMove {
  private final Card card;
  private final int row;
  private final int col;

  public BasicMove(Card card, int row, int col) {
    this.card = card;
    this.row = row;
    this.col = col;
  }

  @Override
  public Card getCard() { return card; }

  @Override
  public int getRow() { return row; }

  @Override
  public int getCol() { return col; }
}
