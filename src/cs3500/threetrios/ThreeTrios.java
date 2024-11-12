package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.controller.BasicThreeTriosController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.view.TTGUIView;

/**
 * Main runner class used for intermediate testing throughout implementation process. This
 * class will change in the future.
 */
public class ThreeTrios {

  /**
   * Current runner to test how model and view are interacting.
   *
   * @param args list of specified arguments to run the game
   */
  public static void main(String[] args) {
    Random rand1 = new Random(2);
    BasicThreeTriosModel model4x3 = new BasicThreeTriosModel(rand1);
    BasicThreeTriosController controller = new BasicThreeTriosController("world4x31hole.txt",
            "cards4x3emptyver2.txt");

    controller.playGame(model4x3);
    model4x3.playToGrid(3, 2, 0);
//    model4x3.playToGrid(0, 1, 2);
////    model4x3.playToGrid(2, 0, 1);


    TTGUIView view = new TTGUIView(model4x3);
    view.setVisible(true);
    System.out.println(view);
  }
}