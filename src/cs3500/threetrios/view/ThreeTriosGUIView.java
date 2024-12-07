package cs3500.threetrios.view;

import cs3500.threetrios.controller.HintToggleListener;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.controller.PlayerActionListener;

/**
 * A GUI visual interface that renders ThreeTrios. Includes all the red players,
 * all the blue players cards, and the grid. In the grid holes are represented as grey cells, and
 * card cells are represented as yellow cells.
 */
public interface ThreeTriosGUIView {
  /**
   * Refreshes the view based on the current state of the model.
   *
   * @param cardIdx the selected card index (used for highlighting)
   */
  void refreshPlayingBoard(int cardIdx);

  /**
   * Sets the title of the GUI as the given title.
   *
   * @param title new title
   * @throws IllegalArgumentException if title is null
   */
  void updateTitle(String title);

  /**
   * Adds a listener to handle player actions.
   *
   * @param listener is the listener for player actions
   */
  void addPlayerActionListener(PlayerActionListener listener);

  /**
   * Handles the selection of a card by the player.
   *
   * @param cardIndex is the index of the card being selected
   * @param color     is the color of the player selecting the card
   */
  void notifySelectedCard(int cardIndex, TeamColor color);

  /**
   * Handles the placement of a card on the grid by the player.
   *
   * @param row is the row where the card is being placed
   * @param col is the column where the card is being placed
   */
  void notifyPlacedCard(int row, int col);

  /**
   * Adds the given HintToggleListener to this views list of hint listeners.
   *
   * @param listener Hint listener to be added.
   */
  void addHintToggleListeners(HintToggleListener listener);

  /**
   * Renders the cell at the given coordinates to indicate the number of given flips.
   *
   * @param row   row of this cell panel
   * @param col   col of this cell panel
   * @param flips flips to render
   */
  void cellExposeHint(int row, int col, int flips);


  /**
   * Method that allows this view to listen for the key pressed event of 'h' which indicates to
   * toggle the hints.
   */
  void setupHintListeners();
}