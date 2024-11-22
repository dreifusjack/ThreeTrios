package cs3500.threetrios.controller;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTrioCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosPlayer;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.MockAIPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.CornerStrategy;
import cs3500.threetrios.player.strategy.MockHumanPlayer;
import cs3500.threetrios.strategy.MockThreeTriosModel;
import cs3500.threetrios.view.TTGUIView;

public class ThreeTriosFeaturesControllerTest {

  private MockThreeTriosModel mockModel;
  private MockThreeTriosModel mockModelLie;
  private Player redPlayer;

  ThreeTriosSetupController setupController =
          new ThreeTriosSetupController(
                  "world4x3manyholes.txt",
                  "cardsopponentweak.txt");

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


  @Test
  public void test1() {
    // constructs model to be playable
    ThreeTriosModel model = new BasicThreeTriosModel(new Random(2));

    setupController.playGame(model);

    // view setup for each player
    TTGUIView redView = new TTGUIView(model);
    TTGUIView blueView = new TTGUIView(model);

    // specified players
    PlayerActions redPlayerActions = new MockHumanPlayer(TeamColor.RED);

    PlayerActions bluePlayerActions = new MockAIPlayer(TeamColor.BLUE, new CornerStrategy());

    // creates instance of controllers that are each responsible for representing a team,
    // features controllers maintain a playable game via controlling that MV with user interactions
    ThreeTriosFeaturesController redController =
            new ThreeTriosFeaturesController(model, redView, redPlayerActions);
    ThreeTriosFeaturesController blueController =
            new ThreeTriosFeaturesController(model, blueView, bluePlayerActions);

  }











}