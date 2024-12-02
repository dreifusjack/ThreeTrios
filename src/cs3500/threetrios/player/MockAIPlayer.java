package cs3500.threetrios.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.PlayerActionListener;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.player.strategy.PlayedMove;
import cs3500.threetrios.player.strategy.ThreeTriosStrategy;

/**
 * Mock class for the AIPLayer class.
 */
public class MockAIPlayer extends AIPlayer {

  private final ThreeTriosStrategy strategy;
  private final TeamColor teamColor;
  private final List<PlayerActionListener> actionListeners;
  private final List<String> log;


  /**
   * Constructs an AIPlayer with the given team color and strategy.
   *
   * @param teamColor is the team color of the player (either RED or BLUE)
   * @param strategy  is the strategy used by the AI to make decisions
   * @throws IllegalArgumentException if the strategy or team color is null
   */
  public MockAIPlayer(TeamColor teamColor, ThreeTriosStrategy strategy) {
    super(teamColor, strategy);

    this.teamColor = teamColor;
    this.strategy = strategy;
    this.actionListeners = new ArrayList<>();
    this.log = new ArrayList<>();
  }


  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    actionListeners.add(listener);
    log.add("addPlayerActionListener called");
  }

  @Override
  public void notifySelectedCard(ReadOnlyThreeTriosModel model) {
    Player playerAI = model.getCurrentPlayer();
    PlayedMove move = strategy.findBestMove(model, playerAI);
    for (PlayerActionListener listener : actionListeners) {
      listener.handleCardSelection(teamColor, move.getHandInx(), null,
              null);
      log.add("notifySelectedCard called");
      log.add("AI selected card at index: " + move.getHandInx());
    }

  }

  @Override
  public void notifyPlacedCard(ReadOnlyThreeTriosModel model) {
    Player playerAI = model.getCurrentPlayer();
    PlayedMove move = strategy.findBestMove(model, playerAI);
    for (PlayerActionListener listener : actionListeners) {
      listener.handleBoardSelection(move.getRow(), move.getCol());
      log.add("notifyPlacedCard called");
      log.add("AI placed card at row: " + move.getRow() + ", col: " + move.getCol());
    }
  }

  @Override
  public TeamColor getColor() {
    log.add("getColor called");
    return teamColor;
  }

  @Override
  public boolean addsPlayerActions() {
    log.add("addsPlayerActions called");
    return true;
  }

  public List<String> getLog() {
    return this.log;
  }

}
