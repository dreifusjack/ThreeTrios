package cs3500.threetrios.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.player.strategy.PlayedMove;
import cs3500.threetrios.player.strategy.ThreeTriosStrategy;

/**
 * Represents an AI player in the ThreeTrios game. AI players make moves based on a given strategy.
 * So the strategy defines how the AI selects cards and places them on the grid.
 */
public class AIPlayer implements PlayerActions {
  private final ThreeTriosStrategy strategy;
  private final TeamColor teamColor;
  private final List<PlayerActionFeatures> actionListeners;

  /**
   * Constructs an AIPlayer with the given team color and strategy.
   *
   * @param teamColor is the team color of the player (either RED or BLUE)
   * @param strategy is the strategy used by the AI to make decisions
   * @throws IllegalArgumentException if the strategy or team color is null
   */
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
  public void notifySelectedCard(ReadOnlyThreeTriosModel model) {
    Player playerAI = model.getCurrentPlayer();
    PlayedMove move = strategy.findBestMove(model, playerAI);
    for (PlayerActionFeatures listener : actionListeners) {
      listener.handleCardSelection(teamColor, move.getHandInx(), null, null);
    }
  }

  @Override
  public void notifyPlacedCard(ReadOnlyThreeTriosModel model) {
    javax.swing.Timer timer = new javax.swing.Timer(1500, e -> { // delays move (so its visible)
      Player playerAI = model.getCurrentPlayer();
      PlayedMove move = strategy.findBestMove(model, playerAI);
      for (PlayerActionFeatures listener : actionListeners) {
        listener.handleBoardSelection(move.getRow(), move.getCol());
      }
    });
    timer.setRepeats(false);
    timer.start();
  }

  @Override
  public TeamColor getColor() {
    return teamColor;
  }

  @Override
  public boolean addsPlayerActions() {
    return true;
  }
}