package cs3500.threetrios.view.decorators;

import java.util.List;

import cs3500.threetrios.controller.PlayerActionListener;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.TTGUIView;


/**
 * Decorates our current view to add hint functionality. Where a hint shows on each available cell
 * how many flips the selected card would flip.
 */
public class HintViewDecorator extends TTGUIView {
  private final TTGUIView decoratedView;
  private final ThreeTriosModel model;

  /**
   * Constructs a HintViewDecorator in terms of the given model and a delegate original view.
   *
   * @param decoratedView delegate to call view methods on
   * @param model         model responsible for internal game state
   * @throws IllegalArgumentException if either argument is null
   */
  public HintViewDecorator(TTGUIView decoratedView, ThreeTriosModel model) {
    super(model);
    if (decoratedView == null || model == null) {
      throw new IllegalArgumentException();
    }
    this.decoratedView = decoratedView;
    this.model = model;
  }

  @Override
  public void refreshPlayingBoard(int cardIdx) {
    decoratedView.refreshPlayingBoard(cardIdx);
    showHints(cardIdx);
  }

  @Override
  public void updateTitle(String title) {
    decoratedView.updateTitle(title);
  }

  /**
   * For each unoccupied grid cell, show the number of cards it would flip on the opposing team
   * if the given cardIdx was played from the models current player. If the given card index
   * is negative, that indicates that no card has been selected and this should do nothing.
   *
   * @param cardIdx selected card index from user hand
   */
  private void showHints(int cardIdx) {
    if (cardIdx == -1) {
      return;
    }
    Player currentPlayer = model.getCurrentPlayer();
    Card selectedCard = currentPlayer.getHand().get(cardIdx);
    List<List<ReadOnlyGridCell>> grid = model.getGridReadOnly();
    for (int row = 0; row < grid.size(); row++) {
      for (int col = 0; col < grid.get(row).size(); col++) {
        if (model.getCell(row, col).isOccupied()) {
          continue;
        }
        int flips = model.numCardFlips(selectedCard, row, col, currentPlayer);
        decoratedView.cellExposeHint(row, col, flips);
      }
    }
  }


  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    decoratedView.addPlayerActionListener(listener);
  }

  @Override
  public void notifySelectedCard(int cardIndex, TeamColor color) {
    decoratedView.notifySelectedCard(cardIndex, color);
  }

  @Override
  public void notifyPlacedCard(int row, int col) {
    decoratedView.notifyPlacedCard(row, col);
  }
}