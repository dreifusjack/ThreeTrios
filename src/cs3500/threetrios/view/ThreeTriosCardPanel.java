package cs3500.threetrios.view;

import cs3500.threetrios.model.TeamColor;

/**
 * Represents a card panel in the ThreeTrios game. This interface defines the behaviors for
 * interacting with a card in the player's hand, such as getting the card index, toggling its
 * highlight status, and getting the card's color.
 */
public interface ThreeTriosCardPanel {
  /**
   * Gets the index of the card in the player's hand.
   *
   * @return the index of the card in the player's hand
   */
  int getHandInx();

  /**
   * Toggles the highlight status of the card. If a card is highlighted it has a thickened green
   * border, else it has a default size black border.
   */
  void toggleHighlight();

  /**
   * Gets the color of the card.
   *
   * @return the team color of the card (either RED or BLUE)
   */
  TeamColor getColor();
}
