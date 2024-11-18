package cs3500.threetrios.player;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;

/**
 * Represents a human player in the ThreeTrios game. Human players make their moves based on
 * user interactions, such as mouse clicks on the GUI board. That is why all methods of it
 * are empty.
 */
public class HumanPlayer implements PlayerActions {
  private final TeamColor color;

  /**
   * Constructs a HumanPlayer with the given team color.
   *
   * @param color is the team color of the player (either RED or BLUE)
   * @throws IllegalArgumentException if the given color is null
   */
  public HumanPlayer(TeamColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.color = color;
  }

  @Override
  public void addPlayerActionListener(PlayerActionFeatures listener) {
    // omitted, handled within the view
  }

  @Override
  public void notifySelectedCard(ReadOnlyThreeTriosModel model) {
    // omitted, handled within the view
  }

  @Override
  public void notifyPlacedCard(ReadOnlyThreeTriosModel model) {
    // omitted, handled within the view
  }

  @Override
  public TeamColor getColor() {
    return color;
  }

  @Override
  public boolean addsPlayerActions() {
    return false;
  }
}
