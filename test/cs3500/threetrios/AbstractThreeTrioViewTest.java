package cs3500.threetrios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.model.ThreeTrioCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.ThreeTrioTextView;
import cs3500.threetrios.view.ThreeTrioView;

/**
 * Responsible for testing the behaviors of the ThreeTrioView interface.
 */
public abstract class AbstractThreeTrioViewTest {

  protected abstract ThreeTriosModel createModel(String gridFileName, String cardFileName);

  protected abstract ThreeTriosModel createModelWithRandom(String gridFileName, String cardFileName, Random random);


  protected ThreeTriosModel model5x7;

  protected ThreeTriosModel model2x2;

  protected ThreeTriosModel model3x3;

  protected ThreeTriosModel modelWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;

  private ThreeTrioCard.CardValue ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, A;

  private Random rand1;

  @Before
  public void setUp() {
    rand1 = new Random(2);

    model5x7 = createModelWithRandom("world1.txt", "card1.txt", rand1);
    model2x2 = createModelWithRandom("world2x2.txt", "cards2x2.txt", rand1);
    model3x3 = createModelWithRandom("world3x3.txt", "cards3x3.txt", rand1);
    modelWithNotEnoughCards = createModelWithRandom("world3x3.txt", "3cardsonly.txt", rand1);
    model2x2SameValueOf1 = createModelWithRandom("world2x2ver2.txt", "cardswithsamevalueof1.txt", rand1);
    model2x2SameValueOf1Ver2 = createModelWithRandom("world2x2ver3.txt", "cardswithsamevalueof1.txt", rand1);

    ONE = ThreeTrioCard.CardValue.ONE;
    TWO = ThreeTrioCard.CardValue.TWO;
    THREE = ThreeTrioCard.CardValue.THREE;
    FOUR = ThreeTrioCard.CardValue.FOUR;
    FIVE = ThreeTrioCard.CardValue.FIVE;
    SIX = ThreeTrioCard.CardValue.SIX;
    SEVEN = ThreeTrioCard.CardValue.SEVEN;
    EIGHT = ThreeTrioCard.CardValue.EIGHT;
    NINE = ThreeTrioCard.CardValue.NINE;
    A = ThreeTrioCard.CardValue.A;
  }

  @Test
  //Test view to show the normal of the game (starting state)
  public void testViewNormalState() {
    model3x3.startGame();
    ThreeTrioView view = new ThreeTrioTextView(model3x3);

    String expectedView = "Player: RED\n"
            + "__ \n"
            + "__ \n"
            + "_ _\n"
            + " _ \n"
            + "Hand:\n"
            + "WorldDragon 1 6 5 1\n"
            + "HeroKnight A 4 4 1\n"
            + "CorruptKing 3 1 1 2\n"
            + "FirePhoenix 2 3 4 2\n";

    Assert.assertEquals(expectedView, view.toString());
  }

  @Test
  //Test view to show the change of the stage after playToGrid
  public void testViewAfterPlayToGrid() {
    model3x3.startGame();
    model3x3.playToGrid(2, 2, 0);
    ThreeTrioView view = new ThreeTrioTextView(model3x3);

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
    model3x3.startGame();
    // Player 1 (Red)
    model3x3.playToGrid(1, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model3x3.playToGrid(2, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 1, 0);

    ThreeTrioView view = new ThreeTrioTextView(model3x3);

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
    model3x3.startGame();
    // Player 1 (Red)
    model3x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 2, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 0, 1);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 0, 0);

    ThreeTrioView view = new ThreeTrioTextView(model3x3);

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
