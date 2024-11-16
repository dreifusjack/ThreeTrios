package cs3500.threetrios.view;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.player.PlayerActionFeatures;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent the TTGUIView class to show the graphical view for the ThreeTrios game. Uses a
 * ReadOnlyModel in constructed a view so that any view implementations cannot directly mutate the
 * given model.
 */
public class TTGUIView extends JFrame implements ThreeTriosGUIView {
  private final ThreeTriosPanel gridPanel;
  private final List<PlayerActionFeatures> actionListeners;
  private final TeamColor currentPlayerColor;

  /**
   * Constructs a graphical view for the Three Trios game.
   *
   * @param model is the readonly model to render
   */
  public TTGUIView(ReadOnlyThreeTriosModel model) {
    super("Current Player: " + model.getCurrentPlayer().getColor());

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1000, 800);
    this.setLayout(new BorderLayout());

    gridPanel = new TTPanel(model, this);

    this.add((Component) gridPanel, BorderLayout.CENTER);

    this.setLocationRelativeTo(null);
    this.currentPlayerColor = model.getCurrentPlayer().getColor();
    this.actionListeners = new ArrayList<>();
  }

  @Override
  public void refresh() {
    gridPanel.refresh();
  }

  @Override
  public void setFeatures(ViewFeatures viewFeatures) {
    gridPanel.setFeatures(viewFeatures);
  }

  @Override
  public void addPlayerActionListener(PlayerActionFeatures listener) {
    actionListeners.add(listener);
  }

  @Override
  public void handleCardSelection(int cardIndex, TeamColor color) {
    if (currentPlayerColor.equals(color)) {
      for (PlayerActionFeatures listener : actionListeners) {
        listener.onCardSelected(currentPlayerColor, cardIndex);
      }
    }
  }

  @Override
  public void handleCardPlacement(int row, int col) {
    for (PlayerActionFeatures listener : actionListeners) {
      listener.onCardPlaced(currentPlayerColor, row, col);
    }
    refresh();
  }
}
