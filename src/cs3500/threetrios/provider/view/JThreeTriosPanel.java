package cs3500.threetrios.provider.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Objects;

import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.CellType;
import cs3500.threetrios.provider.model.ICard;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.provider.model.ReadOnlyThreeTriosModel;

/**
 * Panel representing the grid. It is a 2D-grid with either Card cells or Hole cells being
 * rendered.
 */
public class JThreeTriosPanel extends AbstractCardPanel {
  private final ReadOnlyThreeTriosModel model;

  /**
   * Constructs a grid panel for the game board.
   *
   * @param model the read-only model.
   */
  public JThreeTriosPanel(ReadOnlyThreeTriosModel model) {
    this.model = Objects.requireNonNull(model);
    this.setBackground(Color.WHITE);
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (!isEnabled()) {
          return;
        }
        AffineTransform transform = transformPhysicalToLogical();
        Point2D logicalPoint = transform.transform(e.getPoint(), null);
        Cell[][] grid = model.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        double cellWidth = 100.0 / cols;
        double cellHeight = 100.0 / rows;

        int col = (int) (logicalPoint.getX() / cellWidth);
        int row = (int) (logicalPoint.getY() / cellHeight);

        if (col >= 0 && col < cols && row >= 0 && row < rows) {
          if (actionListener != null) {
            actionListener.cellClicked(row, col);
          }
        }
      }
    });
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 600);
  }

  @Override
  public void setInteractionEnabled(boolean enabled) {
    this.setEnabled(enabled);
    if (!enabled) {
      repaint();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    AffineTransform transform = transformLogicalToPhysical();
    g2d.transform(transform);
    drawGrid(g2d);
    g2d.dispose();
  }

  /**
   * Draws the game grid.
   *
   * @param g2d the Graphics2D object.
   */
  private void drawGrid(Graphics2D g2d) {
    Cell[][] grid = model.getGrid();
    int rows = grid.length;
    int cols = grid[0].length;

    double cellWidth = 100.0 / cols;
    double cellHeight = 100.0 / rows;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        double x = col * cellWidth;
        double y = row * cellHeight;

        Cell cell = grid[row][col];

        // Draw cell background
        if (cell.getCellType() == CellType.CARD) {
          if (cell.isEmpty()) {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect((int) x, (int) y, (int) cellWidth, (int) cellHeight);
          }
        } else {
          g2d.setColor(Color.LIGHT_GRAY);
          g2d.fillRect((int) x, (int) y, (int) cellWidth, (int) cellHeight);
        }

        // Draw cell border
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int) x, (int) y, (int) cellWidth, (int) cellHeight);

        // Draw card if present
        if (!cell.isEmpty()) {
          ICard card = cell.getCard();

          // Set card color based on owner
          if (card.getOwner() == PlayerType.RED) {
            g2d.setColor(COLOR_RED);
          } else if (card.getOwner() == PlayerType.BLUE) {
            g2d.setColor(COLOR_BLUE);
          } else {
            g2d.setColor(Color.WHITE);
          }
          g2d.fillRect((int) x, (int) y, (int) cellWidth, (int) cellHeight);

          // Draw card border
          g2d.setColor(CARD_BORDER_COLOR);
          g2d.drawRect((int) x, (int) y, (int) cellWidth, (int) cellHeight);

          // Draw card values
          drawCardValues(g2d, card, x, y, cellWidth, cellHeight);
        }
      }
    }
  }
}
