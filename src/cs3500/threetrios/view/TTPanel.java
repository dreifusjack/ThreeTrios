package cs3500.threetrios.view;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Implementation of the TTTPanel interface.
 */
class TTPanel extends JPanel implements ThreeTriosPanel {
  private final ReadOnlyThreeTriosModel model;
  private final JPanel redPlayerPanel;
  private final JPanel bluePlayerPanel;
  private final JPanel gridPanel;

  /**
   * Constructs a panel for the Three Trios game grid and player hands.
   *
   * @param model the read-only model to render
   */
  public TTPanel(ReadOnlyThreeTriosModel model) {
    this.model = model;
    this.setLayout(new BorderLayout());

    // Create grid panel
    gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(
            model.getGrid().size(), model.getGrid().get(0).size(), 0, 0));
    this.add(gridPanel, BorderLayout.CENTER);

    // Create player panels
    redPlayerPanel = new JPanel();
    bluePlayerPanel = new JPanel();
    redPlayerPanel.setLayout(new BoxLayout(redPlayerPanel, BoxLayout.Y_AXIS));
    bluePlayerPanel.setLayout(new BoxLayout(bluePlayerPanel, BoxLayout.Y_AXIS));
    this.add(redPlayerPanel, BorderLayout.WEST);
    this.add(bluePlayerPanel, BorderLayout.EAST);

    refresh();
  }

  @Override
  public void refresh() {
    gridPanel.removeAll();
    redPlayerPanel.removeAll();
    bluePlayerPanel.removeAll();

    renderGrid();
    renderPlayerCards();

    gridPanel.revalidate();
    gridPanel.repaint();
    redPlayerPanel.revalidate();
    redPlayerPanel.repaint();
    bluePlayerPanel.revalidate();
    bluePlayerPanel.repaint();
  }

  private void renderPlayerCards() {
    for (Card card : model.getRedPlayer().getHand()) {
      JPanel cardPanel = createCard(card.getColor(), card.toString());
      redPlayerPanel.add(cardPanel);
    }

    for (Card card : model.getBluePlayer().getHand()) {
      JPanel cardPanel = createCard(card.getColor(), card.toString());
      bluePlayerPanel.add(cardPanel);
    }
  }

  private JPanel createCard(TeamColor color, String cardString) {
    String[] partsForCardShape = cardString.split(" ");
    String north = partsForCardShape[1];
    String south = partsForCardShape[2];
    String east = partsForCardShape[3];
    String west = partsForCardShape[4];

    CardPanel.CardShape cardShape = new CardPanel.CardShape(north, south, east, west, color);
    return new CardPanel(cardShape);
  }

  private void renderGrid() {
    List<List<ReadOnlyGridCell>> grid = model.getGrid();
    for (int row = 0; row < model.numRows(); row++) {
      for (int col = 0; col < model.numCols(); col++) {
        ReadOnlyGridCell cell = grid.get(row).get(col);
        JPanel cellPanel = new JPanel();
        try {
          if (cell.toString().equals("_")) {
            createBlankCell(Color.YELLOW, cellPanel);
          } else {
            String cardString = cell.cardToString();
            TeamColor color = cell.getCardColor();
            cellPanel = createCard(color, cardString);
          }
        } catch (IllegalStateException e) {  // handle holes in the grid
          createBlankCell(Color.WHITE, cellPanel);
        }

        int finalRow = row;
        int finalCol = col;
        cellPanel.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            System.out.println("Clicked on cell at: (" + finalRow + ", " + finalCol + ")");
          }
        });
        gridPanel.add(cellPanel);
      }
    }
  }

  private void createBlankCell(Color color, JPanel cellPanel) {
    cellPanel.setPreferredSize(new Dimension(100, 150));
    cellPanel.setBackground(color);
    cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
  }

  @Override
  public void setFeatures(Features features) {
    // future implementation
  }
}
