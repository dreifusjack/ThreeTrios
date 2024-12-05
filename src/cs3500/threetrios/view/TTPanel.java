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
 * Implements the behaviors of the ThreeTriosPanel interface.
 */
class TTPanel extends JPanel implements ThreeTriosPanel {
  private final ReadOnlyThreeTriosModel model;
  private final JPanel redPlayerPanel;
  private final JPanel bluePlayerPanel;
  private final JPanel gridPanel;
  private ThreeTriosCardPanel highlightedCard;
  private static final int CELL_WIDTH = 100;
  private static final int CELL_HEIGHT = 150;
  private final TTGUIView view;
  private final JPanel[][] cellPanels;
  private final ThreeTriosCardPanel[] redCardPanels;
  private final ThreeTriosCardPanel[] blueCardPanels;

  /**
   * Constructs a TTPanel in terms of the given read only model by initializing the player
   * hand panels and grid panel.
   *
   * @param model the read-only model to render
   * @param view  is the GUI view.
   */
  public TTPanel(ReadOnlyThreeTriosModel model, TTGUIView view) {
    this.model = model;
    this.setLayout(new BorderLayout());

    // Create grid panel
    gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(
            model.getGridReadOnly().size(), model.getGridReadOnly().get(0).size(), 0, 0));
    this.add(gridPanel, BorderLayout.CENTER);

    // Initialize the grid cells for easy reference
    int numRows = model.numRows();
    int numCols = model.numCols();
    cellPanels = new JPanel[numRows][numCols];

    // Create player panels
    redPlayerPanel = new JPanel();
    bluePlayerPanel = new JPanel();
    redPlayerPanel.setLayout(new BoxLayout(redPlayerPanel, BoxLayout.Y_AXIS));
    bluePlayerPanel.setLayout(new BoxLayout(bluePlayerPanel, BoxLayout.Y_AXIS));
    this.add(redPlayerPanel, BorderLayout.WEST);
    this.add(bluePlayerPanel, BorderLayout.EAST);
    this.highlightedCard = null;

    // Initialize card in hands for easy reference
    redCardPanels = new ThreeTriosCardPanel[model.getRedPlayer().getHand().size()];
    blueCardPanels = new ThreeTriosCardPanel[model.getBluePlayer().getHand().size()];

