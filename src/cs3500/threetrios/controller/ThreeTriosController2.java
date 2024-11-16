package cs3500.threetrios.controller;

import cs3500.threetrios.model.ModelStatusFeatures;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.PlayerActionFeatures;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.view.ViewFeatures;

import javax.swing.*;

// TODO: features should be a has-a relationship in the controller
public class ThreeTriosController2 implements ViewFeatures, PlayerActionFeatures, ModelStatusFeatures {
  private final ThreeTriosModel model;
  private final TTGUIView view;
  private final PlayerActions playerActions;
  private int selectedCardIndex;
  private final TeamColor controllerTeam;

  public ThreeTriosController2(ThreeTriosModel model, TTGUIView view, PlayerActions playerActions) {
    this.model = model;
    this.view = view;
    this.playerActions = playerActions;

    selectedCardIndex = -1;
    controllerTeam = playerActions.getColor();

    this.view.setFeatures(this);
    this.model.addModelStatusListener(this);
    this.view.addPlayerActionListener(this);
  }

  @Override
  public void startGame() {
    view.refresh();
    // TODO: should also tell what player this view is for
    if (model.getCurrentPlayer().getColor().equals(playerActions.getColor())) {
      view.setTitle(playerActions.getColor() + " Player: Your Turn");
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
    Player currentPlayer = model.getCurrentPlayer();

    if (model.getCurrentPlayer().getColor().equals(playerColor)) {
      selectedCardIndex = cardIndex;
      System.out.println(playerColor + " selected card at index: " + cardIndex);
    } else {
      JOptionPane.showMessageDialog(null, "Only select cards from your hand.");
      selectedCardIndex = -1;
    }
  }

  private boolean outOfTurn() {
    return controllerTeam != model.getCurrentPlayer().getColor();
  }

  @Override
  public void placeCard(TeamColor playerColor, int row, int col) {
    if (outOfTurn()) {
      JOptionPane.showMessageDialog(view, "You are out of turn!");
      return;
    }
    System.out.println(model.getCurrentPlayer().getColor());
    System.out.println(playerColor);

    if (selectedCardIndex >= 0 && model.getCurrentPlayer().getColor().equals(playerColor)) {
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
  public void onCardPlaced(TeamColor playerColor, int row, int col) {
    placeCard(playerColor, row, col);
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
      gameOverMessage.append("It's a draw. Red + Blue team score: "
              + model.getPlayerScore(TeamColor.RED));
    }
    view.refresh();
    JOptionPane.showMessageDialog(null, gameOverMessage.toString());
  }
}
