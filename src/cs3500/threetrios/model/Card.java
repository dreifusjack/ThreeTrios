package cs3500.threetrios.model;

/**
 * Behaviors of a card in the three trios game.
 */
public interface Card {
  /**
   * Provides a textual view of this card.
   *
   * @return string format of this card
   */
  String toString();

  /**
   * Compares this card with the given card based of the given direction. E.g. if direction is
   * south, compare this cards south value with the given cards north value.
   *
   * @param other     Card to be compared with this one.
   * @param direction direction of adjacency for comparing values
   * @return true iff this card is higher the given card in terms of the direction
   * @throws IllegalArgumentException if either parameter is null
   */
  boolean compare(Card other, Direction direction);

  /**
   * Return the TeamColor of this Card.
   *
   * @return the TeamColor of this Card
   */
  TeamColor getColor();

  /**
   * Changes the TeamColor of this Card. If this card was on red team and toggleColor is called,
   * the card is now on blue team.
   */
  void toggleColor();

  /**
   * Sets the color of this card.
   *
   * @param color color to update this cards field with
   */
  void setColor(TeamColor color);
}