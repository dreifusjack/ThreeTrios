package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.decorators.level1.ComboModelDecorator;
import cs3500.threetrios.model.decorators.level1.FallenAceCardDecorator;
import cs3500.threetrios.model.decorators.level1.PassThroughCardDecorator;
import cs3500.threetrios.model.decorators.level1.ReverseCardDecorator;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.MaximizeFlipsStrategy;
import cs3500.threetrios.provider.controller.AdapterListenerController;
import cs3500.threetrios.provider.model.ModelAdapter;
import cs3500.threetrios.provider.model.ThreeTriosModel;
import cs3500.threetrios.provider.view.SimpleThreeTriosView;
import cs3500.threetrios.view.TTGUIView;

public class ThreeTrios3 {

  public static void main(String[] args) {

     ThreeTriosListenerController redController;
     ThreeTriosListenerController blueController;


     ComboModelDecorator modelAce;

     ComboModelDecorator modelBoth;

     List<PassThroughCardDecorator> reverseList = new ArrayList<>(List.of(new ReverseCardDecorator(), new FallenAceCardDecorator()));

     List<PassThroughCardDecorator> aceList = new ArrayList<>(List.of(new FallenAceCardDecorator()));

     List<PassThroughCardDecorator> bothList = new ArrayList<>();

      bothList.add(new ReverseCardDecorator());
      bothList.add(new FallenAceCardDecorator());

      BasicThreeTriosModel model = new BasicThreeTriosModel(new Random(2));

      ComboModelDecorator modelReverse = new ComboModelDecorator(model, reverseList);
//      modelAce = new ComboModelDecorator(model, aceList);
//      modelBoth = new ComboModelDecorator(model, bothList);


      ThreeTriosSetupController setupController = new ThreeTriosSetupController(
              "worldbig.txt",
              "bigcards.txt");
      setupController.playGame(modelReverse);

      PlayerActions redPlayerActions = new HumanPlayer(TeamColor.RED);
      PlayerActions bluePlayerActions = new HumanPlayer(TeamColor.BLUE);

      TTGUIView redView = new TTGUIView(modelReverse);
      TTGUIView blueView = new TTGUIView(modelReverse);

      ThreeTriosListenerController controllerRed = new ThreeTriosListenerController(modelReverse, redView, redPlayerActions);
      ThreeTriosListenerController controllerBlue = new ThreeTriosListenerController(modelReverse, blueView, bluePlayerActions);
  }
}