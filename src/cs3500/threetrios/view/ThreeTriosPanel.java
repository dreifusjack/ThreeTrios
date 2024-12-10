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
   *
   * @param cardIdx the selected card index used for highlighting
   */
  void refresh(int cardIdx);

  /**
   * Renders the cell on the grid at this position adding text the shows how many of the given
   * flips are present.
   *
   * @param row   row of the cell
   * @param col   col of the cell
   * @param flips flips to display
   */
  void cellExposeHint(int row, int col, int flips);
}