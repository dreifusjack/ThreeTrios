package cs3500.threetrios;

import java.util.List;

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
public interface ThreeTriosModel extends ReadOnlyThreeTriosModel {
  /**
   * Initialize the grid to specified size and cells filling the grid by the user created grid file
   * this model will read from. Deals cards to each player using information by the user created
   * card file this model will read from.
   *
   * @throws IllegalStateException    if this game has started
   * @throws IllegalArgumentException if while reading from grid file or
   *                                  card file the format of that file is invalid or incorrect
   * @throws IllegalArgumentException if the number of playing cards is not enough
   *                                  to fill each players hand and all card cells on the grid
   */
  public void startGame() throws Exception;


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
   * @throws IllegalStateException    if the specified coordinate already has a card or is a hole.
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
   * @throws IllegalStateException    if the specified coordinate does not have a card or is a hole.
   */
  public void battleCards(int row, int col);
}
