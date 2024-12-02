package cs3500.threetrios.provider.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ModelStatusFeatures;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.provider.controller.ModelStatusListener;

public class ModelAdapter implements ThreeTriosModel {

  private final cs3500.threetrios.model.ThreeTriosModel adapteeModel;

  private final List<ModelStatusListener> statusListeners;


  public ModelAdapter(cs3500.threetrios.model.ThreeTriosModel adapteeModel) {
    this.adapteeModel = adapteeModel;
    this.statusListeners = new ArrayList<>();
  }


  @Override
  public boolean isGameOver() {
    return this.adapteeModel.isGameOver();
  }

  @Override
  public int getPlayerScore(PlayerType player) {
    if (player.equals(PlayerType.RED)) {
      return this.adapteeModel.getPlayerScore(TeamColor.RED);
    } else if (player.equals(PlayerType.BLUE)) {
      return this.adapteeModel.getPlayerScore(TeamColor.BLUE);
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
    if (winner.getColor().equals(TeamColor.RED)) {
      return PlayerType.RED;
    } else {
      return PlayerType.BLUE;
    }
  }

  @Override
  public Cell[][] getGrid() {
    List<List<ReadOnlyGridCell>> listCellAdaptee = this.adapteeModel.getGridReadOnly();

    int numRows = listCellAdaptee.size();
    int numCols = listCellAdaptee.get(0).size();
    Cell[][] resultListCell = new Cell[numRows][numCols];

    for (int row = 0; row < numRows; row++) {
      List<ReadOnlyGridCell> gridCells = listCellAdaptee.get(row);
      for (int col = 0; col < numCols; col++) {
        ReadOnlyGridCell eachAdapteeCell = gridCells.get(col);
        if (eachAdapteeCell.toString().equals(" ")) { // hole
          resultListCell[row][col] = new Cell(CellType.HOLE);
        } else if (eachAdapteeCell.toString().equals("_")) { //empty cell
          resultListCell[row][col] = new Cell(CellType.CARD);
        } else {
          Cell cardCell = new Cell(CellType.CARD);
          ICard transformCard = new CardAdapter(eachAdapteeCell.getCard(), eachAdapteeCell.getColor()); //need to check with jack about eachAdapteeCell.getCard() because our ReadOnlyGridCEll cannot getCard so I just moved it from the non-readonly to readonly (even-though we can't change our code);
          // another way: parse from toString like in our CardShape and then construct a CardCell and pass it into "eachAdapteeCell.getCard()"
          cardCell.setCard(transformCard);
          resultListCell[row][col] = cardCell;
        }
      }
    }
    return resultListCell;
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
    Player currentPlayer = this.adapteeModel.getCurrentPlayer();
    this.adapteeModel.playToGrid(row, col, cardIndex);


    for (ModelStatusListener eachListener : statusListeners) {
      if (this.isGameOver()) {
        Player winnerPLayer = this.adapteeModel.getWinner();

        if (winnerPLayer.getColor().equals(TeamColor.RED)) {
          eachListener.gameOver(PlayerType.RED, this.adapteeModel.getPlayerScore(TeamColor.RED), this.adapteeModel.getPlayerScore(TeamColor.BLUE));
        } else if (winnerPLayer.getColor().equals(TeamColor.BLUE)) {
          eachListener.gameOver(PlayerType.BLUE, this.adapteeModel.getPlayerScore(TeamColor.RED), this.adapteeModel.getPlayerScore(TeamColor.BLUE));
        } else {
          eachListener.gameOver(PlayerType.OVER, this.adapteeModel.getPlayerScore(TeamColor.RED), this.adapteeModel.getPlayerScore(TeamColor.BLUE));
        }
      } else {
        if (currentPlayer.getColor().equals(TeamColor.RED)) {
          eachListener.currentPlayerChanged(PlayerType.BLUE);
        } else {
          eachListener.currentPlayerChanged(PlayerType.RED);
        }
      }
    }
    // update that cells color meaning

  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    this.statusListeners.add(listener);
  }

  @Override
  public void startGame() {
  }
}
