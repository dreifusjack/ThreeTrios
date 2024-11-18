package cs3500.threetrios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.TTTextView;
import cs3500.threetrios.view.ThreeTriosTextualView;

/**
 * Responsible for testing the behaviors of the ThreeTrioView interface.
 */
public abstract class AbstractThreeTrioViewTest {

  protected abstract ThreeTriosModel createModel();

  protected abstract ThreeTriosModel createModelWithRandom(Random random);


  protected ThreeTriosModel model5x7;
  protected ThreeTriosController controller5x7;

  protected ThreeTriosModel model2x2;
  protected ThreeTriosController controller2x2;

  protected ThreeTriosModel model4x3;
  protected ThreeTriosController controller4x3;

  protected ThreeTriosModel modelWithNotEnoughCards;
  protected ThreeTriosController controllerWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;
  protected ThreeTriosController controller2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;
  protected ThreeTriosController controller2x2SameValueOf1Ver2;

  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model5x7 = createModelWithRandom(rand1);
    controller5x7 = new ThreeTriosSetupController(
            "world1.txt", "card1.txt");

    model2x2 = createModelWithRandom(rand1);
    controller2x2 = new ThreeTriosSetupController(
            "world2x2.txt", "cards2x2.txt");

    model4x3 = createModelWithRandom(rand1);
    controller4x3 = new ThreeTriosSetupController(
            "world4x3.txt", "cards3x3.txt");

    modelWithNotEnoughCards = createModelWithRandom(rand1);
    controllerWithNotEnoughCards = new ThreeTriosSetupController(
            "world4x3.txt","3cardsonly.txt");

    model2x2SameValueOf1 = createModelWithRandom(rand1);
    controller2x2SameValueOf1 = new ThreeTriosSetupController(
            "world2x2ver2.txt","cardswithsamevalueof1.txt");

    model2x2SameValueOf1Ver2 = createModelWithRandom(rand1);
    controller2x2SameValueOf1Ver2 = new ThreeTriosSetupController(
            "world2x2ver3.txt","cardswithsamevalueof1.txt");
  }

  @Test
  //Test view to show the normal of the game (starting state)
  public void testViewNormalState() {
    controller4x3.playGame(model4x3);
    ThreeTriosTextualView view = new TTTextView(model4x3);

    String expectedView = "Player: RED\n"
            + "__ \n"
            + "__ \n"
            + "_ _\n"
            + " _ \n"
            + "Hand:\n"
            + "WorldDragon 1 6 5 1\n"
            + "HeroKnight A 4 4 2\n"
            + "CorruptKing 3 1 1 2\n"
            + "FirePhoenix 2 3 4 2\n";

    Assert.assertEquals(expectedView, view.toString());
  }

  @Test
  //Test view to show the change of the stage after playToGrid
  public void testViewAfterPlayToGrid() {
    controller4x3.playGame(model4x3);
    model4x3.playToGrid(2, 2, 0);
    ThreeTriosTextualView view = new TTTextView(model4x3);

    String expectedView = "Player: BLUE\n"
            + "__ \n"
            + "__ \n"
            + "_ R\n"
            + " _ \n"
            + "Hand:\n"
            + "ThunderTiger 3 9 5 4\n"
            + "WindBird 2 5 5 A\n"
            + "AngryDragon 5 7 1 4\n"
            + "SkyWhale 3 1 1 2\n";

    Assert.assertEquals(expectedView, view.toString());
  }

  //Test view to show a standard gameplay and win
  @Test
  public void testViewPlayAndWin() {
    controller4x3.playGame(model4x3);
    // Player 1 (Red)
    model4x3.playToGrid(1, 0, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model4x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model4x3.playToGrid(2, 0, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(1, 1, 0);

    ThreeTriosTextualView view = new TTTextView(model4x3);

    String expectedView = "Player: RED\n"
            + "BB \n"
            + "BB \n"
            + "R R\n"
            + " _ \n"
            + "Hand:\n"
            + "FirePhoenix 2 3 4 2\n";

    Assert.assertEquals(expectedView, view.toString());

  }

  //Test view to show a standard gameplay and tie
  @Test
  public void testViewPlayAndThenTie() {
    controller4x3.playGame(model4x3);
    // Player 1 (Red)
    model4x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 2, 0);
    // Player 1 (Red)
    model4x3.playToGrid(1, 0, 1);
    // Player 2 (Blue)
    model4x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model4x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 0, 0);

    ThreeTriosTextualView view = new TTTextView(model4x3);

    String expectedView = "Player: RED\n"
            + "RR \n"
            + "BR \n"
            + "B B\n"
            + " _ \n"
            + "Hand:\n"
            + "FirePhoenix 2 3 4 2\n";

    Assert.assertEquals(expectedView, view.toString());
  }
}
