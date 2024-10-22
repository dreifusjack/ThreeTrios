package cs3500.threetrios;

/**
 * First variant model of Three Trio model implementation.
 */
public class BasicThreeTrioModel implements ThreeTriosModel {
  GridCell[][] grid;
  Player playerTurn;
  Player redPlayer;
  Player bluePlayer;
  boolean gameOver;

  public BasicThreeTrioModel() {

  }


  @Override
  public void startGame() {
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    playerTurn = redPlayer;
    // We will then read from the files and replace this files with our "grid" (we need to decide if we should initialize this in the constructor of the startGame().
    //+first line will take the row and col and the rest of the lines will be the cards in the grid (then pass the row + col + cards after we read to a createGrid to make a grid for our model)
    // **After thinking about it, I think we should initialize everything in the constructor as we have the isGameOver, so we should initialize the fields so that this method can be used.
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
}
