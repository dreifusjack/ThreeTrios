package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.IconView;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class CornerStrategy implements ThreeTriosStrategy {

  @Override
  public BasicMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
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
      if (!cell.isEmpty()) {
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


    // if no valid move was found, pick the uppermost-leftmost position
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

    return bestMove;
  }

  /**
   * Helper method to calculate the total value of a card.
   *
   * @param card  is the card to want to calculate.
   * @param model is the readonly version of the model.
   * @param row   is the row of this card.
   * @param col   is the column of this card.
   * @return the total of 2 sides of this card.
   */
  private int calculateSumValueForCorner(Card card, ReadOnlyThreeTriosModel model, int row, int col) {
    int sum = 0;

    // top-left
    if (row == 0 && col == 0) {
      sum += (model.getCell(row, col + 1).toString().equals(" ")) ? 0 : card.getEast().getValue();
      sum += (model.getCell(row + 1, col).toString().equals(" ")) ? 0 : card.getSouth().getValue();
    }
    // top-right
    else if (row == 0 && col == model.numCols() - 1) {
      sum += (model.getCell(row, col - 1).toString().equals(" ")) ? 0 : card.getWest().getValue();
      sum += (model.getCell(row + 1, col).toString().equals(" ")) ? 0 : card.getSouth().getValue();
    }
    // bottom-left
    else if (row == model.numRows() - 1 && col == 0) {
      sum += (model.getCell(row, col + 1).toString().equals(" ")) ? 0 : card.getEast().getValue();
      sum += (model.getCell(row - 1, col).toString().equals(" ")) ? 0 : card.getNorth().getValue();
    }
    // bottom-right
    else if (row == model.numRows() - 1 && col == model.numCols() - 1) {
      sum += (model.getCell(row, col - 1).toString().equals(" ")) ? 0 : card.getWest().getValue();
      sum += (model.getCell(row - 1, col).toString().equals(" ")) ? 0 : card.getNorth().getValue();
    }

    return sum;
  }

}
