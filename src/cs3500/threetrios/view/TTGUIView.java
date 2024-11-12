package cs3500.threetrios.view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import java.awt.Component;

public class TTGUIView extends JFrame implements ThreeTriosGUIView {
  private final ThreeTriosPanel gridPanel;

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

    gridPanel = new TTPanel(model);

    this.add((Component) gridPanel, BorderLayout.CENTER);

    this.setLocationRelativeTo(null);
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
