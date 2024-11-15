package cs3500.threetrios.view;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;


public interface PlayerActions {

  void selectCard(ReadOnlyThreeTriosModel model);

  void makeMove(ReadOnlyThreeTriosModel model);

  TeamColor getColor();
}
