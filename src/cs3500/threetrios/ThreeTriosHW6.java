package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.ThreeTrioCard;
import cs3500.threetrios.view.TTGUIView;

/**
 * Main runner class used for intermediate testing throughout implementation process. This
 * class will change in the future.
 */
public class ThreeTriosHW6 {
  /**
   * Current runner to test how model and view are interacting.
   *
   * @param args list of specified arguments to run the game
   */
  public static void main(String[] args) {
    Random rand1 = new Random(2);
    BasicThreeTriosModel model4x3 = new BasicThreeTriosModel(rand1);

    model4x3.startGame(grid(), deck(), 7);
    model4x3.playToGrid(2, 2, 0); // added to test playing a card with GUI

    TTGUIView view = new TTGUIView(model4x3);
    view.setVisible(true);
    System.out.println(view);
  }

  /**
   * Used to create an instance of the grid for ThreeTrios.jar purposes. This allows the jar file
   * to be run outside the project and not requiring access to grid configuration files.
   *
   * @return grid of GridCells for model construction
   */
  private static GridCell[][] grid() {
    GridCell[][] grid = new GridCell[4][3];
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length; col++) {
        grid[row][col] = new CardCell();
      }
    }
    grid[0][2] = new Hole();
    grid[1][2] = new Hole();
    grid[2][1] = new Hole();
    grid[3][0] = new Hole();
    grid[3][2] = new Hole();
    return grid;
  }

  /**
   * Used to create an instance of the deck for ThreeTrios.jar purposes. This allows the jar file
   * to be run outside the project and not requiring access to card configuration files.
   *
   * @return List of cards for model construction
   */
  private static List<Card> deck() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(new ThreeTrioCard("CorruptKing",
            ThreeTrioCard.AttackValue.fromString("3"),
            ThreeTrioCard.AttackValue.fromString("1"),
            ThreeTrioCard.AttackValue.fromString("1"),
            ThreeTrioCard.AttackValue.fromString("2")));

    cards.add(new ThreeTrioCard("AngryDragon",
            ThreeTrioCard.AttackValue.fromString("5"),
            ThreeTrioCard.AttackValue.fromString("7"),
            ThreeTrioCard.AttackValue.fromString("1"),
            ThreeTrioCard.AttackValue.fromString("4")));

    cards.add(new ThreeTrioCard("WindBird",
            ThreeTrioCard.AttackValue.fromString("2"),
            ThreeTrioCard.AttackValue.fromString("5"),
            ThreeTrioCard.AttackValue.fromString("5"),
            ThreeTrioCard.AttackValue.fromString("A")));

    cards.add(new ThreeTrioCard("HeroKnight",
            ThreeTrioCard.AttackValue.fromString("A"),
            ThreeTrioCard.AttackValue.fromString("4"),
            ThreeTrioCard.AttackValue.fromString("4"),
            ThreeTrioCard.AttackValue.fromString("1")));

    cards.add(new ThreeTrioCard("WorldDragon",
            ThreeTrioCard.AttackValue.fromString("1"),
            ThreeTrioCard.AttackValue.fromString("6"),
            ThreeTrioCard.AttackValue.fromString("5"),
            ThreeTrioCard.AttackValue.fromString("1")));

    cards.add(new ThreeTrioCard("SkyWhale",
            ThreeTrioCard.AttackValue.fromString("3"),
            ThreeTrioCard.AttackValue.fromString("1"),
            ThreeTrioCard.AttackValue.fromString("1"),
            ThreeTrioCard.AttackValue.fromString("2")));

    cards.add(new ThreeTrioCard("FirePhoenix",
            ThreeTrioCard.AttackValue.fromString("2"),
            ThreeTrioCard.AttackValue.fromString("3"),
            ThreeTrioCard.AttackValue.fromString("4"),
            ThreeTrioCard.AttackValue.fromString("2")));

    cards.add(new ThreeTrioCard("ThunderTiger",
            ThreeTrioCard.AttackValue.fromString("3"),
            ThreeTrioCard.AttackValue.fromString("9"),
            ThreeTrioCard.AttackValue.fromString("5"),
            ThreeTrioCard.AttackValue.fromString("4")));

    cards.add(new ThreeTrioCard("SilverWolf",
            ThreeTrioCard.AttackValue.fromString("4"),
            ThreeTrioCard.AttackValue.fromString("3"),
            ThreeTrioCard.AttackValue.fromString("6"),
            ThreeTrioCard.AttackValue.fromString("8")));

    cards.add(new ThreeTrioCard("MysticFairy",
            ThreeTrioCard.AttackValue.fromString("5"),
            ThreeTrioCard.AttackValue.fromString("5"),
            ThreeTrioCard.AttackValue.fromString("A"),
            ThreeTrioCard.AttackValue.fromString("2")));

    cards.add(new ThreeTrioCard("OceanKraken",
            ThreeTrioCard.AttackValue.fromString("1"),
            ThreeTrioCard.AttackValue.fromString("4"),
            ThreeTrioCard.AttackValue.fromString("8"),
            ThreeTrioCard.AttackValue.fromString("6")));

    cards.add(new ThreeTrioCard("GoldenEagle",
            ThreeTrioCard.AttackValue.fromString("A"),
            ThreeTrioCard.AttackValue.fromString("2"),
            ThreeTrioCard.AttackValue.fromString("7"),
            ThreeTrioCard.AttackValue.fromString("1")));
    return cards;
  }
}