package cs3500.threetrios.model;

/**
 * Representation of a playing card in ThreeTrios. A Card is composed of 5 traits, a unique
 * name, and 4 attack values for each direction (NEWS). Cards are used to be played on the board
 * as well as dealt to players.
 */
public interface Card {
  /**
   * Provides a textual view of this card. Format: "Name NorthValue SouthValue EastValue WestValue".
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
   * Returns a direct copy of this card. Used for client protection of cards within the model.
   *
   * @return this card with a new memory location
   */
  Card copy();

  /**
   * Returns this cards east attack value. Return value is an enum, thus it cannot be mutated.
   *
   * @return this cards East AttackValue.
   */
  ThreeTriosCard.AttackValue getEast();

  /**
   * Returns this cards west attack value. Return value is an enum, thus it cannot be mutated.
   *
   * @return this cards West AttackValue.
   */
  ThreeTriosCard.AttackValue getWest();

  /**
   * Returns this cards south attack value. Return value is an enum, thus it cannot be mutated.
   *
   * @return this cards South AttackValue.
   */
  ThreeTriosCard.AttackValue getSouth();

  /**
   * Returns this cards north attack value. Return value is an enum, thus it cannot be mutated.
   *
   * @return this cards North AttackValue.
   */
  ThreeTriosCard.AttackValue getNorth();

  /**
   * Returns the unique name of this card.
   *
   * @return this cards name
   */
  String getName();

  /**
   * Gets this cards value in the given direction.
   * @param dir direction of value to return
   * @return value associated with given direction
   */
  int getValue(Direction dir);
}