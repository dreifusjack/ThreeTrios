package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;

/**
 * Interface for a move to play on the grid.
 */
public interface PlayedMove {
  int getHandInx();
  int getRow();
  int getCol();
}
