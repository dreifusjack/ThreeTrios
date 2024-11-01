package cs3500.threetrios.view;

public interface TTTPanel {
  /**
   * Refreshes the game grid based on the current state of the model.
   */
  void refresh();

  /**
   * Set up listeners for user interactions.
   *
   * @param features the features to be used by the panel
   */
  void setFeatures(Features features);
}