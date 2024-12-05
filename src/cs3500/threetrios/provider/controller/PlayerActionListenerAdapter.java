package cs3500.threetrios.provider.controller;

import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.controller.PlayerActionListener;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.view.ThreeTriosCardPanel;

/**
 * Adapter that allows an AdapterFeatureController to be used where a PlayerActionFeatures
 * is required. This allows our player actions features with the newly created adapted controller.
 */
public class PlayerActionListenerAdapter implements PlayerActionListener {
  private final AdapterListenerController controller;

  /**
   * Constructs a PlayerActionFeaturesAdapter that wraps an AdapterFeatureController.
   *
   * @param controller the controller to be adapted
   * @throws IllegalArgumentException if controller is null
   */
  public PlayerActionListenerAdapter(AdapterListenerController controller) {
    if (controller == null) {
      throw new IllegalArgumentException();
    }
    this.controller = controller;
  }

  @Override
  public void handleCardSelection(TeamColor playerColor, int cardIndex) {
    // Delegate to the corresponding method in AdapterFeatureController
    controller.cardSelected(PlayerType.valueOf(playerColor.toString()), cardIndex);
  }

  @Override
  public void handleBoardSelection(int row, int col) {
    // Delegate to the corresponding method in AdapterFeatureController
    controller.cellClicked(row, col);
  }
}
