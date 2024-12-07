package cs3500.threetrios.model.decorators.level2;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.PassThroughModelDecorator;
import cs3500.threetrios.model.decorators.level1.PassThroughCardDecorator;
import cs3500.threetrios.model.decorators.level1.VariantCardModelDecorator;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

public class SameModelDecoratorTest {

  private ThreeTriosModel modelBase;

  private ThreeTriosListenerController redControllerSame;
  private ThreeTriosListenerController blueControllerSame;

  private SameModelDecorator modelSame;

  @Before
  public void setUp() {

    modelBase = new BasicThreeTriosModel(new Random(2));

    modelSame = new SameModelDecorator(modelBase);

    ThreeTriosSetupController setupControllerSame = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupControllerSame.playGame(modelSame);

    PlayerActions redPlayerActionsR = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActionsR = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redViewSame = new TTGUIView(modelSame);
    TTGUIView blueViewSame = new TTGUIView(modelSame);

    redControllerSame = new ThreeTriosListenerController(modelSame, redViewSame, redPlayerActionsR);
    blueControllerSame = new ThreeTriosListenerController(modelSame, blueViewSame, bluePlayerActionsR);

  }



  // When both adjacent cards are Blue and have the same attack value with the RED card. (1, 1) and
  // (3, 3)
  @Test
  public void testSameNormalCase() {
//    redControllerSame.handleCardSelection(TeamColor.RED, 0);
//    redControllerSame.handleBoardSelection(3, 1);
//
//    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
//    blueControllerSame.handleBoardSelection(1, 2);
//
//    redControllerSame.handleCardSelection(TeamColor.RED, 0);
//    redControllerSame.handleBoardSelection(3, 0);
//
//    blueControllerSame.handleCardSelection(TeamColor.BLUE, 3);
//    blueControllerSame.handleBoardSelection(0, 1);
//
//    redControllerSame.handleCardSelection(TeamColor.RED, 0);
//    redControllerSame.handleBoardSelection(1, 1);
//
//    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
//    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.RED);

    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(3, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerSame.handleBoardSelection(0, 1);

    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(3, 0);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerSame.handleBoardSelection(1, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.RED);
    // Both used to be blue
  }


  // When 2 adjacent cards (1 blue and 1 red) and they have the same attack value with the
  // RED card. (1, 1) and (3, 3).
  @Test
  public void testSameNormalCaseVer2() {
//    redControllerSame.handleCardSelection(TeamColor.RED, 0);
//    redControllerSame.handleBoardSelection(3, 1);
//
//    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
//    blueControllerSame.handleBoardSelection(1, 2);
//
//    redControllerSame.handleCardSelection(TeamColor.RED, 0);
//    redControllerSame.handleBoardSelection(3, 0);
//
//    blueControllerSame.handleCardSelection(TeamColor.BLUE, 3);
//    blueControllerSame.handleBoardSelection(0, 1);
//
//    redControllerSame.handleCardSelection(TeamColor.RED, 0);
//    redControllerSame.handleBoardSelection(1, 1);
//
//    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
//    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.RED);

    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(0, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerSame.handleBoardSelection(2, 1);

    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(3, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerSame.handleBoardSelection(1, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.RED);
    // One of them used to be blue
  }


  // Cannot apply the rule (can't flip since 1 pair of sides are not equal)
  @Test
  public void testSameCannotFlip1Side() {
    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(0, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerSame.handleBoardSelection(1, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.BLUE);
  }

  // Cannot apply the rule (can't flip since 2 pair of sides are not equal)
  @Test
  public void testSameCannotFlip2Sides() {
    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(0, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 2);
    blueControllerSame.handleBoardSelection(1, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.BLUE);
  }


  // Rule apply and then the flipped cards won't perform combo flips
  @Test
  public void testSameNoTriggerComboFlip() {
    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(0, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 5);
    blueControllerSame.handleBoardSelection(1, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 4);
    redControllerSame.handleBoardSelection(3, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerSame.handleBoardSelection(2, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(2).get(2).getColor(), TeamColor.BLUE);
    // This card is still because even though adjacent card's south is 6 (RED) and it's north is 4.
  }





}