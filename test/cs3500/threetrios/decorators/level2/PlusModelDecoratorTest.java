package cs3500.threetrios.decorators.level2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.level2.PlusModelDecorator;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

/**
 * Tests our Plus variant from level 2.
 */
public class PlusModelDecoratorTest {

  private ThreeTriosListenerController redControllerPlus;
  private ThreeTriosListenerController blueControllerPlus;

  private PlusModelDecorator modelPlus;

  @Before
  public void setUp() {

    ThreeTriosModel modelBase = new BasicThreeTriosModel(new Random(2));

    modelPlus = new PlusModelDecorator(modelBase);

    ThreeTriosSetupController setupControllerSame = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupControllerSame.playGame(modelPlus);

    PlayerActions redPlayerActionsR = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActionsR = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redViewPlus = new TTGUIView(modelPlus);
    TTGUIView blueViewPlus = new TTGUIView(modelPlus);

    redControllerPlus = new ThreeTriosListenerController(
            modelPlus, redViewPlus, redPlayerActionsR);
    blueControllerPlus = new ThreeTriosListenerController(
            modelPlus, blueViewPlus, bluePlayerActionsR);
  }


  // When there is more than 2 pairs of cards that have the same sum of attack value (in this case
  // is 7)
  @Test
  public void testNormalCase() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 0);
    redControllerPlus.handleBoardSelection(3, 1);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerPlus.handleBoardSelection(1, 2);

    redControllerPlus.handleCardSelection(TeamColor.RED, 0);
    redControllerPlus.handleBoardSelection(3, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 3);
    blueControllerPlus.handleBoardSelection(0, 1);

    redControllerPlus.handleCardSelection(TeamColor.RED, 0);
    redControllerPlus.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(1).get(2).getColor(), TeamColor.RED);
    // Sum of each of these pair is 7 (and there are 2 pairs so the rule applied and flipped
    // adjacent cards
  }

  // When there is more than 2 pairs of cards (1 has the same color as the placed card)
  // that have the same sum of attack value (in this case is 7)
  @Test
  public void testNormalCaseVer2() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 0);
    redControllerPlus.handleBoardSelection(0, 1);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerPlus.handleBoardSelection(1, 0);

    redControllerPlus.handleCardSelection(TeamColor.RED, 1);
    redControllerPlus.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(1).get(0).getColor(), TeamColor.RED);
    // This used to be BLUE but after the rule applied, it became RED.
  }

  // Test to see that when the rule applied, the flipped adjacent cards will also perform combo
  // flip around them
  @Test
  public void testPLusThenComboFlip() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 0);
    redControllerPlus.handleBoardSelection(0, 1);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerPlus.handleBoardSelection(1, 0);

    redControllerPlus.handleCardSelection(TeamColor.RED, 3);
    redControllerPlus.handleBoardSelection(3, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerPlus.handleBoardSelection(2, 0);

    redControllerPlus.handleCardSelection(TeamColor.RED, 1);
    redControllerPlus.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(1).get(0).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(2).get(0).getColor(), TeamColor.RED);

    // The card (2, 0) is now RED  as the flipped card above it turned to RED and then
    // performed the combo flip on it.
  }

  // Test when there are not more than 2 pair os adjacent cards with similar sum then the rule
  // won't be apply (won't flip)
  @Test
  public void testPlusNoFlip() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 5);
    redControllerPlus.handleBoardSelection(0, 1);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerPlus.handleBoardSelection(1, 0);

    redControllerPlus.handleCardSelection(TeamColor.RED, 1);
    redControllerPlus.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(1).get(0).getColor(), TeamColor.BLUE);
    // This card is still blue because the rule failed to be applied.
  }






}