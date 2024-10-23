package cs3500.threetrios;

import java.util.List;

public class ThreeTrioTextView implements ThreeTrioView{
  private final ThreeTriosModel model;

  public ThreeTrioTextView(ThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(model.getCurrentPlayer().toString()).append("\n");

    List<List<GridCell>> grid = model.getGridCell();
    for (List<GridCell> row : grid) {
      for (GridCell cell : row) {
        if (cell.isHole()) {
          sb.append("  ");
        } else if (cell.getCard() != null) {
          Card card = cell.getCard();
          sb.append(card.getColor().toStringAbbreviation()).append(card.getColor().toStringAbbreviation()).append(" ");
        } else {
          sb.append("_ ");
        }
      }
      sb.append("\n");
    }
    sb.append("\n");

    sb.append("Hand:\n");

    Player currentPlayer = model.getCurrentPlayer();
    for (Card card : currentPlayer.getHand()) {
      sb.append(card.toString()).append("\n");
    }

    return sb.toString();
  }
}
