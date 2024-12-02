package cs3500.threetrios.provider.model;


/**
 * Represents a cell in the grid. A cell has two types, either a card or a hole. If it is a card
 * cell, a card may occupy it.
 */
public class Cell {

  private final CellType cellType;
  private ICard card;

  /**
   * Constructs a new Cell.
   *
   * @param cellType the type of the cell (CARD/HOLE)
   */
  public Cell(CellType cellType) {
    this.cellType = cellType;
    this.card = null;
  }

  /**
   * Returns cell type.
   *
   * @return cell type
   */
  public CellType getCellType() {
    return cellType;
  }

  /**
   * Returns card in this cell.
   *
   * @return the card in this cell, or null if the cell is empty
   */
  public ICard getCard() {
    return card;
  }

  /**
   * Places a card in this cell if it's a Card cell.
   *
   * @param card the card to place in this cell
   * @throws IllegalStateException if player tries to place a card in a Hole cell
   */
  public void setCard(ICard card) {
    if (this.cellType == CellType.CARD) {
      this.card = card;
    } else {
      throw new IllegalStateException("Cannot place a card in a hole cell");
    }
  }

  /**
   * Checks if the cell is empty.
   *
   * @return true if the cell is empty, false otherwise.
   */
  public boolean isEmpty() {
    return this.card == null;
  }

  @Override
  public String toString() {
    if (card == null) {
      return "Empty Card";
    }
    String s = card.toString() + " Owner: ";
    if (card.getOwner() == null) {
      s += "None";
    } else {
      s += card.getOwner();
    }
    return s;
  }
}
