package cs3500.threetrios.model;

import java.util.List;

/**
 * All observation behaviors of the Three Trios Model. These behaviors are to be used
 * in the view, so it has no access to the models operations (internal state).
 */
public interface ReadOnlyThreeTriosModel {
  /**
   * Determines if the game is over by the behaviors of the game.
   *
   * @return true iff the game is over
   * @throws IllegalStateException if the game has not started
   */
  public boolean isGameOver();

  /**
   * Returns a copy winning player after the game is over. Modifying this returning object will
   * have no effect on the game. If the game ends in a tie returns null.
   *
   * @return the winning player
   * @throws IllegalStateException if the game has not started or the game is not over
   */
  public Player getWinner();

  /**
   * Returns a copy of the current player. Modifying this player will have no effect on the game
   * state.
   *
   * @return the player whose turn it is, or the last player to play if the game is over
   * @throws IllegalStateException if the game has not started
   */
  public Player getCurrentPlayer();

  /**
   * Returns a state of the Grid that is read only for the user to view the current state of the
   * grid.
   *
   * @return a 2D array list representing the current playing grid.
   * @throws IllegalStateException if the game has not started
   */
  public List<List<ReadOnlyGridCell>> getGrid();
}
