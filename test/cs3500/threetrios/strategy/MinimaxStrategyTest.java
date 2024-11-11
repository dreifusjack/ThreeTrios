package cs3500.threetrios.strategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

public class MinimaxStrategyTest {
  protected ThreeTriosModel model5x7;
  protected ThreeTriosModel model2x2;
  protected ThreeTriosModel model4x3;
  protected ThreeTriosModel model4x3ver2;
  protected ThreeTriosModel model4x3ver3;
  protected ThreeTriosModel model4x3CornersWithHoles;
  protected ThreeTriosModel model4x3plain;
  protected ThreeTriosModel model4x3CornersWithHolesVer2;
  protected ThreeTriosModel model4x3CornersWithHolesVer3;

  protected ThreeTriosModel model4x3CornersWith2Holes;
  protected ThreeTriosModel model4x3Corner1SideHole;

  protected ThreeTriosModel modelWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;
  protected ThreeTriosModel model4x3empty;
  protected ThreeTriosModel model4x3emptyver2;
  protected ThreeTriosModel model4x3equalsides;
  protected ThreeTriosModel model4x31hole;
  protected ThreeTriosModel modelopponentweak;
  protected ThreeTriosModel modelmanyholes;
  protected ThreeTriosStrategy cornerstrategy;
  protected ThreeTriosStrategy maxinumflipstrategy;
  protected ThreeTriosStrategy minimizeflipstrategy;
  List<ThreeTriosStrategy> listcorner;
  List<ThreeTriosStrategy> listmaximum;
  List<ThreeTriosStrategy> listminimum;
  List<ThreeTriosStrategy> listcornermaximum;
  List<ThreeTriosStrategy> listcornerminimum;
  List<ThreeTriosStrategy> listmaximumminimum;
  List<ThreeTriosStrategy> listallstrategies;
  protected ThreeTriosModel model4x32holes;

  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model5x7 = new BasicThreeTriosModel("world1.txt", "card1.txt", rand1);
    model2x2 = new BasicThreeTriosModel("world2x2.txt", "cards2x2.txt", rand1);
    model4x3 = new BasicThreeTriosModel("world4x3.txt", "cards4x3.txt", rand1);
    model4x3ver2 = new BasicThreeTriosModel("world4x3.txt", "cards3x3ver2.txt", rand1);
    model4x3ver3 = new BasicThreeTriosModel("world4x3ver2.txt", "cards3x3ver2.txt", rand1);
    modelWithNotEnoughCards = new BasicThreeTriosModel("world4x3.txt",
            "3cardsonly.txt", rand1);
    model2x2SameValueOf1 = new BasicThreeTriosModel("world2x2ver2.txt",
            "cardswithsamevalueof1.txt", rand1);
    model2x2SameValueOf1Ver2 = new BasicThreeTriosModel("world2x2ver3.txt",
            "cardswithsamevalueof1.txt", rand1);

