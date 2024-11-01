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
class TTTPanelImpl extends JPanel implements TTTPanel {
  private final ReadOnlyThreeTriosModel model;
  private final JPanel redPlayerPanel;
  private final JPanel bluePlayerPanel;
  private final JPanel gridPanel;

  /**
   * Constructs a panel for the Three Trios game grid and player hands.
   *
   * @param model the read-only model to render
   */
  public TTTPanelImpl(ReadOnlyThreeTriosModel model) {
    this.model = model;
    this.setLayout(new BorderLayout());

    // Create grid panel
    gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(model.getGrid().size(), model.getGrid().get(0).size(), 0, 0));
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

    // Render the grid
    List<List<ReadOnlyGridCell>> grid = model.getGrid();
    for (int row = 0; row < grid.size(); row++) {
      for (int col = 0; col < grid.get(row).size(); col++) {
        ReadOnlyGridCell cell = grid.get(row).get(col);
        JPanel cellPanel;
        try {
          if (cell.toString().equals("_")) {
            // empty cell
            cellPanel = new JPanel();
            cellPanel.setPreferredSize(new Dimension(100, 150));
            cellPanel.setBackground(Color.WHITE);
            cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
          } else {
            // cellcard
            String cardString = cell.cardToString(); // so I have been thinking abt this abt how to
            // get the card so that we can extract its info to parse it to each side of the graphical cell.
            // We can't use getCard since it will allow the clients to edit the internal state, so I came up with
            // another toString that call the this.card.toString as a ReadOnlyGridCell has a ThreeTriosCard.
            TeamColor cardColor = cell.getCardColor();   // This getCardColor is also fine to make I guess as
            String[] partsForCardShape = cardString.split(" ");
            String name = partsForCardShape[0];
            String north = partsForCardShape[1];
            String south = partsForCardShape[2];
            String east= partsForCardShape[3];
            String west = partsForCardShape[4];

            CardShape cardShape = new CardShape(name, north, south, east, west, cardColor);
            cellPanel = new CardPanel(cardShape);
          }
        } catch (IllegalStateException e) {  // handle holes in the grid
          cellPanel = new JPanel();
          cellPanel.setPreferredSize(new Dimension(100, 150));
          cellPanel.setBackground(Color.YELLOW);
          cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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


    // render red player's hand but I also did the same thing for blue player as I want to wait until we discuss tmr about how we store the currentPlayer in model since our current implementation does not allow rendering the blue player's hand
      for(Card card : model.getCurrentPlayer().getHand()) {
      String cardString = card.toString();
      TeamColor cardColor = card.getColor();
      String[] partsForCardShape = card.toString().split(" ");
      String name = partsForCardShape[0];
      String north = partsForCardShape[1];
      String south = partsForCardShape[2];
      String east= partsForCardShape[3];
      String west = partsForCardShape[4];

      CardShape cardShape = new CardShape(name, north, south, east, west, cardColor);
      JPanel cardPanel = new CardPanel(cardShape);
      redPlayerPanel.add(cardPanel);
    };

    // render blue player's hand (comment on the red player hand)
    for(Card card : model.getCurrentPlayer().getHand()) {
      TeamColor cardColor = card.getColor();
      String[] partsForCardShape = card.toString().split(" ");
      String name = partsForCardShape[0];
      String north = partsForCardShape[1];
      String south = partsForCardShape[2];
      String east= partsForCardShape[3];
      String west = partsForCardShape[4];

      CardShape cardShape = new CardShape(name, north, south, east, west, cardColor);
      JPanel cardPanel = new CardPanel(cardShape);
      bluePlayerPanel.add(cardPanel);
    };

    gridPanel.revalidate();
    gridPanel.repaint();
    redPlayerPanel.revalidate();
    redPlayerPanel.repaint();
    bluePlayerPanel.revalidate();
    bluePlayerPanel.repaint();
  }

  @Override
  public void setFeatures(Features features) {
    // future implementation
  }
}
