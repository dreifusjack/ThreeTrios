package cs3500.threetrios.strategy;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

public class CornerStrategyTest {
  protected ThreeTriosModel model5x7;

  protected ThreeTriosModel model2x2;

  protected ThreeTriosModel model3x3;
  protected ThreeTriosModel model3x3ver2;
  protected ThreeTriosModel model3x3ver3;

  protected ThreeTriosModel modelWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;

  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model5x7 = new BasicThreeTriosModel("world1.txt", "card1.txt", rand1);
    model2x2 = new BasicThreeTriosModel("world2x2.txt", "cards2x2.txt", rand1);
    model3x3 = new BasicThreeTriosModel("world4x3.txt", "cards3x3.txt", rand1);
    model3x3ver2 = new BasicThreeTriosModel("world4x3.txt", "cards3x3ver2.txt", rand1);
    model3x3ver3 = new BasicThreeTriosModel("world4x3ver2.txt", "cards3x3ver2.txt", rand1);

    modelWithNotEnoughCards = new BasicThreeTriosModel("world4x3.txt",
            "3cardsonly.txt", rand1);
    model2x2SameValueOf1 = new BasicThreeTriosModel("world2x2ver2.txt",
            "cardswithsamevalueof1.txt", rand1);
    model2x2SameValueOf1Ver2 = new BasicThreeTriosModel("world2x2ver3.txt",
            "cardswithsamevalueof1.txt", rand1);
  }

  @Test
  public void testWhenCannotFindBestMove() {

  }

  // When there is no best move play to the first open cell but some cells like (0, 0), (0, 1),... are occupied by a card
  @Test
  public void testWhenCannotFindBestMoveSpecial() {

  }

}