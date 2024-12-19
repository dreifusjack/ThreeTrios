package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import cs3500.threetrios.model.decorators.level1.VariantCardModelDecorator;
import cs3500.threetrios.model.decorators.level1.FallenAceCardDecorator;
import cs3500.threetrios.model.decorators.level1.PassThroughCardDecorator;
import cs3500.threetrios.model.decorators.level1.ReverseCardDecorator;
import cs3500.threetrios.model.decorators.level2.PlusModelDecorator;
import cs3500.threetrios.model.decorators.level2.SameModelDecorator;
import cs3500.threetrios.controller.ThreeTriosListenerController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosCard;
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
    ThreeTriosModel model = createAndSetupModel();
    String redPlayerType;
    String bluePlayerType;
    List<String> redStrategies = new ArrayList<>();
    List<String> blueStrategies = new ArrayList<>();

    if (!parseCommandLine(args, redStrategies, blueStrategies)) {
      redPlayerType = parseITerminalInput("RED", redStrategies);
      bluePlayerType = parseITerminalInput("BLUE", blueStrategies);
    } else {
      redPlayerType = redStrategies.remove(0);
      bluePlayerType = blueStrategies.remove(0);
    }

    TTGUIView redView = new TTGUIView(model);
    TTGUIView blueView = new TTGUIView(model);

    PlayerActions redPlayerActions = createPlayerActions(
            redPlayerType, TeamColor.RED, redStrategies);
    PlayerActions bluePlayerActions = createPlayerActions(
            bluePlayerType, TeamColor.BLUE, blueStrategies);

    new ThreeTriosListenerController(model, redView, redPlayerActions);
    new ThreeTriosListenerController(model, blueView, bluePlayerActions);
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
    ThreeTriosModel model4x3 = new BasicThreeTriosModel(rand1);

    Scanner scanner = new Scanner(System.in);
    List<PassThroughCardDecorator> decorators = new ArrayList<>();

    // Collect rules to apply
    System.out.println("Enter rule sets to apply (reverse, fallenace). Type 'done' to finish:");
    while (true) {
      String ruleInput = scanner.nextLine().trim().toLowerCase();
      switch (ruleInput) {
        case "reverse":
          decorators.add(new ReverseCardDecorator());
          break;
        case "fallenace":
          decorators.add(new FallenAceCardDecorator());
          break;
        case "done":
          model4x3 = decorators.isEmpty()
                  ? model4x3
                  : new VariantCardModelDecorator(model4x3, decorators);
          break;
        default:
          System.out.println("Invalid rule. Valid options are reverse, fallenace, or done.");
      }
      if ("done".equals(ruleInput)) {
        break;
      }
    }


    Scanner scanner2 = new Scanner(System.in);
    System.out.println("Enter the game logic you want to apply (same, plus). " +
            "Type 'none' for no game logic:");
    String ruleInput2 = scanner2.nextLine().trim().toLowerCase();


    switch (ruleInput2) {
      case "same":
        model4x3 = new SameModelDecorator(model4x3);
        break;
      case "plus":
        model4x3 = new PlusModelDecorator(model4x3);
        break;
      case "none":
        break;
      default:
        System.out.println("Invalid game logic");
        break;
    }


    model4x3.startGame(grid(), deck(), 7);
    return model4x3;
  }

  /**
   * Helper method to create PlayerActions based on the input argument.
   *
   * @param arg        user input
   * @param teamColor  given color
   * @param strategies given user specified strategies
   * @return given player actions.
   */
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

  /**
   * Helper method to retrieve a ThreeTriosStrategy based on the input string.
   *
   * @param input user input
   * @return strategy
   */
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

  /**
   * Helper method to checks if a player type is valid.
   *
   * @param playerType player
   * @return true if valid player
   */
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
    deck.add(createCard("HeroKnight", "1", "1", "2", "1"));
    deck.add(createCard("WorldDragon", "1", "6", "5", "1"));
    deck.add(createCard("SkyWhale", "3", "1", "1", "2"));
    deck.add(createCard("FirePhoenix", "7", "3", "4", "2"));
    deck.add(createCard("ThunderTiger", "1", "9", "5", "4"));
    deck.add(createCard("SilverWolf", "4", "3", "1", "7"));
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
  private static ThreeTriosCard createCard(
          String name, String attack1, String attack2, String attack3, String attack4) {
    return new ThreeTriosCard(
            name,
            ThreeTriosCard.AttackValue.fromString(attack1),
            ThreeTriosCard.AttackValue.fromString(attack2),
            ThreeTriosCard.AttackValue.fromString(attack3),
            ThreeTriosCard.AttackValue.fromString(attack4)
    );
  }
}