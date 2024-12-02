package cs3500.threetrios.provider.view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cs3500.threetrios.provider.controller.PlayerActionListener;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.provider.model.ReadOnlyThreeTriosModel;

/**
 * The main view class for the Three Trios game. This class implements the GUI view using Swing to
 * render the current game state.
 */
public class SimpleThreeTriosView extends JFrame implements ThreeTriosSwingView {
  private static final Color COLOR_RED = Color.PINK;
  private static final Color COLOR_BLUE = new Color(135, 206, 250);
  private final JThreeTriosPanel gridPanel;
  private final JHandPanel redHandPanel;
  private final JHandPanel blueHandPanel;
  private final JLabel playerStatusLabel;

  /**
   * Constructs a new view with the given model.
   *
   * @param model the read-only model of the game, Three Trios.
   */
  public SimpleThreeTriosView(ReadOnlyThreeTriosModel model) {
    super("Three Trios Game");

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    playerStatusLabel = new JLabel();
    playerStatusLabel.setHorizontalAlignment(JLabel.CENTER);
    playerStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
    playerStatusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10,
            0));
    this.add(playerStatusLabel, BorderLayout.NORTH);

    redHandPanel = new JHandPanel(model, PlayerType.RED);
    gridPanel = new JThreeTriosPanel(model);
    blueHandPanel = new JHandPanel(model, PlayerType.BLUE);

    JPanel centerPanel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.2;
    c.weighty = 1.0;
    c.fill = GridBagConstraints.BOTH;
    centerPanel.add(redHandPanel, c);

    c.gridx = 1;
    c.weightx = 0.6;
    centerPanel.add(gridPanel, c);

    c.gridx = 2;
    c.weightx = 0.2;
    centerPanel.add(blueHandPanel, c);

    this.add(centerPanel, BorderLayout.CENTER);

    updateView();

    this.pack();
    this.setMinimumSize(new Dimension(800, 600));
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    redHandPanel.addPlayerActionListener(listener);
    blueHandPanel.addPlayerActionListener(listener);
    gridPanel.addPlayerActionListener(listener);
  }

  @Override
  public void setInteractionEnabled(boolean enabled, PlayerType playerType) {
    if (playerType == PlayerType.RED) {
      redHandPanel.setInteractionEnabled(enabled);
      blueHandPanel.setInteractionEnabled(false);
    } else if (playerType == PlayerType.BLUE) {
      redHandPanel.setInteractionEnabled(false);
      blueHandPanel.setInteractionEnabled(enabled);
    }
    gridPanel.setInteractionEnabled(enabled);
  }

  @Override
  public void resetSelectedCard(PlayerType playerType) {
    if (playerType == PlayerType.RED) {
      redHandPanel.resetSelectedCard();
    } else if (playerType == PlayerType.BLUE) {
      blueHandPanel.resetSelectedCard();
    }
  }

  @Override
  public void updateView() {
    redHandPanel.repaint();
    blueHandPanel.repaint();
    gridPanel.repaint();
  }

  /**
   * Updates the player status label based on the player's turn.
   *
   * @param playerType the player (RED or BLUE)
   * @param isMyTurn   true if it's this player's turn, false otherwise
   */
  @Override
  public void updatePlayerStatus(PlayerType playerType, boolean isMyTurn) {
    String statusText = "Player " + playerType.toString();
    if (isMyTurn) {
      statusText += " (Your Turn)";
      this.playerStatusLabel.setForeground(playerType == PlayerType.RED ? COLOR_RED : COLOR_BLUE);
    } else {
      statusText += " (Waiting)";
      this.playerStatusLabel.setForeground(Color.GRAY);
    }
    this.playerStatusLabel.setText(statusText);
  }
}
