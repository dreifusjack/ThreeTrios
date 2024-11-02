package cs3500.threetrios.view;

/**
 * Behaviors for the textual rendering of ThreeTrios.
 */
public interface ThreeTriosTextualView {
  /**
   * Renders the game state as a textual view.
   * This method is expected to provide a textual representation of the current state of the game,
   * including the grid and the player's hand.
   *
   * @return a string representing the current game state.
   */
  String toString();
}
