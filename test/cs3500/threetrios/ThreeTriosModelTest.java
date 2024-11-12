package cs3500.threetrios;

import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Testing class for test cases specific to BasicThreeTriosModel. None yet, but subject to change
 * in new variants.
 */
public class ThreeTriosModelTest extends AbstractThreeTriosModelTest {
  @Override
  protected ThreeTriosModel createModel() {
    return new BasicThreeTriosModel();
  }

  @Override
  protected ThreeTriosModel createModelWithRandom(Random random) {
    return new BasicThreeTriosModel(random);
  }
}
