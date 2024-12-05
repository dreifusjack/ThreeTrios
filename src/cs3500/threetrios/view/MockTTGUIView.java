package cs3500.threetrios.view;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.controller.PlayerActionListener;

/**
 * Mock GUI to log when callbacks to the controller are called.
 */
public class MockTTGUIView extends TTGUIView {
  private final List<String> log;

  /**
   * Constructs a graphical view for the Three Trios game.
   *
   * @param model is the readonly model to render
   */
  public MockTTGUIView(ReadOnlyThreeTriosModel model) {
    super(model);
    this.log = new ArrayList<>();
  }

  @Override
  public void refreshPlayingBoard(int cardIdx) {
    log.add("refreshPlayingBoard called");
    super.refreshPlayingBoard(cardIdx);
  }

  @Override
  public void updateTitle(String title) {
    log.add("updateTitle called");
    log.add("Update the view with the string: " + title);
    super.updateTitle(title);
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    log.add("addPlayerActionListener called");
    super.addPlayerActionListener(listener);
  }
  // notify selected 0 indexed card with color RED

  @Override
  public void notifySelectedCard(int cardIndex, TeamColor color) {
    log.add("notifySelectedCard called");
    log.add("notify selecting " + cardIndex + " indexed card with color " + color);
    super.notifySelectedCard(cardIndex, color);
  }

  @Override
  public void notifyPlacedCard(int row, int col) {
    log.add("refreshPlayingBoard called");
    log.add("notify placing selected card at row " + row + " and col " + col);
    super.notifyPlacedCard(row, col);
  }

  public List<String> getLog() {
    return this.log;
  }
}