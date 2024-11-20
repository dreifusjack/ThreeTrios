package cs3500.threetrios.player;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;

/**
 * Represents an interface of actions a player can carry out in ThreeTrios. A player is one
 * of a human or machine players. Human players are expected to play moves based on their inputs,
 * which occur by mouse clicks on the UI. Machine players are expected to play moves based on
 * their strategy, where a strategy is part of the construction of a player.
 */
public interface PlayerActions {

  /**
   * Adds a listener to handle player actions.
   *
   * @param listener the listener to be added for player actions
   */
  void addPlayerActionListener(PlayerActionFeatures listener);


  /**
   * Selects a card based on the current model state.
   * For machine players, the selection is determined by the strategy.
   * For human players, it is based on user interaction.
   *
   * @param model the read-only model representing the current game state
   */
  void notifySelectedCard(ReadOnlyThreeTriosModel model);

  /**
   * Makes a move based on the current model state.
   * For machine players, the move is computed using their strategy.
   * For human players, it is based on user interaction.
   *
   * @param model the read-only model representing the current game state
   */
  void notifyPlacedCard(ReadOnlyThreeTriosModel model);

  /**
   * Gets the color of the player.
   *
   * @return the team color of the player (either RED or BLUE)
   */
  TeamColor getColor();

  /**
   * Determines if this PlayerActions is responsible for adding a listener (controller) to its
   * internal list of observers it notifies.
   *
   * @return true iff this PlayerActions is responsible for notifying a observer.
   */
  boolean addsPlayerActions();
}
