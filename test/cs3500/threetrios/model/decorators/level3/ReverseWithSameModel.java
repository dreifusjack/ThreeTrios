package cs3500.threetrios.model.decorators.level3;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.decorators.level1.PassThroughCardDecorator;
import cs3500.threetrios.model.decorators.level1.ReverseCardDecorator;
import cs3500.threetrios.model.decorators.level1.VariantCardModelDecorator;
import cs3500.threetrios.model.decorators.level2.PlusModelDecorator;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;

public class ReverseWithSameModel {

  private ThreeTriosListenerController redControllerPlus;
  private ThreeTriosListenerController blueControllerPlus;

  private PlusModelDecorator modelPlus;

  @Before
  public void setUp() {

    ThreeTriosModel modelBase = new BasicThreeTriosModel(new Random(2));

    List<PassThroughCardDecorator> reverseList = new ArrayList<>(List.of(new ReverseCardDecorator()));

    VariantCardModelDecorator modelReverse = new VariantCardModelDecorator(modelBase, reverseList);

    modelPlus = new PlusModelDecorator(modelReverse);

    ThreeTriosSetupController setupControllerSame = new ThreeTriosSetupController(
            "worldbig.txt",
            "bigcards.txt");
    setupControllerSame.playGame(modelPlus);

    PlayerActions redPlayerActionsR = new HumanPlayer(TeamColor.RED);
    PlayerActions bluePlayerActionsR = new HumanPlayer(TeamColor.BLUE);

    TTGUIView redViewPlus = new TTGUIView(modelPlus);
    TTGUIView blueViewPlus = new TTGUIView(modelPlus);

    redControllerPlus = new ThreeTriosListenerController(modelPlus, redViewPlus, redPlayerActionsR);
    blueControllerPlus = new ThreeTriosListenerController(modelPlus, blueViewPlus, bluePlayerActionsR);
  }

  @Test
  public void testForReverseNoFlip() {

  }

  @Test
  public void testForReverseFlip() {

  }

  @Test
  public void testForSameFlip() {

  }

  @Test
  public void testForSameNoFlip() {

  }

  @Test
  public void testForReverseAndSame() {

  }



}
