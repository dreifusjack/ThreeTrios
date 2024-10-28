package cs3500.threetrios;

import java.io.IOException;

import cs3500.threetrios.model.BasicThreeTrioModel;
import cs3500.threetrios.view.ThreeTrioTextView;

public class Main {
  public static void main(String[] args) throws IOException {
    BasicThreeTrioModel model3x3 = new BasicThreeTrioModel("world3x3.txt", "cards3x3.txt");
    model3x3.startGame();
    ThreeTrioTextView view = new ThreeTrioTextView(model3x3);
    System.out.println(view.toString());

  }
}