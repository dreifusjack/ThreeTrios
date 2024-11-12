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

public class CornerStrategyTest {
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

  @Test
  public void testWhenCannotFindBestMove() {
    controller4x3CornersWithHoles.playGame(model4x3CornersWithHoles);

    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getHandInx());
  }

  // When there is no best move play to the first open cell but some cells like (0, 0), (0, 1),... are occupied by a card or are holes
  @Test
  public void testWhenCannotFindBestMoveSpecial() {
    controller4x3CornersWithHoles.playGame(model4x3CornersWithHoles);

    model4x3CornersWithHoles.playToGrid(0, 1, 0);

    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getHandInx());
  }

  //Test Initial Corner Play
  @Test
  public void testInitialCornerPlay() {
    controller4x3Ver2.playGame(model4x3Ver2);

    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3Ver2, model4x3Ver2.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3Ver2, model4x3Ver2.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3Ver2, model4x3Ver2.getCurrentPlayer()).getHandInx());
  }

  // Test One Corner Taken
    @Test
    public void testOneCornerTaken() {
    controller4x3CornersWithHolesVer2.playGame(model4x3CornersWithHolesVer2);

    System.out.println( new CornerStrategy().findBestMove(model4x3CornersWithHolesVer2, model4x3CornersWithHolesVer2.getCurrentPlayer()));
    //Total that card value is 15.
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer2, model4x3CornersWithHolesVer2.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer2, model4x3CornersWithHolesVer2.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer2, model4x3CornersWithHolesVer2.getCurrentPlayer()).getHandInx());
    }

  // Test tie that have many best cards from different corners
  @Test
  public void testTieBreakerInDifferentCorners() {
    controller4x3CornersWith2Holes.playGame(model4x3CornersWith2Holes);
    //card(1) is best card for top-left (value = 11)
    //card(0) is best card for top-right (value = 11)

    System.out.println(new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()));
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getHandInx());
  }

  //Test tie for many best cards in a single corners
  @Test
  public void testTieBreakerInSameCorner() {
    controller4x3CornersWithHolesVer3.playGame(model4x3CornersWithHolesVer3);
    //card(1) is best card for top-right (value = 7)
    //card(2) is best card for top-left (value = 7)
    //card(3) is best card for top-right (value = 7)

    System.out.println(new CornerStrategy().findBestMove(model4x3CornersWithHolesVer3, model4x3CornersWithHolesVer3.getCurrentPlayer()));
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer3, model4x3CornersWithHolesVer3.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer3, model4x3CornersWithHolesVer3.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer3, model4x3CornersWithHolesVer3.getCurrentPlayer()).getHandInx());
  }

  // When there is 1 side of each corner as a hole (sum is only 1 side) and result in a tie case
  @Test
  public void testSpecialCase() {
    controller4x3Corner1SideHole.playGame(model4x3Corner1SideHole);
    //card(0) is best card for bottom-right (value = 10) no holes on its sides
    //card(1) is best card for top-right (value = 10) west.value = 10 with a hole on south side
    //card(2) is best card for bottom-right (value = 10) no holes on its sides

    System.out.println(new CornerStrategy().findBestMove(model4x3Corner1SideHole, model4x3Corner1SideHole.getCurrentPlayer()));
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3Corner1SideHole, model4x3Corner1SideHole.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new CornerStrategy().findBestMove(model4x3Corner1SideHole, model4x3Corner1SideHole.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new CornerStrategy().findBestMove(model4x3Corner1SideHole, model4x3Corner1SideHole.getCurrentPlayer()).getHandInx());
  }

  // Play to the corner and flip cards around it
  @Test
  public void testPlayCornerAndFlip() {
    controller4x3.playGame(model4x3);
    model4x3.playToGrid(3, 1, 1);
    model4x3.playToGrid(1 ,0 ,1);
    model4x3.playToGrid(2, 2, 2);
    model4x3.playToGrid(1, 1, 0);

    System.out.println(new CornerStrategy().findBestMove(model4x3, model4x3.getCurrentPlayer()));
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3, model4x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3, model4x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3, model4x3.getCurrentPlayer()).getHandInx());

  }

  // Play to corner but the first top-left has a card, bottom-right/bottom-left have holes
  @Test
  public void testFirstCornerIsOccupied() {
    controller4x3CornersWith2Holes.playGame(model4x3CornersWith2Holes);
    model4x3CornersWith2Holes.playToGrid(0, 0, 0);

    System.out.println(model4x3CornersWith2Holes.getCurrentPlayer().getHand().toString());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getHandInx());
  }

  @Test
  public void test3CornersOccupiedCards() {
    controller4x3Plain.playGame(model4x3Plain);
    model4x3Plain.playToGrid(0, 0, 0);
    model4x3Plain.playToGrid(0, 2, 0);
    model4x3Plain.playToGrid(3, 2, 0);

    System.out.println(model4x3Plain.getCurrentPlayer().getHand().toString());
    Assert.assertEquals(3, new CornerStrategy().findBestMove(model4x3Plain, model4x3Plain.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3Plain, model4x3Plain.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3Plain, model4x3Plain.getCurrentPlayer()).getHandInx());
  }
}