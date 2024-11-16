package cs3500.threetrios.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.player.strategy.PlayedMove;
import cs3500.threetrios.player.strategy.ThreeTriosStrategy;

public class AIPlayer implements PlayerActions {
  private final ThreeTriosStrategy strategy;
  private final TeamColor teamColor;
  private final List<PlayerActionFeatures> actionListeners;

  public AIPlayer(TeamColor teamColor, ThreeTriosStrategy strategy) {
    if (strategy == null || teamColor == null) {
      throw new IllegalArgumentException("Strategy and team color cannot be null");
    }
    this.teamColor = teamColor;
    this.strategy = strategy;
    this.actionListeners = new ArrayList<>();
  }

  @Override
  public void addPlayerActionListener(PlayerActionFeatures listener) {
    actionListeners.add(listener);
  }

  @Override
  public void selectCard(ReadOnlyThreeTriosModel model) {
    Player currentPlayer = model.getCurrentPlayer();

    if (currentPlayer.getColor().equals(teamColor) && !currentPlayer.getHand().isEmpty()) {
      Player playerAI = model.getCurrentPlayer();

      PlayedMove move = strategy.findBestMove(model, playerAI);
      // TODO: should this ever be the case?
      if (move != null) {
        System.out.println(playerAI.getColor() + " (machine) selected card at index: " + move.getHandInx());
        for (PlayerActionFeatures listener : actionListeners) {
          listener.onCardSelected(teamColor, move.getHandInx());
        }
      }
    }
  }

  @Override
  public void makeMove(ReadOnlyThreeTriosModel model) {
    javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {
      Player playerAI = model.getCurrentPlayer();
      PlayedMove move = strategy.findBestMove(model, playerAI);
      if (move != null) {
        System.out.println(playerAI.getColor() + " (machine) placed card at row: " + move.getRow() + ", col: " + move.getCol());
        for (PlayerActionFeatures listener : actionListeners) {
          listener.onCardPlaced(move.getRow(), move.getCol());
        }
      }
    });
    timer.setRepeats(false);
    timer.start();
  }

  @Override
  public TeamColor getColor() {
    return teamColor;
  }
}