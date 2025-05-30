package cs3500.threetrios.network.messages;

import cs3500.threetrios.network.GameMessage;

/**
 * Message sent by a client to play a card to the grid.
 */
public class PlayCardMessage extends GameMessage {
  private static final long serialVersionUID = 1L;

  private final int row;
  private final int col;
  private final int handIndex;

  /**
   * Creates a new play card message.
   * 
   * @param row       the row to play the card to
   * @param col       the column to play the card to
   * @param handIndex the index of the card in the player's hand
   */
  public PlayCardMessage(int row, int col, int handIndex) {
    this.row = row;
    this.col = col;
    this.handIndex = handIndex;
  }

  /**
   * Gets the row.
   * 
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column.
   * 
   * @return the column
   */
  public int getCol() {
    return col;
  }

  /**
   * Gets the hand index.
   * 
   * @return the hand index
   */
  public int getHandIndex() {
    return handIndex;
  }

  @Override
  public MessageType getType() {
    return MessageType.PLAY_CARD;
  }
}
