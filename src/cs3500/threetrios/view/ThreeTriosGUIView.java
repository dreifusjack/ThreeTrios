package cs3500.threetrios.view;

import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.player.PlayerActionFeatures;

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
  void setFeatures(ViewFeatures viewFeatures);

  void addPlayerActionListener(PlayerActionFeatures listener);
  // color is the selected cards color
  void handleCardSelection(int cardIndex, TeamColor color);
  void handleCardPlacement(int row, int col);

}
