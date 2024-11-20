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
  void refreshPlayingBoard();

  /**
   * Sets the title of the GUI as the given title.
   * @param title new title
   * @throws IllegalArgumentException if title is null
   */
  void updateTitle(String title);

  /**
   * Adds a listener to handle player actions.
   *
   * @param listener is the listener for player actions
   */
  void addPlayerActionListener(PlayerActionFeatures listener);

  /**
   * Handles the selection of a card by the player.
   *
   * @param cardIndex       is the index of the card being selected
   * @param color           is the color of the player selecting the card
   * @param selectedCard    is the card panel that was selected by the player
   * @param highlightedCard is the card panel that is currently highlighted
   */
  void notifySelectedCard(int cardIndex, TeamColor color,
                          ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard);

  /**
   * Handles the placement of a card on the grid by the player.
   *
   * @param row is the row where the card is being placed
   * @param col is the column where the card is being placed
   */
  void notifyPlacedCard(int row, int col);
}