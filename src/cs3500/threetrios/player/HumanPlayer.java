package cs3500.threetrios.player;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;

public class HumanPlayer implements PlayerActions {
  private final TeamColor color;

  public HumanPlayer(TeamColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.color = color;
  }

  @Override
  public void selectCard(ReadOnlyThreeTriosModel model) {
    // handled within the view
  }

  @Override
  public void makeMove(ReadOnlyThreeTriosModel model) {
    // handled within the view
  }

  @Override
  public TeamColor getColor() {
    return color;
  }
}
