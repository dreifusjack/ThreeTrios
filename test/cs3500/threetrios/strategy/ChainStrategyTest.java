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
 * Test class for the ChainStrategy class.
 */
public class ChainStrategyTest {

  private ThreeTriosModel model4x3;
  private ThreeTriosController controller4x3;
  private ThreeTriosModel model4x3CornersWithHoles;
  private ThreeTriosController controller4x3CornersWithHoles;
  private ThreeTriosModel model4x32Holes;
  private ThreeTriosController controller4x32Holes;
  private ThreeTriosStrategy cornerStrategy;
  private ThreeTriosStrategy maxinumFlipStrategy;
  private ThreeTriosStrategy minimizeFlipStrategy;
  private List<ThreeTriosStrategy> listMaximum;
  private List<ThreeTriosStrategy> listCornerMaximum;
  private List<ThreeTriosStrategy> listCornerMinimum;
  private List<ThreeTriosStrategy> listMaximumMinimum;
  private List<ThreeTriosStrategy> listAllStrategies;


  @Before
  public void setUp() {
    Random rand1 = new Random(2);
    model4x3 = new BasicThreeTriosModel(rand1);
    controller4x3 = new BasicThreeTriosController("world4x3.txt",
            "cards4x3.txt");

    model4x3CornersWithHoles = new BasicThreeTriosModel(rand1);
    controller4x3CornersWithHoles = new BasicThreeTriosController(
            "world4x3cornerswithholes.txt", "cards3x3ver2.txt");

    model4x32Holes = new BasicThreeTriosModel(rand1);
    controller4x32Holes = new BasicThreeTriosController("world4x32holes.txt",
            "cards4x3emptyver2.txt");

    cornerStrategy = new CornerStrategy();
    maxinumFlipStrategy = new MaximizeFlipsStrategy();
    minimizeFlipStrategy = new MinimizeFlipsStrategy();
    listMaximum = List.of(maxinumFlipStrategy);
    listCornerMaximum = List.of(cornerStrategy, maxinumFlipStrategy);
    listCornerMinimum = List.of(cornerStrategy, minimizeFlipStrategy);
    listMaximumMinimum = List.of(maxinumFlipStrategy, minimizeFlipStrategy);
    listAllStrategies = List.of(cornerStrategy, maxinumFlipStrategy, minimizeFlipStrategy);

  }

  //Test for list of 1 strategy (maximizeflip strategy)
  @Test
  public void testMaximize() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);
    model4x32Holes.playToGrid(0, 2, 0);


    System.out.println(new ChainStrategy(listMaximum).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(1, new ChainStrategy(listMaximum).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new ChainStrategy(listMaximum).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(2, new ChainStrategy(listMaximum).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  //Test for list of 2 strategies (corner, maximizeflip) (CornerStrategy found a move)
  @Test
  public void testMaximizeCorner() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);


    System.out.println(new ChainStrategy(listCornerMaximum).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(0, new ChainStrategy(listCornerMaximum).findBestMove(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new ChainStrategy(listCornerMaximum).findBestMove(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new ChainStrategy(listCornerMaximum).findBestMove(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  //Test for list of 3 strategies (corner, maximizeflip, minimizeflip
  @Test
  public void testAllStrategies() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);


    System.out.println(new ChainStrategy(listAllStrategies).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(0, new ChainStrategy(listAllStrategies).findBestMove(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new ChainStrategy(listAllStrategies).findBestMove(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new ChainStrategy(listAllStrategies).findBestMove(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  // Test with 4 strategies with Minimax has nested strategies
  @Test
  public void testComplexWithNestedMinimax() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);

    List<ThreeTriosStrategy> complexList = List.of(new MinimaxStrategy(listMaximum),
            minimizeFlipStrategy, maxinumFlipStrategy, cornerStrategy);

    System.out.println(new ChainStrategy(complexList).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(0, new ChainStrategy(complexList).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new ChainStrategy(complexList).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new ChainStrategy(complexList).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  // Put MaximumFlipStrategy at the front of the list, won't ever go to the other
  @Test
  public void testMaximumDominates() {
    controller4x3.playGame(model4x3);

    model4x3.playToGrid(0, 0, 0);


    System.out.println(new ChainStrategy(listMaximumMinimum).findBestMove(model4x3,
            model4x3.getCurrentPlayer()));
    Assert.assertEquals(0, new ChainStrategy(listMaximumMinimum).findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new ChainStrategy(listMaximumMinimum).findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new ChainStrategy(listMaximumMinimum).findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getHandInx());
  }

  // CornerStrategy returns null so process the MinimumFlipStrategy
  @Test
  public void testCorner() {
    controller4x3CornersWithHoles.playGame(model4x3CornersWithHoles);

    System.out.println(new ChainStrategy(listCornerMinimum).findBestMove(model4x3CornersWithHoles,
            model4x3CornersWithHoles.getCurrentPlayer()));
    Assert.assertEquals(0, new ChainStrategy(listMaximumMinimum).findBestMove(
            model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new ChainStrategy(listMaximumMinimum).findBestMove(
            model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new ChainStrategy(listMaximumMinimum).findBestMove(
            model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getHandInx());
  }

  // Test findBestMoveForChain it should produce the same result as findBestMove
  @Test
  public void testAllStrategiesChain() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);


    System.out.println(new ChainStrategy(listAllStrategies).findBestMoveForChain(model4x32Holes,
            model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(0, new ChainStrategy(listAllStrategies).findBestMoveForChain(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new ChainStrategy(listAllStrategies).findBestMoveForChain(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new ChainStrategy(listAllStrategies).findBestMoveForChain(
            model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

}