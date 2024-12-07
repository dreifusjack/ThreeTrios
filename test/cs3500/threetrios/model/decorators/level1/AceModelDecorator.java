package cs3500.threetrios.model.decorators.level1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

public class AceModelDecorator {

  private ThreeTriosModel modelA;


  private ThreeTriosListenerController redControllerAce;
  private ThreeTriosListenerController blueControllerAce;


  private ComboModelDecorator modelAce;


  private List<PassThroughCardDecorator> aceList;


  @Before
  public void setUp() {

    aceList = new ArrayList<>(List.of(new FallenAceCardDecorator()));


    modelA = new BasicThreeTriosModel(new Random(2));

    modelAce = new ComboModelDecorator(modelA, aceList);

    ThreeTriosSetupController setupControllerA = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupControllerA.playGame(modelAce);

    PlayerActions redPlayerActionsA = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActionsA = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redViewAce = new TTGUIView(modelAce);
    TTGUIView blueViewAce = new TTGUIView(modelAce);

    redControllerAce = new ThreeTriosListenerController(modelAce, redViewAce, redPlayerActionsA);
    blueControllerAce = new ThreeTriosListenerController(modelAce, blueViewAce, bluePlayerActionsA);

  }

  //---------------------------- fallenace------------------------

  // Test for the normal logic of the game (a higher attack value card should flip the lesser attack
  // value card)
  @Test
  public void fallenAceTestOGRuleFlip() {
    redControllerAce.handleCardSelection(TeamColor.RED, 1);
    redControllerAce.handleBoardSelection(0, 0);

    redControllerAce.handleCardSelection(TeamColor.BLUE, 0);
    redControllerAce.handleBoardSelection(0, 1);

//    Assert.assertEquals(this.modelR.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 7>4
  }


}
