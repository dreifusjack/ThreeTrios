package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

import java.util.List;

public class ChainStrategy implements ThreeTriosStrategy {
  private final List<ThreeTriosStrategy> strategies;

  public ChainStrategy(List<ThreeTriosStrategy> strategies) {
    this.strategies = strategies;
  }

  @Override
  public PlayedMove findBestMove(ReadOnlyThreeTriosModel model, Player player) {
    for (ThreeTriosStrategy strategy : strategies) {
      PlayedMove move = strategy.findBestMove(model, player);
      if (move != null) {
        return move;
      }
    }
    // if no strategies provide a valid move, choose the upper-most, left-most open position and the card at index 0.
    if (!player.getHand().isEmpty()) {
      for (int row = 0; row < model.numRows(); row++) {
        for (int col = 0; col < model.numCols(); col++) {
          if (model.getCell(row, col).cardToString().equals("_")) {
            return new BasicMove(0, row, col);
          }
        }
      }
    }
    return null; // or maybe throw an exception like the lecture did(discuss with jack)
  }
}
