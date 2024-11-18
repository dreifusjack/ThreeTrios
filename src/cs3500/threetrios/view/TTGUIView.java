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
  private final ThreeTriosPanel gamePanel;
  private final List<PlayerActionFeatures> actionListeners;

  /**
   * Constructs a graphical view for the Three Trios game.
   *
   * @param model is the readonly model to render
   */
  public TTGUIView(ReadOnlyThreeTriosModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1000, 800);
    this.setLayout(new BorderLayout());

    gamePanel = new TTPanel(model, this);

    this.add((Component) gamePanel, BorderLayout.CENTER);

    this.setLocationRelativeTo(null);
    this.actionListeners = new ArrayList<>();
  }

  @Override
  public void refreshPlayingBoard() {
    gamePanel.refresh();
  }

  @Override
  public void updateTitle(String title) {
    setTitle(title);
  }

  @Override
  public void setFeatures(ViewFeatures viewFeatures) {
    gamePanel.setFeatures(viewFeatures);
  }

  @Override
  public void addPlayerActionListener(PlayerActionFeatures listener) {
    actionListeners.add(listener);
  }

  @Override
  public void selectCard(int cardIndex, TeamColor color,
                         ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard) {
    for (PlayerActionFeatures listener : actionListeners) {
      listener.notifyCardSelection(color, cardIndex, selectedCard, highlightedCard);
    }
  }

  @Override
  public void placeSelectedCard(int row, int col) {
    for (PlayerActionFeatures listener : actionListeners) {
      listener.notifyBoardSelection(row, col);
    }
  }
}
