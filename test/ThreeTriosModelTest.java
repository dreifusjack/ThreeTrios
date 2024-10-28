import cs3500.threetrios.model.BasicThreeTrioModel;
import cs3500.threetrios.model.ThreeTriosModel;

public class ThreeTriosModelTest extends AbstractThreeTriosModelTest {
  @Override
  protected ThreeTriosModel createModel(String gridFileName, String cardFileName) {
    return new BasicThreeTrioModel(gridFileName, cardFileName);
  }
}
