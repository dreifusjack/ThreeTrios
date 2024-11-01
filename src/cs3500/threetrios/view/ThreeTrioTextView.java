package cs3500.threetrios.view;

import java.util.List;

import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Implements the behaviors of ThreeTrioView. Responsible for transforming a read only model into
 * a textual view representation.
 */
public class ThreeTrioTextView implements ThreeTrioView {
  private final ReadOnlyThreeTriosModel model;

  /**
   * Constructs ThreeTrioView in terms of the given ReadOnlyThreeTriosModel, so the view
   * does not have access to model operations.
   *
   * @param model read only model
   * @throws IllegalArgumentException if model is null
   */
  public ThreeTrioTextView(ReadOnlyThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // Append the current player's color
    String[] playerString = model.getCurrentPlayer().toString().split("\n");
    sb.append(playerString[0]).append("\n");

    // Append the grid
    List<List<ReadOnlyGridCell>> grid = model.getGrid();
    for (List<ReadOnlyGridCell> row : grid) {
      for (ReadOnlyGridCell cell : row) {
        sb.append(cell.toString());
      }
      sb.append("\n");
    }

    // Append the current players hand
    sb.append("Hand:").append("\n");
    for (int index = 1; index < playerString.length; index++) {
      sb.append(playerString[index]).append("\n");
    }

    return sb.toString();
  }

  @Override
  public void refresh() {

  }

  @Override
  public void setFeatures(Features features) {

  }

}
