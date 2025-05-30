package cs3500.threetrios.network.messages;

import cs3500.threetrios.network.GameMessage;

/**
 * Message sent by a client to request joining a game.
 */
public class JoinGameMessage extends GameMessage {
  private static final long serialVersionUID = 1L;

  private final String playerName;

  /**
   * Creates a new join game message.
   * 
   * @param playerName the name of the player requesting to join
   */
  public JoinGameMessage(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Gets the player name.
   * 
   * @return the player name
   */
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public MessageType getType() {
    return MessageType.JOIN_GAME;
  }
}
