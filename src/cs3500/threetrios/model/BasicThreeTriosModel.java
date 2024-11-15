package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * First variant model of Three Trio model implementation. Implementation of the behaviors
 * of the ThreeTriosModel interface.
 */

public class BasicThreeTriosModel implements ThreeTriosModel {
  private GridCell[][] grid; // 0 index grid
  //CLASS INVARIANT: playerTurn is one of redPlayer or bluePlayer (more details in README)
  private final Player redPlayer;
  private Player playerTurn;
  private final Player bluePlayer;
  private final Random random;
  private final List<ModelStatusFeatures> statusListeners;

  /**
   * Constructs a BasicThreeTrioModel in terms of the names of the grid file and card file
   * it will read from to instantiate the game. We are assumed we cannot construct/start a game
   * without configuration files to build the game off of.
   *
   * @throws IllegalArgumentException cannot find a file for either file name
   * @throws IllegalArgumentException if gridFileName or cardFileName are null
   */
  public BasicThreeTriosModel() {
    // Assuming both file readers ensure given names are not null, and are found in their
    // corresponding config file locations.
    grid = null;
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    playerTurn = redPlayer;
    random = new Random();
    statusListeners = new ArrayList<>();
  }

  /**
   * Constructs a BasicThreeTrioModel that same as before but takes a Random object to seed
   * randomness for testing purposes.
   *
   * @param rand         seed for testing
   * @throws IllegalArgumentException cannot find a file for either file name
   * @throws IllegalArgumentException if gridFileName or cardFileName are null
   */
  public BasicThreeTriosModel(Random rand) {
    grid = null;
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    playerTurn = redPlayer;
    random = rand;
    statusListeners = new ArrayList<>();
  }


  /**
   * Construct a BasicThreeTriosModel given a ReadOnlyThreeTriosModel.
   *
   * @param readOnlyModel is a ReadOnlyThreeTriosModel.
   */
  public BasicThreeTriosModel(ReadOnlyThreeTriosModel readOnlyModel) {
    this.grid = new GridCell[readOnlyModel.numRows()][readOnlyModel.numCols()];
    this.redPlayer = readOnlyModel.getRedPlayer();
    this.bluePlayer = readOnlyModel.getBluePlayer();
    this.playerTurn = readOnlyModel.getCurrentPlayer().getColor()
            == TeamColor.RED ? redPlayer : bluePlayer;

    // making the grid
    for (int row = 0; row < readOnlyModel.numRows(); row++) {
      for (int col = 0; col < readOnlyModel.numCols(); col++) {
        ReadOnlyGridCell readOnlyCell = readOnlyModel.getCell(row, col);
        this.grid[row][col] = convertReadOnlyToGridCell(readOnlyCell);
      }
    }
    this.random = new Random();
    statusListeners = new ArrayList<>();
  }

  // private method for converting ReadOnlyGridCell to GridCell
  private GridCell convertReadOnlyToGridCell(ReadOnlyGridCell readOnlyCell) {
    switch (readOnlyCell.toString()) {
      case " ":
        return new Hole();
      case "_":
        return new CardCell();
      default:
        String[] partsForCardShape = readOnlyCell.cardToString().split(" ");
        if (partsForCardShape.length == 5) {
          Card card = new ThreeTrioCard(
                  partsForCardShape[0],
                  ThreeTrioCard.AttackValue.fromString(partsForCardShape[1]),
                  ThreeTrioCard.AttackValue.fromString(partsForCardShape[2]),
                  ThreeTrioCard.AttackValue.fromString(partsForCardShape[3]),
                  ThreeTrioCard.AttackValue.fromString(partsForCardShape[4])
          );
          return new CardCell(card, readOnlyCell.getColor());
        }
    }
    throw new IllegalArgumentException("Invalid ReadOnlyGridCell data");
  }


