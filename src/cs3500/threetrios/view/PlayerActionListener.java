package cs3500.threetrios.view;

import cs3500.threetrios.model.TeamColor;

public interface PlayerActionListener {
  void onCardSelected(TeamColor playerColor, int cardIndex);
  void onCardPlaced(TeamColor playerColor, int row, int col);
}