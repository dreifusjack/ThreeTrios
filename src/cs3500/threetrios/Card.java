package cs3500.threetrios;

/**
 * Behaviors of a card in the three trios game.
 */
public interface Card {

  /**
   * Provides a textual view of this card.
   *
   * @return string format of this card
   */
  public String toString();

  /**
   * Compares this card with the given card based of the given direction. E.g. if direction is
   * south, compare this cards south value with the given cards north value.
   *
   * @param other     Card to be compared with this one.
   * @param direction direction of adjacency for comparing values
   * @return true iff this card is higher the given card in terms of the direction
   */
  public boolean compare(Card other, Direction direction);


  /**
   * Return the TeamColor of this Card.
   * @return the TeamColor of this Card
   */
  public TeamColor getColor();

  /**
   * Return the name of this card.
   * @return the name of this card.
   */
  public String getName();
}
