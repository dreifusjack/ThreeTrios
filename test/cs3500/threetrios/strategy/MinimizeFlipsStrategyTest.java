package cs3500.threetrios.strategy;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.BasicThreeTriosController;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Test class for the MinimizeFlipsStrategy class.
 */
public class MinimizeFlipsStrategyTest {

  private ThreeTriosModel model4x3Empty;
  private ThreeTriosController controller4x3Empty;
  private ThreeTriosModel model4x3EmptyVer2;
  private ThreeTriosController controller4x3EmptyVer2;
  private ThreeTriosModel model4x3EqualSides;
  private ThreeTriosController controller4x3EqualSides;
  private ThreeTriosModel model4x31Hole;
  private ThreeTriosController controller4x31Hole;
  private ThreeTriosModel modelManyHoles;
  private ThreeTriosController controllerManyHoles;
  private ThreeTriosStrategy cornerStrategy;
  private ThreeTriosStrategy maxinumFlipStrategy;
  private ThreeTriosStrategy minimizeFlipStrategy;


  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model4x3Empty = new BasicThreeTriosModel(rand1);
    controller4x3Empty = new BasicThreeTriosController("world4x3empty.txt",
            "cards4x3empty.txt");

    model4x3EmptyVer2 = new BasicThreeTriosModel(rand1);
    controller4x3EmptyVer2 = new BasicThreeTriosController("world4x3empty.txt",
            "cards4x3emptyver2.txt");

    model4x3EqualSides = new BasicThreeTriosModel(rand1);
    controller4x3EqualSides = new BasicThreeTriosController("world4x31hole.txt",
            "cards4x3emptyver2.txt");

    model4x31Hole = new BasicThreeTriosModel(rand1);
    controller4x31Hole = new BasicThreeTriosController("world4x31hole.txt",
            "cards4x3nobestmove.txt");

    modelManyHoles = new BasicThreeTriosModel(rand1);
    controllerManyHoles = new BasicThreeTriosController("world4x3manyholes.txt",
            "cardsopponentweak.txt");

    cornerStrategy = new CornerStrategy();
    maxinumFlipStrategy = new MaximizeFlipsStrategy();
    minimizeFlipStrategy = new MinimizeFlipsStrategy();
  }

  //Break ties, choose a card that it upper-most, left most
  @Test
  public void testBreakTie() {
    controller4x3Empty.playGame(model4x3Empty);

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3Empty,
            model4x3Empty.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3Empty,
            model4x3Empty.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3Empty,
            model4x3Empty.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimizeFlipsStrategy().findBestMove(model4x3Empty,
            model4x3Empty.getCurrentPlayer()).getHandInx());
  }

  // Find the best move without breaks tie. Best move only has 1 open side
  @Test
  public void testFindBestMoveWith1Opening() {
    controller4x3EmptyVer2.playGame(model4x3EmptyVer2);

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3EmptyVer2,
            model4x3EmptyVer2.getCurrentPlayer()));
    Assert.assertEquals(3, new MinimizeFlipsStrategy().findBestMove(model4x3EmptyVer2,
            model4x3EmptyVer2.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new MinimizeFlipsStrategy().findBestMove(model4x3EmptyVer2,
            model4x3EmptyVer2.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3EmptyVer2,
            model4x3EmptyVer2.getCurrentPlayer()).getHandInx());
  }

  // Find the best move without breaks tie. Each cell has 2 opening sides.
  @Test
  public void testFindBestMoveEqualOpening() {
    controller4x3EqualSides.playGame(model4x3EqualSides);

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()).getCol());
    Assert.assertEquals(5, new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()).getHandInx());
  }

  // When there is no best move
  @Test
  public void testNoBestMove() {
    controller4x31Hole.playGame(model4x31Hole);

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x31Hole,
            model4x31Hole.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x31Hole,
            model4x31Hole.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x31Hole,
            model4x31Hole.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x31Hole,
            model4x31Hole.getCurrentPlayer()).getHandInx());
  }

  //Opponent has no flippable cards.
  // (this is also the tie break case and some of the first cell are holes)
  @Test
  public void testOpponentWeakCards() {
    controllerManyHoles.playGame(modelManyHoles);

    System.out.println(new MinimizeFlipsStrategy().findBestMove(modelManyHoles,
            modelManyHoles.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimizeFlipsStrategy().findBestMove(modelManyHoles,
            modelManyHoles.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new MinimizeFlipsStrategy().findBestMove(modelManyHoles,
            modelManyHoles.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(modelManyHoles,
            modelManyHoles.getCurrentPlayer()).getHandInx());
  }

  //All cells are occupied


  // Do the strategy when mid game. (With some occupied cells + holes)
  @Test
  public void testMidGame() {
    controller4x3EqualSides.playGame(model4x3EqualSides);

    model4x3EqualSides.playToGrid(3, 2, 0);
    model4x3EqualSides.playToGrid(0, 1, 2);
    model4x3EqualSides.playToGrid(2, 0, 1);


    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()).getHandInx());
  }

  // Test findBestMoveForChain
  @Test
  public void testMidGameChain() {
    controller4x3EqualSides.playGame(model4x3EqualSides);

    model4x3EqualSides.playToGrid(3, 2, 0);
    model4x3EqualSides.playToGrid(0, 1, 2);
    model4x3EqualSides.playToGrid(2, 0, 1);


    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimizeFlipsStrategy()
            .findBestMoveForChain(model4x3EqualSides,
                    model4x3EqualSides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy()
            .findBestMoveForChain(model4x3EqualSides,
                    model4x3EqualSides.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimizeFlipsStrategy()
            .findBestMoveForChain(model4x3EqualSides,
                    model4x3EqualSides.getCurrentPlayer()).getHandInx());
  }
}