package cs3500.threetrios;

import java.io.IOException;
import java.util.Random;

import cs3500.threetrios.model.BasicThreeTrioModel;
import cs3500.threetrios.view.ThreeTrioTextView;

public class Main {


  public static void main(String[] args) throws IOException {
    Random rand1 = new Random(2);


    BasicThreeTrioModel model3x3 = new BasicThreeTrioModel("world3x3.txt", "cards3x3.txt", rand1);
    model3x3.startGame();

    model3x3.playToGrid(0, 0, 1);

    model3x3.playToGrid(0, 1, 1);

    ThreeTrioTextView view = new ThreeTrioTextView(model3x3);
    System.out.println(view.toString());
    System.out.println(model3x3.isGameOver());
    System.out.println(model3x3.getWinner());


    System.out.println(model3x3.getCurrentPlayer().getHand());
  }
}