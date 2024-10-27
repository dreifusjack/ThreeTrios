package cs3500.threetrios;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    BasicThreeTrioModel model3x3 = new BasicThreeTrioModel("src/cs3500/threetrios/world3x3.txt", "src/cs3500/threetrios/cards3x3.txt");
    model3x3.startGame();
    ThreeTrioTextView view = new ThreeTrioTextView(model3x3);
    System.out.println(view.toString());

  }
}