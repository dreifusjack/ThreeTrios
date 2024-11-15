package cs3500.threetrios.view;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.TTGUIView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreeTriosController implements Features, PlayerActionListener, ModelStatusListener {
  private final ThreeTriosModel model;
  private final TTGUIView view;
  private final PlayerActions playerActions;
  private boolean cardSelected;
  private int selectedCardIndex;

  public ThreeTriosController(ThreeTriosModel model, TTGUIView view, PlayerActions playerActions) {
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
    String message = (winningPlayer != null) ? ("Game Over! Winner: " + winningPlayer.getColor()) : "Game Over! It's a draw.";
    JOptionPane.showMessageDialog(null, message);
  }
}
