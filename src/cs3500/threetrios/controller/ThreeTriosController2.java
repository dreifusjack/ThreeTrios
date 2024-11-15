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

// TODO: features should be a has-a relationship in the model
public class ThreeTriosController2 implements ViewFeatures, PlayerActionFeatures, ModelStatusFeatures {
  private final ThreeTriosModel model;
  private final TTGUIView view;
  private final PlayerActions playerActions;
  private boolean cardSelected;
  private int selectedCardIndex;

  public ThreeTriosController2(ThreeTriosModel model, TTGUIView view, PlayerActions playerActions) {
    this.model = model;
    this.view = view;
    this.playerActions = playerActions;

    this.view.setFeatures(this);
    this.cardSelected = false;
    this.selectedCardIndex = -1;

    this.model.addModelStatusListener(this);
    this.view.addPlayerActionListener(this);
  }

  public void startGame() {
    view.refresh();
    // TODO: should also tell what player this view is for
    if (model.getCurrentPlayer().getColor().equals(playerActions.getColor())) {
      view.setTitle("Your Turn");
    } else {
      view.setTitle("Waiting for Opponent");
    }
  }


  @Override
  public void selectCard(TeamColor playerColor, int cardIndex) {
    Player currentPlayer = model.getCurrentPlayer();

    if (model.getCurrentPlayer().getColor().equals(playerColor)) {
      if (cardIndex >= 0 && cardIndex < currentPlayer.getHand().size()) {
        cardSelected = true;
        selectedCardIndex = cardIndex;
        System.out.println(playerColor + " selected card at index: " + cardIndex);
      } else {
        JOptionPane.showMessageDialog(null, "Invalid card index.");
      }
    } else {
      JOptionPane.showMessageDialog(null, "It's not your turn or invalid player.");
    }
  }

  @Override
  public void placeCard(TeamColor playerColor, int row, int col) {
    Player currentPlayer = model.getCurrentPlayer();
    System.out.println(model.getCurrentPlayer().getColor());
    System.out.println(playerColor);

    if (cardSelected) {
      try {
        model.playToGrid(row, col, selectedCardIndex);
        view.refresh();
        cardSelected = false;
        selectedCardIndex = -1;
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, "Invalid move: " + e.getMessage());
      }
    } else {
      JOptionPane.showMessageDialog(null, "No card selected.");
    }

  }

  @Override
  public void refreshView() {
    view.refresh();
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
      view.setTitle("Your Turn");
      playerActions.selectCard(model);
      playerActions.makeMove(model);
    } else {
      view.setTitle("Waiting for Opponent");
    }
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
    JOptionPane.showMessageDialog(null, gameOverMessage.toString());
  }
}
