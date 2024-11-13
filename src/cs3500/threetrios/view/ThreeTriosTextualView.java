package cs3500.threetrios.view;

/**
 * Responsible for rendering ThreeTrios in a textual view that is presented in the console.
 * Example:
 * Player: BLUE
 * BB   _
 * _B   _
 * _ R  _
 * _  _ _
 * _   _R
 * Hand:
 * CorruptKing 7 3 9 A
 * AngryDragon 2 8 9 9
 * WindBird 7 2 5 3
 * HeroKnight A 2 4 4
 * WorldDragon 8 3 5 7
 * SkyWhale 4 5 9 9
 */
public interface ThreeTriosTextualView {
  /**
   * Renders the game state as a textual view.
   * This method is expected to provide a textual representation of the current state of the game,
   * including the grid and the player's hand.
   *
   * @return a string representing the current game state.
   */
  String toString();
}
