package cs3500.threetrios.view;

/**
 * Interface representing a view for the Three Trios game.
 * Any class that implements this interface will be responsible for displaying the game state.
 */
public interface ThreeTrioView {

  /**
   * Renders the game state as a string.
   * This method is expected to provide a textual representation of the current state of the game,
   * including the grid and the player's hand.
   *
   * @return a string representing the current game state.
   */
  String toString();
}
