package cs3500.threetrios.network.messages;

import cs3500.threetrios.network.GameMessage;

public class PlayerTurnChangeMessage extends GameMessage {
  private static final long serialVersionUID = 1L;

  @Override
  public MessageType getType() {
    return MessageType.PLAYER_TURN_CHANGE;
  }
}