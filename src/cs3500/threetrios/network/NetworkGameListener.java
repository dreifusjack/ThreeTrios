package cs3500.threetrios.network;

import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Interface for listening to network game events from a Three Trios client.
 */
public interface NetworkGameListener {

  /**
   * Called when the player has been assigned a team color.
   * 
   * @param color the assigned team color
   */
  void onPlayerAssigned(TeamColor color);

  /**
   * Called when the game state has been updated.
   * 
   * @param gameState the updated game state
   */
  void onGameStateUpdate(ReadOnlyThreeTriosModel gameState);

  /**
   * Called when the game has started.
   */
  void onGameStarted();

  /**
   * Called when the game is over.
   */
  void onGameOver();

  /**
   * Called when the player turn has changed.
   */
  void onPlayerTurnChange();

  /**
   * Called when an invalid move was attempted.
   */
  void onInvalidMove();

  /**
   * Called when the connection to the server was lost.
   */
  void onConnectionLost();
}
