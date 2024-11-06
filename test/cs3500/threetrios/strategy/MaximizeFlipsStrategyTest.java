package cs3500.threetrios.strategy;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

public class MaximizeFlipsStrategyTest {

  protected ThreeTriosModel model5x7;

  protected ThreeTriosModel model2x2;

  protected ThreeTriosModel model3x3;
  protected ThreeTriosModel model3x3ver2;


  protected ThreeTriosModel modelWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;

  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model5x7 = new BasicThreeTriosModel("world1.txt", "card1.txt", rand1);
    model2x2 = new BasicThreeTriosModel("world2x2.txt", "cards2x2.txt", rand1);
    model3x3 = new BasicThreeTriosModel("world4x3.txt", "cards3x3.txt", rand1);
    model3x3ver2 = new BasicThreeTriosModel("world4x3.txt", "cards3x3ver2.txt", rand1);
    modelWithNotEnoughCards = new BasicThreeTriosModel("world4x3.txt",
            "3cardsonly.txt", rand1);
    model2x2SameValueOf1 = new BasicThreeTriosModel("world2x2ver2.txt",
            "cardswithsamevalueof1.txt", rand1);
    model2x2SameValueOf1Ver2 = new BasicThreeTriosModel("world2x2ver3.txt",
            "cardswithsamevalueof1.txt", rand1);
  }


  //-----------test findBestMove-------------

  //When there are a cell that has a max potential flip of 2. (all the other cells when play to can
  //flip 1.
  @Test
  public void testWhenPotential2MaxFlip() {
    model3x3.startGame();
    // Red Player
    model3x3.playToGrid(1, 0, 0);
    // Blue Player
    model3x3.playToGrid(2, 2, 0);
    // Red Player
    model3x3.playToGrid(0, 1, 1);
    // Blue Player
    model3x3.playToGrid(0, 0, 2);
    // Red Player
    model3x3.playToGrid(3, 1, 0);

    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getHandInx());
  }

  // When there are 2 cells and each has 2 max potential flips but 1 of them is closer to the top-left corner than the other.
  @Test
  public void test2MaxFlipChooseCloser() {
    model3x3.startGame();
    // Red Player
    model3x3.playToGrid(1, 0, 0);
    // Blue Player
    model3x3.playToGrid(2, 2, 0);
    // Red Player
    model3x3.playToGrid(0, 1, 1);

    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getHandInx());

  }

  // When there is a tie-breaker (all of the cards that being played were surrounded by holes)
  @Test
  public void testWhenThereIsATie() {
    model3x3.startGame();

    model3x3.playToGrid(3, 1, 0);
    model3x3.playToGrid(2, 2, 0);


    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getHandInx());
  }

  // When MaximizeFlipsStrategy is trigger right after the game starts
  @Test
  public void testTieAfterGameStart() {
    model3x3.startGame();
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getHandInx());
  }


  // When MaximizeFlipsStrategy is trigger right after the game starts
  @Test
  public void testasdkjn() {
    model3x3.startGame();

    model3x3.playToGrid(2, 2, 0);
    model3x3.playToGrid(0, 0, 1);
    model3x3.playToGrid(3, 1, 1);
    model3x3.playToGrid(1, 0, 1);
    System.out.println(new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()));


    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model3x3, model3x3.getCurrentPlayer()).getHandInx());
  }
}