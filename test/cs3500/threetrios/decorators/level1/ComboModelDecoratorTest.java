package cs3500.threetrios.decorators.level1;

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
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

/**
 * Tests our decorators for level 1, both individually and together.
 */
public class ComboModelDecoratorTest {

  private ThreeTriosModel modelR;
  private ThreeTriosModel modelA;
  private ThreeTriosModel modelRA;

  private ThreeTriosModel modelAR;
  private ThreeTriosListenerController redControllerReverse;
  private ThreeTriosListenerController blueControllerReverse;

  private ThreeTriosListenerController redControllerAce;
  private ThreeTriosListenerController blueControllerAce;

  private ThreeTriosListenerController redControllerRA;
  private ThreeTriosListenerController blueControllerRA;

  private ThreeTriosListenerController redControllerAR;
  private ThreeTriosListenerController blueControllerAR;

  @Before
  public void setUp() {

    List<PassThroughCardDecorator> reverseList = new ArrayList<>(
            List.of(new ReverseCardDecorator()));
    List<PassThroughCardDecorator> aceList = new ArrayList<>(
            List.of(new FallenAceCardDecorator()));
    List<PassThroughCardDecorator> bothListRA = new ArrayList<>();
    bothListRA.add(new ReverseCardDecorator());
    bothListRA.add(new FallenAceCardDecorator());

    List<PassThroughCardDecorator> bothListAR = new ArrayList<>();
    bothListAR.add(new FallenAceCardDecorator());
    bothListAR.add(new ReverseCardDecorator());

    modelR = new BasicThreeTriosModel(new Random(2));


    VariantCardModelDecorator modelReverse = new VariantCardModelDecorator(modelR, reverseList);


    ThreeTriosSetupController setupControllerR = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupControllerR.playGame(modelReverse);

    PlayerActions redPlayerActionsR = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActionsR = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redViewReverse = new TTGUIView(modelReverse);
    TTGUIView blueViewReverse = new TTGUIView(modelReverse);

    redControllerReverse = new ThreeTriosListenerController(
            modelReverse, redViewReverse, redPlayerActionsR);
    blueControllerReverse = new ThreeTriosListenerController(
            modelReverse, blueViewReverse, bluePlayerActionsR);

    modelA = new BasicThreeTriosModel(new Random(2));

    VariantCardModelDecorator modelAce = new VariantCardModelDecorator(modelA, aceList);

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


    modelRA = new BasicThreeTriosModel(new Random(2));
    VariantCardModelDecorator modelReverseAce = new VariantCardModelDecorator(modelRA, bothListRA);
    ThreeTriosSetupController setupControllerRA = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupControllerRA.playGame(modelReverseAce);

    PlayerActions redPlayerActionsB = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActionsB = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redViewBoth = new TTGUIView(modelReverseAce);
    TTGUIView blueViewBoth = new TTGUIView(modelReverseAce);

    redControllerRA = new ThreeTriosListenerController(
            modelReverseAce, redViewBoth, redPlayerActionsB);
    blueControllerRA = new ThreeTriosListenerController(
            modelReverseAce, blueViewBoth, bluePlayerActionsB);



    modelAR = new BasicThreeTriosModel(new Random(2));
    VariantCardModelDecorator modelAceReverse = new VariantCardModelDecorator(modelAR, bothListAR);
    ThreeTriosSetupController setupControllerAR = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupControllerAR.playGame(modelAceReverse);

    PlayerActions redPlayerActionsAR = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActionsAR = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redViewAR = new TTGUIView(modelAceReverse);
    TTGUIView blueViewAR = new TTGUIView(modelAceReverse);

    redControllerAR = new ThreeTriosListenerController(
            modelAceReverse, redViewAR, redPlayerActionsAR);
    blueControllerAR = new ThreeTriosListenerController(
            modelAceReverse, blueViewAR, bluePlayerActionsAR);
  }

  //--------------------------- reverse--------------------------

  @Test
  public void reverseTestDoesNotFlip() {
    redControllerReverse.handleCardSelection(TeamColor.RED, 1);
    redControllerReverse.handleBoardSelection(0, 0);

    blueControllerReverse.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerReverse.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelR.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not change color since 7>4
  }

  @Test
  public void reverseDoesFlip() {
    redControllerReverse.handleCardSelection(TeamColor.RED, 1);
    redControllerReverse.handleBoardSelection(0, 0);

    blueControllerReverse.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerReverse.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelR.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Should flip since 1<4
  }

  @Test
  public void reverseDoesComboFlip() {
    redControllerReverse.handleCardSelection(TeamColor.RED, 0);
    redControllerReverse.handleBoardSelection(0, 0);

    blueControllerReverse.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerReverse.handleBoardSelection(2, 2);

    redControllerReverse.handleCardSelection(TeamColor.RED, 0);
    redControllerReverse.handleBoardSelection(1, 0);

    blueControllerReverse.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerReverse.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelR.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    Assert.assertEquals(this.modelR.getGridReadOnly().get(1).get(0).getColor(), TeamColor.BLUE);
    // Both used to be red.
  }

  //---------------------------- fallenace------------------------

