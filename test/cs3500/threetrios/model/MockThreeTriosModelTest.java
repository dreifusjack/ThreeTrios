package cs3500.threetrios.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.MockThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.player.MockAIPlayer;
import cs3500.threetrios.player.MockHumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.ChainStrategy;
import cs3500.threetrios.player.strategy.CornerStrategy;
import cs3500.threetrios.player.strategy.MaximizeFlipsStrategy;
import cs3500.threetrios.player.strategy.MinimaxStrategy;
import cs3500.threetrios.player.strategy.MinimizeFlipsStrategy;
import cs3500.threetrios.player.strategy.PlayedMove;
import cs3500.threetrios.view.CardPanel;
import cs3500.threetrios.view.TTGUIView;

/**
 * Test class for the MockThreeTriosModel.
 */
public class MockThreeTriosModelTest {
  private MockThreeTriosModel mockModel;
  private MockThreeTriosModel mockModelLie;
  private Player redPlayer;

  private MockThreeTriosListenerController redController;

  private List<String> mockModelLog;


  @Before
  public void setUp() {
    redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    redPlayer.addToHand(new ThreeTriosCard("WorldDragon", ThreeTriosCard.AttackValue.ONE,
            ThreeTriosCard.AttackValue.FIVE, ThreeTriosCard.AttackValue.SIX,
            ThreeTriosCard.AttackValue.ONE));
    Player bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    bluePlayer.addToHand(new ThreeTriosCard("RedDragon", ThreeTriosCard.AttackValue.THREE,
            ThreeTriosCard.AttackValue.A, ThreeTriosCard.AttackValue.ONE,
            ThreeTriosCard.AttackValue.TWO));

    mockModel = new MockThreeTriosModel(3, 3, 1,
            1, false, redPlayer, bluePlayer);
    mockModelLie = new MockThreeTriosModel(3, 3, 1,
            1, true, redPlayer, bluePlayer);


    MockNormalThreeTriosModel model = new MockNormalThreeTriosModel(new Random(2));

    ThreeTriosSetupController setupController = new ThreeTriosSetupController(
            "world4x3manyholes.txt",
            "cardsopponentweak.txt");
    setupController.playGame(model);

    PlayerActions redPlayerActions = new MockHumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActions = new MockAIPlayer(TeamColor.BLUE, new CornerStrategy());

    TTGUIView redView = new TTGUIView(model);
    TTGUIView blueView = new TTGUIView(model);

    redController =  new MockThreeTriosListenerController(model, redView, redPlayerActions);


    mockModelLog = model.getLog();
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


  // Test to see if addModelStatusListener is called on our model after the feature controller is
  // created.
  @Test
  public void testAddModelStatusListener() {
    Assert.assertTrue(mockModelLog.contains("addModelStatusListener called"));
  }


  // Test to see if notifyPlayerTurnChange after the first player (human) made the move.
  @Test
  public void testNotifyPlayerTurnChange() {
    CardPanel.CardShape cardShape1 = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel card1 = new CardPanel(cardShape1);

    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 2);

    Assert.assertTrue(mockModelLog.contains("notifyPlayerTurnChange called"));

  }

  @Test
  public void testNotifyGameOver() {
    CardPanel.CardShape cardShape1 = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel card1 = new CardPanel(cardShape1);

    CardPanel.CardShape cardShape2 = new CardPanel.CardShape("1", "1", "1",
            "6", TeamColor.RED);

    CardPanel card2 = new CardPanel(cardShape2);

    CardPanel.CardShape cardShape3 = new CardPanel.CardShape("1", "1", "1",
            "3", TeamColor.RED);

    CardPanel card3 = new CardPanel(cardShape3);

    CardPanel.CardShape cardShape4 = new CardPanel.CardShape("7", "7", "7",
            "8", TeamColor.RED);

    CardPanel card4 = new CardPanel(cardShape4);

    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 2);

    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(1, 2);

    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(3, 1);

    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 1);

    Assert.assertTrue(mockModelLog.contains("notifyGameOver called"));

  }
}
