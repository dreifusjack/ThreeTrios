package cs3500.threetrios.controller;

import java.util.List;

import cs3500.threetrios.model.Card;

/**
 * Behaviors for a card configuration reader. Responsible for reading from a card configuration,
 * validating the format of the file, construction of the card, and uniqueness of the card names.
 */
public interface CardReader {
  /**
   * Reads from this CardReader's scanner to scan through the specified card configuration file.
   * Creates an instance of a card with the given params from the config file and adds it to
   * this CardReader's cards. This list will be used for the model to use in dealing cards.
   *
   * @throws IllegalArgumentException if card file has no contents
   * @throws IllegalArgumentException if the format of the card configuration is incorrect,
   *                                  must follow: CARDNAME CARDVALUE CARDVALUE CARDVALUE CARDVALUE
   * @throws IllegalArgumentException if there is a duplicate card name in the card configuration,
   *                                  all card names must be unique
   *                                  ASSUME: CardValue enum @throws IllegalArgument if card
   *                                  config writes and invalid CardValue
   */
  void readFile();

  /**
   * Returns a copy of this CardReader's saved cards after reading from their file. Mutating this
   * list has no effect on this CardReader's cards field.
   *
   * @return list of cards representing the card configuration
   */
  List<Card> getCards();
}
