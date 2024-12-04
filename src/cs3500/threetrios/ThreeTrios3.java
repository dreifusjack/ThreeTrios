package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.MaximizeFlipsStrategy;
import cs3500.threetrios.provider.controller.AdapterListenerController;
import cs3500.threetrios.provider.model.ModelAdapter;
import cs3500.threetrios.provider.view.SimpleThreeTriosView;
import cs3500.threetrios.view.TTGUIView;

public class ThreeTrios3 {

  public static void main(String[] args) {
    ThreeTriosModel model = new BasicThreeTriosModel(new Random(2));

    cs3500.threetrios.provider.model.ThreeTriosModel modelNew = new ModelAdapter(model);

    ThreeTriosSetupController setupController = new ThreeTriosSetupController("world4x3manyholes.txt",
            "cardsopponentweak.txt");
    setupController.playGame(model);

    TTGUIView view1 = new TTGUIView(model);
    TTGUIView view2 = new TTGUIView(model);

//    SimpleThreeTriosView view2 = new SimpleThreeTriosView(modelNew);

    PlayerActions redPlayerActions = new HumanPlayer(TeamColor.RED);

//    PlayerActions redPlayerActions = new AIPlayer(TeamColor.RED, new CornerStrategy());
    PlayerActions bluePlayerActions = new AIPlayer(TeamColor.BLUE, new MaximizeFlipsStrategy());

    ThreeTriosListenerController redController = new ThreeTriosListenerController(model, view1, redPlayerActions);
    ThreeTriosListenerController blueController = new ThreeTriosListenerController(model, view2, bluePlayerActions);

//    AdapterFeatureController blueController = new AdapterFeatureController(modelNew, view2, bluePlayerActions2);

  }
}