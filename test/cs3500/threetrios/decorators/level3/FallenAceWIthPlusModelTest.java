package cs3500.threetrios.decorators.level3;

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
import cs3500.threetrios.model.decorators.level1.FallenAceCardDecorator;
import cs3500.threetrios.model.decorators.level1.PassThroughCardDecorator;
import cs3500.threetrios.model.decorators.level1.VariantCardModelDecorator;
import cs3500.threetrios.model.decorators.level2.PlusModelDecorator;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

/**
 * Tests the fallen ace variant with plus variant.
 */
public class FallenAceWIthPlusModelTest {

  private ThreeTriosListenerController redControllerPlus;
  private ThreeTriosListenerController blueControllerPlus;

  private PlusModelDecorator modelPlus;

  @Before
  public void setUp() {

    ThreeTriosModel modelBase = new BasicThreeTriosModel(new Random(2));

    List<PassThroughCardDecorator> fallenAceList = new ArrayList<>(List.of(new FallenAceCardDecorator()));

    VariantCardModelDecorator modelReverse = new VariantCardModelDecorator(modelBase, fallenAceList);


    modelPlus = new PlusModelDecorator(modelReverse);

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

  // Check if you can perform a flip with a 1 vs an A
  @Test
  public void fallenAce1WinsA() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 5);
    redControllerPlus.handleBoardSelection(0, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 5);
    blueControllerPlus.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 1>A under FallenAce rule
  }

  // Test for a case that trigger the rule for Same model.
  @Test
  public void testForPlusModelFlip() {
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

  // Test for a case that won't trigger the rule for Same model.
  @Test
  public void testForPlusModelNoFlip() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 0);
    redControllerPlus.handleBoardSelection(0, 1);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerPlus.handleBoardSelection(1, 0);

    redControllerPlus.handleCardSelection(TeamColor.RED, 0);
    redControllerPlus.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(1).get(0).getColor(), TeamColor.BLUE);
    // This card is still blue because the rule failed to be applied.
  }

  // Test for the normal logic of the game (a higher attack value card should flip the lesser attack
  // value card)
  @Test
  public void fallenAceTestOGRuleFlip() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 1);
    redControllerPlus.handleBoardSelection(0, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerPlus.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 7>4
  }

  // Test for the normal logic of the game (a lesser attack value card should not flip the higher
  // attack value card)
  @Test
  public void fallenAceTestOGRuleNoFlip() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 1);
    redControllerPlus.handleBoardSelection(0, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerPlus.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not flip since 1<4
  }

  // Check to see if the combo flip still applied for these rules.
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
}
