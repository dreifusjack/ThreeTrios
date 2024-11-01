package cs3500.threetrios.view;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import javax.swing.*;
import java.awt.*;


public class ThreeTriosSwingView extends JFrame implements ThreeTrioView {
  private final TTTPanel gridPanel;

  /**
   * Constructs a graphical view for the Three Trios game.
   *
   * @param model is the readonly model to render
   */
  public ThreeTriosSwingView(ReadOnlyThreeTriosModel model) {
    super("Three Trios Game");

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1000, 800);
    this.setLayout(new BorderLayout());

    gridPanel = new TTTPanelImpl(model);

    this.add((Component) gridPanel, BorderLayout.CENTER);

    this.setLocationRelativeTo(null);
    // I used a similar structure with the TicTacToe game you sent to me, I seperated all the drawing stuff into TTTPanel
  }

  @Override
  public void refresh() {
    gridPanel.refresh();
  }

  @Override
  public void setFeatures(Features features) {
    gridPanel.setFeatures(features);
  }
}
