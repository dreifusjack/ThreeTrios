package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.view.ThreeTrioTextView;
import cs3500.threetrios.view.ThreeTriosSwingView;

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

    // Player 1 (Red)
    model3x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 0, 0);
//    // Player 1 (Red)
//    model3x3.playToGrid(2, 2, 1);
//    // Player 2 (Blue)
//    model3x3.playToGrid(1, 0, 1);
//    // Player 1 (Red)
//    model3x3.playToGrid(3, 1, 1);
//    // Player 2 (Blue)
//    model3x3.playToGrid(1, 1, 1);
//    // Player 1 (Red)
//    model3x3.playToGrid(0, 1, 0);

      ThreeTriosSwingView view = new ThreeTriosSwingView(model3x3);
      view.setVisible(true);
      System.out.println(view);
  }
}