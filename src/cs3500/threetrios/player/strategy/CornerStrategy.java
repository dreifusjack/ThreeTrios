package cs3500.threetrios.player.strategy;

import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represent the CornerStrategy class. This strategy prioritize playing to the 4 corners on
 * the board. The strategy will loop through each of the 4 corners, then for each corner it will
 * loop through each card in the hand to find the "hardest card" to flip. The "hardest card" to
 * flip is the card that has the largest sum of its opening sides (each card in the corner will
 * be exposed to of its sides). The side that is facing a hole will has the value of 0. If there
 * is a tie with many best move then break the tie by choosing the move with the uppermost-leftmost
 * coordinate for the position and then choose the best card for that position with an index
 * closest to 0 in the hand. If there are no valid moves, your player should pass choose
 * the upper-most, left-most open position and the card at index 0.
 */
public class CornerStrategy implements ThreeTriosStrategy {

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

  // Helper method to handle when the strategy returns null for the best move.
  private PlayedMove handleNullMove(ReadOnlyThreeTriosModel model, Player player,
                                    PlayedMove bestMove) {
    if (bestMove == null && !player.getHand().isEmpty()) {
      for (int row = 0; row < model.numRows(); row++) {
        for (int col = 0; col < model.numCols(); col++) {
          ReadOnlyGridCell cell = model.getCell(row, col);
          if (cell.toString().equals("_")) {
            return new BasicMove(0, row, col);
          }
        }
      }
    }
    return null;
  }

  /**
   * Helper method to calculate the total value of a card. If a side is facing a hole then its
   * value will be 0.
   *
   * @param card  is the card to want to calculate.
   * @param model is the readonly version of the model.
   * @param row   is the row of this card.
   * @param col   is the column of this card.
   * @return the total of 2 sides of this card.
   */
  private int calculateSumValueForCorner(Card card, ReadOnlyThreeTriosModel model,
                                         int row, int col) {
    int sum = 0;

    // top-left
    if (row == 0 && col == 0) {
      sum += (model.getCell(row, col + 1).toString().equals(" ")) ? 0 :
              card.getEast().getValue();
      sum += (model.getCell(row + 1, col).toString().equals(" ")) ? 0 :
              card.getSouth().getValue();
    }
    // top-right
    else if (row == 0 && col == model.numCols() - 1) {
      sum += (model.getCell(row, col - 1).toString().equals(" ")) ? 0 :
              card.getWest().getValue();
      sum += (model.getCell(row + 1, col).toString().equals(" ")) ? 0 :
              card.getSouth().getValue();
    }
    // bottom-left
    else if (row == model.numRows() - 1 && col == 0) {
      sum += (model.getCell(row, col + 1).toString().equals(" ")) ? 0 :
              card.getEast().getValue();
      sum += (model.getCell(row - 1, col).toString().equals(" ")) ? 0 :
              card.getNorth().getValue();
    }
    // bottom-right
    else if (row == model.numRows() - 1 && col == model.numCols() - 1) {
      sum += (model.getCell(row, col - 1).toString().equals(" ")) ? 0 :
              card.getWest().getValue();
      sum += (model.getCell(row - 1, col).toString().equals(" ")) ? 0 :
              card.getNorth().getValue();
    }

    return sum;
  }

  @Override
  public PlayedMove findBestMoveForChain(ReadOnlyThreeTriosModel model, Player player) {
    BasicMove bestMove = null;
    int maxComboSoFar = -1;

    List<List<Integer>> corners = List.of(List.of(0, 0), // top-left
            List.of(0, model.numCols() - 1), // top-right
            List.of(model.numRows() - 1, 0), // bottom-left
            List.of(model.numRows() - 1, model.numCols() - 1) // bottom-right
    );

    for (List<Integer> corner : corners) {
      int row = corner.get(0);
      int col = corner.get(1);
      ReadOnlyGridCell cell = model.getCell(row, col);

      // skip cells that are not empty (hole / occupied)
      if (!cell.toString().equals("_")) {
        continue;
      }

      // loop through the player hand
      for (int cardIndex = 0; cardIndex < player.getHand().size(); cardIndex++) {
        Card card = player.getHand().get(cardIndex);

        int sumValue = calculateSumValueForCorner(card, model, row, col);

        // check if the currentMax is larger than the globalMax
        if (sumValue > maxComboSoFar) {
          maxComboSoFar = sumValue;
          bestMove = new BasicMove(cardIndex, row, col);
        }
      }
    }
    return bestMove;
  }
}
