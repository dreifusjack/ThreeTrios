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
import cs3500.threetrios.model.decorators.level2.SameModelDecorator;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

/**
 * Tests the fallen ace variant with same variant.
 */
public class FallenAceWithSameModelTest {

  private ThreeTriosListenerController redControllerSame;
  private ThreeTriosListenerController blueControllerSame;

  private SameModelDecorator modelSame;

  @Before
  public void setUp() {

    ThreeTriosModel modelBase = new BasicThreeTriosModel(new Random(2));

    List<PassThroughCardDecorator> fallenAceList = new ArrayList<>(
            List.of(new FallenAceCardDecorator()));

    VariantCardModelDecorator modelReverse = new VariantCardModelDecorator(
            modelBase, fallenAceList);

    modelSame = new SameModelDecorator(modelReverse);

    ThreeTriosSetupController setupControllerSame = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupControllerSame.playGame(modelSame);

    PlayerActions redPlayerActionsR = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActionsR = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redViewSame = new TTGUIView(modelSame);
    TTGUIView blueViewSame = new TTGUIView(modelSame);

    redControllerSame = new ThreeTriosListenerController(
            modelSame, redViewSame, redPlayerActionsR);
    blueControllerSame = new ThreeTriosListenerController(
            modelSame, blueViewSame, bluePlayerActionsR);
  }

  // Check if you can perform a flip with a 1 vs an A
  @Test
  public void fallenAce1WinsA() {
    redControllerSame.handleCardSelection(TeamColor.RED, 5);
    redControllerSame.handleBoardSelection(0, 0);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 5);
    blueControllerSame.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 1>A under FallenAce rule
  }

  // When the same variant did apply
  @Test
  public void testSameFLip() {
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

  // When the same variant did not apply
  @Test
  public void testSameNoFLip() {
    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(0, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerSame.handleBoardSelection(1, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.BLUE);
  }

  // Test for the normal logic of the game (a higher attack value card should flip the lesser attack
  // value card)
  @Test
  public void fallenAceTestOGRuleFlip() {
    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(0, 0);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerSame.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 7>4
  }

  // Test for the normal logic of the game (a lesser attack value card should not flip the higher
  // attack value card)
  @Test
  public void fallenAceTestOGRuleNoFlip() {
    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(0, 0);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerSame.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not flip since 1<4
  }

  // Rule apply and then the flipped cards will perform combo flips around them.
  @Test
  public void testSameThenTriggerComboFlip() {
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

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelSame.getGridReadOnly().get(2).get(2).getColor(), TeamColor.RED);
    // The card (2, 2) is now turn to RED after the card above it turned to RED and then applied
    // combo flip on it.
  }

  // Test when the same rule failed but then trigger fallen ace rule.
  @Test
  public void testPlusFailTriggerAce() {
    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(0, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerSame.handleBoardSelection(1, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.RED);
  }
}
