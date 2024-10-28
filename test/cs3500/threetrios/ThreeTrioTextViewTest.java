package cs3500.threetrios;

import cs3500.threetrios.model.BasicThreeTrioModel;
import cs3500.threetrios.model.ThreeTriosModel;

public class ThreeTrioTextViewTest extends AbstractThreeTrioViewTest {

  @Override
  protected ThreeTriosModel createModel(String gridFileName, String cardFileName) {
    return new BasicThreeTrioModel(gridFileName, cardFileName);
  }
}