package cs3500.threetrios.model.decorators;

import java.util.List;

import cs3500.threetrios.controller.ModelStatusListener;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * "Pass through" model, where this class simply takes a ThreeTriosModel delegate to call
 * all of its methods for each method. Will be extended by decorated model variants.
 */
public abstract class PassThroughModelDecorator implements ThreeTriosModel {
  private final ThreeTriosModel baseModel;

  /**
   * Constructs this pass through model in terms of the delegate that it will call on all methods.
   *
   * @param baseModel model that handles all implementations.
   */
  public PassThroughModelDecorator(ThreeTriosModel baseModel) {
    this.baseModel = baseModel;
  }

  @Override
  public boolean isGameOver() {
    return this.baseModel.isGameOver();
  }

  @Override
  public Player getWinner() {
    return this.baseModel.getWinner();
  }

  @Override
  public Player getCurrentPlayer() {
    return this.baseModel.getCurrentPlayer();
  }

  @Override
  public Player getRedPlayer() {
    return this.baseModel.getRedPlayer();
  }

  @Override
  public Player getBluePlayer() {
    return this.baseModel.getBluePlayer();
  }

  @Override
  public List<List<ReadOnlyGridCell>> getGridReadOnly() {
    return this.baseModel.getGridReadOnly();
  }

  @Override
  public int numRows() {
    return this.baseModel.numRows();
  }

  @Override
  public int numCols() {
    return this.baseModel.numCols();
  }

  @Override
  public ReadOnlyGridCell getCell(int row, int col) {
    return this.baseModel.getCell(row, col);
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {
    return this.baseModel.getPlayerScore(teamColor);
  }

  @Override
  public int numCardFlips(Card card, int row, int col, Player player) {
    return this.baseModel.numCardFlips(card, row, col, player);
  }

  @Override
  public ReadOnlyThreeTriosModel simulateMove(int row, int col, int handIdx) {
    return this.baseModel.simulateMove(row, col, handIdx);
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    this.baseModel.addModelStatusListener(listener);
  }

  @Override
  public void notifyPlayerTurnChange() {
    this.baseModel.notifyPlayerTurnChange();
  }

  @Override
  public void notifyGameOver() {
    this.baseModel.notifyGameOver();
  }

  @Override
  public void startGame(GridCell[][] grid, List<Card> deck, int numOfCardCells) {
    this.baseModel.startGame(grid, deck, numOfCardCells);
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    this.baseModel.playToGrid(row, col, handIdx);
  }

  @Override
  public void battleCards(int row, int col) {
    this.baseModel.battleCards(row, col);
  }

  /**
   * Determines if the given coordinate is in the range of the models board.
   *
   * @param row given row
   * @param col given col
   * @return true iff this coordinate is in bounds in the grid
   */
  protected boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < baseModel.numRows() && col >= 0 && col < baseModel.numCols();
  }

  /**
   * Flips this cell's adjacent cell's card if they are on the same team.
   *
   * @param row         given row
   * @param col         given col
   * @param dir         direction of the cell to operate on
   * @param playerColor this cells color
   */
  protected void flipAdjacentCardIfMatchesColor(
          int row, int col, Direction dir, TeamColor playerColor) {
    int adjRow = row + Direction.getRowHelper(dir);
    int adjCol = col + Direction.getColHelper(dir);

    if (playerColor == getCell(adjRow, adjCol).getColor()) {
      ((GridCell) getCell(adjRow, adjCol)).toggleColor();
    }
  }
}