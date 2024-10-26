import cs3500.threetrios.BasicThreeTrioModel;
import cs3500.threetrios.ThreeTriosModel;

public class ThreeTriosModelTest extends AbstractThreeTriosModelTest {
  @Override
  protected ThreeTriosModel createModel(String gridFileName, String cardFileName) {
    return new BasicThreeTrioModel(gridFileName, cardFileName);
  }
}
