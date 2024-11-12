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

public class ChainStrategyTest {

  private ThreeTriosModel model5x7;
  private ThreeTriosController controller5x7;
  private ThreeTriosModel model2x2;
  private ThreeTriosController controller2x2;
  private ThreeTriosModel model4x3;
  private ThreeTriosController controller4x3;
  private ThreeTriosModel model4x3Ver2;
  private ThreeTriosController controller4x3Ver2;
  private ThreeTriosModel model4x3Ver3;
  private ThreeTriosController controller4x3Ver3;
  private ThreeTriosModel model4x3CornersWithHoles;
  private ThreeTriosController controller4x3CornersWithHoles;
  private ThreeTriosModel model4x3Plain;
  private ThreeTriosController controller4x3Plain;
  private ThreeTriosModel model4x3CornersWithHolesVer2;
  private ThreeTriosController controller4x3CornersWithHolesVer2;
  private ThreeTriosModel model4x3CornersWithHolesVer3;
  private ThreeTriosController controller4x3CornersWithHolesVer3;
  private ThreeTriosModel model4x3CornersWith2Holes;
  private ThreeTriosController controller4x3CornersWith2Holes;
  private ThreeTriosModel model4x3Corner1SideHole;
  private ThreeTriosController controller4x3Corner1SideHole;
  private ThreeTriosModel modelWithNotEnoughCards;
  private ThreeTriosController controllerWithNotEnoughCards;
  private ThreeTriosModel model2x2SameValueOf1;
  private ThreeTriosController controller2x2SameValueOf1;
  private ThreeTriosModel model2x2SameValueOf1Ver2;
  private ThreeTriosController controller2x2SameValueOf1Ver2;
  private ThreeTriosModel model4x3Empty;
  private ThreeTriosController controller4x3Empty;
  private ThreeTriosModel model4x3EmptyVer2;
  private ThreeTriosController controller4x3EmptyVer2;
  private ThreeTriosModel model4x3EqualSides;
  private ThreeTriosController controller4x3EqualSides;
  private ThreeTriosModel model4x31Hole;
  private ThreeTriosController controller4x31Hole;
  private ThreeTriosModel modelOpponentWeak;
  private ThreeTriosController controllerOpponentWeak;
  private ThreeTriosModel modelManyHoles;
  private ThreeTriosController controllerManyHoles;
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
  private List<ThreeTriosStrategy> listMaximumMinimum;
  private List<ThreeTriosStrategy> listAllStrategies;


  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model5x7 = new BasicThreeTriosModel(rand1);
    controller5x7 = new BasicThreeTriosController("world1.txt", "card1.txt");

    model2x2 = new BasicThreeTriosModel(rand1);
    controller2x2 = new BasicThreeTriosController("world2x2.txt", "cards2x2.txt");

    model4x3 = new BasicThreeTriosModel(rand1);
    controller4x3 = new BasicThreeTriosController("world4x3.txt", "cards4x3.txt");

    model4x3Ver2 = new BasicThreeTriosModel(rand1);
    controller4x3Ver2 = new BasicThreeTriosController("world4x3.txt", "cards3x3ver2.txt");

    model4x3Ver3 = new BasicThreeTriosModel(rand1);
    controller4x3Ver3 = new BasicThreeTriosController("world4x3ver2.txt", "cards3x3ver2.txt");

    modelWithNotEnoughCards = new BasicThreeTriosModel(rand1);
    controllerWithNotEnoughCards = new BasicThreeTriosController("world4x3.txt", "3cardsonly.txt");

    model2x2SameValueOf1 = new BasicThreeTriosModel(rand1);
    controller2x2SameValueOf1 = new BasicThreeTriosController("world2x2ver2.txt", "cardswithsamevalueof1.txt");

    model2x2SameValueOf1Ver2 = new BasicThreeTriosModel(rand1);
    controller2x2SameValueOf1Ver2 = new BasicThreeTriosController("world2x2ver3.txt", "cardswithsamevalueof1.txt");

    model4x3CornersWithHoles = new BasicThreeTriosModel(rand1);
    controller4x3CornersWithHoles = new BasicThreeTriosController("world4x3cornerswithholes.txt", "cards3x3ver2.txt");

    model4x3CornersWithHolesVer2 = new BasicThreeTriosModel(rand1);
    controller4x3CornersWithHolesVer2 = new BasicThreeTriosController("world4x3cornerswithholesver2.txt", "cards3x3ver2.txt");

