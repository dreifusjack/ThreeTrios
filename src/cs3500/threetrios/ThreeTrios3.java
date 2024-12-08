package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.decorators.level1.FallenAceCardDecorator;
import cs3500.threetrios.model.decorators.level1.PassThroughCardDecorator;
import cs3500.threetrios.model.decorators.level1.ReverseCardDecorator;
import cs3500.threetrios.model.decorators.level1.VariantCardModelDecorator;
import cs3500.threetrios.model.decorators.level2.PlusModelDecorator;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

public class ThreeTrios3 {

  public static void main(String[] args) {

    ThreeTriosListenerController redController;
    ThreeTriosListenerController blueController;


    VariantCardModelDecorator modelAce;

    VariantCardModelDecorator modelBoth;

    List<PassThroughCardDecorator> reverseList = new ArrayList<>(List.of(new ReverseCardDecorator(), new FallenAceCardDecorator()));

    List<PassThroughCardDecorator> aceList = new ArrayList<>(List.of(new FallenAceCardDecorator()));

    List<PassThroughCardDecorator> bothList = new ArrayList<>();

    bothList.add(new ReverseCardDecorator());
    bothList.add(new FallenAceCardDecorator());

    BasicThreeTriosModel model = new BasicThreeTriosModel(new Random(2));


    VariantCardModelDecorator modelReverse = new VariantCardModelDecorator(model, reverseList);

    PlusModelDecorator modelSame = new PlusModelDecorator(modelReverse);

//      modelAce = new ComboModelDecorator(model, aceList);
//      modelBoth = new ComboModelDecorator(model, bothList);


    ThreeTriosSetupController setupController = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupController.playGame(modelSame);

    PlayerActions redPlayerActions = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActions = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redView = new TTGUIView(modelSame);
    TTGUIView blueView = new TTGUIView(modelSame);

    ThreeTriosListenerController controllerRed = new ThreeTriosListenerController(modelSame, redView, redPlayerActions);
    ThreeTriosListenerController controllerBlue = new ThreeTriosListenerController(modelSame, blueView, bluePlayerActions);
  }
}