  // Test for the normal logic of the game (a higher attack value card should flip the lesser attack
  // value card)
  @Test
  public void fallenAceTestOGRuleFlip() {
    redControllerAce.handleCardSelection(TeamColor.RED, 1);
    redControllerAce.handleBoardSelection(0, 0);

    blueControllerAce.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerAce.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelA.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 7>4
  }

  // Test for the normal logic of the game (a lesser attack value card should not flip the higher
  // attack value card)
  @Test
  public void fallenAceTestOGRuleNoFlip() {
    redControllerAce.handleCardSelection(TeamColor.RED, 1);
    redControllerAce.handleBoardSelection(0, 0);

    blueControllerAce.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerAce.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelA.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not flip since 1<4
  }

  // Test if a 1 can flip an A
  @Test
  public void fallenAce1WinsA() {
    redControllerAce.handleCardSelection(TeamColor.RED, 5);
    redControllerAce.handleBoardSelection(0, 0);

    blueControllerAce.handleCardSelection(TeamColor.BLUE, 5);
    blueControllerAce.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelA.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 1>A under FallenAce rule
  }

  @Test
  public void fallenAceComboFlip() {
    redControllerAce.handleCardSelection(TeamColor.RED, 5);
    redControllerAce.handleBoardSelection(0, 0);

    blueControllerAce.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerAce.handleBoardSelection(2, 2);

    redControllerAce.handleCardSelection(TeamColor.RED, 2);
    redControllerAce.handleBoardSelection(1, 0);

    blueControllerAce.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerAce.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelA.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    Assert.assertEquals(this.modelA.getGridReadOnly().get(1).get(0).getColor(), TeamColor.BLUE);
    // Both used to be red.
  }

  //----------------------------both (Reverse and then FallenAce) -----------------------------


  // Test to see under Reverse and FallenAce, a higher attack value won't flip a lower attack value
  @Test
  public void bothReverseDoesNotFlip() {
    redControllerRA.handleCardSelection(TeamColor.RED, 1);
    redControllerRA.handleBoardSelection(0, 0);

    blueControllerRA.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerRA.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelRA.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not change color since 7>4
  }

  // Test to see under Reverse and FallenAce, a lower attack value won't flip a higher attack value
  @Test
  public void bothReverseDoesFlip() {
    redControllerRA.handleCardSelection(TeamColor.RED, 1);
    redControllerRA.handleBoardSelection(0, 0);

    blueControllerRA.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerRA.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelRA.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Should flip since 1<4
  }

  // Test if a 1 will flip an A (due to FallenAce being applied later)
  @Test
  public void bothTest1FlipA() {
    redControllerRA.handleCardSelection(TeamColor.RED, 5);
    redControllerRA.handleBoardSelection(0, 0);

    blueControllerRA.handleCardSelection(TeamColor.BLUE, 5);
    blueControllerRA.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelRA.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since 1>A under FallenAce rule
  }

  // Test if an A won't flip a 1 (due to FallenAce being applied later)
  @Test
  public void bothTestANoFlip1() {
    redControllerRA.handleCardSelection(TeamColor.RED, 1);
    redControllerRA.handleBoardSelection(0, 0);

    blueControllerRA.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerRA.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelRA.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does flip since 1>A under FallenAce rule
  }

  //----------------------------both (FallenAce and then Reverse) -----------------------------


  // Test to see under Reverse and FallenAce, a higher attack value won't flip a lower attack value
  @Test
  public void ARReverseDoesNotFlip() {
    redControllerAR.handleCardSelection(TeamColor.RED, 1);
    redControllerAR.handleBoardSelection(0, 0);

    blueControllerAR.handleCardSelection(TeamColor.BLUE, 0);
    blueControllerAR.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelAR.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not change color since 7>4
  }

  // Test to see under Reverse and FallenAce, a lower attack value won't flip a higher attack value
  @Test
  public void ARReverseDoesFlip2() {
    redControllerAR.handleCardSelection(TeamColor.RED, 1);
    redControllerAR.handleBoardSelection(0, 0);

    blueControllerAR.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerAR.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelAR.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Should flip since 1<4
  }

  // Test if a 1 will flip an A (due to FallenAce being applied first)
  @Test
  public void ARTestAFlip1() {
    redControllerAR.handleCardSelection(TeamColor.RED, 2);
    redControllerAR.handleBoardSelection(0, 0);

    blueControllerAR.handleCardSelection(TeamColor.BLUE, 4);
    blueControllerAR.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelAR.getGridReadOnly().get(0).get(0).getColor(), TeamColor.BLUE);
    // Does flip since A>1 under FallenAce and the Reverse
  }

  // Test if an A won't flip a 1 (due to FallenAce being applied first)
  @Test
  public void ARTest1NoFlipA() {
    redControllerAR.handleCardSelection(TeamColor.RED, 5);
    redControllerAR.handleBoardSelection(0, 0);

    blueControllerAR.handleCardSelection(TeamColor.BLUE, 1);
    blueControllerAR.handleBoardSelection(0, 1);

    Assert.assertEquals(this.modelAR.getGridReadOnly().get(0).get(0).getColor(), TeamColor.RED);
    // Does not flip since 1<A under FallenAce and the Reverse
  }
}