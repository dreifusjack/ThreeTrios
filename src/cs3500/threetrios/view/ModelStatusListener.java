package cs3500.threetrios.view;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosPlayer;

public interface ModelStatusListener {
  void onPlayerTurnChange(Player currentPlayer);
  void onGameOver(Player winningPlayer);
}
