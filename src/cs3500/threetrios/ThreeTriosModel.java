package cs3500.threetrios;

/**
 * Behaviors for a game of ThreeTrios.
 * The game consists of the following structures:
 * <li>
 * 2D grid to play cards to
 * Players with hands to play from
 * Cards to battle against each other on the grid
 * </li>
 * The goal of the game is to have the most amount of cards after one player
 * has played all of their cards to the grid.
 */
public interface ThreeTriosModel {

  //TODO: fill this out (determine what needs to be init in the constructor and what in this method)

  /**
   * Responsibilities:
   * <li>
   * deal hands for each player
   * </li>
   */
  public void startGame();


  /**
   * The player whose turn it is plays a card from their hand at the given hand index
   * to the grid at the given row and col. Plays the player's card to the grid if it is a
   * valid index and location.
   *
   * @param row     row value of the grid
   * @param col     column value of the grid
   * @param handIdx index value from the players hand
   * @throws IllegalStateException    if the game is over or has not started yet
   * @throws IllegalArgumentException if handIdx < 0 or greater than or equal
   *                                  to the number of cards in the hand.
   * @throws IllegalArgumentException if row < 0 or greater than or equal
   *                                  to the number of rows in the grid.
   * @throws IllegalArgumentException if col < 0 or greater than or equal
   *                                  to the number of columns in the grid.
   */
  public void playToGrid(int row, int col, int handIdx);

  /**
   * Battles the given card with all adjacent cards opposing cards, if any adjacent cards are
   * defeated then they are changed to the opposite color and a chain reaction occurs. Cards
   * are battled by comparing their directions values that are adjacent to each other.
   *
   * @param row row value of the grid
   * @param col column value of the grid
   * @throws IllegalStateException    if the game is over or has not started yet
   * @throws IllegalArgumentException if row < 0 or greater than or equal
   *                                  to the number of rows in the grid.
   * @throws IllegalArgumentException if col < 0 or greater than or equal
   *                                  to the number of columns in the grid.
   */
  public void battleCards(int row, int col);

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
}
