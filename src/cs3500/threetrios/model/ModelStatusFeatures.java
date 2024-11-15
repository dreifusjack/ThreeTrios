package cs3500.threetrios.model;

/**
 * "Publishes" to corresponding player controllers when player turn changes and a message to
 * be rendered when the game is over.
 */
public interface ModelStatusFeatures {
  /**
   * When a player turn changes in the model (after the current player plays to grid), this
   * feature will notify the new current player controller that it is their turn to make a move.
   *
   * @param currentPlayer new current player after the turn has changed
   */
  void onPlayerTurnChange(Player currentPlayer);

  /**
   * When the game ends this feature will notify the controller that the game has ended, and
   * it is time to display some message to the user. If a team wins: display the corresponding
   * winner along with their score. If there's a tie (winningPlayer == null) then display drawn
   * game message and tied score.
   *
   * @param winningPlayer the winning player
   */
  void onGameOver(Player winningPlayer);
}
