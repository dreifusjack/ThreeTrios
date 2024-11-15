package cs3500.threetrios.view;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.strategy.PlayedMove;
import cs3500.threetrios.strategy.ThreeTriosStrategy;

public class AIPlayer implements PlayerActions {
  private final ThreeTriosStrategy strategy;
  private final TeamColor teamColor;

  public AIPlayer(TeamColor teamColor, ThreeTriosStrategy strategy) {
    this.teamColor = teamColor;
    this.strategy = strategy;
  }

  @Override
  public void selectCard(ReadOnlyThreeTriosModel model) {
    Player currentPlayer = model.getCurrentPlayer();

    if (currentPlayer.getColor().equals(teamColor) && !currentPlayer.getHand().isEmpty()) {
      Player playerAI = model.getCurrentPlayer();

      PlayedMove move = strategy.findBestMove(model, playerAI);
      if (move != null) {
        System.out.println(playerAI.getColor() + " (machine) selected card at index: " + move.getHandInx());
      }
    }
  }

  @Override
  public void makeMove(ReadOnlyThreeTriosModel model) {
    Player playerAI = model.getCurrentPlayer();
    PlayedMove move = strategy.findBestMove(model, playerAI);

    if (move != null) {
      System.out.println(playerAI.getColor() + " (machine) placed card at row: " + move.getRow() + ", col: " + move.getCol());
    }
  }

  @Override
  public TeamColor getColor() {
    return this.teamColor;
  }

}