package cs3500.threetrios.HintScore;

import java.awt.event.KeyListener;
import java.util.List;

import cs3500.threetrios.controller.PlayerActionListener;
import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.view.ThreeTriosCardPanel;


/**
 * A decorator class that adds hints functionality to the view.
 */
public class HintViewDecorator extends TTGUIView {
  private final TTGUIView decoratedView;
  private final ThreeTriosModel model;

  private final ThreeTriosListenerController controller;

  public HintViewDecorator(TTGUIView decoratedView, ThreeTriosModel model, ThreeTriosListenerController controller) {
    super(model);
    this.decoratedView = decoratedView;
    this.model = model;
    this.controller = controller;
  }

  @Override
  public void refreshPlayingBoard() {
    decoratedView.refreshPlayingBoard();
    showHints();
  }

  @Override
  public void updateTitle(String title) {
    decoratedView.updateTitle(title);
  }

  private void showHints() {

    Player currentPlayer = model.getCurrentPlayer();
    Card selectedCard = currentPlayer.getHand().get(controller.getSelectedCardIndex());

    List<List<ReadOnlyGridCell>> grid = model.getGridReadOnly();
    for (int row = 0; row < grid.size(); row++) {
      for (int col = 0; col < grid.get(row).size(); col++) {

        ReadOnlyGridCell cell = model.getCell(row, col);


        if (cell.isOccupied()) {
          continue;
        }


        int flips = model.numCardFlips(selectedCard, row, col, currentPlayer);
        if (flips >= 0) {
          decoratedView.highlightCell(row, col, flips);
        }
      }
    }
  }

  @Override
  public void setVisible() {
    decoratedView.setVisible();
  }



  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    decoratedView.addPlayerActionListener(listener);
  }

  @Override
  public void addHintKeyListener(KeyListener listener) {
    decoratedView.addHintKeyListener(listener);
  }

  @Override
  public void notifySelectedCard(int cardIndex, TeamColor color,
                                 ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard) {
    decoratedView.notifySelectedCard(cardIndex, color, selectedCard, highlightedCard);
  }

  @Override
  public void notifyPlacedCard(int row, int col) {
    decoratedView.notifyPlacedCard(row, col);
  }


}