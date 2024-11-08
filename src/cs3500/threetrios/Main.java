package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.view.TTGUIView;

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

    BasicThreeTriosModel model3x3ver2 = new BasicThreeTriosModel("world4x3.txt", "cards3x3.txt", rand1);

    model3x3ver2.startGame();
    model3x3ver2.playToGrid(3, 1, 1);
    model3x3ver2.playToGrid(1 ,0 ,1);
    model3x3ver2.playToGrid(2, 2, 2);
    model3x3ver2.playToGrid(1, 1, 0);
    model3x3ver2.playToGrid(0, 0, 0);


//
//    model3x3ver2.playToGrid(2, 2, 0);
//    model3x3ver2.playToGrid(1, 0, 0);
//    model3x3ver2.playToGrid(3, 1, 0);
//    model3x3ver2.playToGrid(1, 1, 1);


    TTGUIView view = new TTGUIView(model3x3ver2);
      view.setVisible(true);
      System.out.println(view);
  }
}