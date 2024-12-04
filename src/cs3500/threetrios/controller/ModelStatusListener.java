package cs3500.threetrios.controller;

/**
 * "Publishes" to corresponding player controllers when player turn changes and a message to
 * be rendered when the game is over.
 */
public interface ModelStatusListener {
  /**
   * When a player turn changes in the model (after the current player plays to grid), this
   * feature will notify the new current player controller that it is their turn to make a move.
   */
  void onPlayerTurnChange();

  /**
   * When the game ends this feature will notify the controller that the game has ended, and
   * it is time to display some message to the user. If a team wins: display the corresponding
   * winner along with their score. If there's a tie (winningPlayer == null) then display drawn
   * game message and tied score.
   */
  void onGameOver();

  /**
   * Returns the index of the currently selected card. If no card is selected returns -1. Indexes
   * are zero based.
   *
   * @return current user selected index
   */
  int getSelectedCardIndex();
}
