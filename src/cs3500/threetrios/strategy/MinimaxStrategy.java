package cs3500.threetrios.strategy;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

import java.util.List;

public class MinimaxStrategy implements ThreeTriosStrategy {
  private final List<ThreeTriosStrategy> opponentStrategies;

  public MinimaxStrategy(List<ThreeTriosStrategy> opponentStrategies) {
    this.opponentStrategies = opponentStrategies;
  }

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    PlayedMove bestMove = null;
    int minOpponentMaxScore = Integer.MAX_VALUE;

    // loop through the card in the hand
    for (int index = 0; index < player.getHand().size(); index++) {
      for (int row = 0; row < model.numRows(); row++) {
        for (int col = 0; col < model.numCols(); col++) {
          if (!model.getCell(row, col).cardToString().equals("_")) {
            continue;
          }

          // simulate the player's move
          PlayedMove playerMove = new BasicMove(index, row, col);
          ReadOnlyThreeTriosModel modelAfterMove = model.simulateMove(row, col, index); // this create a copy so it won't mutate or alter any information in the OG model

          // determine the worst possible move by the opponent after this move (worst for us and best for them)
          int opponentMaxScore = calculateOpponentMaxScore(modelAfterMove, player);

          if (opponentMaxScore < minOpponentMaxScore ||
                  (opponentMaxScore == minOpponentMaxScore && (bestMove == null || row < bestMove.getRow() ||
                          (row == bestMove.getRow() && col < bestMove.getCol())))) {
            minOpponentMaxScore = opponentMaxScore;
            bestMove = playerMove;
          }
        }
      }
    }

    // if no valid move is found, choose the uppermost-leftmost open position and card at index 0
    if (bestMove == null && !player.getHand().isEmpty()) {
      for (int row = 0; row < model.numRows(); row++) {
        for (int col = 0; col < model.numCols(); col++) {
          ReadOnlyGridCell cell = model.getCell(row, col);
          if (cell.cardToString().equals("_")) {
            return new BasicMove(0, row, col);
          }
        }
      }
    }

    return bestMove;
  }

  /**
   * Calculate the worst move (best move for them) that the opponent can make given the current state.
   */
  private int calculateOpponentMaxScore(ReadOnlyThreeTriosModel modelAfterMove, Player player) {
    int maxScore = -1;

    // for each strategy the opponent might use, find the maximum score
    for (ThreeTriosStrategy opponentStrategy : opponentStrategies) {

      Player opponentPlayer = modelAfterMove.getCurrentPlayer(); // we get current player here as we just playToGrid above and it changes the player
      PlayedMove opponentMove = opponentStrategy.findBestMove(modelAfterMove, opponentPlayer);
      if (opponentMove != null) {
        int score = modelAfterMove.numCardFlips(opponentPlayer.getHand().get(opponentMove.getHandInx()),
                opponentMove.getRow(), opponentMove.getCol(), opponentPlayer);
        maxScore = Math.max(maxScore, score);
      }
    }
    return maxScore;
  }
}
