package cs3500.threetrios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.MockThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.MockNormalThreeTriosModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosPlayer;
import cs3500.threetrios.player.MockAIPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.CornerStrategy;
import cs3500.threetrios.player.MockHumanPlayer;
import cs3500.threetrios.view.TTGUIView;


/**
 * Testing class for ThreeTriosFeaturesController.
 */
public class ThreeTriosFeaturesControllerTest {

  private MockThreeTriosListenerController redController;

  private List<String> redControllerLog;
  private List<String> blueControllerLog;


  @Before
  public void setUp() {
    Player redPlayer = new ThreeTriosPlayer(TeamColor.RED);
    redPlayer.addToHand(new ThreeTriosCard("WorldDragon", ThreeTriosCard.AttackValue.ONE,
            ThreeTriosCard.AttackValue.FIVE, ThreeTriosCard.AttackValue.SIX,
            ThreeTriosCard.AttackValue.ONE));
    Player bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    bluePlayer.addToHand(new ThreeTriosCard("RedDragon", ThreeTriosCard.AttackValue.THREE,
            ThreeTriosCard.AttackValue.A, ThreeTriosCard.AttackValue.ONE,
            ThreeTriosCard.AttackValue.TWO));

    MockNormalThreeTriosModel model = new MockNormalThreeTriosModel(new Random(2));


    ThreeTriosSetupController setupController = new ThreeTriosSetupController(
            "world4x3manyholes.txt",
            "cardsopponentweak.txt");
    setupController.playGame(model);

    PlayerActions redPlayerActions = new MockHumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActions = new MockAIPlayer(TeamColor.BLUE, new CornerStrategy());

    TTGUIView redView = new TTGUIView(model);
    TTGUIView blueView = new TTGUIView(model);

    redController = new MockThreeTriosListenerController(model, redView, redPlayerActions);

    MockThreeTriosListenerController blueController = new MockThreeTriosListenerController(model,
            blueView, bluePlayerActions);

    redControllerLog = redController.getLog();

    blueControllerLog = blueController.getLog();
  }

  // Check if handlePlayerTurn is triggered when a controller is created.
  @Test
  public void testHandlePlayerTurn() {
    Assert.assertTrue(redControllerLog.contains("handlePlayerTurn called"));
    Assert.assertTrue(blueControllerLog.contains("handlePlayerTurn called"));

  }

  // When the human player make a move (human), then handleAIMoveIfPresent will be called for the
  // blue player (AI player)
  @Test
  public void testHandleAIMoveIfPresent() {
    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 2);

    Assert.assertTrue(blueControllerLog.contains("handlePlayerTurn called"));
    Assert.assertTrue(blueControllerLog.contains("handlePlayerTurn called"));
    Assert.assertTrue(blueControllerLog.contains("handleAIMoveIfPresent called"));

  }

  // Human player make move and then check testOutOfTurn.
  @Test
  public void testOutOfTurn() {
    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 2);


    Assert.assertTrue(redControllerLog.contains("outOfTurn called"));
    Assert.assertTrue(blueControllerLog.contains("outOfTurn called"));

  }

  @Test
  public void testHandleCardSelection() {
    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 2);

    // For Human player:
    Assert.assertTrue(redControllerLog.contains("handleCardSelection called"));

    // For AI player (after the Human player has made their move):
    Assert.assertTrue(blueControllerLog.contains("handleCardSelection called"));

  }

  @Test
  public void testHandleBoardSelection() {
    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 2);

    // For Human player:
    Assert.assertTrue(redControllerLog.contains("handleBoardSelection called"));

    // For AI player (after the Human player has made their move):
    Assert.assertTrue(blueControllerLog.contains("handleBoardSelection called"));

  }

  @Test
  public void testOnPlayerTurnChange() {
    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 2);

    // For Human player:
    Assert.assertTrue(redControllerLog.contains("onPlayerTurnChange called"));

    // For AI player (after the Human player has made their move):
    Assert.assertTrue(blueControllerLog.contains("onPlayerTurnChange called"));
  }

  @Test
  public void testOnGameOver() {
    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 2);

    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(1, 2);

    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(3, 1);

    redController.handleCardSelection(TeamColor.RED, 0);
    redController.handleBoardSelection(2, 1);

    Assert.assertTrue(redControllerLog.contains("onGameOver called"));
    Assert.assertTrue(blueControllerLog.contains("onGameOver called"));
  }

}