  @Override
  public void startGame(GridCell[][] grid, List<Card> deck, int numOfCardCells) {
    if (this.grid != null) {
      throw new IllegalStateException("Game already started");
    }

    // init the grid
    this.grid = grid;

    // init hands for each player
    int minNumOfCards = numOfCardCells + 1; // N+1
    int numOfCards = deck.size();
    if (numOfCards < minNumOfCards) {
      throw new IllegalArgumentException("Not enough playing cards");
    }

    // Shuffle the deck using the Random instance
    Collections.shuffle(deck, random);
    dealCards(minNumOfCards / 2, redPlayer, TeamColor.RED, deck); // (N+1)/2
    dealCards(minNumOfCards / 2, bluePlayer, TeamColor.BLUE, deck); // (N+1)/2
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
    grid[row][col].addCard(playingCard);
    // Assuming addCard throws exceptions if GridCell is hole or occupied card cell
    // Update played to cell but setting its color to make the current player's TeamColor.
    grid[row][col].setColor(playerTurn.getColor());
    playerTurn.removeCard(handIdx);
    // Assuming removeCard throws exceptions if index out of bounds
    battleCards(row, col);
    if (!isGameOver()) {
      playerTurn = playerTurn == redPlayer ? bluePlayer : redPlayer;
      notifyPlayerTurnChange(playerTurn);
    }
  }

  @Override
  public void battleCards(int row, int col) {
    isGameNotStarted();
    if (!isValidCoordinate(row, col)) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
    Card placedCard = grid[row][col].getCard();
    GridCell placedCell = grid[row][col];
    // Assuming getCard throws exception if cell is a hole
    if (placedCard == null) {
      throw new IllegalStateException("Empty card cell tried to battle");
    }

    for (Direction dir : Direction.values()) {
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);

      if (isValidCoordinate(adjRow, adjCol)) {
        try {
          Card adjCard = grid[adjRow][adjCol].getCard();
          GridCell adjCell = grid[adjRow][adjCol];
          if (adjCard != null) {
            battleHelper(dir, adjCell, placedCell, adjRow, adjCol);
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
        } catch (IllegalStateException ignored) { // case where cell is a hole
        }
      }
    }
    notifyGameOver(getWinner());
    return true;
  }

  @Override
  public Player getWinner() {
    isGameNotStarted();
    if (!isGameOver()) {
      throw new IllegalStateException("The game is not over yet");
    }

    int redCount = getPlayerScore(TeamColor.RED);
    int blueCount = getPlayerScore(TeamColor.BLUE);

    if (redCount > blueCount) {
      return redPlayer.clone(); // Assuming .clone returns a copy of the player
    }
    if (blueCount > redCount) {
      return bluePlayer.clone(); // Assuming .clone returns a copy of the player
    }
    return null; // representing a tie
  }

  @Override
  public Player getCurrentPlayer() {
    isGameNotStarted();
    return playerTurn.clone(); // Assuming .clone returns a copy of the player
  }

  @Override
  public Player getRedPlayer() {
    isGameNotStarted();
    return redPlayer.clone(); // Assuming .clone returns a copy of the player
  }

  @Override
  public Player getBluePlayer() {
    isGameNotStarted();
    return bluePlayer.clone(); // Assuming .clone returns a copy of the player
  }


  @Override
  public List<List<ReadOnlyGridCell>> getGridReadOnly() {
    isGameNotStarted();
    List<List<ReadOnlyGridCell>> gridCopy = new ArrayList<>();

    for (GridCell[] gridCells : grid) {
      List<ReadOnlyGridCell> rowCopy = new ArrayList<>();
      Collections.addAll(rowCopy, gridCells);
      gridCopy.add(rowCopy);
    }
    return gridCopy;
  }

  @Override
  public int numRows() {
    isGameNotStarted();
    return grid.length;
  }

  @Override
  public int numCols() {
    isGameNotStarted();
    return grid[0].length;
  }

