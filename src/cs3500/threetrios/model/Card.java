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
   * Returns this cards east attack value. Return value is an enum, thus it cannot be mutated.
   *
   * @return this cards East AttackValue.
   */
  ThreeTrioCard.CardValue getEast();

  /**
   * Returns this cards west attack value. Return value is an enum, thus it cannot be mutated.
   *
   * @return this cards West AttackValue.
   */
  ThreeTrioCard.CardValue getWest();

  /**
   * Returns this cards south attack value. Return value is an enum, thus it cannot be mutated.
   *
   * @return this cards South AttackValue.
   */
  ThreeTrioCard.CardValue getSouth();

  /**
   * Returns this cards north attack value. Return value is an enum, thus it cannot be mutated.
   *
   * @return this cards North AttackValue.
   */
  ThreeTrioCard.CardValue getNorth();
}