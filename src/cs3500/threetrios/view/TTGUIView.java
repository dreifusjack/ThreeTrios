package cs3500.threetrios.view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Component;

import cs3500.threetrios.controller.HintToggleListener;
import cs3500.threetrios.controller.PlayerActionListener;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent the TTGUIView class to show the graphical view for the ThreeTrios game. Uses a
 * ReadOnlyModel in constructed a view so that any view implementations cannot directly mutate the
 * given model.
 */
public class TTGUIView extends JFrame implements ThreeTriosGUIView {
  private final ThreeTriosPanel gamePanel;
  private final List<PlayerActionListener> actionListeners;
  private final List<HintToggleListener> hintToggleListeners;

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
    this.hintToggleListeners = new ArrayList<>();
  }

  @Override
  public void refreshPlayingBoard(int cardIdx) {
    gamePanel.refresh(cardIdx);
  }

  @Override
  public void updateTitle(String title) {
    setTitle(title);
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    actionListeners.add(listener);
  }

  @Override
  public void notifySelectedCard(int cardIndex, TeamColor color) {
    for (PlayerActionListener listener : actionListeners) {
      listener.handleCardSelection(color, cardIndex);
    }
  }

  @Override
  public void notifyPlacedCard(int row, int col) {
    for (PlayerActionListener listener : actionListeners) {
      listener.handleBoardSelection(row, col);
    }
  }

  @Override
  public void addHintToggleListeners(HintToggleListener listener) {
    hintToggleListeners.add(listener);
    setupHintListeners();
  }


  @Override
  public void setupHintListeners() {
    for (HintToggleListener listener : hintToggleListeners) {
      this.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_H && listener != null) {
            listener.onHintToggleRequested();
          }
        }
      });
    }
  }

  @Override
  public void cellExposeHint(int row, int col, int flips) {
    gamePanel.cellExposeHint(row, col, flips);
  }
}
