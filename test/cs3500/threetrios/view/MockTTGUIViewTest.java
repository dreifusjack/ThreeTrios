package cs3500.threetrios.view;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.MockThreeTriosFeaturesController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.MockNormalThreeTriosModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTrioCard;
import cs3500.threetrios.model.ThreeTriosPlayer;
import cs3500.threetrios.player.MockAIPlayer;
import cs3500.threetrios.player.MockHumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.CornerStrategy;

public class MockTTGUIViewTest {

  private MockThreeTriosFeaturesController redController;

  private List<String> redControllerLog;
  private List<String> blueControllerLog;

  private List<String> guiViewLogRed;
  private List<String> guiViewLogBlue;

  private MockTTGUIView redView;

  private MockTTGUIView blueView;


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

    redView = new MockTTGUIView(model);
    blueView = new MockTTGUIView(model);

    redController =  new MockThreeTriosFeaturesController(model, redView, redPlayerActions);

    MockThreeTriosFeaturesController blueController = new MockThreeTriosFeaturesController(model, blueView, bluePlayerActions);

    redControllerLog = redController.getLog();
    blueControllerLog = blueController.getLog();

    guiViewLogRed = redView.getLog();
    guiViewLogBlue = blueView.getLog();
  }


  // Test if this is being called in handlePlayerTurn (which is when the feature controller is
  // first initialized
  @Test
  public void testRefreshPlayingBoard() {
    Assert.assertTrue(guiViewLogRed.contains("refreshPlayingBoard called"));
    Assert.assertTrue(guiViewLogBlue.contains("refreshPlayingBoard called"));
  }

  // Test if the "Player: Your Turn" and "Player: Waiting for opponent" are sent to be updated
  // when the game first started.
  @Test
  public void testUpdateTitle() {
    Assert.assertTrue(guiViewLogRed.contains("Update the view with the string: RED Player: Your Turn"));
    Assert.assertTrue(guiViewLogBlue.contains("Update the view with the string: BLUE Player: Waiting for opponent"));
  }

  // Test if the controller for the AI player trigger the addPlayerActionListener in the view when
  // handlePlayerTurn is first processed in the controller.
  @Test
  public void testAddPlayerActionListener() {
    Assert.assertTrue(guiViewLogRed.contains("addPlayerActionListener called"));
  }

  // Test if the controller for the
  @Test
  public void testNotifySelectedCard() {
    CardPanel.CardShape cardShapeEx = new CardPanel.CardShape("7", "7", "7", "A", TeamColor.RED);

    CardPanel cardEx = new CardPanel(cardShapeEx);

    redView.notifySelectedCard(0, TeamColor.RED, cardEx, cardEx);

    Assert.assertTrue(guiViewLogRed.contains("notifySelectedCard called"));

    Assert.assertTrue(guiViewLogRed.contains("notify selecting 0 indexed card with color RED"));

  }

  @Test
  public void testNotifyPlacedCard() {
    CardPanel.CardShape cardShapeEx = new CardPanel.CardShape("7", "7", "7", "A", TeamColor.RED);

    CardPanel cardEx = new CardPanel(cardShapeEx);

    redView.notifySelectedCard(0, TeamColor.RED, cardEx, cardEx);
    redView.notifyPlacedCard(2, 2);

    Assert.assertTrue(guiViewLogRed.contains("refreshPlayingBoard called"));
    Assert.assertTrue(guiViewLogRed.contains("notify placing selected card at row 2 and col 2"));

  }

}