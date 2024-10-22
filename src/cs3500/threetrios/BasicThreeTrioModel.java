package cs3500.threetrios;

import java.io.FileReader;
import java.util.List;

/**
 * First variant model of Three Trio model implementation.
 */
public class BasicThreeTrioModel implements ThreeTriosModel {
  private GridCell[][] grid;
  private Player playerTurn;
  private final Player redPlayer;
  private final  Player bluePlayer;
  private final GridFileReader gridFileReader;
  private final CardFileReader cardFileReader;


  public BasicThreeTrioModel(String gridFileName, String cardFileName) {
    gridFileReader = new GridFileReader(gridFileName);
    cardFileReader = new CardFileReader(cardFileName);
    grid = null;
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    playerTurn = redPlayer;
  }


  @Override
  public void startGame() {
    List<Integer> gridCords = gridFileReader.gridCords;
    // init the size of the board
    grid = new GridCell[gridCords.get(0)][gridCords.get(1)];
    // init the cells in the board
    for (int row = 0; row < gridFileReader.gridCells.get(0).size(); row++) {
      for (int col = 0; col < gridFileReader.gridCells.get(row).size(); col++) {
        grid[row][col] = gridFileReader.gridCells.get(row).get(col);
      }
    }

    // init the playing cards
    int minNumOfCardsPerPlayer = (gridFileReader.numOfCardCells + 1) / 2;
    int numOfCardsPerPlayer = cardFileReader.cards.size() / 2;
    if (numOfCardsPerPlayer > minNumOfCardsPerPlayer) {
      throw new IllegalArgumentException("not enough cards");
    }
    for (int index = 0; index < numOfCardsPerPlayer; index++) {
      redPlayer.addToHand(cardFileReader.cards.remove(index));
    }
    for (int index = 0; index < numOfCardsPerPlayer; index++) {
      bluePlayer.addToHand(cardFileReader.cards.remove(index));
    }
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over");
    }

    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
      throw new IllegalArgumentException("Invalid inputs for row and col");
    }

    // get the card played from player
    Card playingCard = playerTurn.getHand().get(handIdx);
    // add card to grid
    grid[row][col].addCard(playingCard);
    // remove card from player hand
    playerTurn.removeCard(handIdx);
    // battle cards at posn of new card
    battleCards(row, col);
    // update player turn
    playerTurn = playerTurn == redPlayer ? bluePlayer : redPlayer;
  }

  @Override
  public void battleCards(int row, int col) {
    Card placedCard = grid[row][col].getCard();

    for (Direction dir : Direction.values()) {
      int adjRow = row + getRowHelper(dir);
      int adjCol = col + getColHelper(dir);

      if (isValidCoordinate(adjRow, adjCol)) {
        try {
          Card adjCard = grid[adjRow][adjCol].getCard();
          if (adjCard != null) {
            battleHelper(dir, adjCard, placedCard, adjRow, adjCol);
          }
        } catch (IllegalStateException ignored) {
        }
      }
    }
  }

  private void battleHelper(Direction dir, Card adjCard, Card placedCard, int adjRow, int adjCol) {
    if (adjCard.getColor() != placedCard.getColor()) {
      if (placedCard.compare(adjCard, dir)) {
        adjCard.changeColor();
        battleCards(adjRow, adjCol);
      }
    }
  }

  private boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
  }

  // we need to init all cards cells to be something other than null bc all cells rn are null
  // and there no way to determine if a cell is a null card cell or a null hole.
  @Override
  public boolean isGameOver() {
    for (GridCell[] row : grid) {
      for (GridCell cell : row) {
        try {
          if (cell.getCard() == null) {
            return false;
          }
        } catch (IllegalStateException ignored) { // case where cell is a hole
        }
      }
    }
    return redPlayer.getHand().isEmpty() || bluePlayer.getHand().isEmpty();
  }

  @Override
  public Player getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("The game is not over yet");
    }

    int redCount = 0;
    int blueCount = 0;

    for (GridCell[] rows : grid) {
      for (GridCell cell : rows) {
        try {
          Card currentCard = cell.getCard();
          if (currentCard.getColor() == TeamColor.RED) {
            redCount++;
          } else if (currentCard.getColor() == TeamColor.BLUE) {
            blueCount++;
          }
        } catch (IllegalStateException ignored) { // case where cell is null
        }
      }
    }
    redCount += redPlayer.getHand().size();
    blueCount += bluePlayer.getHand().size();

    if (redCount > blueCount) {
      return redPlayer;
    }
    if (blueCount > redCount) {
      return bluePlayer;
    }
    return null; // representing a tie
  }


  private int getRowHelper(Direction dir) {
    switch (dir) {
      case NORTH:
        return -1;
      case SOUTH:
        return 1;
      default:
        return 0;
    }
  }

  private int getColHelper(Direction dir) {
    switch (dir) {
      case EAST:
        return 1;
      case WEST:
        return -1;
      default:
        return 0;
    }
  }
}

