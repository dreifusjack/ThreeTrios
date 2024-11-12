package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represent the MinimizeFlipsStrategy which is one of the ThreeTriosStrategy to pick the best
 * move to play the ThreeTrios game. This strategy will rely on the concept of choosing a card
 * that "are less likely to be flipped in general." A card that is "are less likely to be
 * flipped in general" is a card that when being played to the grid, it will have the lowest
 * amount of opponent cards that can flip it (play to its sides and flip it to be more specific).
 * This ThreeTriosStrategy will loop through each card in the current player's hand, and for each
 * card, we will loop through each cell in the grid, and for each cell we will do
 * calculateFlipability with this card, the coordinate of this cell and the opponent's hand. The
 * calculateFlipability will then loop through each card in the opponent's hand, and for each card
 * it will try to perform compare method with this opponent's card to each of the sides of the card
 * and coordinate of the current player is choosing. Again, if there is a tie with many best move
 * then break the tie by choosing the move with the uppermost-leftmost coordinate for the position
 * and then choose the best card for that position with an index closest to 0 in the hand. If there
 * are no valid moves, your player should pass choose the upper-most, left-most open position and
 * the card at index 0.
 */
public class MinimizeFlipsStrategy implements ThreeTriosStrategy {

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    PlayedMove bestMove = this.findBestMoveForChain(model, player);

    if (bestMove != null) {
      return bestMove;
    } else {
      return handleNullMove(model, player, bestMove);
    }
  }

  private static BasicMove handleNullMove(ReadOnlyThreeTriosModel model, Player player, PlayedMove bestMove) {
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
    int minFlipability = Integer.MAX_VALUE;

    // get the opponent player
    Player opponent = model.getRedPlayer().getColor().equals(player.getColor())
            ? model.getBluePlayer() : model.getRedPlayer();

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
          if (flipability < minFlipability || (flipability == minFlipability && (bestMove
                  == null || row < bestMove.getRow() || (row == bestMove.getRow() && col
                  < bestMove.getCol())))) {
            minFlipability = flipability;
            bestMove = new BasicMove(index, row, col);
          }
        }
      }
    }
    return bestMove;
  }

  // Private method to calculates the flipability of placing a card at the given position.
  private int calculateFlipability(ReadOnlyThreeTriosModel model, Card card, int row,
                                   int col, Player opponent) {
    int flipability = 0;

    // loop through each card in the opponent's hand
    for (Card opponentCard : opponent.getHand()) {
      // loop through each direction to determine if the opponent card could
      // potentially flip the current card
      for (Direction direction : Direction.values()) {
        int adjRow = row + Direction.getRowHelper(direction);
        int adjCol = col + Direction.getColHelper(direction);

        // check if the adjacent position is within bounds
        if (!(adjRow >= 0 && adjRow < model.numRows() && adjCol >= 0 && adjCol < model.numCols())) {
          continue;
        }

        ReadOnlyGridCell cell = model.getCell(adjRow, adjCol);

        // skip cells that are not empty or holds a card already
        if (cell.isOccupied()) {
          continue;
        }

        // determine if the opponent's card could flip the player's card
        if (opponentCard.compare(card, direction.getOppositeDirection())) {
          flipability++;
        }
      }
    }

    return flipability;
  }
}