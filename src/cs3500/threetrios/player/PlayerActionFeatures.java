package cs3500.threetrios.player;

import cs3500.threetrios.model.TeamColor;

public interface PlayerActionFeatures {
  void onCardSelected(TeamColor playerColor, int cardIndex);
  void onCardPlaced(TeamColor playerColor, int row, int col);
}