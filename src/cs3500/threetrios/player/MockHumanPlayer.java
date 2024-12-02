package cs3500.threetrios.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.PlayerActionListener;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;

/**
 * Mock class for the HumanPlayer class.
 */
public class MockHumanPlayer extends HumanPlayer {

  private final List<String> log;


  /**
   * Constructs a HumanPlayer with the given team color.
   *
   * @param color is the team color of the player (either RED or BLUE)
   * @throws IllegalArgumentException if the given color is null
   */
  public MockHumanPlayer(TeamColor color) {
    super(color);
    this.log = new ArrayList<>();
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    // omitted, handled within the view
    log.add("addPlayerActionListener called");
  }

  @Override
  public void notifySelectedCard(ReadOnlyThreeTriosModel model) {
    // omitted, handled within the view
    log.add("notifySelectedCard called");
  }

  @Override
  public void notifyPlacedCard(ReadOnlyThreeTriosModel model) {
    // omitted, handled within the view
    log.add("notifyPlacedCard called");
  }

  @Override
  public TeamColor getColor() {
    log.add("getColor called");
    return super.getColor();
  }

  @Override
  public boolean addsPlayerActions() {
    log.add("addsPlayerActions called");
    return false;
  }

  public List<String> getLog() {
    return this.log;
  }


}
