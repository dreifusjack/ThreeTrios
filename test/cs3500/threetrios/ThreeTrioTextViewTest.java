package cs3500.threetrios;

import junit.framework.TestCase;

public class ThreeTrioTextViewTest extends AbstractThreeTrioViewTest {

  @Override
  protected ThreeTriosModel createModel(String gridFileName, String cardFileName) {
    return new BasicThreeTrioModel(gridFileName, cardFileName);
  }
}