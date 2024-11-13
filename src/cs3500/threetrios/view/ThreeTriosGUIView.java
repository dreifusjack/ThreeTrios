package cs3500.threetrios.view;

/**
 * A GUI visual interface that renders ThreeTrios. Includes all the red players,
 * all the blue players cards, and the grid. In the grid holes are represented as grey cells, and
 * card cells are represented as yellow cells.
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
