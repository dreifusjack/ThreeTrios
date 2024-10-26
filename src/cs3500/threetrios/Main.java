package cs3500.threetrios;

import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    BasicThreeTrioModel model = new BasicThreeTrioModel("src/cs3500/threetrios/world1.txt", "src/cs3500/threetrios/card1.txt");
    model.startGame();
    model.playToGrid(0, 0, 0);
    ThreeTrioTextView view = new ThreeTrioTextView(model);
//    System.out.println(model.getCurrentPlayer().getHand());
    // Print the grid and current player


    System.out.println(view.toString());
  }
}