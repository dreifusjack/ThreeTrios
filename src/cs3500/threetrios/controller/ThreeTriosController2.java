package cs3500.threetrios.controller;

import cs3500.threetrios.model.ModelStatusFeatures;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.PlayerActionFeatures;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.view.ThreeTriosCardPanel;

import javax.swing.*;

/**
 * The ThreeTriosController2 class is responsible for coordinating between the model, view, and
 * player actions. It implements Features, PlayerActionListener, and ModelStatusListener to
 * provide a complete control layer for managing user interactions, updating the game state, and
 * responding to model changes.
 */
public class ThreeTriosController2 implements PlayerActionFeatures, ModelStatusFeatures {
  private final ThreeTriosModel model;
  private final TTGUIView view;
  private final PlayerActions playerActions;
  private int selectedCardIndex;
  private final TeamColor controllerTeam;

  /**
   * Constructs a ThreeTriosController2 with the given model, view, and player actions.
   *
   * @param model         the model representing the game state
   * @param view          the view that displays the game interface
   * @param playerActions the actions associated with the player (AI or human)
   */
  public ThreeTriosController2(ThreeTriosModel model, TTGUIView view, PlayerActions playerActions) {
    this.model = model;
    this.view = view;
    this.playerActions = playerActions;

    selectedCardIndex = -1;
    controllerTeam = playerActions.getColor();

    //this.view.setFeatures(this);
    this.model.addModelStatusListener(this);
    if (playerActions.addsPlayerActions()) {
      this.playerActions.addPlayerActionListener(this);
    } else {
      this.view.addPlayerActionListener(this);
    }
  }

  public void startGame() {
    handlePlayerTurn();
  }

  private void handlePlayerTurn() {
    if (model.getCurrentPlayer().getColor().equals(playerActions.getColor())) {
      view.updateTitle(playerActions.getColor() + " Player: Your Turn");
      handleAIMoveIfPresent();
    } else {
      view.updateTitle(playerActions.getColor() + " Player: Waiting for opponent");
    }
    view.refreshPlayingBoard();
  }

  /**
   * Calls player action methods to make a move that will play to the models grid. This only occurs
   * if playerActions is an AI player, if playActions is a human player these method calls will
   * be omitted as human player actions are handled with the user interacting with the GUI.
   */
  private void handleAIMoveIfPresent() {
    playerActions.selectCard(model);
    playerActions.placeSelectedCard(model);
  }

  // Private method to check if the current player is out of turn.
  private boolean outOfTurn() {
    return controllerTeam != model.getCurrentPlayer().getColor();
  }

  @Override
  public void handleCardSelection(TeamColor playerColor, int cardIndex, ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard) {
    if (model.isGameOver()) {
      return;
    }
    if (outOfTurn()) {
      JOptionPane.showMessageDialog(view, "You are out of turn!");
      return;
    }
    if (model.getCurrentPlayer().getColor().equals(playerColor)) {
      selectedCardIndex = cardIndex;
      if (selectedCard != null) {
        selectedCard.toggleHighlight();
      }
    } else {
      JOptionPane.showMessageDialog(null, "Only select cards from your hand.");
      selectedCardIndex = -1;
    }
    if (highlightedCard != null && highlightedCard.getColor().equals(controllerTeam)) {
      highlightedCard.toggleHighlight();
    }
  }

  @Override
  public void handleBoardSelection(int row, int col) {
    if (model.isGameOver()) {
      return;
    }
    if (outOfTurn()) {
      JOptionPane.showMessageDialog(view, "You are out of turn!");
      return;
    }
    if (selectedCardIndex >= 0) {
      try {
        model.playToGrid(row, col, selectedCardIndex);
        view.refreshPlayingBoard();
        selectedCardIndex = -1;
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null, "Invalid move: " + e.getMessage());
      }
    } else {
      JOptionPane.showMessageDialog(null, "Please select a card to play to the board.");
    }
  }

  @Override
  public void onPlayerTurnChange() {
    handlePlayerTurn();
  }

  @Override
  public void onGameOver() {
    Player winner = model.getWinner();
    StringBuilder gameOverMessage = new StringBuilder();
    gameOverMessage.append("Game Over! ");
    if (winner != null) {
      gameOverMessage.append("Winner: " + winner.getColor()
              + ", with a score of: " + model.getPlayerScore(winner.getColor()));
    } else {
      gameOverMessage.append("It's a draw, with a tied score of: "
              + model.getPlayerScore(TeamColor.RED));
    }
    view.refreshPlayingBoard();
    view.updateTitle(playerActions.getColor() + " Player: Game Over!");
    JOptionPane.showMessageDialog(null, gameOverMessage.toString());
  }
}
