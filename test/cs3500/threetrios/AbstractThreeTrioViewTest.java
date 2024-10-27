package cs3500.threetrios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractThreeTrioViewTest {

  protected abstract ThreeTriosModel createModel(String gridFileName, String cardFileName);

  protected ThreeTriosModel model5x7;

  protected ThreeTriosModel model2x2;

  protected ThreeTriosModel model3x3;

  protected ThreeTriosModel modelWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;

  private ThreeTrioCard.CardValue ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, A;

  @Before
  public void setUp() {
    model5x7 = createModel("src/cs3500/threetrios/world1.txt", "src/cs3500/threetrios/card1.txt");
    model2x2 = createModel("src/cs3500/threetrios/world2x2.txt", "src/cs3500/threetrios/cards2x2.txt");
    model3x3 = createModel("src/cs3500/threetrios/world3x3.txt", "src/cs3500/threetrios/cards3x3.txt");
    modelWithNotEnoughCards = createModel("src/cs3500/threetrios/world3x3.txt", "src/cs3500/threetrios/3cardsonly.txt");
    model2x2SameValueOf1 = createModel("src/cs3500/threetrios/world2x2ver2.txt", "src/cs3500/threetrios/cardswithsamevalueof1.txt");
    model2x2SameValueOf1Ver2 = createModel("src/cs3500/threetrios/world2x2ver3.txt", "src/cs3500/threetrios/cardswithsamevalueof1.txt");

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
  public void testViewNormalState() throws Exception {
    model3x3.startGame();
    ThreeTrioView view = new ThreeTrioTextView(model3x3);

    String expectedView = "Player: RED\n"
            + "   \n"
            + "   \n"
            + " _ \n"
            + "Hand:\n"
            + "CorruptKing THREE ONE ONE ONE\n"
            + "AngryDragon FOUR SEVEN ONE FOUR\n"
            + "WindBird TWO FIVE FIVE A\n"
            + "HeroKnight A TWO FOUR FOUR\n";

    Assert.assertEquals(expectedView, view.toString());
  }

  @Test
  //Test view to show the change of the stage after playToGrid
  public void testViewAfterPlayToGrid() throws Exception {
    model3x3.startGame();
    model3x3.playToGrid(2, 2, 0);
    ThreeTrioView view = new ThreeTrioTextView(model3x3);

    String expectedView = "Player: BLUE\n"
            + "   \n"
            + "   \n"
            + " _R\n"
            + "Hand:\n"
            + "WorldDragon ONE TWO FIVE ONE\n"
            + "SkyWhale THREE ONE ONE ONE\n"
            + "FirePhoenix TWO THREE NINE TWO\n"
            + "ThunderTiger SIX SEVEN FIVE EIGHT\n";

    Assert.assertEquals(expectedView, view.toString());
  }

  //Test view to show a standard gameplay and win
  @Test
  public void testViewPlayAndWin() throws Exception {
    model3x3.startGame();
    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 2, 0);
    // Player 1 (Red)
    model3x3.playToGrid(2, 0, 0);

    ThreeTrioView view = new ThreeTrioTextView(model3x3);

    String expectedView = "Player: RED\n"
            + "R B\n"
            + "RRR\n"
            + "R_R\n"
            + "Hand:\n";

    Assert.assertEquals(expectedView, view.toString());

  }

  //Test view to show a standard gameplay and tie
  @Test
  public void testViewPlayAndThenTie() throws Exception {
    model3x3.startGame();
    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(0, 1, 0);

    ThreeTrioView view = new ThreeTrioTextView(model3x3);

    String expectedView = "Player: RED\n"
            + "BR \n"
            + "BRR\n"
            + "B_R\n"
            + "Hand:\n";

    Assert.assertEquals(expectedView, view.toString());
  }

}
