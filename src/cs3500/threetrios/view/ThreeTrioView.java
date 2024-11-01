package cs3500.threetrios.view;

/**
 * Behaviors for the rendering of ThreeTrios. Currently responsible for textual rendering
 * of ThreeTrios. **Will be updated to GUI in the future**.
 */
public interface ThreeTrioView {
  /**
   * Renders the game state as a textual view.
   * This method is expected to provide a textual representation of the current state of the game,
   * including the grid and the player's hand.
   *
   * @return a string representing the current game state.
   */
  String toString();


  /**
   * Refreshes the view based on the current state of the model.
   */
  void refresh();

  /**
   * Set up the listeners for user interactions.
   */
  void setFeatures(Features features);
}
