package cs3500.threetrios.controller;

import cs3500.threetrios.model.ModelStatusFeatures;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.PlayerActionFeatures;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.view.ViewFeatures;

import javax.swing.*;

/**
 * The ThreeTriosController2 class is responsible for coordinating between the model, view, and
 * player actions. It implements Features, PlayerActionListener, and ModelStatusListener to
 * provide a complete control layer for managing user interactions, updating the game state, and
 * responding to model changes.
 */
public class ThreeTriosController2 implements ViewFeatures, PlayerActionFeatures, ModelStatusFeatures {
  private final ThreeTriosModel model;
  private final TTGUIView view;
  private final PlayerActions playerActions;
  private int selectedCardIndex;
  private final TeamColor controllerTeam;


  /**
   * Constructs a ThreeTriosController2 with the given model, view, and player actions.
   *
   * @param model          the model representing the game state
   * @param view           the view that displays the game interface
   * @param playerActions  the actions associated with the player (AI or human)
   */
  public ThreeTriosController2(ThreeTriosModel model, TTGUIView view, PlayerActions playerActions) {
    this.model = model;
    this.view = view;
    this.playerActions = playerActions;

    selectedCardIndex = -1;
    controllerTeam = playerActions.getColor();

    this.view.setFeatures(this);
    this.model.addModelStatusListener(this);
    this.view.addPlayerActionListener(this);
    this.playerActions.addPlayerActionListener(this);
  }


  @Override
  public void startGame() {
    view.refresh();
    if (model.getCurrentPlayer().getColor().equals(playerActions.getColor())) {
      view.setTitle(playerActions.getColor() + " Player: Your Turn");
      playerActions.selectCard(model);
      playerActions.makeMove(model);
    } else {
      view.setTitle(playerActions.getColor() + " Player: Waiting for opponent");
    }
  }

  @Override
  public void selectCard(TeamColor playerColor, int cardIndex) {
    if (outOfTurn()) {
      JOptionPane.showMessageDialog(view, "You are out of turn!");
      return;
    }
    if (model.getCurrentPlayer().getColor().equals(playerColor)) {
      selectedCardIndex = cardIndex;
      System.out.println(playerColor + " selected card at index: " + cardIndex);
    } else {
      JOptionPane.showMessageDialog(null, "Only select cards from your hand.");
      selectedCardIndex = -1;
    }
  }

  // Private method to checks if the controller's player is out of turn.
  private boolean outOfTurn() {
    return controllerTeam != model.getCurrentPlayer().getColor();
  }

  @Override
  public void placeCard(int row, int col) {
    if (outOfTurn()) {
      JOptionPane.showMessageDialog(view, "You are out of turn!");
      return;
    }
    if (selectedCardIndex >= 0) {
      try {
        model.playToGrid(row, col, selectedCardIndex);
        view.refresh();
        selectedCardIndex = -1;
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null, "Invalid move: " + e.getMessage());
      }
    } else {
      JOptionPane.showMessageDialog(null, "Please select a card to play to the board.");
    }
  }

  @Override
  public void onCardSelected(TeamColor playerColor, int cardIndex) {
    selectCard(playerColor, cardIndex);
  }

  @Override
  public void onCardPlaced(int row, int col) {
    placeCard(row, col);
  }

  @Override
  public void onPlayerTurnChange(Player currentPlayer) {
    if (currentPlayer.getColor().equals(playerActions.getColor())) {
      view.setTitle(playerActions.getColor() + " Player: Your Turn");
      playerActions.selectCard(model);
      playerActions.makeMove(model);
    } else {
      view.setTitle(playerActions.getColor() + " Player: Waiting for opponent");
    }
    view.refresh();
  }

  @Override
  public void onGameOver(Player winningPlayer) {
    StringBuilder gameOverMessage = new StringBuilder();
    gameOverMessage.append("Game Over! ");
    if (winningPlayer != null) {
      gameOverMessage.append("Winner: " + winningPlayer.getColor()
              + ", with a score of: " + model.getPlayerScore(winningPlayer.getColor()));
    } else {
      gameOverMessage.append("It's a draw, with a tied score of: "
              + model.getPlayerScore(TeamColor.RED));
    }
    view.refresh();
    JOptionPane.showMessageDialog(null, gameOverMessage.toString());
  }
}
