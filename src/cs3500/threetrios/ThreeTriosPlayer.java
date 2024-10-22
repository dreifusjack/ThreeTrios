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
}
