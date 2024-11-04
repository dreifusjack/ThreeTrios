package cs3500.threetrios.view;

import java.awt.*;
import java.awt.geom.Path2D;

import javax.swing.*;

import cs3500.threetrios.model.TeamColor;


/**
 * Represents a panel for displaying a card shape with its values.
 */
class CardPanel extends JPanel {
  private final CardShape cardShape;
  private boolean isHighlighted;
  private static final int DEFAULT_BORDER_THICKNESS = 2;
  private static final int HIGHLIGHT_BORDER_THICKNESS = 5;

  /**
   * Constructs a panel to display the given card shape.
   *
   * @param cardShape the card shape to display
   */
  public CardPanel(CardShape cardShape) {
    this.cardShape = cardShape;
    this.setPreferredSize(new Dimension(100, 150));
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    this.isHighlighted = false;
  }

  private String valueToString(int value) {
    if (value == 10) {
      return "A";
    }
    return String.valueOf(value);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    // set the color based on the player's color
    if (cardShape.color.equals(TeamColor.RED)) {
      g2.setColor(Color.RED);
    } else if (cardShape.color.equals(TeamColor.BLUE)) {
      g2.setColor(Color.BLUE);
    } else {
      g2.setColor(Color.LIGHT_GRAY);
    }

    // Fill the card to the panel
    g2.fillRect(0, 0, getWidth(), getHeight());
    g2.setColor(Color.BLACK);
    g2.drawRect(0, 0, getWidth(), getHeight());

    // Draw the values on each side of the card
    g2.setFont(new Font("Arial", Font.BOLD, 14));
    g2.drawString(valueToString(cardShape.north), getWidth() / 2 - 5, 15);
    g2.drawString(valueToString(cardShape.south), getWidth() / 2 - 5, getHeight() - 5);
    g2.drawString(valueToString(cardShape.east), getWidth() - 15, getHeight() / 2);
    g2.drawString(valueToString(cardShape.west), 5, getHeight() / 2);
  }

  public void highlight() {
    if (!isHighlighted) {
      this.setBorder(BorderFactory.createLineBorder(Color.GREEN, HIGHLIGHT_BORDER_THICKNESS));
      isHighlighted = true;
    } else {
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK, DEFAULT_BORDER_THICKNESS));
      isHighlighted = false;
    }
  }


  /**
   * Represents a card shape, extending Path2D.Double for easy rendering of card shapes.
   */
  static class CardShape extends Path2D.Double {
    private final int north;
    private final int south;
    private final int east;
    private final int west;
    private final TeamColor color;

    /**
     * Construct a CardShape.
     *
     * @param north is an int.
     * @param south is an int.
     * @param east  is an int.
     * @param west  is an int.
     * @param color is an int.
     */
    public CardShape(String north, String south, String east, String west, TeamColor color) {
      this.north = parseValue(north);
      this.south = parseValue(south);
      this.east = parseValue(east);
      this.west = parseValue(west);
      this.color = color;
    }

    /**
     * Parses the value of the card side.
     *
     * @param value the value to parse
     * @return the parsed integer value
     */
    private int parseValue(String value) {
      if (value.equals("A")) {
        return 10;
      }
      return Integer.parseInt(value);
    }
  }
}