package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;

public class ThreeTriosPlayer implements Player {
  List<Card> hand;
  TeamColor team;

  public ThreeTriosPlayer(TeamColor team) {
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
  public void removeCard(int cardInx) {
    if (cardInx >= 0 && cardInx < hand.size()) {
      hand.remove(cardInx);
    } else {
      throw new IllegalArgumentException("Invalid card index");
    }
  }

  @Override
  public String toString() {
    return this.team.toString();
  }
}
