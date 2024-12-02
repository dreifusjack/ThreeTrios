package cs3500.threetrios.provider.view;

import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Dimension;

import javax.swing.JPanel;

import cs3500.threetrios.provider.controller.PlayerActionListener;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.ICard;

/**
 * Abstract class for panels that render cards.
 */
public abstract class AbstractCardPanel extends JPanel implements Panel {

  protected static final Color COLOR_RED = Color.PINK;
  protected static final Color COLOR_BLUE = new Color(135, 206, 250);
  protected static final Color CARD_HIGHLIGHT_COLOR = Color.GRAY;
  protected static final Color CARD_BORDER_COLOR = Color.BLACK;
  protected static final Color TEXT_COLOR = Color.BLACK;
  protected static final int CARD_DISTANCE = 5;
  protected PlayerActionListener actionListener;

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    this.actionListener = listener;
  }

  /**
   * Draws the values on a card.
   *
   * @param g2d        the Graphics2D object.
   * @param card       the card to draw.
   * @param x          the x position.
   * @param y          the y position.
   * @param cardWidth  the width of the card.
   * @param cardHeight the height of the card.
   */
  protected void drawCardValues(Graphics2D g2d, ICard card, double x, double y,
                                double cardWidth, double cardHeight) {
    int fontSize = (int) (Math.min(cardWidth, cardHeight) / CARD_DISTANCE);
    fontSize = Math.max(10, fontSize);
    g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
    g2d.setColor(TEXT_COLOR);

    FontMetrics metrics = g2d.getFontMetrics();

    double centerX = x + cardWidth / 2;
    double centerY = y + cardHeight / 2;

    String northValue = card.getValues().get(Direction.NORTH).toString();
    double northY = y + metrics.getAscent() + 2;
    g2d.drawString(northValue,
            (float) (centerX - metrics.stringWidth(northValue) / 2),
            (float) northY);

    String eastValue = card.getValues().get(Direction.EAST).toString();
    double eastX = x + cardWidth - metrics.stringWidth(eastValue) - 2;
    g2d.drawString(eastValue, (float) eastX, (float) (centerY + metrics.getAscent() / 2));

    String southValue = card.getValues().get(Direction.SOUTH).toString();
    double southY = y + cardHeight - 2;
    g2d.drawString(southValue,
            (float) (centerX - metrics.stringWidth(southValue) / 2),
            (float) southY);

    String westValue = card.getValues().get(Direction.WEST).toString();
    double westX = x + 2;
    g2d.drawString(westValue, (float) westX, (float) (centerY + metrics.getAscent() / 2));
  }

  /**
   * Computes the transformation that converts logical coordinates
   * into screen coordinates.
   *
   * @return the AffineTransform object.
   */
  protected AffineTransform transformLogicalToPhysical() {
    AffineTransform at = new AffineTransform();
    Dimension size = getSize();
    at.scale(size.getWidth() / 100.0, size.getHeight() / 100.0);
    return at;
  }

  /**
   * Computes the transformation that converts screen coordinates
   * into logical coordinates.
   *
   * @return the AffineTransform object.
   */
  protected AffineTransform transformPhysicalToLogical() {
    AffineTransform at = new AffineTransform();
    Dimension size = getSize();
    at.scale(100.0 / size.getWidth(), 100.0 / size.getHeight());
    return at;
  }
}
