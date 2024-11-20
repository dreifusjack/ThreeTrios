package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.CornerStrategy;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.controller.ThreeTriosFeaturesController;

/**
 * Main runner class used to run a new game of ThreeTrios.
 */
public class ThreeTrios {
  /**
   * Runner that allows users to play a game of ThreeTrios!
   * Pass in arguments to specify the player types for the game.
   * "human" represents a human player.
   * "strategy1" represents an AIPlayer using the MaximizeFlipsStrategy.
   * "strategy2" represents an AIPlayer using the CornerStrategy.
   * "strategy3" represents an AIPlayer using the MinimizeFlipsStrategy.
   * "strategy4" represents an AIPlayer using the MinimaxStrategy.
   * "strategychain:strategyX,strategyY,..." represents an AIPlayer using a specified chain of
   * strategies, where each strategy is one of the above.
   * Specified arguments are applied iff two strings are enter that match the above options, else
   * defaults to human player vs. human player.
   *
   * @param args two strings, first specifying the RED player then the BLUE player
   */
  // TODO: implement args logic
  public static void main(String[] args) {
    // creates instance of model to be used (not yet playable)
    ThreeTriosModel model = new BasicThreeTriosModel(new Random(2));

    // creates instance of controller that reads configuration files to construct a playable model
    ThreeTriosSetupController setupController =
            new ThreeTriosSetupController(
                    "world4x3manyholes.txt",
                    "cardsopponentweak.txt");

    // constructs model to be playable
    setupController.playGame(model);

    // view setup for each player
    TTGUIView redView = new TTGUIView(model);
    TTGUIView blueView = new TTGUIView(model);

    // specified players
    PlayerActions redPlayerActions = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActions = new AIPlayer(TeamColor.BLUE, new CornerStrategy());

    // creates instance of controllers that are each responsible for representing a team,
    // features controllers maintain a playable game via controlling that MV with user interactions
    ThreeTriosFeaturesController redController =
            new ThreeTriosFeaturesController(model, redView, redPlayerActions);
    ThreeTriosFeaturesController blueController =
            new ThreeTriosFeaturesController(model, blueView, bluePlayerActions);
  }
}