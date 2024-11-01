package cs3500.threetrios.view;

import cs3500.threetrios.model.TeamColor;

/**
 * Interface representing a card shape for rendering purposes.
 */
interface ICardShape {
  /**
   * Gets the value on the north side of the card.
   *
   *
   * @return the north value
   */
  int getNorth();

  /**
   * Gets the value on the south side of the card.
   *
   * @return the south value
   */
  int getSouth();

  /**
   * Gets the value on the east side of the card.
   *
   * @return the east value
   */
  int getEast();

  /**
   * Gets the value on the west side of the card.
   *
   * @return the west value
   */
  int getWest();

  /**
   * Get the color of the card.
   * @return a TeamColor.
   */
  TeamColor getColor();
}