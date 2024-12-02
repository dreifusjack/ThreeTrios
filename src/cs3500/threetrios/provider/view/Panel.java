package cs3500.threetrios.provider.view;


import cs3500.threetrios.provider.controller.PlayerActionListener;

/**
 * Panel interface that holds common methods for interacting with each panel.
 */
public interface Panel {
  /**
   * Adds a listener for player actions.
   *
   * @param listener the PlayerActionListener to be added
   */
  void addPlayerActionListener(PlayerActionListener listener);

  /**
   * Enables or disables user interaction with this panel.
   *
   * @param enabled true to enable interaction, false to disable
   */
  void setInteractionEnabled(boolean enabled);
}
