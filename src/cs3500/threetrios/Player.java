package cs3500.threetrios;

import java.util.List;

/**
 * Behaviors for a player in the Three Trios game.
 */
public interface Player {
  /**
   * Textual string of this player.
   *
   * @return string format of this player
   */
  public String toString();

  /**
   * Adds the given card to this player's hand.
   *
   * @param card to be added to the hand
   */
  public void addToHand(Card card);

  /**
   * Returns a list of cards from this player's hand. Modifying this list will have no event
   * of the field in this class.
   *
   * @return this players hand
   */
  public List<Card> getHand();
}
