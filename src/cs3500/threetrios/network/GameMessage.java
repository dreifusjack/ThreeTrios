package cs3500.threetrios.network;

import java.io.Serializable;

/**
 * Represents a message that can be sent between client and server in a
 * networked Three Trios game.
 * All network messages must be serializable to be transmitted over the network.
 */
public abstract class GameMessage implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Gets the type of this message for routing purposes.
   * 
   * @return the message type
   */
  public abstract MessageType getType();

  /**
   * Enumeration of all possible message types in the network protocol.
   */
  public enum MessageType {
    // Client to Server messages
    JOIN_GAME,
    PLAY_CARD,
    DISCONNECT,

    // Server to Client messages
    GAME_STATE_UPDATE,
    PLAYER_ASSIGNED,
    GAME_STARTED,
    GAME_OVER,
    INVALID_MOVE,
    PLAYER_TURN_CHANGE,

    // Bidirectional
    HEARTBEAT
  }
}
