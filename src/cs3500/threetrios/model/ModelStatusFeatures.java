package cs3500.threetrios.model;

public interface ModelStatusFeatures {
  void onPlayerTurnChange(Player currentPlayer);
  void onGameOver(Player winningPlayer);
}
