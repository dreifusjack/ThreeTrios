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

  /**
   * Return a hand of cards of this player.
   * @return a List of Card.
   */
  public List<Card> getHand();

  /**
   * Remove a card from the inputted cardInx.
   * @param cardInx is the index of a card in the player's hand we want to remove.
   */
  public void removeCard(int cardInx);

}
