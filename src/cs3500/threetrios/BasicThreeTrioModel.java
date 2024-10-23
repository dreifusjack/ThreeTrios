package cs3500.threetrios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * First variant model of Three Trio model implementation.
 */
public class BasicThreeTrioModel implements ThreeTriosModel {
  private GridCell[][] grid;
  private Player playerTurn;
  private final Player redPlayer;
  private final Player bluePlayer;
  private final GridFileReader gridFileReader;
  private final CardFileReader cardFileReader;


  public BasicThreeTrioModel(String gridFileName, String cardFileName) throws IOException {
    gridFileReader = new GridFileReader(gridFileName);
    gridFileReader.readFile();
    cardFileReader = new CardFileReader(cardFileName);
    cardFileReader.readFile();
    grid = null;
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    playerTurn = redPlayer;
  }

//TODO: adding constructors for AI player(s)

  @Override
  public void startGame() {
    if (grid != null) {
      throw new IllegalStateException("Game already started");
    }
    List<Integer> gridCords = gridFileReader.coordinates();
    // init the size of the board
    grid = new GridCell[gridCords.get(0)][gridCords.get(1)];
    // init the cells in the board
    for (int row = 0; row < gridFileReader.getCells().get(0).size(); row++) {
      for (int col = 0; col < gridFileReader.getCells().get(row).size(); col++) {
        grid[row][col] = gridFileReader.getCells().get(row).get(col);
      }
    }

    // init the playing cards
    int minNumOfCardsPerPlayer = (gridFileReader.getNumberOfCardCells() + 1) / 2;
    int numOfCardsPerPlayer = cardFileReader.getCards().size() / 2;
    if (numOfCardsPerPlayer > minNumOfCardsPerPlayer) {
      throw new IllegalArgumentException("not enough cards");
    }
    dealCards(numOfCardsPerPlayer, redPlayer);
    dealCards(numOfCardsPerPlayer, bluePlayer);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    isGameNotInPlay();
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
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);

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

  @Override
  public Player getCurrentPlayer() {
    return this.playerTurn.clone();
  }

  @Override
  public List<List<GridCell>> getGrid() {
    List<List<GridCell>> gridCopy = new ArrayList<>();

    for (GridCell[] gridCells : grid) {
      List<GridCell> rowCopy = new ArrayList<>();
      Collections.addAll(rowCopy, gridCells);
      gridCopy.add(rowCopy);
    }
    return gridCopy;
  }

  /**
   * Deals the given number of cards to the given player's list of cards to play with.
   *
   * @param numOfCardsPerPlayer amount of cards to be dealt.
   * @param player              player receiving cards
   */
  private void dealCards(int numOfCardsPerPlayer, Player player) {
    for (int index = 0; index < numOfCardsPerPlayer; index++) {
      player.addToHand(cardFileReader.getCards().remove(index));
    }
  }


  /**
   * Error checker if the game is not in play for various game functionality methods.
   *
   * @throws IllegalStateException if game is not in play
   */
  private void isGameNotInPlay() {
    if (grid == null || isGameOver()) {
      throw new IllegalStateException("Game not in play");
    }
  }

  /**
   * Battles the user placed card with the given adjacent card at the specified direction if the
   * adjacent card belongs to the opposite team. If placed card wins the battle, adjacent changes
   * team and battles all adjacent cards to it.
   *
   * @param dir
   * @param adjCard
   * @param placedCard
   * @param adjRow
   * @param adjCol
   */
  private void battleHelper(Direction dir, Card adjCard, Card placedCard, int adjRow, int adjCol) {
    if (adjCard.getColor() != placedCard.getColor()) {
      if (placedCard.compare(adjCard, dir)) {
        adjCard.changeColor();
        battleCards(adjRow, adjCol);
      }
    }
  }

  /**
   * Determines if the given row and col are valid indexes of the grid.
   *
   * @param row row input
   * @param col column input
   * @return true iff the coordinates are valid indexes of the grid
   */
  private boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
  }
}

