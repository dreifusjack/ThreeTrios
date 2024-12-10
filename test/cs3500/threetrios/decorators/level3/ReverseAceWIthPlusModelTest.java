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
import cs3500.threetrios.model.decorators.level1.ReverseCardDecorator;
import cs3500.threetrios.model.decorators.level1.VariantCardModelDecorator;
import cs3500.threetrios.model.decorators.level2.PlusModelDecorator;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

/**
 * Tests the reverse variant with plus variant.
 */
public class ReverseAceWIthPlusModelTest {

  private ThreeTriosListenerController redControllerPlus;
  private ThreeTriosListenerController blueControllerPlus;

  private PlusModelDecorator modelPlus;

  @Before
  public void setUp() {

    ThreeTriosModel modelBase = new BasicThreeTriosModel(new Random(2));

    List<PassThroughCardDecorator> reverseAndAveList = new ArrayList<>(List.of(
            new ReverseCardDecorator(), new FallenAceCardDecorator()));

    VariantCardModelDecorator modelReverse = new VariantCardModelDecorator(modelBase, reverseAndAveList);


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

  // Test to see under Reverse and FallenAce, a higher attack value won't flip a lower attack value
  @Test
  public void bothReverseDoesNotFlip() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 1);
    redControllerPlus.handleBoardSelection(0, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerPlus.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not change color since 7>4
  }

  // Test to see under Reverse and FallenAce, a lower attack value won't flip a higher attack value
  @Test
  public void bothReverseDoesFlip() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 1);
    redControllerPlus.handleBoardSelection(0, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerPlus.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Should flip since 1<4
  }

  // Test if a 1 will flip an A (due to FallenAce being applied later)
  @Test
  public void bothTest1FlipA() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 5);
    redControllerPlus.handleBoardSelection(0, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 5);
    blueControllerPlus.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 1>A under FallenAce rule
  }

  // Test if an A won't flip a 1 (due to FallenAce being applied later)
  @Test
  public void bothTestANoFlip1() {
    redControllerPlus.handleCardSelection(TeamColor.RED, 1);
    redControllerPlus.handleBoardSelection(0, 0);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerPlus.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
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
    redControllerPlus.handleCardSelection(TeamColor.RED, 5);
    redControllerPlus.handleBoardSelection(0, 1);

    blueControllerPlus.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerPlus.handleBoardSelection(1, 0);

    redControllerPlus.handleCardSelection(TeamColor.RED, 0);
    redControllerPlus.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelPlus.getGridReadOnly().get(1).get(0).getColor(), TeamColor.BLUE);
    // This card is still blue because the rule failed to be applied.
  }
}
