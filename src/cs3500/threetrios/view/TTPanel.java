package cs3500.threetrios.view;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Implements the behaviors of the ThreeTriosPanel interface.
 * TTPanel is broken up into the following structure:
 * Red        Grid      Blue
 * Player     Panel     Player
 * Hand                 Hand
 * Panel                Panel
 */
class TTPanel extends JPanel implements ThreeTriosPanel {
  private final ReadOnlyThreeTriosModel model;
  private final JPanel redPlayerPanel;
  private final JPanel bluePlayerPanel;
  private final JPanel gridPanel;
  private CardPanel highlightedCard;
  private static final int CELL_WIDTH = 100;
  private static final int CELL_HEIGHT = 150;

  /**
   * Constructs a TTPanel in terms of the given read only model by initializing the player
   * hand panels and grid panel.
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
    this.highlightedCard = null;

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

  /**
   * Queries the model for each player's hands. Creates card panels for each card in the
   * corresponding hands, add a mouse listener for each card panel for highlighting cards, adds
   * the newly created card panel to the corresponding player panel.
   */
  private void renderPlayerCards() {
    for (Card card : model.getRedPlayer().getHand()) {
      CardPanel cardPanel = createCardPanel(card.getColor(), card.toString());
      cardPanel.addMouseListener(new CardInHandClickListener(cardPanel));
      redPlayerPanel.add(cardPanel);
    }

    for (Card card : model.getBluePlayer().getHand()) {
      CardPanel cardPanel = createCardPanel(card.getColor(), card.toString());
      cardPanel.addMouseListener(new CardInHandClickListener(cardPanel));
      bluePlayerPanel.add(cardPanel);
    }
  }

  /**
   * Creates an instance of a CardPanel using the given TeamColor and cardString. Where a cardString
   * is the result of calling toString on a card from the model output has this format:
   * "Name NorthAttackValue SouthAttackValue EastAttackValue WestAttackValue".
   *
   * @param color      color for the newly created CardPanel.
   * @param cardString toString of the card that the panel is being created for
   * @return a new CardPanel constructed through the given parameters.
   */
  private CardPanel createCardPanel(TeamColor color, String cardString) {
    String[] partsForCardShape = cardString.split(" ");
    String north = partsForCardShape[1];
    String south = partsForCardShape[2];
    String east = partsForCardShape[3];
    String west = partsForCardShape[4];

    CardPanel.CardShape cardShape = new CardPanel.CardShape(north, south, east, west, color);
    return new CardPanel(cardShape);
  }

  /**
   * Queries the model for a grid copy, for each cell creates a JPanel: yellow square -> blank
   * card cell, CardPanel -> occupied card cell, gray square -> hole. Adds a mouse listener for
   * each newly created JPanel, used for tracking coordinates of mouse clicks, adds each JPanel
   * to this TTPanel's gridPanel.
   */
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
            cellPanel = createCardPanel(color, cardString);
          }
        } catch (IllegalStateException e) {  // handle holes in the grid
          createBlankCell(Color.LIGHT_GRAY, cellPanel);
        }
        // add a mouse listener for each cell panel
        cellPanel.addMouseListener(new CellClickListener(row, col));
        gridPanel.add(cellPanel);
      }
    }
  }

  /**
   * Mutates the given cellPanel a blank square of the given color.
   *
   * @param color     color to modify this cellPanel to
   * @param cellPanel JPanel to be modified.
   */
  private void createBlankCell(Color color, JPanel cellPanel) {
    cellPanel.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
    cellPanel.setBackground(color);
    cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
  }

  @Override
  public void setFeatures(Features features) {
    // future controller implementation
  }

  /**
   * Responsible for logging to the console its row and column when clicked. Also, un-highlights
   * any highlights cards on a mouseclick.
   */
  private class CellClickListener extends MouseAdapter {
    private final int row;
    private final int col;

    /**
     * Constructs a CellClickListener in terms of the given row and column.
     *
     * @param row row value this CellClickListener will listen for
     * @param col column value this CellClickListener will listen for
     */
    public CellClickListener(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      if (highlightedCard != null) {
        highlightedCard.highlight();
        highlightedCard = null;
      }
      System.out.println("Row: " + row + ", Col: " + col);
    }
  }

  /**
   * Responsible for highlighting and un-highlighting cards based on user clicks.
   */
  private class CardInHandClickListener extends MouseAdapter {
    CardPanel cardPanel;

    /**
     * Constructs a CardInHandClickListener in terms of the given cardPanel.
     *
     * @param cardPanel CardPanel this CardInHandClickListener will listen for
     */
    public CardInHandClickListener(CardPanel cardPanel) {
      if (cardPanel == null) {
        throw new IllegalArgumentException("CardPanel cannot be null");
      }
      this.cardPanel = cardPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      // value this CellClickListener will listen for
      if (highlightedCard != null) {
        highlightedCard.highlight();
      }
      // highlights this cardPanel and sets it to TTPanel's highlightedCard
      if (highlightedCard != cardPanel) {
        cardPanel.highlight();
        highlightedCard = cardPanel;
      } else {
        highlightedCard = null; // case where this cardPanel was previously highlighted
      }
    }
  }
}

