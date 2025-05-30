package cs3500.threetrios.network.messages;

import cs3500.threetrios.network.GameMessage;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Message sent by the server to update clients with the current game state.
 */
public class GameStateUpdateMessage extends GameMessage {
  private static final long serialVersionUID = 1L;

  private final SerializableGameState gameState;

  /**
   * Creates a new game state update message.
   * 
   * @param gameState the current game state
   */
  public GameStateUpdateMessage(ReadOnlyThreeTriosModel gameState) {
    this.gameState = new SerializableGameState(gameState);
  }

  /**
   * Gets the serializable game state.
   * 
   * @return the serializable game state
   */
  public SerializableGameState getGameState() {
    return gameState;
  }

  @Override
  public MessageType getType() {
    return MessageType.GAME_STATE_UPDATE;
  }
}