    this.view = view;
    refresh(-1);
  }

  @Override
  public void refresh(int cardIdx) {
    gridPanel.removeAll();
    redPlayerPanel.removeAll();
    bluePlayerPanel.removeAll();

    renderGrid();
    renderPlayerCards();
    highlightSelectedCard(cardIdx);

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
    for (int redHandIndex = 0; redHandIndex < model.getRedPlayer().getHand().size(); redHandIndex++) {
      Card card = model.getRedPlayer().getHand().get(redHandIndex);
      CardPanel cardPanel = createCardPanel(TeamColor.RED, card.toString(), redHandIndex);
      cardPanel.addMouseListener(
              new CardInHandClickListener(redHandIndex, TeamColor.RED, view));
      redPlayerPanel.add(cardPanel);
      redCardPanels[redHandIndex] = cardPanel;
    }

    for (int blueHandIndex = 0; blueHandIndex < model.getBluePlayer().getHand().size(); blueHandIndex++) {
      Card card = model.getBluePlayer().getHand().get(blueHandIndex);
      CardPanel cardPanel = createCardPanel(TeamColor.BLUE, card.toString(), blueHandIndex);
      cardPanel.addMouseListener(
              new CardInHandClickListener(blueHandIndex, TeamColor.BLUE, view));
      bluePlayerPanel.add(cardPanel);
      blueCardPanels[blueHandIndex] = cardPanel;
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
  private CardPanel createCardPanel(TeamColor color, String cardString, int cardInx) {
    String[] partsForCardShape = cardString.split(" ");
    String north = partsForCardShape[1];
    String south = partsForCardShape[2];
    String east = partsForCardShape[3];
    String west = partsForCardShape[4];

    CardPanel.CardShape cardShape = new CardPanel.CardShape(north, south, east, west, color);
    if (cardInx == -1) {
      return new CardPanel(cardShape);
    } else {
      return new CardPanel(cardShape, cardInx);
    }
  }

  /**
   * Queries the model for a grid copy, for each cell creates a JPanel: yellow square -> blank
   * card cell, CardPanel -> occupied card cell, gray square -> hole. Adds a mouse listener for
   * each newly created JPanel, used for tracking coordinates of mouse clicks, adds each JPanel
   * to this TTPanel's gridPanel.
   */
  private void renderGrid() {
    List<List<ReadOnlyGridCell>> grid = model.getGridReadOnly();
    for (int row = 0; row < model.numRows(); row++) {
      for (int col = 0; col < model.numCols(); col++) {
        ReadOnlyGridCell cell = grid.get(row).get(col);
        JPanel cellPanel = new JPanel();
        try {
          if (cell.toString().equals("_")) {
            createBlankCell(new Color(255, 237, 140), cellPanel);
          } else {
            String cardString = cell.cardToString();
            TeamColor color = cell.getColor();
            cellPanel = createCardPanel(color, cardString, -1);
          }
        } catch (IllegalStateException e) {
          createBlankCell(new Color(140, 145, 150), cellPanel);
        }

        // add a mouse listener for each cell panel
        cellPanel.addMouseListener(new CellClickListener(row, col, view));
        gridPanel.add(cellPanel);
        cellPanels[row][col] = cellPanel; // Store reference for easy access
      }
    }
  }

  /**
   * Handles the internal logic for highlighting the card based off the given index and replacing
   * that with the card that is currently highlighted.
   *
   * @param cardIdx index of card to be highlighted
   */
  private void highlightSelectedCard(int cardIdx) {
    ThreeTriosCardPanel[] targetPanelList =
            model.getCurrentPlayer().getColor() == TeamColor.RED ? redCardPanels : blueCardPanels;
    if (cardIdx < 0) {
      return;
    }
    ThreeTriosCardPanel selectedCard = targetPanelList[cardIdx];
    if (highlightedCard != null) {
      highlightedCard.toggleHighlight();
    }
    // highlights this cardPanel and sets it to TTPanel's highlightedCard
    if (highlightedCard != selectedCard) {
      selectedCard.toggleHighlight();
      highlightedCard = selectedCard;
    } else {
      highlightedCard = null; // case where this cardPanel was previously highlighted
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
  public void cellExposeHint(int row, int col, int flips) {
    if (isValidCoordinate(row, col)) {
      JPanel cellPanel = cellPanels[row][col];

      if (cellPanel != null) {
        cellPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));

        cellPanel.removeAll();
        JLabel flipsLabel = new JLabel("Flips: " + flips);
        flipsLabel.setForeground(Color.RED);
        cellPanel.add(flipsLabel);

        cellPanel.revalidate();
        cellPanel.repaint();
      }
    }
  }

  /**
   * determines if the given coords are in bounds of the rendered board.
   *
   * @param row row of the grid
   * @param col col of the grid
   * @return true iff the given coordinates exists in the rendered grid
   */
  private boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < model.numRows() && col >= 0 && col < model.numCols();
  }

  /**
   * Responsible for logging to the console its row and column when clicked.
   */
  private static class CellClickListener extends MouseAdapter {
    private final int row;
    private final int col;
    private final TTGUIView view;

    /**
     * Constructs a CellClickListener in terms of the given row and column.
     *
     * @param row row value this CellClickListener will listen for
     * @param col column value this CellClickListener will listen for
     */
    public CellClickListener(int row, int col, TTGUIView view) {
      this.row = row;
      this.col = col;
      this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      view.notifyPlacedCard(row, col);
    }
  }

  /**
   * Responsible for highlighting and un-highlighting cards based on user clicks.
   */
  private static class CardInHandClickListener extends MouseAdapter {
    private final int index;
    private final TeamColor color;
    private final TTGUIView view;

    /**
     * Constructs a CardInHandClickListener in terms of the given cardPanel.
     *
     * @throws IllegalArgumentException if the given panel or color is null
     */
    public CardInHandClickListener(int index, TeamColor color, TTGUIView view) {
      if (color == null) {
        throw new IllegalArgumentException("Panel or color is null");
      }
      this.index = index;
      this.color = color;
      this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      view.notifySelectedCard(index, color);
    }
  }
}

