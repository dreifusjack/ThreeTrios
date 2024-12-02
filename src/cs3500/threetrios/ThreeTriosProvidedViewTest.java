package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosFeaturesController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.MaximizeFlipsStrategy;
import cs3500.threetrios.provider.controller.AdapterFeatureController;
import cs3500.threetrios.provider.model.ModelAdapter;
import cs3500.threetrios.provider.model.PlayerType;
import cs3500.threetrios.provider.view.SimpleThreeTriosView;
import cs3500.threetrios.view.TTGUIView;

public class ThreeTriosProvidedViewTest {

  public static void main(String[] args) {
    ThreeTriosModel model = new BasicThreeTriosModel(new Random(2));

    ThreeTriosSetupController setupController = new ThreeTriosSetupController("world4x3manyholes.txt",
            "cardsopponentweak.txt");
    setupController.playGame(model);

    cs3500.threetrios.provider.model.ThreeTriosModel modelNew = new ModelAdapter(model);

    SimpleThreeTriosView view2 = new SimpleThreeTriosView(modelNew);


    view2.updateView();

    view2.setVisible(true);



//    PlayerActions redPlayerActions = new HumanPlayer(TeamColor.RED);
//    PlayerActions bluePlayerActions2 = new HumanPlayer(TeamColor.BLUE);

//    PlayerActions redPlayerActions = new AIPlayer(TeamColor.RED, new CornerStrategy());
//    PlayerActions bluePlayerActions = new AIPlayer(TeamColor.BLUE, new MaximizeFlipsStrategy());

//    ThreeTriosFeaturesController redController = new ThreeTriosFeaturesController(model, view1, redPlayerActions);
//    AdapterFeatureController blueController = new AdapterFeatureController(modelNew, view2, bluePlayerActions2);


  }
}
