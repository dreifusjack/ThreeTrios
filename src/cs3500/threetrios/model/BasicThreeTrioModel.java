package cs3500.threetrios.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.model.fileIO.CardFileReader;
import cs3500.threetrios.model.fileIO.GridFileReader;

/**
 * First variant model of Three Trio model implementation. Implementation of the behaviors
 * of the ThreeTriosModel interface.
 */
public class BasicThreeTrioModel implements ThreeTriosModel {
  private GridCell[][] grid;
  //CLASS INVARIANT: playerTurn is one of redPlayer or bluePlayer (more details in README)
  private final Player redPlayer;
  private Player playerTurn;
  private final Player bluePlayer;
  private final GridFileReader gridFileReader;
  private final CardFileReader cardFileReader;
  private final Random random;

  /**
   * Constructs a BasicThreeTrioModel in terms of the names of the grid file and card file
   * it will read from to instantiate the game. We are assumed we cannot construct/start a game
   * without configuration files to build the game off of.
   *
   * @param gridFileName name of the file with grid construction
   * @param cardFileName name of the file with card construction
   * @throws IllegalArgumentException cannot find a file for either file name
   */
  public BasicThreeTrioModel(String gridFileName, String cardFileName) {
    gridFileReader = new GridFileReader(gridFileName);
    cardFileReader = new CardFileReader(cardFileName);
    grid = null;
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    playerTurn = redPlayer;
    random = new Random();
  }

  public BasicThreeTrioModel(String gridFileName, String cardFileName, Random rand) {
    gridFileReader = new GridFileReader(gridFileName);
    cardFileReader = new CardFileReader(cardFileName);
    grid = null;
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    playerTurn = redPlayer;
    random = rand;
  }

//TODO: adding constructors for AI player(s)

  @Override
  public void startGame() throws IOException {
    if (grid != null) {
      throw new IllegalStateException("Game already started");
    }
    // readers gather data from files (assuming throws exceptions if necessary)
    gridFileReader.readFile();
    cardFileReader.readFile();
    // init the grid
    List<Integer> gridCords = gridFileReader.specifiedSizes();
    grid = new GridCell[gridCords.get(0)][gridCords.get(1)];
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        grid[row][col] = gridFileReader.getGrid().get(row).get(col);
      }
    }
    // init hands for each player
    int minNumOfCards = gridFileReader.getNumberOfCardCells() + 1;
    int numOfCards = cardFileReader.getCards().size();
    if (numOfCards < minNumOfCards) {
      throw new IllegalArgumentException("Not enough playing cards");
    }

    // Shuffle the deck using the Random instance
    List<Card> deck = new ArrayList<>(cardFileReader.getCards());
    Collections.shuffle(deck, random);

    dealCards(minNumOfCards / 2, redPlayer, TeamColor.RED, deck);
    dealCards(minNumOfCards / 2, bluePlayer, TeamColor.BLUE, deck);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    isGameNotInPlay();
    if (playerTurn.getHand().size() <= handIdx || handIdx < 0) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }
    if (!isValidCoordinate(row, col)) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
    Card playingCard = playerTurn.getHand().get(handIdx);
    grid[row][col].addCard(playingCard); // assuming throws exceptions if cell is hole or occupied
    playerTurn.removeCard(handIdx);
    battleCards(row, col);
    if (!isGameOver()) {
      playerTurn = playerTurn == redPlayer ? bluePlayer : redPlayer;
    }
  }


  @Override
  public void battleCards(int row, int col) {
    isGameNotStarted();
    if (!isValidCoordinate(row, col)) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
    Card placedCard = grid[row][col].getCard();
    if (placedCard == null) {
      throw new IllegalStateException("Empty card cell tried to battle");
    }

    for (Direction dir : Direction.values()) {
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);

      if (isValidCoordinate(adjRow, adjCol)) {
        try {
          Card adjCard = grid[adjRow][adjCol].getCard();
          if (adjCard != null) {
            battleHelper(dir, adjCard, placedCard, adjRow, adjCol);
          }
        } catch (IllegalStateException ignored) { // case where cell is a hole
        }
      }
    }
  }

  @Override
  public boolean isGameOver() {
    isGameNotStarted();

    for (GridCell[] row : grid) {
      for (GridCell cell : row) {
        try {
          if (cell.getCard() == null) {
            return false;
          }
        } catch (IllegalStateException ignored) { // hole cell case
        }
      }
    }

    return true;
  }


  @Override
  public Player getWinner() {
    isGameNotStarted();
    if (!isGameOver()) {
      throw new IllegalStateException("The game is not over yet");
    }

    int[] counts = countPlacedCards();
    int redCount = counts[0] + redPlayer.getHand().size();
    int blueCount = counts[1] + bluePlayer.getHand().size();

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
    isGameNotStarted();
    return this.playerTurn.clone();
  }

  @Override
  public List<List<GridCell>> getGrid() {
    isGameNotStarted();
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
  private void dealCards(int numOfCardsPerPlayer, Player player, TeamColor color, List<Card> deck) {
    for (int i = 0; i < numOfCardsPerPlayer; i++) {
      Card card = deck.remove(0);
      card.setColor(color);
      player.addToHand(card);
    }
  }

  /**
   * Error checker if the game is not in play for various game functionality methods.
   *
   * @throws IllegalStateException if game is not in play
   */
  private void isGameNotInPlay() {
    if (grid == null || isGameOver()) {
      throw new IllegalStateException("Game not in play or game has ended");
    }
  }

  /**
   * Error check if the game is not started for various game functionality methods.
   *
   * @throws IllegalStateException if startGame has not been called on this game
   */
  private void isGameNotStarted() {
    if (grid == null) {
      throw new IllegalStateException("The game has not been started");
    }
  }

  /**
   * Iterates over the grid and adds placed cards to the corresponding given counter.
   */
  private int[] countPlacedCards() { //// I fixed this one too as it messed up the return redCount and blueCount
    int redCount = 0;
    int blueCount = 0;

    for (GridCell[] rows : grid) {
      for (GridCell cell : rows) {
        try {
          Card currentCard = cell.getCard();
          if (currentCard != null) {
            if (currentCard.getColor() == TeamColor.RED) {
              redCount++;
            } else if (currentCard.getColor() == TeamColor.BLUE) {
              blueCount++;
            }
          }
        } catch (IllegalStateException ignored) {
        }
      }
    }
    return new int[]{redCount, blueCount};
  }

  /**
   * Battles the user placed card with the given adjacent card at the specified direction if the
   * adjacent card belongs to the opposite team. If placed card wins the battle, adjacent changes
   * team and battles all adjacent cards to it.
   *
   * @param dir        direction the placed card is battling from
   * @param adjCard    card the placed card is battling against
   * @param battleCard card to battle with
   * @param adjRow     row of the card being battled against
   * @param adjCol     column of the card being battled against
   */
  private void battleHelper(Direction dir, Card adjCard, Card battleCard, int adjRow, int adjCol) {
    if (battleCard.compare(adjCard, dir)) {
      adjCard.toggleColor();
      battleCards(adjRow, adjCol);
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

