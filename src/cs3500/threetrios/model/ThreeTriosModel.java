package cs3500.threetrios.model;

import java.util.List;

/**
 * Behaviors for a game of ThreeTrios.
 * The game consists of the following structures:
 * <li>
 * 2D grid (0 index) to play cards to
 * Players with hands to play from
 * Cards to battle against each other on the grid
 * </li>
 * The model represents a rules keeper for ThreeTrios. It is constructed in a way that validates
 * inputs however is not instantly ready to start a game. A method is required to be called to start
 * a game. The model ensures valid game play occurs and keeps track of the internal game-state.
 */
public interface ThreeTriosModel extends ReadOnlyThreeTriosModel {
  /**
   * Initializes the grid to be equal to the given grid. Uses the given deck to deal an equal number
   * of cards to each player.
   *
   * @throws IllegalStateException    if this game has started
   * @throws IllegalArgumentException if the given grid has an invalid format for the game.
   * @throws IllegalArgumentException if the number of playing cards is not enough
   *                                  to fill each players hand and all card cells on the grid
   */
  void startGame(GridCell[][] grid, List<Card> deck, int numOfCardCells);

  /**
   * The player whose turn it is plays a card from their hand at the given hand index
   * to the grid at the given row and col. Plays the player's card to the grid if it is a
   * valid index and location. The grid will be updated, mutating the card cell that was played to
   * by adding the played card, and updating the cell team color.
   * SIDE-EFFECT: playing to grid called battleCards which enters the battle phase, read
   * documentation on battleCards for more information.
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
  void playToGrid(int row, int col, int handIdx);

  /**
   * Battles the cell at the given coordinates with all adjacent cells opposing cards,
   * if any adjacent cells are defeated then they are changed to the opposite color and a
   * chain reaction occurs. Cells are battled by their occupied cards attack values based off
   * the direction of battle. Last battle occurs after the current player plays the game ending
   * move.
   *
   * @param row row value of the grid
   * @param col column value of the grid
   * @throws IllegalStateException    if the game has not started yet
   * @throws IllegalArgumentException if row < 0 or greater than or equal
   *                                  to the number of rows in the grid.
   * @throws IllegalArgumentException if col < 0 or greater than or equal
   *                                  to the number of columns in the grid.
   * @throws IllegalStateException    if the specified coordinate does not cell occupied by a card
   *                                  or is a hole.
   */
  void battleCards(int row, int col);

}
