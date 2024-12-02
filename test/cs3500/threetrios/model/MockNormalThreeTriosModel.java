package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.ModelStatusListener;

/**
 * Mock class for a BasicThreeTriosModel.
 */
public class MockNormalThreeTriosModel extends BasicThreeTriosModel {

  private final List<String> log;

  public MockNormalThreeTriosModel(Random random) {
    super(random);
    this.log = new ArrayList<>();
  }

  @Override
  public void startGame(GridCell[][] grid, List<Card> deck, int numOfCardCells) {
    log.add("startGame called");
    super.startGame(grid, deck, numOfCardCells);
  }

  @Override
  public void battleCards(int row, int col) {
    log.add("battleCards called");
    super.battleCards(row, col);
  }

  @Override
  public boolean isGameOver() {
    log.add("isGameOver called");
    return super.isGameOver();
  }

  @Override
  public Player getWinner() {
    log.add("getWinner called");
    return super.getWinner();
  }

  @Override
  public Player getCurrentPlayer() {
    log.add("getCurrentPlayer called");
    return super.getCurrentPlayer();
  }

  @Override
  public Player getRedPlayer() {
    log.add("getRedPlayer called");
    return super.getRedPlayer();
  }

  @Override
  public Player getBluePlayer() {
    log.add("getBluePlayer called");
    return super.getBluePlayer();
  }


  @Override
  public List<List<ReadOnlyGridCell>> getGridReadOnly() {
    log.add("getGridReadOnly called");
    return super.getGridReadOnly();
  }

  @Override
  public int numRows() {
    log.add("numRows called");
    return super.numRows();
  }

  @Override
  public int numCols() {
    log.add("numCols called");
    return super.numCols();
  }

  @Override
  public ReadOnlyGridCell getCell(int row, int col) {
    log.add("getCell called");
    return super.getCell(row, col);
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {
    log.add("getPlayerScore called");
    return super.getPlayerScore(teamColor);
  }

  @Override
  public int numCardFlips(Card card, int row, int col, Player player) {
    log.add("numCardFlips called");
    return super.numCardFlips(card, row, col, player);
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
  protected int flipCounterHelper(Direction dir, GridCell adjCell, Card card,
                                  int adjRow, int adjCol, int flipsSoFar, Player player) {
    log.add("flipCounterHelper called");
    return super.flipCounterHelper(dir, adjCell, card, adjRow, adjCol, flipsSoFar, player);
  }

  /**
   * Deals the given number of cards to the given player's list of cards to play with.
   *
   * @param numOfCardsPerPlayer amount of cards to be dealt.
   * @param player              player receiving cards
   */
  protected void dealCards(
          int numOfCardsPerPlayer, Player player, TeamColor color, List<Card> deck) {
    log.add("dealCards called");
    super.dealCards(numOfCardsPerPlayer, player, color, deck);
  }

  /**
   * Error checker if the game is not in play for various game functionality methods.
   *
   * @throws IllegalStateException if game is not in play
   */
  protected void isGameNotInPlay() {
    log.add("isGameNotInPlay called");
    super.isGameNotInPlay();
  }

  /**
   * Error check if the game is not started for various game functionality methods.
   *
   * @throws IllegalStateException if startGame has not been called on this game
   */
  protected void isGameNotStarted() {
    log.add("isGameNotStarted called");
    super.isGameNotStarted();
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
  protected void battleHelper(
          Direction dir, GridCell adjCell, GridCell battleCell, int adjRow, int adjCol) {
    log.add("battleHelper called");
    super.battleHelper(dir, adjCell, battleCell, adjRow, adjCol);
  }

  /**
   * Determines if the given row and col are valid indexes of the grid.
   *
   * @param row row input
   * @param col column input
   * @return true iff the coordinates are valid indexes of the grid
   */
  protected boolean isValidCoordinate(int row, int col) {
    log.add("isValidCoordinate called");
    return super.isValidCoordinate(row, col);
  }

  @Override
  public ReadOnlyThreeTriosModel simulateMove(int row, int col, int handIdx) {
    log.add("simulateMove called");
    return super.simulateMove(row, col, handIdx);
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    log.add("addModelStatusListener called");
    super.addModelStatusListener(listener);
  }

  @Override
  public void notifyPlayerTurnChange() {
    log.add("notifyPlayerTurnChange called");
    super.notifyPlayerTurnChange();
  }

  @Override
  public void notifyGameOver() {
    log.add("notifyGameOver called");
    super.notifyGameOver();
  }

  /**
   * To get the mock log.
   *
   * @return Mock log.
   */
  public List<String> getLog() {
    return this.log;
  }
}
