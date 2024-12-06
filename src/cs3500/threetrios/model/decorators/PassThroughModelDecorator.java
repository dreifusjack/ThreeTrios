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

public abstract class PassThroughModelDecorator implements ThreeTriosModel {
  private final ThreeTriosModel baseModel;

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

  protected boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < baseModel.numRows() && col >= 0 && col < baseModel.numCols();
  }

  protected void flipAdjacentCardIfMatchesColor(
          int row, int col, Direction dir, TeamColor playerColor) {
    int adjRow = row + Direction.getRowHelper(dir);
    int adjCol = col + Direction.getColHelper(dir);

    if (playerColor == getCell(adjRow, adjCol).getColor()) {
      ((GridCell) getCell(adjRow, adjCol)).toggleColor();
    }
  }
}