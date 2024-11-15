package cs3500.threetrios.view;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;

public class HumanPlayer implements PlayerActions {
  TeamColor color;

  public HumanPlayer(TeamColor color) {
    this.color = color;
  }

  @Override
  public void selectCard(ReadOnlyThreeTriosModel model) {
  }

  @Override
  public void makeMove(ReadOnlyThreeTriosModel model) {
  }

  @Override
  public TeamColor getColor() {
    return this.color;
  }
}
