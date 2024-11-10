package cs3500.threetrios.strategy;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

public class MinimizeFlipsStrategyTest {
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
  }

  //Break ties, choose a card that it upper-most, left most
  @Test
  public void testBreakTie() {
    model4x3empty.startGame();

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3empty, model4x3empty.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3empty, model4x3empty.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3empty, model4x3empty.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimizeFlipsStrategy().findBestMove(model4x3empty, model4x3empty.getCurrentPlayer()).getHandInx());
  }

  // Find the best move without breaks tie. Best move only has 1 open side
  @Test
  public void testFindBestMoveWith1Opening() {
    model4x3emptyver2.startGame();

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3emptyver2, model4x3emptyver2.getCurrentPlayer()));
    Assert.assertEquals(3, new MinimizeFlipsStrategy().findBestMove(model4x3emptyver2, model4x3emptyver2.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new MinimizeFlipsStrategy().findBestMove(model4x3emptyver2, model4x3emptyver2.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3emptyver2, model4x3emptyver2.getCurrentPlayer()).getHandInx());
  }

  // Find the best move without breaks tie. Each cell has 2 opening sides.
  @Test
  public void testFindBestMoveEqualOpening() {
    model4x3equalsides.startGame();

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getCol());
    Assert.assertEquals(5, new MinimizeFlipsStrategy().findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getHandInx());
  }

  // When there is no best move
  @Test
  public void testNoBestMove() {
    model4x31hole.startGame();

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x31hole, model4x31hole.getCurrentPlayer()));
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x31hole, model4x31hole.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x31hole, model4x31hole.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x31hole, model4x31hole.getCurrentPlayer()).getHandInx());
  }

  //Opponent has no flippable cards. (this is also tie break case and some of the first cell are holes)
  @Test
  public void testOpponentWeakCards() {
    modelmanyholes.startGame();

    System.out.println(new MinimizeFlipsStrategy().findBestMove(modelmanyholes, modelmanyholes.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimizeFlipsStrategy().findBestMove(modelmanyholes, modelmanyholes.getCurrentPlayer()).getRow());
    Assert.assertEquals(2, new MinimizeFlipsStrategy().findBestMove(modelmanyholes, modelmanyholes.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(modelmanyholes, modelmanyholes.getCurrentPlayer()).getHandInx());
  }

  //All cells are occupied


  // Do the strategy when mid game. (With some occupied cells + holes)
  @Test
  public void testMidGame() {
    model4x3equalsides.startGame();

    model4x3equalsides.playToGrid(3, 2, 0);
    model4x3equalsides.playToGrid(0, 1, 2);
    model4x3equalsides.playToGrid(2, 0, 1);

    System.out.println(new MinimizeFlipsStrategy().findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()));
    Assert.assertEquals(1, new MinimizeFlipsStrategy().findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MinimizeFlipsStrategy().findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MinimizeFlipsStrategy().findBestMove(model4x3equalsides, model4x3equalsides.getCurrentPlayer()).getHandInx());
  }
}