package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import cs3500.threetrios.controller.ThreeTriosFeaturesController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTrioCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.AIPlayer;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.player.strategy.ChainStrategy;
import cs3500.threetrios.player.strategy.CornerStrategy;
import cs3500.threetrios.player.strategy.MaximizeFlipsStrategy;
import cs3500.threetrios.player.strategy.MinimizeFlipsStrategy;
import cs3500.threetrios.player.strategy.MinimaxStrategy;
import cs3500.threetrios.player.strategy.ThreeTriosStrategy;
import cs3500.threetrios.provider.controller.AdapterFeatureController;
import cs3500.threetrios.provider.model.ModelAdapter;
import cs3500.threetrios.provider.view.SimpleThreeTriosView;
import cs3500.threetrios.provider.view.ThreeTriosSwingView;
import cs3500.threetrios.view.TTGUIView;

/**
 * Main runner class used to run a new game of ThreeTrios.
 */
public class ThreeTrios {

  /**
   * Runner that allows users to play a game of ThreeTrios!
   * Pass in arguments to specify the player types for the game.
   * "human" represents a human player.
   * "strategy1" represents an AIPlayer using the MaximizeFlipsStrategy.
   * "strategy2" represents an AIPlayer using the CornerStrategy.
   * "strategy3" represents an AIPlayer using the MinimizeFlipsStrategy.
   * "strategy4" represents an AIPlayer using the MinimaxStrategy.
   * "strategychain:strategyX,strategyY,..." represents an AIPlayer using a specified chain of
   * strategies, where each strategy is one of the above.
   *
   * @param args two strings, first specifying the RED player then the BLUE player
   */
  public static void main(String[] args) {
    String redPlayerType = null;
    String bluePlayerType = null;
    List<String> redStrategies = new ArrayList<>();
    List<String> blueStrategies = new ArrayList<>();

    if (!parseCommandLine(args, redStrategies, blueStrategies)) {
      redPlayerType = parseITerminalInput("RED", redStrategies);
      bluePlayerType = parseITerminalInput("BLUE", blueStrategies);
    } else {
      redPlayerType = redStrategies.remove(0);
      bluePlayerType = blueStrategies.remove(0);
    }

    ThreeTriosModel model = createAndSetupModel();
    cs3500.threetrios.provider.model.ThreeTriosModel modelAdapter = new ModelAdapter(model);

    TTGUIView redView = new TTGUIView(model);
    SimpleThreeTriosView blueView = new SimpleThreeTriosView(modelAdapter);

    PlayerActions redPlayerActions = createPlayerActions(
            redPlayerType, TeamColor.RED, redStrategies);
    PlayerActions bluePlayerActions = createPlayerActions(
            bluePlayerType, TeamColor.BLUE, blueStrategies);

    new ThreeTriosFeaturesController(model, redView, redPlayerActions);
    new AdapterFeatureController(modelAdapter, model, blueView, bluePlayerActions);
  }

  /**
   * Helper method to parses the command-line arguments.
   */
  private static boolean parseCommandLine(String[] args, List<String> redStrategies,
                                          List<String> blueStrategies) {
    if (args.length >= 2) {
      int index = 0;

      String redPlayerType = parsePlayerType(args, index, redStrategies);
      if (redPlayerType == null) {
        return false;
      }
      redStrategies.add(0, redPlayerType);
      index += redStrategies.size();

      String bluePlayerType = parsePlayerType(args, index, blueStrategies);
      if (bluePlayerType == null) {
        return false;
      }
      blueStrategies.add(0, bluePlayerType);

      return true;
    }
    return false;
  }

  /**
   * Helper method to parses player type and strategies from command-line arguments.
   */
  private static String parsePlayerType(String[] args, int index, List<String> strategies) {
    if (index < args.length && args[index].equalsIgnoreCase("chainstrategy")) {
      index++;
      while (index < args.length && !args[index].equalsIgnoreCase("end")) {
        strategies.add(args[index]);
        index++;
      }
      if (index < args.length && args[index].equalsIgnoreCase("end")) {
        return "chainstrategy";
      } else {
        System.out.println("Invalid arguments for chainstrategy. Missing 'end' keyword.");
        return null;
      }
    } else if (index < args.length && isValidPlayerType(args[index])) {
      return args[index];
    } else {
      System.out.println("Invalid player type: " + (index < args.length ? args[index] : "none"));
      return null;
    }
  }


