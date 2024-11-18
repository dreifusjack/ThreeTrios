package cs3500.threetrios.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.strategy.MaximizeFlipsStrategy;

/**
 * Test class for the MaximizeFlipStrategy class.
 */
public class MaximizeFlipsStrategyTest {

  private ThreeTriosModel model4x3;
  private ThreeTriosController controller4x3;
  private ThreeTriosModel model4x3Ver2;
  private ThreeTriosController controller4x3Ver2;
  private ThreeTriosModel model4x3Ver3;
  private ThreeTriosController controller4x3Ver3;
  private ThreeTriosModel model3x31Cell;
  private ThreeTriosController controller3x31Cell;

  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model4x3 = new BasicThreeTriosModel(rand1);
    controller4x3 = new ThreeTriosSetupController("world4x3.txt",
            "cards4x3.txt");

    model4x3Ver2 = new BasicThreeTriosModel(rand1);
    controller4x3Ver2 = new ThreeTriosSetupController("world4x3.txt",
            "cards3x3ver2.txt");

    model4x3Ver3 = new BasicThreeTriosModel(rand1);
    controller4x3Ver3 = new ThreeTriosSetupController("world4x3ver2.txt",
            "cards3x3ver2.txt");

    model3x31Cell = new BasicThreeTriosModel(rand1);
    controller3x31Cell = new ThreeTriosSetupController("world3x31Cell.txt",
            "cards4x3emptyver2.txt");

  }


  //-----------test findBestMove-------------

  //When there are a cell that has a max potential flip of 2. (all the other cells when play to can
  //flip 1.
  @Test
  public void testWhenPotential2MaxFlip() {
    controller4x3.playGame(model4x3);

    // Red Player
    model4x3.playToGrid(1, 0, 0);
    // Blue Player
    model4x3.playToGrid(2, 2, 0);
    // Red Player
    model4x3.playToGrid(0, 1, 1);
    // Blue Player
    model4x3.playToGrid(0, 0, 2);
    // Red Player
    model4x3.playToGrid(3, 1, 0);

    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getHandInx());
  }

  // When there are 2 cells and each has 2 max potential flips but 1 of them is closer to the
  // top-left corner than the other.
  @Test
  public void test2MaxFlipChooseCloser() {
    controller4x3.playGame(model4x3);

    // Red Player
    model4x3.playToGrid(1, 0, 0);
    // Blue Player
    model4x3.playToGrid(2, 2, 0);
    // Red Player
    model4x3.playToGrid(0, 1, 1);

    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getHandInx());

  }

  // When there is a tie-breaker (all of the cards that being played were surrounded by holes)
  @Test
  public void testWhenThereIsATie() {
    controller4x3.playGame(model4x3);

    model4x3.playToGrid(3, 1, 0);
    model4x3.playToGrid(2, 2, 0);

    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getHandInx());
  }

  // When MaximizeFlipsStrategy is trigger right after the game starts
  @Test
  public void testTieAfterGameStart() {
    controller4x3.playGame(model4x3);
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3,
            model4x3.getCurrentPlayer()).getHandInx());
  }


  // When a card can be played to 2 positions and it produces 1 for the maximum for both positions
  //It will take the upper-most position
  @Test
  public void testUpperMostPosision() {
    controller4x3Ver2.playGame(model4x3Ver2);

    model4x3Ver2.playToGrid(2, 2, 0);
    model4x3Ver2.playToGrid(0, 0, 1);
    model4x3Ver2.playToGrid(3, 1, 1);
    model4x3Ver2.playToGrid(1, 0, 1);
    System.out.println(new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()));

    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getHandInx());
  }

  @Test
  // Test for left-most position when the current max flips is equals to the global max flip
  public void testLeftMostPosition() {
    controller4x3Ver2.playGame(model4x3Ver2);

    model4x3Ver2.playToGrid(2, 2, 2);
    model4x3Ver2.playToGrid(1, 0, 1);
    model4x3Ver2.playToGrid(3, 1, 2);
    model4x3Ver2.playToGrid(1, 1, 2);

    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getHandInx());
  }

  // When there is no best move play to the first open cell in this case is (0, 0)
  @Test
  public void testWhenThereIsNoBestMove() {
    controller4x3Ver2.playGame(model4x3Ver2);
    model4x3Ver2.playToGrid(2, 2, 0);
    model4x3Ver2.playToGrid(1, 0, 0);
    model4x3Ver2.playToGrid(3, 1, 0);
    model4x3Ver2.playToGrid(1, 1, 1);

    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver2,
            model4x3Ver2.getCurrentPlayer()).getHandInx());
  }

  // When there is no best move play to the first open cell but some cells like
  // (0, 0), (0, 1),... are occupied by a card
  @Test
  public void testWhenThereIsNoBestMoveSpecial() {
    controller4x3Ver3.playGame(model4x3Ver3);
    model4x3Ver3.playToGrid(2, 2, 0);
    model4x3Ver3.playToGrid(1, 0, 0);
    model4x3Ver3.playToGrid(3, 1, 0);
    model4x3Ver3.playToGrid(1, 1, 1);

    Assert.assertEquals(2, new MaximizeFlipsStrategy().findBestMove(model4x3Ver3,
            model4x3Ver3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver3,
            model4x3Ver3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model4x3Ver3,
            model4x3Ver3.getCurrentPlayer()).getHandInx());
  }

  // Test findBestMoveChain, it should produce the same result as findBestMove.
  @Test
  public void testWhenThereIsNoBestMoveSpecialChain() {
    controller4x3Ver3.playGame(model4x3Ver3);
    model4x3Ver3.playToGrid(2, 2, 0);
    model4x3Ver3.playToGrid(1, 0, 0);
    model4x3Ver3.playToGrid(3, 1, 0);
    model4x3Ver3.playToGrid(1, 1, 1);

    Assert.assertEquals(2, new MaximizeFlipsStrategy().findBestMoveForChain(model4x3Ver3,
            model4x3Ver3.getCurrentPlayer()).getRow());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMoveForChain(model4x3Ver3,
            model4x3Ver3.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMoveForChain(model4x3Ver3,
            model4x3Ver3.getCurrentPlayer()).getHandInx());
  }

  // When the grid only has 1 cell to play to.
  @Test
  public void test1CellAvailable() {
    controller3x31Cell.playGame(model3x31Cell);

    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model3x31Cell,
            model3x31Cell.getCurrentPlayer()).getRow());
    Assert.assertEquals(1, new MaximizeFlipsStrategy().findBestMove(model3x31Cell,
            model3x31Cell.getCurrentPlayer()).getCol());
    Assert.assertEquals(0, new MaximizeFlipsStrategy().findBestMove(model3x31Cell,
            model3x31Cell.getCurrentPlayer()).getHandInx());
  }
}