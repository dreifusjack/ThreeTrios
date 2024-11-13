package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.controller.BasicThreeTriosController;
import cs3500.threetrios.controller.ThreeTriosController;

public class ThreeTriosControllerTest {
  private ThreeTriosController controller5x7;

  @Before
  public void setUp() {
    controller5x7 = new BasicThreeTriosController(
            "world1.txt", "card1.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullModel() {
    controller5x7.playGame(null);
  }
}
