package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the behaviors of the player interface. A Player has a team
 * and a list of cards representing its playing hand.
 */
public class ThreeTriosPlayer implements Player {
  private final List<Card> hand;
  private final TeamColor team;

  /**
   * Constructs a ThreeTriosPlayer in terms of its team. Playing hand is initialized to empty.
   *
   * @param team red or blue
   * @throws IllegalArgumentException if team is null
   */
  public ThreeTriosPlayer(TeamColor team) {
    if (team == null) {
      throw new IllegalArgumentException("Team color cannot be null");
    }
    this.team = team;
    hand = new ArrayList<>();
  }

  @Override
  public void addToHand(Card card) {
    hand.add(card);
  }

  @Override
  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }

  @Override
  public void removeCard(int handIdx) {
    if (handIdx >= 0 && handIdx < hand.size()) {
      hand.remove(handIdx);
    } else {
      throw new IllegalArgumentException("Invalid card index");
    }
  }

  @Override
  public Player clone() {
    Player clone = new ThreeTriosPlayer(team);
    for (Card card : hand) {
      clone.addToHand(card);
    }
    return clone;
  }

  @Override
  public TeamColor getColor() {
    return this.team;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Player: ").append(team.toString()).append("\n");

    for (Card card : hand) {
      sb.append(card.toString()).append("\n");
    }
    return sb.toString();
  }

}
