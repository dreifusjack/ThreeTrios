package cs3500.threetrios.controller;

import cs3500.threetrios.model.ModelStatusFeatures;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.PlayerActionFeatures;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.view.ThreeTriosCardPanel;
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
    if (this.playerActions.addsPlayerActions()) {
      this.playerActions.addPlayerActionListener(this);
    } else {
      this.view.addPlayerActionListener(this);
    }
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
  public void selectCard(TeamColor playerColor, int cardIndex, ThreeTriosCardPanel cardPanel, ThreeTriosCardPanel highlightedCard) {
    if (model.isGameOver()) {
      return;
    }
    if (outOfTurn()) {
      JOptionPane.showMessageDialog(view, "You are out of turn!");
      return;
    }
    if (model.getCurrentPlayer().getColor().equals(playerColor)) {
      selectedCardIndex = cardIndex;
      if (cardPanel != null) {
        cardPanel.toggleHighlight();
      }
    } else {
      JOptionPane.showMessageDialog(null, "Only select cards from your hand.");
      selectedCardIndex = -1;
    }
    if (highlightedCard != null && highlightedCard.getColor().equals(controllerTeam)) {
      highlightedCard.toggleHighlight();
    }
  }

  private boolean outOfTurn() {
    return controllerTeam != model.getCurrentPlayer().getColor();
  }

  @Override
  public void placeCard(int row, int col) {
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
        view.refresh();
        selectedCardIndex = -1;
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null, "Invalid move: " + e.getMessage());
        selectedCardIndex = -1;
      }
    } else {
      JOptionPane.showMessageDialog(null, "Please select a card to play to the board.");
    }
  }

  @Override
  public void onCardSelected(TeamColor playerColor, int cardIndex, ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard) {
    selectCard(playerColor, cardIndex, selectedCard, highlightedCard);
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
    view.setTitle("Game Over!");
    JOptionPane.showMessageDialog(null, gameOverMessage.toString());
  }
}
