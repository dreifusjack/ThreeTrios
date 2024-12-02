package cs3500.threetrios.player;

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
import cs3500.threetrios.player.strategy.CornerStrategy;
import cs3500.threetrios.view.CardPanel;
import cs3500.threetrios.view.TTGUIView;

/**
 * Testing class for the PlayerActions components (AIPlayer and HumanPlayer).
 */
public class PlayerActionsTest {

  private MockThreeTriosListenerController redController;

  private List<String> humanLog;
  private List<String> aiLog;


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

    MockHumanPlayer redPlayerActions = new MockHumanPlayer(TeamColor.RED);
    MockAIPlayer bluePlayerActions = new MockAIPlayer(TeamColor.BLUE, new CornerStrategy());

    TTGUIView redView = new TTGUIView(model);

    redController =  new MockThreeTriosListenerController(model, redView, redPlayerActions);

    humanLog = redPlayerActions.getLog();
    aiLog = bluePlayerActions.getLog();

  }


  // When the redController and blueController are first setup in the void setUp, check if
  // addPlayerActionListener is called for every PlayerAction (Human and AI players)
  @Test
  public void testAddPlayerActionListener() {
    Assert.assertFalse(humanLog.contains("addPlayerActionListener called"));
    Assert.assertTrue(aiLog.contains("addPlayerActionListener called"));
  }

  // Test after a red player (human) made a move and then the blue player (AI) made the move right
  // after that.
  @Test
  public void testNotifySelectedCard() {
    CardPanel.CardShape cardShapeEx = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel cardEx = new CardPanel(cardShapeEx);

    redController.handleCardSelection(TeamColor.RED, 0, cardEx, cardEx);
    redController.handleBoardSelection(2, 2);

    // For human player after they have made the move:
    Assert.assertTrue(humanLog.contains("notifySelectedCard called"));

    // For AI player right after that after human player has made their move:
    Assert.assertTrue(aiLog.contains("notifySelectedCard called"));
    Assert.assertTrue(aiLog.contains("AI selected card at index: 0"));
  }


  // Test after a red player (human) made a move and then the blue player (AI) made the move right
  // after that. (the AI will also notify about the coordinates of its best move)
  @Test
  public void testNotifyPlacedCard() {
    CardPanel.CardShape cardShapeEx = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel cardEx = new CardPanel(cardShapeEx);

    redController.handleCardSelection(TeamColor.RED, 0, cardEx, cardEx);
    redController.handleBoardSelection(2, 2);

    // For human player after they have made the move:
    Assert.assertTrue(humanLog.contains("notifyPlacedCard called"));

    // For AI player right after that after human player has made their move:
    Assert.assertTrue(aiLog.contains("notifyPlacedCard called"));
    Assert.assertTrue(aiLog.contains("AI placed card at row: 3, col: 2"));
  }

  // Test getColor was called after the red and blue controllers have been made, and getColor was
  // when a PlayerActions notify the controller that its turn to made or choose the best move.
  @Test
  public void testGetColor() {
    Assert.assertTrue(humanLog.contains("getColor called"));
    Assert.assertTrue(aiLog.contains("getColor called"));
  }

  @Test
  public void testGetColorAfterMove() {
    CardPanel.CardShape cardShapeEx = new CardPanel.CardShape("7", "7", "7",
            "A", TeamColor.RED);

    CardPanel cardEx = new CardPanel(cardShapeEx);

    redController.handleCardSelection(TeamColor.RED, 0, cardEx, cardEx);
    redController.handleBoardSelection(2, 2);

    Assert.assertTrue(humanLog.contains("getColor called"));
    Assert.assertTrue(aiLog.contains("getColor called"));
  }

  // Test addsPlayerAction was called after a feature controller was created.
  @Test
  public void tesAddsPlayerActions() {
    Assert.assertTrue(humanLog.contains("addsPlayerActions called"));
    Assert.assertTrue(aiLog.contains("addsPlayerActions called"));
  }

}