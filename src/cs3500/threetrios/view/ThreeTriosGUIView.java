package cs3500.threetrios.view;

/**
 * Behaviors for the GUI rendering of ThreeTrios.
 */
public interface ThreeTriosGUIView {
  /**
   * Refreshes the view based on the current state of the model.
   */
  void refresh();

  /**
   * Set up the listeners for user interactions.
   */
  void setFeatures(Features features);
}
