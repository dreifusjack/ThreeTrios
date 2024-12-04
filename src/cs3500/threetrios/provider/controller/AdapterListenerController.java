package cs3500.threetrios.provider.controller;

import javax.swing.JOptionPane;

import cs3500.threetrios.controller.ModelStatusListener;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.provider.model.ModelAdapter;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.provider.model.ThreeTriosModel;
import cs3500.threetrios.provider.view.SimpleThreeTriosView;

/**
 * AdapterFeaturesController is responsible for adapting the provided model and view to control
 * the game flow of ThreeTrios with our model and the opposing controller. This controller will
 * always be the blue team and the opposing controller will always be the red team.
 */
public class AdapterListenerController implements PlayerActionListener, ModelStatusListener {
  private final cs3500.threetrios.model.ThreeTriosModel model;
  private final ThreeTriosModel adaptedModel;
  private final SimpleThreeTriosView view;
  private final PlayerActions playerActions;
  private int selectedCardIndex;

  /**
   * Constructs an AdapterFeatureController in terms of the model that communicates between both
   * controllers, and the player actions this controller is responsible for.
   *
   * @param model         model that handles game-state between both controllers
   * @param playerActions the actions that a player can take (human or AI)
   * @throws IllegalArgumentException if model or playerActions are null
   */
  public AdapterListenerController(
          cs3500.threetrios.model.ThreeTriosModel model, PlayerActions playerActions) {
    if (model == null || playerActions == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    adaptedModel = new ModelAdapter(model);
    view = new SimpleThreeTriosView(adaptedModel);
    this.playerActions = playerActions;

    selectedCardIndex = -1;
    this.model.addModelStatusListener(this);

    if (playerActions.addsPlayerActions()) {
      this.playerActions.addPlayerActionListener(new PlayerActionListenerAdapter(this));
    } else {
      view.addPlayerActionListener(this);
    }
    // strictly enabling Blue Team interactions because this controller will always be Blue
    // per the assignment instructions
    view.setInteractionEnabled(true, PlayerType.BLUE);
    view.setVisible(true);
    handlePlayerTurn();
  }

  /**
   * Handles the logic for GUI status titles to correspond with if the player is waiting or playing,
   * and is responsible for playing AI moves if this controller belongs to an AI player.
   */
  private void handlePlayerTurn() {
    PlayerType currentPlayer = adaptedModel.getCurrentPlayer();
    // since this controller will always be Blue per the assignment instructions
    if (currentPlayer.equals(PlayerType.BLUE)) {
      view.updatePlayerStatus(currentPlayer, true);
      handleAIMoveIfPresent();
    } else {
      view.updatePlayerStatus(currentPlayer, false);
    }
    view.updateView();
  }

  /**
   * Handles the AI move if the player actions belong to an AI player.
   */
  private void handleAIMoveIfPresent() {
    playerActions.notifySelectedCard(model);
    playerActions.notifyPlacedCard(model);
  }

  @Override
  public void cardSelected(PlayerType playerType, int cardIndex) {
    if (adaptedModel.isGameOver()) {
      return;
    }
    selectedCardIndex = cardIndex;
  }

  @Override
  public void cellClicked(int row, int col) {
    if (adaptedModel.isGameOver()) {
      return;
    }
    if (selectedCardIndex >= 0) {
      try {
        adaptedModel.playCard(selectedCardIndex, row, col);
        view.updateView();
        selectedCardIndex = -1;
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(
                null, "Invalid move: " + e.getMessage());
      }
    } else {
      JOptionPane.showMessageDialog(
              null, "Please select a card to play to the board.");
    }
    // strictly reset Blue Team card selection because this controller will always be Blue
    // per the assignment instructions
    view.resetSelectedCard(PlayerType.BLUE);
  }

  @Override
  public void onPlayerTurnChange() {
    view.updatePlayerStatus(adaptedModel.getCurrentPlayer(), true);
    view.updateView();
    handlePlayerTurn();
  }

  @Override
  public void onGameOver() {
    PlayerType winner = adaptedModel.getWinner();
    int redScore = adaptedModel.getPlayerScore(PlayerType.RED);
    int blueScore = adaptedModel.getPlayerScore(PlayerType.BLUE);
    String message = "Game Over! ";
    if (winner == PlayerType.RED) {
      message += "Winner: RED with score " + redScore;
    } else if (winner == PlayerType.BLUE) {
      message += "Winner: BLUE with score " + blueScore;
    } else {
      message += "It's a draw! RED: " + redScore + ", BLUE: " + blueScore;
    }
    view.updateView();
    view.updatePlayerStatus(winner, false);
    JOptionPane.showMessageDialog(null, message);
  }

  @Override
  public int getSelectedCardIndex() {
    return selectedCardIndex;
  }
}
