package cs3500.threetrios.provider.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.provider.controller.ModelStatusListener;

/**
 * Follows the object adapter pattern to adapt our models behaviors to the behaviors of our
 * providers model. This allows us to construct our providers view using this newly created
 * adapted model.
 */
public class ModelAdapter implements ThreeTriosModel {
  private final cs3500.threetrios.model.ThreeTriosModel adapteeModel;

  /**
   * Constructs a ModelAdapter using our model as a delegate.
   *
   * @param adapteeModel delegate to our model methods
   */
  public ModelAdapter(cs3500.threetrios.model.ThreeTriosModel adapteeModel) {
    this.adapteeModel = adapteeModel;
  }

  @Override
  public boolean isGameOver() {
    return adapteeModel.isGameOver();
  }

  @Override
  public int getPlayerScore(PlayerType player) {
    if (player.equals(PlayerType.RED)) {
      return adapteeModel.getPlayerScore(TeamColor.RED);
    } else if (player.equals(PlayerType.BLUE)) {
      return adapteeModel.getPlayerScore(TeamColor.BLUE);
    } else {
      throw new IllegalArgumentException("Wrong player type");
    }
  }

  @Override
  public boolean isMoveLegal(int cardIndex, int row, int col) {
    try {
      adapteeModel.simulateMove(row, col, cardIndex);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public PlayerType getWinner() {
    Player winner = this.adapteeModel.getWinner();
    if (winner == null) {
      return PlayerType.OVER;
    }
    if (winner.getColor().equals(TeamColor.RED)) {
      return PlayerType.RED;
    } else {
      return PlayerType.BLUE;
    }
  }

  @Override
  public Cell[][] getGrid() {
    List<List<ReadOnlyGridCell>> adapteeGrid = adapteeModel.getGridReadOnly();

    int numRows = adapteeGrid.size();
    int numCols = adapteeGrid.get(0).size();
    Cell[][] resultListCell = new Cell[numRows][numCols];

    for (int row = 0; row < numRows; row++) {
      List<ReadOnlyGridCell> adapteeRow = adapteeGrid.get(row);
      for (int col = 0; col < numCols; col++) {
        ReadOnlyGridCell adapteeCell = adapteeRow.get(col);
        if (adapteeCell.toString().equals(" ")) { // hole
          resultListCell[row][col] = new Cell(CellType.HOLE);
        } else if (adapteeCell.toString().equals("_")) { // empty card cell
          resultListCell[row][col] = new Cell(CellType.CARD);
        } else { // occupied card cell
          Cell cardCell = adapted(adapteeCell);
          resultListCell[row][col] = cardCell;
        }
      }
    }
    return resultListCell;
  }

  /**
   * Transforms the given ReadOnlyGridCell from our model to a Cell type used in the provided model.
   * Uses the CardAdapter to achieve this.
   *
   * @param adapteeCell card to be adapted
   * @return adapted card
   */
  private Cell adapted(ReadOnlyGridCell adapteeCell) {
    Cell cardCell = new Cell(CellType.CARD);
    ICard transformCard = new CardAdapter(
            adapteeCell.getCardCopy(), adapteeCell.getColor());
    cardCell.setCard(transformCard);
    return cardCell;
  }

  @Override
  public List<ICard> getHand(PlayerType player) {
    List<ICard> adaptedCardsHand = new ArrayList<>();

    if (player.equals(PlayerType.RED)) {
      List<Card> handCards = this.adapteeModel.getRedPlayer().getHand();
      for (Card adapteeHandCard : handCards) {
        ICard transformCard = new CardAdapter(adapteeHandCard, TeamColor.RED);
        adaptedCardsHand.add(transformCard);
      }
    } else {
      List<Card> handCards = this.adapteeModel.getBluePlayer().getHand();
      for (Card adapteeHandCard : handCards) {
        ICard transformCard = new CardAdapter(adapteeHandCard, TeamColor.BLUE);
        adaptedCardsHand.add(transformCard);
      }
    }
    return adaptedCardsHand;
  }

  @Override
  public PlayerType getCurrentPlayer() {
    Player currentPlayer = this.adapteeModel.getCurrentPlayer();
    if (currentPlayer.getColor().equals(TeamColor.RED)) {
      return PlayerType.RED;
    } else {
      return PlayerType.BLUE;
    }
  }

  @Override
  public void playCard(int cardIndex, int row, int col) {
    this.adapteeModel.playToGrid(row, col, cardIndex);
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    // ignored handled within adaptee model
  }

  @Override
  public void startGame() {
    // ignored handled within adaptee model
  }
}
