package cs3500.threetrios.provider.controller;


import javax.swing.JOptionPane;

import cs3500.threetrios.model.ModelStatusFeatures;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.provider.model.ThreeTriosModel;
import cs3500.threetrios.provider.view.SimpleThreeTriosView;

/**
 * AdapterFeatureController is responsible for controlling the game
 * flow by adapting the model and views.
 */
public class AdapterFeatureController
        implements ModelStatusListener, PlayerActionListener, ModelStatusFeatures {
  private final ThreeTriosModel adaptedModel;
  private final cs3500.threetrios.model.ThreeTriosModel model;
  private final SimpleThreeTriosView view;
  private final PlayerActions playerActions;
  private int selectedCardIndex;
  private final TeamColor controllerTeam;

  /**
   * Constructs an AdapterFeatureController.
   *
   * @param adaptedModel  the adapted model for the game
   * @param view          the adapted view for the game
   * @param playerActions the actions that a player can take (human or AI)
   */
  public AdapterFeatureController(ThreeTriosModel adaptedModel,
                                  cs3500.threetrios.model.ThreeTriosModel model,
                                  SimpleThreeTriosView view, PlayerActions playerActions) {
    this.adaptedModel = adaptedModel;
    this.view = view;
    this.playerActions = playerActions;
    this.model = model;

    selectedCardIndex = -1;
    controllerTeam = playerActions.getColor();
    this.model.addModelStatusListener(this);

    if (playerActions.addsPlayerActions()) {
      this.playerActions.addPlayerActionListener(new PlayerActionFeaturesAdapter(this));
    } else {
      this.view.addPlayerActionListener(this);
    }

    if (PlayerType.valueOf(controllerTeam.toString()).equals(PlayerType.RED)) {
      view.setInteractionEnabled(true, PlayerType.RED);
    } else {
      view.setInteractionEnabled(true, PlayerType.BLUE);
    }

    this.view.setVisible(true);
    handlePlayerTurn();
  }

  private void handlePlayerTurn() {
    PlayerType currentPlayer = adaptedModel.getCurrentPlayer();
    boolean isMyTurn = currentPlayer == PlayerType.valueOf(controllerTeam.toString());

    if (isMyTurn) {
      view.updatePlayerStatus(PlayerType.valueOf(controllerTeam.toString()), true);
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
  public void currentPlayerChanged(PlayerType newCurrentPlayer) {
    view.updatePlayerStatus(newCurrentPlayer, true);
    view.updateView();
    handlePlayerTurn();
  }

  @Override
  public void gameOver(PlayerType winner, int redScore, int blueScore) {
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
  public void cardSelected(PlayerType playerType, int cardIndex) {
    if (adaptedModel.isGameOver()) {
      return;
    }

    if (adaptedModel.getCurrentPlayer().equals(playerType)) {
      selectedCardIndex = cardIndex;
    } else {
      JOptionPane.showMessageDialog(
              null, "Only select cards from your hand.");
      selectedCardIndex = -1;
    }
    view.updateView();
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
    if (controllerTeam.equals(TeamColor.RED)) {
      view.resetSelectedCard(PlayerType.RED);
    } else {
      view.resetSelectedCard(PlayerType.BLUE);
    }
  }

  @Override
  public void onPlayerTurnChange() {
    this.currentPlayerChanged(adaptedModel.getCurrentPlayer());
  }

  @Override
  public void onGameOver() {
    this.gameOver(adaptedModel.getWinner(),
            adaptedModel.getPlayerScore(PlayerType.RED),
            adaptedModel.getPlayerScore(PlayerType.BLUE));
  }
}
