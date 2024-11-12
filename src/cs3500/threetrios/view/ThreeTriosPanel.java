package cs3500.threetrios.view;

/**
 * Interface for the ThreeTriosPanel to be used to represent a view as a graphical view for the
 * ThreeTrios game.
 */
public interface ThreeTriosPanel {

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