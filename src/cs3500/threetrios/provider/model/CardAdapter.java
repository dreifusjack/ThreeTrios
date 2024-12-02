package cs3500.threetrios.provider.model;

import java.util.HashMap;
import java.util.Map;

import cs3500.threetrios.model.Card;

import cs3500.threetrios.model.TeamColor;

/**
 * Adapts our Three Trios card behaviors to the provides Three Trios card behaviors using an object
 * pattern.
 */
public class CardAdapter implements ICard {
  private final Card adaptedCard;
  private TeamColor teamColor;

  public CardAdapter(Card card, TeamColor color) {
    this.adaptedCard = card;
    this.teamColor = color;
  }

  @Override
  public void setOwner(PlayerType player) {
    switch (player) {
      case RED:
        this.teamColor = TeamColor.RED;
        break;
      case BLUE:
        this.teamColor = TeamColor.BLUE;
        break;
      default:
        this.teamColor = null;
    }
  }

  @Override
  public PlayerType getOwner() {
    if (teamColor == TeamColor.RED) {
      return PlayerType.RED;
    } else if (teamColor == TeamColor.BLUE) {
      return PlayerType.BLUE;
    } else {
      return PlayerType.OVER;
    }
  }

  @Override
  public boolean battle(ICard other, Direction directionTo, Direction directionFrom) {
    // handled within our model
    return false;
  }

  @Override
  public Map<Direction, AttackValue> getValues() {
    Map<Direction, AttackValue> values = new HashMap<>();
    values.put(Direction.NORTH, AttackValue.getAttackValue(adaptedCard.getNorth().toString()));
    values.put(Direction.EAST, AttackValue.getAttackValue(adaptedCard.getEast().toString()));
    values.put(Direction.SOUTH, AttackValue.getAttackValue(adaptedCard.getSouth().toString()));
    values.put(Direction.WEST, AttackValue.getAttackValue(adaptedCard.getWest().toString()));
    return values;
  }

  @Override
  public String getUid() {
    return adaptedCard.toString();
  }
}
