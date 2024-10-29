package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.view.ThreeTrioTextView;

/**
 * Main runner class used for intermediate testing throughout implementation process. This
 * class will change in the future.
 */
public class Main {

  /**
   * Current runner to test how model and view are interacting.
   *
   * @param args list of specified arguments to run the game
   */
  public static void main(String[] args) {
    Random rand1 = new Random(2);

    BasicThreeTriosModel model3x3 = new BasicThreeTriosModel("world3x3.txt", "cards3x3.txt", rand1);
    model3x3.startGame();

    model3x3.playToGrid(0, 0, 1);

    model3x3.playToGrid(0, 1, 1);

    ThreeTrioTextView view = new ThreeTrioTextView(model3x3);
    System.out.println(view.toString());
    System.out.println(model3x3.isGameOver());
    System.out.println(model3x3.getWinner());

    System.out.println(model3x3.getCurrentPlayer().getHand());
  }
}