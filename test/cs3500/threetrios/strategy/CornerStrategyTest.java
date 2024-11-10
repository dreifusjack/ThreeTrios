package cs3500.threetrios.strategy;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

public class CornerStrategyTest {
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
  }

  @Test
  public void testWhenCannotFindBestMove() {
    model4x3CornersWithHoles.startGame();

    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getHandInx());
  }

  // When there is no best move play to the first open cell but some cells like (0, 0), (0, 1),... are occupied by a card or are holes
  @Test
  public void testWhenCannotFindBestMoveSpecial() {
    model4x3CornersWithHoles.startGame();

    model4x3CornersWithHoles.playToGrid(0, 1, 0);

    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3CornersWithHoles, model4x3CornersWithHoles.getCurrentPlayer()).getHandInx());
  }

  //Test Initial Corner Play
  @Test
  public void testInitialCornerPlay() {
    model4x3ver2.startGame();

    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3ver2, model4x3ver2.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3ver2, model4x3ver2.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3ver2, model4x3ver2.getCurrentPlayer()).getHandInx());
  }

  // Test One Corner Taken
    @Test
    public void testOneCornerTaken() {
    model4x3CornersWithHolesVer2.startGame();

    System.out.println( new CornerStrategy().findBestMove(model4x3CornersWithHolesVer2, model4x3CornersWithHolesVer2.getCurrentPlayer()));
    //Total that card value is 15.
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer2, model4x3CornersWithHolesVer2.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer2, model4x3CornersWithHolesVer2.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new CornerStrategy().findBestMove(model4x3CornersWithHolesVer2, model4x3CornersWithHolesVer2.getCurrentPlayer()).getHandInx());
    }

  // Test tie that have many best cards from different corners
  @Test
  public void testTieBreakerInDifferentCorners() {
    model4x3CornersWith2Holes.startGame();
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
    model4x3CornersWithHolesVer3.startGame();
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
    model4x3Corner1SideHole.startGame();
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
    model4x3.startGame();
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
    model4x3CornersWith2Holes.startGame();
    model4x3CornersWith2Holes.playToGrid(0, 0, 0);

    System.out.println(model4x3CornersWith2Holes.getCurrentPlayer().getHand().toString());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new CornerStrategy().findBestMove(model4x3CornersWith2Holes, model4x3CornersWith2Holes.getCurrentPlayer()).getHandInx());
  }

  @Test
  public void test3CornersOccupiedCards() {
    model4x3plain.startGame();
    model4x3plain.playToGrid(0, 0, 0);
    model4x3plain.playToGrid(0, 2, 0);
    model4x3plain.playToGrid(3, 2, 0);

    System.out.println(model4x3plain.getCurrentPlayer().getHand().toString());
    Assert.assertEquals(3, new CornerStrategy().findBestMove(model4x3plain, model4x3plain.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3plain, model4x3plain.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new CornerStrategy().findBestMove(model4x3plain, model4x3plain.getCurrentPlayer()).getHandInx());
  }
}