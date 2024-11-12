package cs3500.threetrios;

import org.junit.Test;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.TTTextView;

/**
 * Testing class for test cases specific to ThreeTriosTextView.
 */
public class ThreeTrioTextViewTest extends AbstractThreeTrioViewTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new TTTextView(null);
  }

  @Override
  protected ThreeTriosModel createModel() {
    return new BasicThreeTriosModel();
  }

  @Override
  protected ThreeTriosModel createModelWithRandom(Random random) {
    return new BasicThreeTriosModel(random);
  }
}