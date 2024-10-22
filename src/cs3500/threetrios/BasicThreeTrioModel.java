package cs3500.threetrios;

/**
 * First variant model of Three Trio model implementation.
 */
public class BasicThreeTrioModel implements ThreeTriosModel {
  Card[][] grid;
  Player turn;
  Player redPlayer;
  Player bluePlayer;
  boolean gameOver;

  public BasicThreeTrioModel() {

  }


  @Override
  public void startGame() {
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    turn = redPlayer;
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

    Card playingCard = turn.getHand().get(handIdx);
    grid[row][col] = playingCard;
    turn.removeCard(handIdx);
    battleCards(row, col);
    turn = turn == redPlayer ? bluePlayer : redPlayer;
  }

  @Override
  public void battleCards(int row, int col) {

  }

  // we need to init all cards cells to be something other than null bc all cells rn are null
  // and there no way to determine if a cell is a null card cell or a null hole.
  @Override
  public boolean isGameOver() {
    for (Card[] rows : grid) {
      for (Card card : rows) {
        if (card == null) {
          return false;
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

    for (Card[] rows : grid) {
      for (Card card : rows) {
        if (card != null) {
          TeamColor color = card.getColor();
          if (color == TeamColor.RED) {
            redCount++;
          } else if (color == TeamColor.BLUE) {
            blueCount++;
          }
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
