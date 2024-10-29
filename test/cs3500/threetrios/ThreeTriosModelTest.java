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
  protected ThreeTriosModel createModel(String gridFileName, String cardFileName) {
    return new BasicThreeTriosModel(gridFileName, cardFileName);
  }

  @Override
  protected ThreeTriosModel createModelWithRandom(String gridFileName, String cardFileName, Random random) {
    return new BasicThreeTriosModel(gridFileName, cardFileName, random);
  }
}
