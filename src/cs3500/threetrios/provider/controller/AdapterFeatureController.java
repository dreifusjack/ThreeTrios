package cs3500.threetrios.provider.controller;

import javax.swing.*;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.provider.model.ThreeTriosModel;
import cs3500.threetrios.provider.view.SimpleThreeTriosView;

/**
 * AdapterFeatureController is responsible for controlling the game flow by adapting the model and views.
 */
public class AdapterFeatureController implements ModelStatusListener, PlayerActionListener {

  private final ThreeTriosModel model;
  private final SimpleThreeTriosView view;
  private final PlayerActions playerActions;
  private int selectedCardIndex;
  private final TeamColor controllerTeam;

  /**
   * Constructs an AdapterFeatureController.
   *
   * @param model         the adapted model for the game
   * @param view          the adapted view for the game
   * @param playerActions the actions that a player can take (human or AI)
   */
  public AdapterFeatureController(ThreeTriosModel model, SimpleThreeTriosView view,
                                  PlayerActions playerActions) {
    this.model = model;
    this.view = view;
    this.playerActions = playerActions;

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

  protected void handlePlayerTurn() {
    PlayerType currentPlayer = model.getCurrentPlayer();
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
  protected void handleAIMoveIfPresent() {
    playerActions.notifySelectedCard((ReadOnlyThreeTriosModel) model); ///// check ReadOnlyThreeTriosModel
    playerActions.notifyPlacedCard((ReadOnlyThreeTriosModel) model);
  }

  @Override
  public void currentPlayerChanged(PlayerType newCurrentPlayer) {
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
    view.updatePlayerStatus(winner, false);
    JOptionPane.showMessageDialog(null, message);
  }

  @Override
  public void cardSelected(PlayerType playerType, int cardIndex) {
    if (model.isGameOver()) {
      return;
    }

    if (model.getCurrentPlayer().equals(playerType)) {
      selectedCardIndex = cardIndex;
    } else {
      JOptionPane.showMessageDialog(null, "Only select cards from your hand.");
      selectedCardIndex = -1;
    }
  }

  @Override
  public void cellClicked(int row, int col) {
    if (model.isGameOver()) {
      return;
    }

    if (selectedCardIndex >= 0) {
      try {
        model.playCard(selectedCardIndex, row, col);
        view.updateView();
        selectedCardIndex = -1;
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null, "Invalid move: " + e.getMessage());
      }
    } else {
      JOptionPane.showMessageDialog(null, "Please select a card to play to the board.");
    }
  }
}
