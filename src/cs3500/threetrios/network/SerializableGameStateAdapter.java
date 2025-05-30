package cs3500.threetrios.network;

import java.util.List;
import java.util.ArrayList;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ThreeTriosPlayer;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.network.messages.SerializableGameState;

/**
 * Adapter that wraps a SerializableGameState to implement
 * ReadOnlyThreeTriosModel.
 * This allows the existing view to work with serialized game state from the
 * network.
 */
public class SerializableGameStateAdapter implements ReadOnlyThreeTriosModel {
  private final SerializableGameState gameState;
  private final List<List<ReadOnlyGridCell>> grid;
  private final Player redPlayer;
  private final Player bluePlayer;
  private final Player currentPlayer;

  /**
   * Creates an adapter for the given serializable game state.
   * 
   * @param gameState the serializable game state to adapt
   */
  public SerializableGameStateAdapter(SerializableGameState gameState) {
    this.gameState = gameState;

    // Convert grid
    this.grid = new ArrayList<>();
    for (List<SerializableGameState.SerializableGridCell> row : gameState.getGrid()) {
      List<ReadOnlyGridCell> adaptedRow = new ArrayList<>();
      for (SerializableGameState.SerializableGridCell cell : row) {
        adaptedRow.add(new GridCellAdapter(cell));
      }
      this.grid.add(adaptedRow);
    }

    // Convert players
    this.redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    for (SerializableGameState.SerializableCard card : gameState.getRedPlayerHand()) {
      this.redPlayer.addToHand(convertCard(card));
    }

    this.bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    for (SerializableGameState.SerializableCard card : gameState.getBluePlayerHand()) {
      this.bluePlayer.addToHand(convertCard(card));
    }

    // Set current player
    this.currentPlayer = gameState.getCurrentPlayerColor() == TeamColor.RED ? redPlayer : bluePlayer;
  }

  private Card convertCard(SerializableGameState.SerializableCard serialCard) {
    return new ThreeTriosCard(
        serialCard.getName(),
        ThreeTriosCard.AttackValue.fromString(serialCard.getNorth()),
        ThreeTriosCard.AttackValue.fromString(serialCard.getSouth()),
        ThreeTriosCard.AttackValue.fromString(serialCard.getEast()),
        ThreeTriosCard.AttackValue.fromString(serialCard.getWest()));
  }

  @Override
  public boolean isGameOver() {
    return gameState.isGameOver();
  }

  @Override
  public Player getWinner() {
    if (!gameState.isGameOver()) {
      throw new IllegalStateException("The game is not over yet");
    }
    TeamColor winnerColor = gameState.getWinnerColor();
    if (winnerColor == null) {
      return null; // tie
    }
    return winnerColor == TeamColor.RED ? redPlayer.clone() : bluePlayer.clone();
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer.clone();
  }

  @Override
  public Player getRedPlayer() {
    return redPlayer.clone();
  }

  @Override
  public Player getBluePlayer() {
    return bluePlayer.clone();
  }

  @Override
  public List<List<ReadOnlyGridCell>> getGridReadOnly() {
    return grid;
  }

  @Override
  public ReadOnlyGridCell getCell(int row, int col) {
    if (row < 0 || row >= gameState.getNumRows() || col < 0 || col >= gameState.getNumCols()) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
    return grid.get(row).get(col);
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {
    if (teamColor == null) {
      throw new IllegalArgumentException("Null team");
    }
    return teamColor == TeamColor.RED ? gameState.getRedScore() : gameState.getBlueScore();
  }

  @Override
  public int numCardFlips(Card card, int row, int col, Player player) {
    // This method is not needed for the view, so we can return 0
    return 0;
  }

  @Override
  public int numRows() {
    return gameState.getNumRows();
  }

  @Override
  public int numCols() {
    return gameState.getNumCols();
  }

  @Override
  public ReadOnlyThreeTriosModel simulateMove(int row, int col, int handIdx) {
    // This method is not needed for the view
    throw new UnsupportedOperationException("Simulate move not supported in network adapter");
  }

  /**
   * Adapter for SerializableGridCell to implement ReadOnlyGridCell.
   */
  private static class GridCellAdapter implements ReadOnlyGridCell {
    private final SerializableGameState.SerializableGridCell cell;

    public GridCellAdapter(SerializableGameState.SerializableGridCell cell) {
      this.cell = cell;
    }

    @Override
    public String toString() {
      switch (cell.getCellType()) {
        case "hole":
          return " ";
        case "empty":
          return "_";
        case "card":
          return cell.getCard() != null ? cell.getCard().toString() : "_";
        default:
          return "_";
      }
    }

    @Override
    public String cardToString() {
      if (cell.getCellType().equals("card") && cell.getCard() != null) {
        return cell.getCard().toString();
      }
      throw new IllegalStateException("No card in this cell");
    }

    @Override
    public TeamColor getColor() {
      if (cell.getCellType().equals("card")) {
        return cell.getColor();
      }
      throw new IllegalStateException("No card in this cell");
    }

    @Override
    public boolean isOccupied() {
      return cell.getCellType().equals("card") || cell.getCellType().equals("hole");
    }

    @Override
    public Card getCardCopy() {
      if (cell.getCellType().equals("card") && cell.getCard() != null) {
        SerializableGameState.SerializableCard serialCard = cell.getCard();
        return new ThreeTriosCard(
            serialCard.getName(),
            ThreeTriosCard.AttackValue.fromString(serialCard.getNorth()),
            ThreeTriosCard.AttackValue.fromString(serialCard.getSouth()),
            ThreeTriosCard.AttackValue.fromString(serialCard.getEast()),
            ThreeTriosCard.AttackValue.fromString(serialCard.getWest()));
      }
      throw new IllegalStateException("No card in this cell");
    }
  }
}
