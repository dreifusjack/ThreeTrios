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
import cs3500.threetrios.model.ThreeTrioCard;
import cs3500.threetrios.model.ThreeTriosPlayer;
import cs3500.threetrios.player.MockAIPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.CornerStrategy;
import cs3500.threetrios.player.MockHumanPlayer;
import cs3500.threetrios.view.CardPanel;
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
    redPlayer.addToHand(new ThreeTrioCard("WorldDragon", ThreeTrioCard.AttackValue.ONE,
            ThreeTrioCard.AttackValue.FIVE, ThreeTrioCard.AttackValue.SIX,
            ThreeTrioCard.AttackValue.ONE));
    Player bluePlayer = new ThreeTriosPlayer(TeamColor.BLUE);
    bluePlayer.addToHand(new ThreeTrioCard("RedDragon", ThreeTrioCard.AttackValue.THREE,
            ThreeTrioCard.AttackValue.A, ThreeTrioCard.AttackValue.ONE,
            ThreeTrioCard.AttackValue.TWO));

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
    CardPanel.CardShape cardShapeEx = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel cardEx = new CardPanel(cardShapeEx);

    redController.handleCardSelection(TeamColor.RED, 0, cardEx, cardEx);
    redController.handleBoardSelection(2, 2);

    Assert.assertTrue(blueControllerLog.contains("handlePlayerTurn called"));
    Assert.assertTrue(blueControllerLog.contains("handlePlayerTurn called"));
    Assert.assertTrue(blueControllerLog.contains("handleAIMoveIfPresent called"));

  }

  // Human player make move and then check testOutOfTurn.
  @Test
  public void testOutOfTurn() {
    CardPanel.CardShape cardShape1 = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel card1 = new CardPanel(cardShape1);

    redController.handleCardSelection(TeamColor.RED, 0, card1, card1);
    redController.handleBoardSelection(2, 2);


    Assert.assertTrue(redControllerLog.contains("outOfTurn called"));
    Assert.assertTrue(blueControllerLog.contains("outOfTurn called"));

  }

  @Test
  public void testHandleCardSelection() {
    CardPanel.CardShape cardShape1 = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel card1 = new CardPanel(cardShape1);

    redController.handleCardSelection(TeamColor.RED, 0, card1, card1);
    redController.handleBoardSelection(2, 2);

    // For Human player:
    Assert.assertTrue(redControllerLog.contains("handleCardSelection called"));

    // For AI player (after the Human player has made their move):
    Assert.assertTrue(blueControllerLog.contains("handleCardSelection called"));

  }

  @Test
  public void testHandleBoardSelection() {
    CardPanel.CardShape cardShape1 = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel card1 = new CardPanel(cardShape1);

    redController.handleCardSelection(TeamColor.RED, 0, card1, card1);
    redController.handleBoardSelection(2, 2);

    // For Human player:
    Assert.assertTrue(redControllerLog.contains("handleBoardSelection called"));

    // For AI player (after the Human player has made their move):
    Assert.assertTrue(blueControllerLog.contains("handleBoardSelection called"));

  }

  @Test
  public void testOnPlayerTurnChange() {
    CardPanel.CardShape cardShape1 = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel card1 = new CardPanel(cardShape1);

    redController.handleCardSelection(TeamColor.RED, 0, card1, card1);
    redController.handleBoardSelection(2, 2);

    // For Human player:
    Assert.assertTrue(redControllerLog.contains("onPlayerTurnChange called"));

    // For AI player (after the Human player has made their move):
    Assert.assertTrue(blueControllerLog.contains("onPlayerTurnChange called"));
  }

  @Test
  public void testOnGameOver() {
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

    redController.handleCardSelection(TeamColor.RED, 0, card1, card1);
    redController.handleBoardSelection(2, 2);

    redController.handleCardSelection(TeamColor.RED, 0, card2, card2);
    redController.handleBoardSelection(1, 2);

    redController.handleCardSelection(TeamColor.RED, 0, card3, card3);
    redController.handleBoardSelection(3, 1);

    redController.handleCardSelection(TeamColor.RED, 0, card4, card4);
    redController.handleBoardSelection(2, 1);

    Assert.assertTrue(redControllerLog.contains("onGameOver called"));
    Assert.assertTrue(blueControllerLog.contains("onGameOver called"));
  }

}