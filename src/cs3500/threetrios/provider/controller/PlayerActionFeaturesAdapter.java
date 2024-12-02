package cs3500.threetrios.provider.controller;

import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.player.PlayerActionFeatures;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.view.ThreeTriosCardPanel;

/**
 * Adapter that allows an AdapterFeatureController to be used where a PlayerActionFeatures is required.
 */
public class PlayerActionFeaturesAdapter implements PlayerActionFeatures {
  private final AdapterFeatureController controller;

  /**
   * Constructs a PlayerActionFeaturesAdapter that wraps an AdapterFeatureController.
   *
   * @param controller the controller to be adapted
   */
  public PlayerActionFeaturesAdapter(AdapterFeatureController controller) {
    this.controller = controller;
  }

  @Override
  public void handleCardSelection(TeamColor playerColor, int cardIndex, ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard) {
    // Delegate to the corresponding method in AdapterFeatureController
    controller.cardSelected(PlayerType.valueOf(playerColor.toString()), cardIndex);
  }

  @Override
  public void handleBoardSelection(int row, int col) {
    // Delegate to the corresponding method in AdapterFeatureController
    controller.cellClicked(row, col);
  }
}
