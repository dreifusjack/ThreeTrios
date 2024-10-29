package cs3500.threetrios.view;

import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class ThreeTrioTextView implements ThreeTrioView {
  private final ReadOnlyThreeTriosModel model;

  public ThreeTrioTextView(ReadOnlyThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(model.getCurrentPlayer().toString()).append("\n");

    List<List<GridCell>> grid = model.getGrid();
    for (List<GridCell> row : grid) {
      for (GridCell cell : row) {
        sb.append(cell.toString());
      }
      sb.append("\n");
    }
    sb.append("Hand:\n");

    Player currentPlayer = model.getCurrentPlayer();
  /*  for (Card card : currentPlayer.getHand()) {
      sb.append(card.toString()).append("\n");
    }*/

    return sb.toString();
  }
}