    model4x3CornersWithHolesVer3 = new BasicThreeTriosModel(rand1);
    controller4x3CornersWithHolesVer3 = new BasicThreeTriosController("world4x3cornerswithholesver2.txt", "cards4x3bestcardcorner.txt");

    model4x3CornersWith2Holes = new BasicThreeTriosModel(rand1);
    controller4x3CornersWith2Holes = new BasicThreeTriosController("world4x3with2holescorners.txt", "cards4x3cornerswith2holes.txt");

    model4x3Corner1SideHole = new BasicThreeTriosModel(rand1);
    controller4x3Corner1SideHole = new BasicThreeTriosController("world4x3cornersspecial.txt", "cards4x3corners1sideholes.txt");

    model4x3Plain = new BasicThreeTriosModel(rand1);
    controller4x3Plain = new BasicThreeTriosController("world4x3plain.txt", "cards4x3.txt");

    model4x3Empty = new BasicThreeTriosModel(rand1);
    controller4x3Empty = new BasicThreeTriosController("world4x3empty.txt", "cards4x3empty.txt");

    model4x3EmptyVer2 = new BasicThreeTriosModel(rand1);
    controller4x3EmptyVer2 = new BasicThreeTriosController("world4x3empty.txt", "cards4x3emptyver2.txt");

    model4x3EqualSides = new BasicThreeTriosModel(rand1);
    controller4x3EqualSides = new BasicThreeTriosController("world4x31hole.txt", "cards4x3emptyver2.txt");

    model4x31Hole = new BasicThreeTriosModel(rand1);
    controller4x31Hole = new BasicThreeTriosController("world4x31hole.txt", "cards4x3nobestmove.txt");

    modelOpponentWeak = new BasicThreeTriosModel(rand1);
    controllerOpponentWeak = new BasicThreeTriosController("world4x31hole.txt", "cardsopponentweak.txt");

    modelManyHoles = new BasicThreeTriosModel(rand1);
    controllerManyHoles = new BasicThreeTriosController("world4x3manyholes.txt", "cardsopponentweak.txt");

    model4x32Holes = new BasicThreeTriosModel(rand1);
    controller4x32Holes = new BasicThreeTriosController("world4x32holes.txt", "cards4x3emptyver2.txt");

    cornerStrategy = new CornerStrategy();
    maxinumFlipStrategy = new MaximizeFlipsStrategy();
    minimizeFlipStrategy = new MinimizeFlipsStrategy();
    listCorner = List.of(cornerStrategy);
    listMaximum = List.of(maxinumFlipStrategy);
    listMinimum = List.of(minimizeFlipStrategy);
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


    System.out.println(new ChainStrategy(listMaximum).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(1, new ChainStrategy(listMaximum).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new ChainStrategy(listMaximum).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(2, new ChainStrategy(listMaximum).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  //Test for list of 2 strategies (corner, maximizeflip)
  @Test
  public void testMaximizeCorner() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);
    model4x32Holes.playToGrid(0, 2, 0);


    System.out.println(new ChainStrategy(listCornerMaximum).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(1, new ChainStrategy(listCornerMaximum).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new ChainStrategy(listCornerMaximum).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new ChainStrategy(listCornerMaximum).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  //Test for list of 3 strategies (corner, maximizeflip, minimizeflip
  @Test
  public void testAllStrategies() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);


    System.out.println(new ChainStrategy(listAllStrategies).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(0, new ChainStrategy(listAllStrategies).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new ChainStrategy(listAllStrategies).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new ChainStrategy(listAllStrategies).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

  // Test with 4 strategies with Minimax has nested strategies
  @Test
  public void testComplexWithNestedMinimax() {
    controller4x32Holes.playGame(model4x32Holes);

    model4x32Holes.playToGrid(2, 2, 0);
    model4x32Holes.playToGrid(0, 1, 2);
    model4x32Holes.playToGrid(3, 0, 0);

    List<ThreeTriosStrategy> complexList = List.of(new MinimaxStrategy(listMaximum), cornerStrategy, maxinumFlipStrategy, minimizeFlipStrategy);

    System.out.println(new ChainStrategy(complexList).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()));
    Assert.assertEquals(0, new ChainStrategy(complexList).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new ChainStrategy(complexList).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new ChainStrategy(complexList).findBestMove(model4x32Holes, model4x32Holes.getCurrentPlayer()).getHandInx());
  }

}