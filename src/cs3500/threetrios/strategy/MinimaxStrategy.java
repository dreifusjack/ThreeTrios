package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

import java.util.List;

/**
 * Represent a MinimaxStrategy which is one of the ThreeTriosStrategy to pick the best move to
 * play the ThreeTrios game. This strategy works by the concept of choosing a move that minimize
 * the impact of the opponent's move as much as possible. This ThreeTriosStrategy takes in a list
 * of strategy of all the ThreeTriosStrategy the opponent could have. Then for every card in the
 * hand of the current player, we will loop through every ReadOnlyThreeTriosModel on the board. And
 * for every ReadOnlyThreeTriosModel, we will try to simulate the game by playToGrid with this
 * card at this cell. Then MinimaxStrategy will pass this updated version of our model to
 * calculateOpponentMaxScore to find the best move out of the list of ThreeTriosStrategy our
 * opponent has. The ThreeTriosStrategy will go through each of their ThreeTriosStrategy, and for
 * each ThreeTriosStrategy, it will loop through each cards in their hand and perform findBestMove
 * with this ThreeTriosStrategy and this card and the best move from this will use on numCardFlips
 * to find the number cards that this opponent's potential can flip. And we will need to choose
 * a move to minimize this number of cards (our card) the opponent could flip. If there
 *  * is a tie with many best move then break the tie by choosing the move with the
 *  uppermost-leftmost coordinate for the position and then choose the best card for that
 *  position with an index closest to 0 in the hand. If there are no valid moves, your player
 *  should pass choose the upper-most, left-most open position and the card at index 0.
 */
public class MinimaxStrategy implements ThreeTriosStrategy {
  private final List<ThreeTriosStrategy> opponentStrategies;

  public MinimaxStrategy(List<ThreeTriosStrategy> opponentStrategies) {
    this.opponentStrategies = opponentStrategies;
  }

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    PlayedMove bestMove = this.findBestMoveForChain(model, player);

    if (bestMove != null) {
      return bestMove;
    }
    else {
      return handleNullMove(model, player, bestMove);
    }
  }

  private static BasicMove handleNullMove(ReadOnlyThreeTriosModel model, Player player,
                                          PlayedMove bestMove) {
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
    return null;
  }

  @Override
  public PlayedMove findBestMoveForChain(ReadOnlyThreeTriosModel model, Player player) {
    PlayedMove bestMove = null;
    int minOpponentMaxScore = Integer.MAX_VALUE;

    // loop through the card in the hand
    for (int index = 0; index < player.getHand().size(); index++) {
      for (int row = 0; row < model.numRows(); row++) {
        for (int col = 0; col < model.numCols(); col++) {

          if (model.getCell(row, col).isOccupied()) {
            continue;
          }

          // simulate the player's move
          PlayedMove playerMove = new BasicMove(index, row, col);
          // this create a copy so it won't mutate or alter any information in the current model
          ReadOnlyThreeTriosModel modelAfterMove = model.simulateMove(row, col, index);

          // determine the worst possible move by the opponent after this move
          // (worst for us and best for them)
          int opponentMaxScore = calculateOpponentMaxScore(modelAfterMove, player);

          if (opponentMaxScore < minOpponentMaxScore
                  || (opponentMaxScore == minOpponentMaxScore && (bestMove == null
                  || row < bestMove.getRow()
                  || (row == bestMove.getRow() && col < bestMove.getCol())))) {
            minOpponentMaxScore = opponentMaxScore;
            bestMove = playerMove;
          }
        }
      }
    }
    return bestMove;
  }

  /**
   * Calculate the worst move (best move for them) that the opponent can make given
   * the current state.
   */
  private int calculateOpponentMaxScore(ReadOnlyThreeTriosModel modelAfterMove, Player player) {
    int maxScore = -1;

    // for each strategy the opponent might use, find the maximum score
    for (ThreeTriosStrategy opponentStrategy : opponentStrategies) {

      Player opponentPlayer = modelAfterMove.getCurrentPlayer();
      // we get current player here as we just playToGrid above and it changes the player
      PlayedMove opponentMove = opponentStrategy.findBestMove(modelAfterMove, opponentPlayer);
      if (opponentMove != null) {
        int score = modelAfterMove.numCardFlips(
                opponentPlayer.getHand().get(opponentMove.getHandInx()),
                opponentMove.getRow(), opponentMove.getCol(), opponentPlayer);
        maxScore = Math.max(maxScore, score);
      }
    }
    return maxScore;
  }
}
