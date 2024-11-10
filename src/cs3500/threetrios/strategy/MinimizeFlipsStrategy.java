package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class MinimizeFlipsStrategy implements ThreeTriosStrategy {

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    PlayedMove bestMove = null;
    int minFlipability = Integer.MAX_VALUE;

    // get the opponent player
    Player opponent = model.getRedPlayer().getColor().equals(player.getColor()) ?
            model.getBluePlayer() : model.getRedPlayer();

    // loop through each card in the player's hand
    for (int index = 0; index < player.getHand().size(); index++) {
      Card currentCard = player.getHand().get(index);

      // loop through each cell in the grid
      for (int row = 0; row < model.numRows(); row++) {
        for (int col = 0; col < model.numCols(); col++) {
          ReadOnlyGridCell cell = model.getCell(row, col);

          // Skip cells that are not empty
          if (cell.isOccupied()) {
            continue;
          }

          // calculate flipability for this card at this position based on opponent's cards
          int flipability = calculateFlipability(model, currentCard, row, col, opponent);

          // choose the move with the minimum flipability
          if (flipability < minFlipability) {
            minFlipability = flipability;
            bestMove = new BasicMove(index, row, col);
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

  // Private method to calculates the flipability of placing a card at the given position.
  private int calculateFlipability(ReadOnlyThreeTriosModel model, Card card, int row, int col, Player opponent) {
    int flipability = 0;

    // loop through each card in the opponent's hand
    for (Card opponentCard : opponent.getHand()) {
      // loop through each direction to determine if the opponent card could potentially flip the current card
      for (Direction direction : Direction.values()) {
        int adjRow = row + Direction.getRowHelper(direction);
        int adjCol = col + Direction.getColHelper(direction);

        ReadOnlyGridCell cell = model.getCell(adjRow, adjCol);

        // skip cells that are not empty or holds a card already
        if (cell.isOccupied()) {
          continue;
        }

        // check if the adjacent position is within bounds
        if (adjRow >= 0 && adjRow < model.numRows() && adjCol >= 0 && adjCol < model.numCols()) {
          // determine if the opponent's card could flip the player's card
          if (opponentCard.compare(card, direction.getOppositeDirection())) {
            flipability++;
          }
        }
      }
    }

    return flipability;
  }
}