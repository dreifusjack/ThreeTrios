package cs3500.threetrios;
import java.util.Random;

import cs3500.threetrios.controller.BasicThreeTriosController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.CornerStrategy;
import cs3500.threetrios.player.strategy.MaximizeFlipsStrategy;
import cs3500.threetrios.player.strategy.MinimaxStrategy;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.controller.ThreeTriosController2;

/**
 * Main runner class used for intermediate testing throughout implementation process. This
 * class will change in the future.
 */
public class ThreeTrios2 {
  /**
   * Current runner to test how model and view are interacting.
   *
   * @param args list of specified arguments to run the game
   */
  public static void main(String[] args) {

    ThreeTriosModel model = new BasicThreeTriosModel(new Random(2));

    BasicThreeTriosController setupController = new BasicThreeTriosController("world4x3manyholes.txt",
            "cardsopponentweak.txt");
    setupController.playGame(model);

    TTGUIView view1 = new TTGUIView(model);
    TTGUIView view2 = new TTGUIView(model);
    view1.setVisible(true);
    view2.setVisible(true);

    PlayerActions redPlayerActions = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActions = new AIPlayer(TeamColor.BLUE, new MaximizeFlipsStrategy());

   // AIPlayer bluePlayerActions = new AIPlayer(TeamColor.BLUE, new CornerStrategy());

    ThreeTriosController2 redController = new ThreeTriosController2(model, view1, redPlayerActions);
    ThreeTriosController2 blueController = new ThreeTriosController2(model, view2, bluePlayerActions);

    model.addModelStatusListener(redController);
    model.addModelStatusListener(blueController);

    redController.startGame();
    blueController.startGame();
  }
}