  /**
   * Helper method to parses player input from the terminal if no valid
   * command-line arguments are provided.
   */
  private static String parseITerminalInput(String team, List<String> strategies) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter player type for " + team +
            " team (human, strategy1, strategy2, strategy3, strategy4, chainstrategy): ");
    String playerType = scanner.nextLine().trim().toLowerCase();
    parseChainStrategies(scanner, playerType, strategies);
    return playerType;
  }

  // Parses chain strategies in the terminal.
  private static void parseChainStrategies(
          Scanner scanner, String playerType, List<String> strategies) {
    if (playerType.equals("chainstrategy")) {
      System.out.println("Enter the strategies for ChainStrategy one by one " +
              "(strategy1, strategy2,...) Type 'end' to finish:");
      while (true) {
        String strategyInput = scanner.nextLine().trim().toLowerCase();
        if ("end".equals(strategyInput)) {
          break;
        }
        ThreeTriosStrategy strategy = getStrategyFromChain(strategyInput);
        if (strategy != null) {
          strategies.add(strategyInput);
        } else {
          System.out.println("Invalid strategy. Please enter again or type 'end' to finish.");
        }
      }
    }
  }

  // Helper method to create and sets up the ThreeTrios model.
  private static ThreeTriosModel createAndSetupModel() {
    Random rand1 = new Random(2);
    BasicThreeTriosModel model4x3 = new BasicThreeTriosModel(rand1);

    model4x3.startGame(grid(), deck(), 7);
    return model4x3;
  }


  // Helper method to create PlayerActions based on the input argument.
  private static PlayerActions createPlayerActions(String arg, TeamColor teamColor,
                                                   List<String> strategies) {
    if (arg == null) {
      throw new IllegalArgumentException("Player type must be provided.");
    }

    switch (arg) {
      case "human":
        return new HumanPlayer(teamColor);
      case "strategy1":
        return new AIPlayer(teamColor, new MaximizeFlipsStrategy());
      case "strategy2":
        return new AIPlayer(teamColor, new CornerStrategy());
      case "strategy3":
        return new AIPlayer(teamColor, new MinimizeFlipsStrategy());
      case "strategy4":
        return new AIPlayer(teamColor, new MinimaxStrategy(new ArrayList<>()));
      case "chainstrategy":
        List<ThreeTriosStrategy> strategyList = new ArrayList<>();
        for (String strategy : strategies) {
          ThreeTriosStrategy s = getStrategyFromChain(strategy);
          if (s != null) {
            strategyList.add(s);
          }
        }
        return new AIPlayer(teamColor, new ChainStrategy(strategyList));
      default:
        throw new IllegalArgumentException("Invalid player type: " + arg);
    }
  }

  // Helper method to retrieve a ThreeTriosStrategy based on the input string.
  private static ThreeTriosStrategy getStrategyFromChain(String input) {
    switch (input) {
      case "strategy1":
        return new MaximizeFlipsStrategy();
      case "strategy2":
        return new CornerStrategy();
      case "strategy3":
        return new MinimizeFlipsStrategy();
      case "strategy4":
        return new MinimaxStrategy(new ArrayList<>());
      default:
        return null;
    }
  }

  // Helper method to checks if a player type is valid.
  private static boolean isValidPlayerType(String playerType) {
    return playerType.equalsIgnoreCase("human") ||
            playerType.equalsIgnoreCase("strategy1") ||
            playerType.equalsIgnoreCase("strategy2") ||
            playerType.equalsIgnoreCase("strategy3") ||
            playerType.equalsIgnoreCase("strategy4") ||
            playerType.equalsIgnoreCase("chainstrategy");
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
   * Used to create an instance of the deck for ThreeTrios.jar purposes.
   * This allows the jar file to be run outside the project and not requiring access
   * to card configuration files.
   *
   * @return List of cards for model construction
   */
  private static List<Card> deck() {
    List<Card> deck = new ArrayList<>();
    deck.add(createCard("CorruptKing", "3", "1", "1", "2"));
    deck.add(createCard("AngryDragon", "5", "7", "1", "4"));
    deck.add(createCard("WindBird", "2", "5", "5", "A"));
    deck.add(createCard("HeroKnight", "A", "4", "4", "1"));
    deck.add(createCard("WorldDragon", "1", "6", "5", "1"));
    deck.add(createCard("SkyWhale", "3", "1", "1", "2"));
    deck.add(createCard("FirePhoenix", "2", "3", "4", "2"));
    deck.add(createCard("ThunderTiger", "3", "9", "5", "4"));
    deck.add(createCard("SilverWolf", "4", "3", "6", "8"));
    deck.add(createCard("MysticFairy", "5", "5", "A", "2"));
    deck.add(createCard("OceanKraken", "1", "4", "8", "6"));
    deck.add(createCard("GoldenEagle", "A", "2", "7", "1"));
    return deck;
  }

  /**
   * Helper method to create a ThreeTrioCard.
   *
   * @param name    Name of the card
   * @param attack1 First attack value
   * @param attack2 Second attack value
   * @param attack3 Third attack value
   * @param attack4 Fourth attack value
   * @return A new instance of ThreeTrioCard
   */
  private static ThreeTrioCard createCard(
          String name, String attack1, String attack2, String attack3, String attack4) {
    return new ThreeTrioCard(
            name,
            ThreeTrioCard.AttackValue.fromString(attack1),
            ThreeTrioCard.AttackValue.fromString(attack2),
            ThreeTrioCard.AttackValue.fromString(attack3),
            ThreeTrioCard.AttackValue.fromString(attack4)
    );
  }
}