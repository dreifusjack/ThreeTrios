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
import cs3500.threetrios.model.decorators.level2.SameModelDecorator;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

/**
 * Tests the reverse variant with same variant.
 */
public class ReverseAceWithSameModelTest {

  private ThreeTriosListenerController redControllerSame;
  private ThreeTriosListenerController blueControllerSame;

  private SameModelDecorator modelSame;

  @Before
  public void setUp() {

    ThreeTriosModel modelBase = new BasicThreeTriosModel(new Random(2));

    List<PassThroughCardDecorator> reverseAndAceList = new ArrayList<>(List.of(
            new ReverseCardDecorator(), new FallenAceCardDecorator()));

    VariantCardModelDecorator modelReverse = new VariantCardModelDecorator(
            modelBase, reverseAndAceList);

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

  // Test to see that the variant for Same model will trigger
  @Test
  public void testForSameFlip() {
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

  // Test to see that the variant for Same model won't trigger
  @Test
  public void testForSameNoFlip() {
    redControllerSame.handleCardSelection(TeamColor.RED, 0);
    redControllerSame.handleBoardSelection(0, 1);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerSame.handleBoardSelection(1, 2);

    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(1, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(1).getColor(), TeamColor.RED);
    Assert.assertEquals(this.modelSame.getGridReadOnly().get(1).get(2).getColor(), TeamColor.BLUE);
  }

  // Test to see under Reverse and FallenAce, a higher attack value won't flip a lower attack value
  @Test
  public void bothReverseDoesNotFlip() {
    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(0, 0);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerSame.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not change color since 7>4
  }

  // Test to see under Reverse and FallenAce, a lower attack value won't flip a higher attack value
  @Test
  public void bothReverseDoesFlip() {
    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(0, 0);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerSame.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Should flip since 1<4
  }

  // Test if a 1 will flip an A (due to FallenAce being applied later)
  @Test
  public void bothTest1FlipA() {
    redControllerSame.handleCardSelection(TeamColor.RED, 5);
    redControllerSame.handleBoardSelection(0, 0);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 5);
    blueControllerSame.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 1>A under FallenAce rule
  }

  // Test if an A won't flip a 1 (due to FallenAce being applied later)
  @Test
  public void bothTestANoFlip1() {
    redControllerSame.handleCardSelection(TeamColor.RED, 1);
    redControllerSame.handleBoardSelection(0, 0);

    blueControllerSame.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerSame.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelSame.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does flip since 1>A under FallenAce rule
  }
}