    model4x3CornersWithHoles = new BasicThreeTriosModel("world4x3cornerswithholes.txt", "cards3x3ver2.txt", rand1);
    model4x3CornersWithHolesVer2 = new BasicThreeTriosModel("world4x3cornerswithholesver2.txt", "cards3x3ver2.txt", rand1);
    model4x3CornersWithHolesVer3 = new BasicThreeTriosModel("world4x3cornerswithholesver2.txt", "cards4x3bestcardcorner.txt", rand1);
    model4x3CornersWith2Holes = new BasicThreeTriosModel("world4x3with2holescorners.txt", "cards4x3cornerswith2holes.txt", rand1);
    model4x3Corner1SideHole = new BasicThreeTriosModel("world4x3cornersspecial.txt", "cards4x3corners1sideholes.txt", rand1);
    model4x3plain = new BasicThreeTriosModel("world4x3plain.txt", "cards4x3.txt", rand1);
    model4x3empty = new BasicThreeTriosModel("world4x3empty.txt", "cards4x3empty.txt", rand1);
    model4x3emptyver2 = new BasicThreeTriosModel("world4x3empty.txt", "cards4x3emptyver2.txt", rand1);
    model4x3equalsides = new BasicThreeTriosModel("world4x31hole.txt", "cards4x3emptyver2.txt", rand1);
    model4x31hole = new BasicThreeTriosModel("world4x31hole.txt", "cards4x3nobestmove.txt", rand1);
    modelopponentweak = new BasicThreeTriosModel("world4x31hole.txt", "cardsopponentweak.txt", rand1);
    modelmanyholes = new BasicThreeTriosModel("world4x3manyholes.txt", "cardsopponentweak.txt", rand1);
    cornerstrategy = new CornerStrategy();
    maxinumflipstrategy = new MaximizeFlipsStrategy();
    minimizeflipstrategy = new MinimizeFlipsStrategy();
    listcorner = List.of(cornerstrategy);
    listmaximum = List.of(maxinumflipstrategy);
    listminimum = List.of(minimizeflipstrategy);
    listcornermaximum = List.of(cornerstrategy, maxinumflipstrategy);
    listcornerminimum = List.of(cornerstrategy, minimizeflipstrategy);
    listmaximumminimum = List.of(maxinumflipstrategy, minimizeflipstrategy);
    listallstrategies = List.of(cornerstrategy, maxinumflipstrategy, minimizeflipstrategy);
    model4x32holes = new BasicThreeTriosModel("world4x32holes.txt", "cards4x3emptyver2.txt", rand1);
  }

  // Test when the opponent use MaximizeFlip, it put the opponent by not playing on col 2
  @Test
  public void testMaximizeFlip() {
    model4x3equalsides.startGame();

    model4x3equalsides.playToGrid(0, 0, 0);

    model4x3equalsides.playToGrid(1, 2, 0);

    model4x3equalsides.playToGrid(1, 0, 0);

    System.out.println(new MinimaxStrategy(listmaximum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listmaximum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MinimaxStrategy(listmaximum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimaxStrategy(listmaximum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getHandInx());
  }

  // Test when the opponent use CornerStrategy
  // The only corner left is (0,0), and play to this place will leave the opponent at a bad place
  // as all the cards won't be able to flip any of the other cards on the board (number of cards
  // blue can make with the corner strategy after this is 0.
  @Test
  public void testCornerStrategy() {
    model4x3.startGame();

    model4x3.playToGrid(3, 1, 1);
    model4x3.playToGrid(1 ,0 ,1);
    model4x3.playToGrid(2, 2, 2);
    model4x3.playToGrid(1, 1, 0);

    System.out.println(new MinimaxStrategy(listcorner).findBestMove(model4x3, model4x3.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listcorner).findBestMove(model4x3, model4x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MinimaxStrategy(listcorner).findBestMove(model4x3, model4x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimaxStrategy(listcorner).findBestMove(model4x3, model4x3.getCurrentPlayer()).getHandInx());
  }

  // Test when the opponent use MinimizeFlipsStrategy
  @Test
  public void testMinimizeFlipsStrategy() {
    model4x3equalsides.startGame();

    model4x3equalsides.playToGrid(3, 2, 0);
    model4x3equalsides.playToGrid(0, 1, 2);
    model4x3equalsides.playToGrid(2, 0, 1);


    System.out.println(new MinimaxStrategy(listminimum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listminimum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimaxStrategy(listminimum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimaxStrategy(listminimum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getHandInx());
  }

  // Test with a list of corner and maximum strategies as input list. (corner and maxumum both
  // return (0, 2) and hand index of 1 as the best move after we chose a move (2, 0, 1) from the
  //minimax strategy
  @Test
  public void testMaximumAndCorner() {
    model4x3equalsides.startGame();

    model4x3equalsides.playToGrid(3, 2, 0);
    model4x3equalsides.playToGrid(0, 1, 2);
    model4x3equalsides.playToGrid(2, 0, 1);


    System.out.println(new MinimaxStrategy(listcornermaximum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimaxStrategy(listcornermaximum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimaxStrategy(listcornermaximum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimaxStrategy(listcornermaximum).findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getHandInx());
  }

  // Test when the input list of strategies are corner + minimum. The best move to minimize the
  // impact of the opponent here is not (1,0) but (1, 2) as the corner would play to
  @Test
  public void testCornerMinimum() {
    model4x32holes.startGame();

    model4x32holes.playToGrid(2, 2, 0);
    model4x32holes.playToGrid(0, 1, 2);
    model4x32holes.playToGrid(3, 0, 0);
    model4x32holes.playToGrid(0, 2, 0);


    System.out.println(new MinimaxStrategy(listcornerminimum).findBestMove(model4x32holes, model4x32holes.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimaxStrategy(listcornerminimum).findBestMove(model4x32holes, model4x32holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new MinimaxStrategy(listcornerminimum).findBestMove(model4x32holes, model4x32holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimaxStrategy(listcornerminimum).findBestMove(model4x32holes, model4x32holes.getCurrentPlayer()).getHandInx());
  }

  //Test for all 3 strategies as input for Minimax
  @Test
  public void testAllStrategies() {
    model4x32holes.startGame();

    model4x32holes.playToGrid(2, 2, 0);
    model4x32holes.playToGrid(0, 1, 2);
    model4x32holes.playToGrid(3, 0, 0);
    model4x32holes.playToGrid(0, 2, 0);


    System.out.println(new MinimaxStrategy(listallstrategies).findBestMove(model4x32holes, model4x32holes.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimaxStrategy(listallstrategies).findBestMove(model4x32holes, model4x32holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimaxStrategy(listallstrategies).findBestMove(model4x32holes, model4x32holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimaxStrategy(listallstrategies).findBestMove(model4x32holes, model4x32holes.getCurrentPlayer()).getHandInx());
  }
}