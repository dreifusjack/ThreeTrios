package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.controller.ThreeTriosFeaturesController;

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

    ThreeTriosSetupController setupController =
            new ThreeTriosSetupController(
                    "world4x3manyholes.txt",
                    "cardsopponentweak.txt");

    setupController.playGame(model);

    TTGUIView redView = new TTGUIView(model);
    TTGUIView blueView = new TTGUIView(model);

    PlayerActions redPlayerActions = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActions = new HumanPlayer(TeamColor.BLUE);

    // AIPlayer bluePlayerActions = new AIPlayer(TeamColor.BLUE, new CornerStrategy());

    ThreeTriosFeaturesController redController = new ThreeTriosFeaturesController(model, redView, redPlayerActions);
    ThreeTriosFeaturesController blueController = new ThreeTriosFeaturesController(model, blueView, bluePlayerActions);
  }
}