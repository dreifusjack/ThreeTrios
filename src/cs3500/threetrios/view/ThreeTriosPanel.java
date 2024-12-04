package cs3500.threetrios.view;

/**
 * Panel interface implements the behaviors of how the GUI is constructed into separate panels
 * as well as implements click listeners for various panels.
 * TTPanel is broken up into the following structure:
 * Red        Grid      Blue
 * Player     Panel     Player
 * Hand                 Hand
 * Panel                Panel
 */
public interface ThreeTriosPanel {

  /**
   * Refreshes the game grid based on the current state of the model.
   */
  void refresh();

  void highlightCell(int row, int col, int flips);
}