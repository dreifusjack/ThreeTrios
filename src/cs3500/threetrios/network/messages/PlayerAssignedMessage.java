package cs3500.threetrios.network.messages;

import cs3500.threetrios.network.GameMessage;
import cs3500.threetrios.model.TeamColor;

/**
 * Message sent by the server to inform a client which team color they have been
 * assigned.
 */
public class PlayerAssignedMessage extends GameMessage {
  private static final long serialVersionUID = 1L;

  private final TeamColor assignedColor;

  /**
   * Creates a new player assigned message.
   * 
   * @param assignedColor the team color assigned to the player
   */
  public PlayerAssignedMessage(TeamColor assignedColor) {
    this.assignedColor = assignedColor;
  }

  /**
   * Gets the assigned team color.
   * 
   * @return the assigned team color
   */
  public TeamColor getAssignedColor() {
    return assignedColor;
  }

  @Override
  public MessageType getType() {
    return MessageType.PLAYER_ASSIGNED;
  }
}
