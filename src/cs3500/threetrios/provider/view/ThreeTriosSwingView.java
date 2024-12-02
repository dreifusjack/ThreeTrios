package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.controller.PlayerActionListener;
import cs3500.threetrios.provider.model.PlayerType;

/**
 * Interface for the Three Trios Swing View. This will render the GUI-Version of the game with
 * a grid and the player's cards.
 */
public interface ThreeTriosSwingView {

  /**
   * Adds a listener for player actions.
   *
   * @param listener the PlayerActionListener to be added
   */
  void addPlayerActionListener(PlayerActionListener listener);

  /**
   * Enables or disables user interaction with the view for the specified player.
   *
   * @param enabled    true to enable interaction, false to disable
   * @param playerType the player whose interaction is being controlled
   */
  void setInteractionEnabled(boolean enabled, PlayerType playerType);

  /**
   * Resets the selected card in the specified player's hand panel.
   *
   * @param playerType the player whose selected card should be reset
   */
  void resetSelectedCard(PlayerType playerType);

  /**
   * Updates the view components based on the model's state.
   */
  void updateView();


  /**
   * Updates the player status to reflect the current turn and player.
   *
   * @param playerType the player (RED or BLUE)
   * @param isMyTurn   true if it's this player's turn, false otherwise
   */
  void updatePlayerStatus(PlayerType playerType, boolean isMyTurn);
}
