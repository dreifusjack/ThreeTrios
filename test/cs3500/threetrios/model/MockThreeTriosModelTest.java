package cs3500.threetrios.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.threetrios.strategy.ChainStrategy;
import cs3500.threetrios.strategy.CornerStrategy;
import cs3500.threetrios.strategy.MaximizeFlipsStrategy;
import cs3500.threetrios.strategy.MinimaxStrategy;
import cs3500.threetrios.strategy.MinimizeFlipsStrategy;
import cs3500.threetrios.strategy.MockThreeTriosModel;
import cs3500.threetrios.strategy.PlayedMove;

/**
 * Test class for the MockThreeTriosModel.
 */
public class MockThreeTriosModelTest {
  private MockThreeTriosModel mockModel;
  private MockThreeTriosModel mockModelLie;
  private Player redPlayer;

  @Before
  public void setUp() {
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    redPlayer.addToHand(new ThreeTrioCard("WorldDragon", ThreeTrioCard.AttackValue.ONE,
            ThreeTrioCard.AttackValue.FIVE, ThreeTrioCard.AttackValue.SIX,
            ThreeTrioCard.AttackValue.ONE));
    Player bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    bluePlayer.addToHand(new ThreeTrioCard("RedDragon", ThreeTrioCard.AttackValue.THREE,
            ThreeTrioCard.AttackValue.A, ThreeTrioCard.AttackValue.ONE,
            ThreeTrioCard.AttackValue.TWO));

    mockModel = new MockThreeTriosModel(3, 3, 1, 1, false, redPlayer, bluePlayer);
    mockModelLie = new MockThreeTriosModel(3, 3, 1, 1, true, redPlayer, bluePlayer);
  }

  //------------Maximum Strategy test------------
  // Test to see if maximize strategy checks all the cells on the board.
  @Test
  public void testMaximizeChecksAllLocations() {
    new MaximizeFlipsStrategy().findBestMove(mockModel, redPlayer);

    List<String> log = mockModel.getMockLog();

    System.out.println(redPlayer.getColor().toString());

    for (int index = 0; index < redPlayer.getHand().size(); index++) {
      for (int row = 0; row < mockModel.numRows(); row++) {
        for (int col = 0; col < mockModel.numCols(); col++) {
          Assert.assertTrue(
                  log.contains("checking at cell (" + row + ", " + col + ")"));
          Assert.assertTrue(
                  log.contains("checking number of card flips for card at cell (" + row + ", "
                          + col + ") for Player: " + "RED"));
        }
      }
    }
  }

  //Lie so that the cell (1, 1) is the only possible cell to play to.
  @Test
  public void testMaximizeWhenLying() {
    PlayedMove bestMove = new MaximizeFlipsStrategy().findBestMove(mockModelLie, redPlayer);

    Assert.assertEquals(1, bestMove.getRow());
    Assert.assertEquals(1, bestMove.getCol());
    Assert.assertEquals(0, bestMove.getHandInx());
  }


  //------------Corner Strategy test------------

  @Test
  public void testCornerStrategyChecksAllCorners() {
    new CornerStrategy().findBestMove(mockModel, redPlayer);

    List<String> log = mockModel.getMockLog();

    Assert.assertTrue(log.contains("checking at cell (0, 0)"));
    Assert.assertTrue(log.contains("checking at cell (0, 2)"));
    Assert.assertTrue(log.contains("checking at cell (2, 0)"));
    Assert.assertTrue(log.contains("checking at cell (2, 2)"));
  }

  //---------MinimizeFlip Strategy test---------
  @Test
  public void testMinimizeFlipStrategy() {
    new MinimizeFlipsStrategy().findBestMove(mockModel, redPlayer);

    List<String> log = mockModel.getMockLog();

    for (int index = 0; index < redPlayer.getHand().size(); index++) {
      for (int row = 0; row < mockModel.numRows(); row++) {
        for (int col = 0; col < mockModel.numCols(); col++) {
          Assert.assertTrue(
                  log.contains("checking at cell (" + row + ", " + col + ")"));
        }
      }
    }
  }

  //----------MinimaxFlip Strategy test-----------
  @Test
  public void testMinimaxFlipStrategy() {
    new MinimaxStrategy(List.of(new MinimizeFlipsStrategy())).findBestMove(mockModel, redPlayer);
    List<String> log = mockModel.getMockLog();

    for (int index = 0; index < redPlayer.getHand().size(); index++) {
      for (int row = 0; row < mockModel.numRows(); row++) {
        for (int col = 0; col < mockModel.numCols(); col++) {
          Assert.assertTrue(
                  log.contains("checking at cell (" + row + ", " + col + ")"));
        }
      }
    }
  }

  @Test
  public void testChainStrategy() {
    new ChainStrategy(List.of(new MinimizeFlipsStrategy(),
            new CornerStrategy())).findBestMove(mockModel, redPlayer);
    List<String> log = mockModel.getMockLog();

    for (int index = 0; index < redPlayer.getHand().size(); index++) {
      for (int row = 0; row < mockModel.numRows(); row++) {
        for (int col = 0; col < mockModel.numCols(); col++) {
          Assert.assertTrue(
                  log.contains("checking at cell (" + row + ", " + col + ")"));
        }
      }
    }
  }
}
