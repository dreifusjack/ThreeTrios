package cs3500.threetrios.provider.model;

import java.util.Map;

import cs3500.threetrios.provider.model.Direction;

/**
 * Interface representing a card in Three Trios.
 * Each card has a unique identifier, four attack values (one for each direction),
 * and can be owned by a player.
 */
public interface ICard {

  /**
   * Sets the owner of the card.
   *
   * @param player the new owner.
   */
  void setOwner(PlayerType player);

  /**
   * Gets the current owner of this card.
   *
   * @return the owner of the card.
   */
  PlayerType getOwner();

  /**
   * Battles another Card. Returns true if this card wins, false otherwise.
   *
   * @param other         the other Card to battle.
   * @param directionTo   the direction battling from this card.
   * @param directionFrom the direction battling from the other card.
   * @return True if this card wins, False otherwise.
   */
  boolean battle(ICard other, cs3500.threetrios.model. Direction directionTo, Direction directionFrom);

  /**
   * Gets a copy of the values of the card.
   *
   * @return a copy of the values of the card.
   */
  Map<Direction, AttackValue> getValues();

  /**
   * Gets the unique id.
   *
   * @return the unique id/name.
   */
  String getUid();
} 