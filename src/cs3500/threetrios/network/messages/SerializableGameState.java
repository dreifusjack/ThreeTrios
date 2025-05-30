package cs3500.threetrios.network.messages;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.Card;

/**
 * A serializable representation of the game state that can be sent over the
 * network.
 * This class extracts the essential game state information from a
 * ReadOnlyThreeTriosModel
 * and packages it in a serializable format.
 */
public class SerializableGameState implements Serializable {
  private static final long serialVersionUID = 1L;

  private final List<List<SerializableGridCell>> grid;
  private final List<SerializableCard> redPlayerHand;
  private final List<SerializableCard> bluePlayerHand;
  private final TeamColor currentPlayerColor;
  private final boolean gameOver;
  private final TeamColor winnerColor; // null if tie or game not over
  private final int redScore;
  private final int blueScore;
  private final int numRows;
  private final int numCols;

  /**
   * Creates a serializable game state from a ReadOnlyThreeTriosModel.
   * 
   * @param model the model to extract state from
   */
  public SerializableGameState(ReadOnlyThreeTriosModel model) {
    this.numRows = model.numRows();
    this.numCols = model.numCols();

    // Extract grid
    this.grid = new ArrayList<>();
    List<List<ReadOnlyGridCell>> modelGrid = model.getGridReadOnly();
    for (List<ReadOnlyGridCell> row : modelGrid) {
      List<SerializableGridCell> serialRow = new ArrayList<>();
      for (ReadOnlyGridCell cell : row) {
        serialRow.add(new SerializableGridCell(cell));
      }
      this.grid.add(serialRow);
    }

    // Extract player hands
    this.redPlayerHand = new ArrayList<>();
    for (Card card : model.getRedPlayer().getHand()) {
      this.redPlayerHand.add(new SerializableCard(card));
    }

    this.bluePlayerHand = new ArrayList<>();
    for (Card card : model.getBluePlayer().getHand()) {
      this.bluePlayerHand.add(new SerializableCard(card));
    }

    // Extract game state
    this.currentPlayerColor = model.getCurrentPlayer().getColor();
    this.gameOver = model.isGameOver();

    if (gameOver) {
      Player winner = model.getWinner();
      this.winnerColor = winner != null ? winner.getColor() : null;
    } else {
      this.winnerColor = null;
    }

    this.redScore = model.getPlayerScore(TeamColor.RED);
    this.blueScore = model.getPlayerScore(TeamColor.BLUE);
  }

  // Getters
  public List<List<SerializableGridCell>> getGrid() {
    return grid;
  }

  public List<SerializableCard> getRedPlayerHand() {
    return redPlayerHand;
  }

  public List<SerializableCard> getBluePlayerHand() {
    return bluePlayerHand;
  }

  public TeamColor getCurrentPlayerColor() {
    return currentPlayerColor;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public TeamColor getWinnerColor() {
    return winnerColor;
  }

  public int getRedScore() {
    return redScore;
  }

  public int getBlueScore() {
    return blueScore;
  }

  public int getNumRows() {
    return numRows;
  }

  public int getNumCols() {
    return numCols;
  }

  /**
   * Serializable representation of a grid cell.
   */
  public static class SerializableGridCell implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String cellType; // "hole", "empty", or "card"
    private final SerializableCard card; // null if not a card cell
    private final TeamColor color; // null if not a card cell

    public SerializableGridCell(ReadOnlyGridCell cell) {
      String cellString = cell.toString();
      if (cellString.equals(" ")) {
        this.cellType = "hole";
        this.card = null;
        this.color = null;
      } else if (cellString.equals("_")) {
        this.cellType = "empty";
        this.card = null;
        this.color = null;
      } else {
        this.cellType = "card";
        SerializableCard tempCard = null;
        TeamColor tempColor = null;
        try {
          String cardString = cell.cardToString();
          String[] parts = cardString.split(" ");
          if (parts.length == 5) {
            tempCard = new SerializableCard(parts[0], parts[1], parts[2], parts[3], parts[4]);
            tempColor = cell.getColor();
          }
        } catch (Exception e) {
          // tempCard and tempColor remain null
        }
        this.card = tempCard;
        this.color = tempColor;
      }
    }

    public String getCellType() {
      return cellType;
    }

    public SerializableCard getCard() {
      return card;
    }

    public TeamColor getColor() {
      return color;
    }
  }

  /**
   * Serializable representation of a card.
   */
  public static class SerializableCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String north;
    private final String south;
    private final String east;
    private final String west;

    public SerializableCard(Card card) {
      String cardString = card.toString();
      String[] parts = cardString.split(" ");
      if (parts.length == 5) {
        this.name = parts[0];
        this.north = parts[1];
        this.south = parts[2];
        this.east = parts[3];
        this.west = parts[4];
      } else {
        this.name = "Unknown";
        this.north = "1";
        this.south = "1";
        this.east = "1";
        this.west = "1";
      }
    }

    public SerializableCard(String name, String north, String south, String east, String west) {
      this.name = name;
      this.north = north;
      this.south = south;
      this.east = east;
      this.west = west;
    }

    public String getName() {
      return name;
    }

    public String getNorth() {
      return north;
    }

    public String getSouth() {
      return south;
    }

    public String getEast() {
      return east;
    }

    public String getWest() {
      return west;
    }

    @Override
    public String toString() {
      return name + " " + north + " " + south + " " + east + " " + west;
    }
  }
}
