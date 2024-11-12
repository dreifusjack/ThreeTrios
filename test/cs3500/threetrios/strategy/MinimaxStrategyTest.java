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
 * Test class for the MinimaxStrategy class.
 */
public class MinimaxStrategyTest {

  private ThreeTriosModel model4x3;
  private ThreeTriosController controller4x3;
  private ThreeTriosModel model4x3EqualSides;
  private ThreeTriosController controller4x3EqualSides;
  private ThreeTriosModel model4x32Holes;
  private ThreeTriosController controller4x32Holes;
  private ThreeTriosStrategy cornerStrategy;
  private ThreeTriosStrategy maxinumFlipStrategy;
  private ThreeTriosStrategy minimizeFlipStrategy;
  private List<ThreeTriosStrategy> listCorner;
  private List<ThreeTriosStrategy> listMaximum;
  private List<ThreeTriosStrategy> listMinimum;
  private List<ThreeTriosStrategy> listCornerMaximum;
  private List<ThreeTriosStrategy> listCornerMinimum;
  private List<ThreeTriosStrategy> listAllStrategies;


  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model4x3 = new BasicThreeTriosModel(rand1);
    controller4x3 = new BasicThreeTriosController("world4x3.txt",
            "cards4x3.txt");

    model4x3EqualSides = new BasicThreeTriosModel(rand1);
    controller4x3EqualSides = new BasicThreeTriosController("world4x31hole.txt",
            "cards4x3emptyver2.txt");

    model4x32Holes = new BasicThreeTriosModel(rand1);
    controller4x32Holes = new BasicThreeTriosController("world4x32holes.txt",
            "cards4x3emptyver2.txt");

    cornerStrategy = new CornerStrategy();
    maxinumFlipStrategy = new MaximizeFlipsStrategy();
    minimizeFlipStrategy = new MinimizeFlipsStrategy();
    listCorner = List.of(cornerStrategy);
    listMaximum = List.of(maxinumFlipStrategy);
    listMinimum = List.of(minimizeFlipStrategy);
    listCornerMaximum = List.of(cornerStrategy, maxinumFlipStrategy);
    listCornerMinimum = List.of(cornerStrategy, minimizeFlipStrategy);
    listAllStrategies = List.of(cornerStrategy, maxinumFlipStrategy, minimizeFlipStrategy);

  }

  // Test when the opponent use MaximizeFlip, it put the opponent by not playing on col 2
  @Test
  public void testMaximizeFlip() {
    controller4x3EqualSides.playGame(model4x3EqualSides);

    model4x3EqualSides.playToGrid(0, 0, 0);

    model4x3EqualSides.playToGrid(1, 2, 0);

    model4x3EqualSides.playToGrid(1, 0, 0);

    System.out.println(new MinimaxStrategy(listMaximum).findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listMaximum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MinimaxStrategy(listMaximum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimaxStrategy(listMaximum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getHandInx());
  }

  // Test when the opponent use CornerStrategy
  // The only corner left is (0,0), and play to this place will leave the opponent at a bad place
  // as all the cards won't be able to flip any of the other cards on the board (number of cards
  // blue can make with the corner strategy after this is 0.
  @Test
  public void testCornerStrategy() {
    controller4x3.playGame(model4x3);

    model4x3.playToGrid(3, 1, 1);
    model4x3.playToGrid(1 ,0 ,1);
    model4x3.playToGrid(2, 2, 2);
    model4x3.playToGrid(1, 1, 0);

    System.out.println(new MinimaxStrategy(listCorner)
            .findBestMove(model4x3, model4x3.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listCorner).findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MinimaxStrategy(listCorner).findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimaxStrategy(listCorner).findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getHandInx());
  }

  // Test when the opponent use MinimizeFlipsStrategy
  @Test
  public void testMinimizeFlipsStrategy() {
    controller4x3EqualSides.playGame(model4x3EqualSides);

    model4x3EqualSides.playToGrid(3, 2, 0);
    model4x3EqualSides.playToGrid(0, 1, 2);
    model4x3EqualSides.playToGrid(2, 0, 1);


    System.out.println(new MinimaxStrategy(listMinimum).findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listMinimum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimaxStrategy(listMinimum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimaxStrategy(listMinimum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getHandInx());
  }

  // Test with a list of corner and maximum strategies as input list. (corner and maxumum both
  // return (0, 2) and hand index of 1 as the best move after we chose a move (2, 0, 1) from the
  //minimax strategy
  @Test
  public void testMaximumAndCorner() {
    controller4x3EqualSides.playGame(model4x3EqualSides);

    model4x3EqualSides.playToGrid(3, 2, 0);
    model4x3EqualSides.playToGrid(0, 1, 2);
    model4x3EqualSides.playToGrid(2, 0, 1);


    System.out.println(new MinimaxStrategy(listCornerMaximum).findBestMove(model4x3EqualSides,
            model4x3EqualSides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listCornerMaximum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimaxStrategy(listCornerMaximum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimaxStrategy(listCornerMaximum)
            .findBestMove(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()).getHandInx());
  }

  // Test when the input list of strategies are corner + minimum. The best move to minimize the
  // impact of the opponent here is not (1,0) but (1, 2) as the corner would play to
  @Test
  public void testCornerMinimum() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);
    model4x32Holes.playToGrid(0, 2, 0);


    System.out.println(new MinimaxStrategy(listCornerMinimum)
            .findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimaxStrategy(listCornerMinimum)
            .findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new MinimaxStrategy(listCornerMinimum)
            .findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimaxStrategy(listCornerMinimum)
            .findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  //Test for all 3 strategies as input for Minimax
  @Test
  public void testAllStrategies() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);
    model4x32Holes.playToGrid(0, 2, 0);


    System.out.println(new MinimaxStrategy(listAllStrategies).findBestMove(model4x32Holes,
            model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimaxStrategy(listAllStrategies)
            .findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimaxStrategy(listAllStrategies)
            .findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimaxStrategy(listAllStrategies)
            .findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  // Test findBestMoveForChain
  @Test
  public void testMaximumAndCornerChain() {
    controller4x3EqualSides.playGame(model4x3EqualSides);

    model4x3EqualSides.playToGrid(3, 2, 0);
    model4x3EqualSides.playToGrid(0, 1, 2);
    model4x3EqualSides.playToGrid(2, 0, 1);


    System.out.println(new MinimaxStrategy(listCornerMaximum)
            .findBestMoveForChain(model4x3EqualSides, model4x3EqualSides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listCornerMaximum)
            .findBestMoveForChain(model4x3EqualSides,
                    model4x3EqualSides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimaxStrategy(listCornerMaximum)
            .findBestMoveForChain(model4x3EqualSides,
                    model4x3EqualSides.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimaxStrategy(listCornerMaximum)
            .findBestMoveForChain(model4x3EqualSides,
                    model4x3EqualSides.getCurrentPlayer()).getHandInx());
  }
}