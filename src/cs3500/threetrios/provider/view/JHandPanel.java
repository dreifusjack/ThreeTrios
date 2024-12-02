package cs3500.threetrios.provider.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Objects;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;

import cs3500.threetrios.provider.model.ICard;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.provider.model.ReadOnlyThreeTriosModel;

/**
 * Panel representing a player's hand. It renders a vertical list of the player's hand.
 */
public class JHandPanel extends AbstractCardPanel {
  private final ReadOnlyThreeTriosModel model;
  private final PlayerType playerType;
  private final Color cardBackgroundColor;
  private int selectedCardIndex = -1;

  /**
   * Constructs a hand panel for a player.
   *
   * @param model      the read-only model.
   * @param playerType the player.
   */
  public JHandPanel(ReadOnlyThreeTriosModel model, PlayerType playerType) {
    this.model = Objects.requireNonNull(model);
    this.playerType = playerType;

    if (playerType == PlayerType.RED) {
      this.setBackground(COLOR_RED);
      this.cardBackgroundColor = COLOR_RED;
    } else {
      this.setBackground(COLOR_BLUE);
      this.cardBackgroundColor = COLOR_BLUE;
    }

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (!isEnabled()) {
          return;
        }
        int index = getCardIndexAtPoint(e.getPoint());
        if (index != -1) {
          if (selectedCardIndex == index) {
            selectedCardIndex = -1;
          } else {
            selectedCardIndex = index;
          }
          repaint();
          if (actionListener != null) {
            actionListener.cardSelected(playerType, selectedCardIndex);
          }
        }
      }
    });
  }

  @Override
  public void setInteractionEnabled(boolean enabled) {
    this.setEnabled(enabled);
    if (!enabled) {
      selectedCardIndex = -1;
      repaint();
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(150, 600);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    AffineTransform transform = transformLogicalToPhysical();
    g2d.transform(transform);
    drawCards(g2d);
    g2d.dispose();
  }

  /**
   * Resets the selected card in this hand panel.
   */
  public void resetSelectedCard() {
    selectedCardIndex = -1;
    repaint();
  }

  /**
   * Gets the index of the card at the given point.
   *
   * @param point the point clicked.
   * @return the index of the card, or -1 if none.
   */
  private int getCardIndexAtPoint(Point point) {
    AffineTransform transform = transformPhysicalToLogical();
    Point2D logicalPoint = transform.transform(point, null);
    List<ICard> hand = model.getHand(playerType);
    double totalSpacing = CARD_DISTANCE * (hand.size() + 1);
    double cardHeight = (100.0 - totalSpacing) / Math.max(hand.size(), 1);
    for (int i = 0; i < hand.size(); i++) {
      double y = i * (cardHeight + CARD_DISTANCE) + CARD_DISTANCE;
      if (logicalPoint.getY() >= y && logicalPoint.getY() <= y + cardHeight) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Draws the cards in the hand.
   *
   * @param g2d the Graphics2D object.
   */
  private void drawCards(Graphics2D g2d) {
    List<ICard> hand = model.getHand(playerType);
    double totalSpacing = CARD_DISTANCE * (hand.size() + 1);
    double cardHeight = (100.0 - totalSpacing) / Math.max(hand.size(), 1);
    double cardWidth = 80;

    for (int i = 0; i < hand.size(); i++) {
      double x = (100.0 - cardWidth) / 2;
      double y = i * (cardHeight + CARD_DISTANCE) + CARD_DISTANCE;

      // Highlight selected card
      if (i == selectedCardIndex) {
        g2d.setColor(CARD_HIGHLIGHT_COLOR);
        g2d.fillRect((int) x - CARD_DISTANCE, (int) y - CARD_DISTANCE, (int)
                cardWidth + 10, (int) cardHeight + 10);
      }

      g2d.setColor(cardBackgroundColor);
      g2d.fillRect((int) x, (int) y, (int) cardWidth, (int) cardHeight);
      g2d.setColor(CARD_BORDER_COLOR);
      g2d.drawRect((int) x, (int) y, (int) cardWidth, (int) cardHeight);
      drawCardValues(g2d, hand.get(i), x, y, cardWidth, cardHeight);
    }
  }
}