  @Override
  public ReadOnlyGridCell getCell(int row, int col) {
    isGameNotStarted();
    if (!isValidCoordinate(row, col)) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
    return grid[row][col];
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {
    isGameNotStarted();
    if (teamColor == null) {
      throw new IllegalArgumentException("Null team");
    }
    int score = 0;
    Player player;
    player = (teamColor == TeamColor.RED) ? redPlayer : bluePlayer;
    for (GridCell[] row : grid) {
      for (GridCell cell : row) {
        try {
          Card currentCard = cell.getCard();
          if (currentCard != null) {
            if (cell.getColor() == teamColor) {
              score++;
            }
          }
        } catch (IllegalStateException ignored) { // case where cell is a hole
        }
      }
    }
    score += player.getHand().size();
    return score;
  }

  @Override
  public int numCardFlips(Card card, int row, int col, Player player) {
    isGameNotStarted();
    if (!isValidCoordinate(row, col)) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
    if (card == null || player == null) {
      throw new IllegalArgumentException("Null card or player");
    }
    try {
      grid[row][col].getCard();
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("Coordinates map to hole");
    }
    int flips = 0;
    for (Direction dir : Direction.values()) {
      int adjRow = row + Direction.getRowHelper(dir);
      int adjCol = col + Direction.getColHelper(dir);

      if (isValidCoordinate(adjRow, adjCol)) {
        try {
          Card adjCard = grid[adjRow][adjCol].getCard();
          GridCell adjCell = grid[adjRow][adjCol];
          if (adjCard != null) {
            flips += flipCounterHelper(dir, adjCell, card, adjRow, adjCol, flips, player);
          }
        } catch (IllegalStateException ignored) { // case where cell is a hole
        }
      }
    }
    return flips;
  }

  /**
   * Compares the given two cards at the given direction, if card beats adjCard, then increments
   * given flipsSoFar counter, and calls numCardFlips on the adjacent card and its coordinates.
   * This method will recursively add the number of flips the given card would have after battle
   * with the adjacent card and potentially starting a chain reaction (dfs).
   *
   * @param dir        direction of battle
   * @param adjCell    adjacent card to compared with
   * @param card       card that is comparing to adjacent card
   * @param adjRow     row of the adjacent card
   * @param adjCol     column of the adjacent card
   * @param flipsSoFar counter for the flips so far
   * @return number of flips the given card has after comparing with the adjacent card
   */
  private int flipCounterHelper(Direction dir, GridCell adjCell, Card card,
                                int adjRow, int adjCol, int flipsSoFar, Player player) {
    if (adjCell.getColor() != player.getColor()) {
      Card adjCard = grid[adjRow][adjCol].getCard();
      if (card.compare(adjCard, dir)) {
        flipsSoFar++;
        flipsSoFar += numCardFlips(adjCard, adjRow, adjCol, player);
        return flipsSoFar;
      }
    }
    return 0;
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
   * Battles the user placed card with the given adjacent card at the specified direction if the
   * adjacent card belongs to the opposite team. If placed card wins the battle, adjacent changes
   * team and battles all adjacent cards to it.
   *
   * @param dir        direction the placed card is battling from
   * @param adjCell    card the placed card is battling against
   * @param battleCell card to battle with
   * @param adjRow     row of the card being battled against
   * @param adjCol     column of the card being battled against
   */
  private void battleHelper(
          Direction dir, GridCell adjCell, GridCell battleCell, int adjRow, int adjCol) {
    if (adjCell.getColor() != battleCell.getColor()) {
      Card battleCard = battleCell.getCard();
      Card adjCard = adjCell.getCard();
      if (battleCard.compare(adjCard, dir)) {
        adjCell.toggleColor();
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

  @Override
  public ReadOnlyThreeTriosModel simulateMove(int row, int col, int handIdx) {
    isGameNotInPlay();
    if (playerTurn.getHand().size() <= handIdx || handIdx < 0) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }
    if (!isValidCoordinate(row, col)) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
    // make a copy
    ThreeTriosModel clonedModel = new BasicThreeTriosModel(this);

    // simulate the move on the copied version.
    clonedModel.playToGrid(row, col, handIdx);

    // return a read-only version.
    return clonedModel;
  }

  @Override
  public void addModelStatusListener(ModelStatusFeatures listener) {
    statusListeners.add(listener);
  }

  @Override
  public void notifyPlayerTurnChange(Player currentPlayer) {
    for (ModelStatusFeatures listener : statusListeners) {
      listener.onPlayerTurnChange(currentPlayer);
    }
  }

  @Override
  public void notifyGameOver(Player winningPlayer) {
    for (ModelStatusFeatures listener : statusListeners) {
      listener.onGameOver(winningPlayer);
    }
  }
}

