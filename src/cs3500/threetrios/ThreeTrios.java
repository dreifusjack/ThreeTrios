package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import cs3500.threetrios.controller.ThreeTriosFeaturesController;
import cs3500.threetrios.controller.ThreeTriosSetupController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
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
   * strategies, where each strategy is one of the above. If you want to create a config file of
   * 2 AI players each with a ChainStrategy, this is an example for you arguments:
   * chainstrategy strategy1 strategy2 end chainstrategy strategy3 strategy4 end.
   *
   * @param args two strings, first specifying the RED player then the BLUE player
   */
  public static void main(String[] args) {
    List<String> redStrategies = new ArrayList<>();
    List<String> blueStrategies = new ArrayList<>();

    if (!parseCommandLine(args, redStrategies, blueStrategies)) {
      parseInteractiveInput(redStrategies, blueStrategies);
    }

    ThreeTriosModel model = createAndSetupModel();

    TTGUIView redView = new TTGUIView(model);
    TTGUIView blueView = new TTGUIView(model);

    PlayerActions redPlayerActions = createPlayerActions(redStrategies.isEmpty() ? "human" :
            "chainstrategy", TeamColor.RED, redStrategies);
    PlayerActions bluePlayerActions = createPlayerActions(blueStrategies.isEmpty() ? "human" :
            "chainstrategy", TeamColor.BLUE, blueStrategies);

    new ThreeTriosFeaturesController(model, redView, redPlayerActions);
    new ThreeTriosFeaturesController(model, blueView, bluePlayerActions);
  }

  /**
   * Parses the command-line arguments.
   *
   * @param args          is the command-line arguments.
   * @param redStrategies is the list to store RED strategies.
   * @param blueStrategies is the list to store BLUE strategies.
   * @return true if valid command-line arguments are given, false otherwise.
   */
  private static boolean parseCommandLine(String[] args, List<String> redStrategies,
                                          List<String> blueStrategies) {
    if (args.length >= 2) {
      int index = 0;

      String redPlayerType = parsePlayerType(args, index, redStrategies);
      if (redPlayerType == null) return false;
      index += redStrategies.isEmpty() ? 1 : redStrategies.size() + 2;

      String bluePlayerType = parsePlayerType(args, index, blueStrategies);
      if (bluePlayerType == null) return false;

      return true;
    }
    return false;
  }

  /**
   * Parses player type and strategies from command-line arguments.
   *
   * @param args          is the command-line arguments.
   * @param index         is the current index to parse.
   * @param strategies    is the list to store parsed strategies.
   * @return the parsed player type, or null if invalid.
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
      System.out.println("Invalid player type: " + args[index]);
      return null;
    }
  }

  /**
   * Parses player input interactively if no valid command-line arguments are provided.
   *
   * @param redStrategies is the list to store RED strategies.
   * @param blueStrategies is the list to store BLUE strategies.
   */
  private static void parseInteractiveInput(List<String> redStrategies,
                                            List<String> blueStrategies) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter player type for RED team (human, strategy1, strategy2, strategy3, " +
            "strategy4, chainstrategy): ");
    String redPlayerType = scanner.nextLine().trim().toLowerCase();
    parseChainStrategies(scanner, redPlayerType, redStrategies);

    System.out.println("Enter player type for BLUE team (human, strategy1, strategy2, strategy3, " +
            "strategy4, chainstrategy): ");
    String bluePlayerType = scanner.nextLine().trim().toLowerCase();
    parseChainStrategies(scanner, bluePlayerType, blueStrategies);
  }

  /**
   * Parses chain strategies interactively.
   *
   * @param scanner     is the scanner to read input.
   * @param playerType  is the type of player.
   * @param strategies  is the list to store strategies.
   */
  private static void parseChainStrategies(Scanner scanner, String playerType,
                                           List<String> strategies) {
    if (playerType.equals("chainstrategy")) {
      System.out.println("Enter the strategies for ChainStrategy one by one (strategy1, " +
              "strategy2,...) Type 'end' to finish:");
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

  /**
   * Creates and sets up the ThreeTrios model.
   *
   * @return the playable ThreeTriosModel.
   */
  private static ThreeTriosModel createAndSetupModel() {
    ThreeTriosModel model = new BasicThreeTriosModel(new Random(2));
    ThreeTriosSetupController setupController = new ThreeTriosSetupController(
            "world4x3manyholes.txt",
            "cardsopponentweak.txt");
    setupController.playGame(model);
    return model;
  }

  /**
   * Creates PlayerActions based on the input argument.
   *
   * @param arg        is the input string specifying the player type.
   * @param teamColor  is the team color (RED or BLUE).
   * @param strategies is list of strategies if chain strategy is selected.
   * @return the corresponding PlayerActions instance.
   */
  private static PlayerActions createPlayerActions(String arg, TeamColor teamColor,
                                                   List<String> strategies) {
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
   * Helper for getting the strategies in tha list of a ChainStrategy.
   *
   * @param input is the input string specifying the strategy.
   * @return the corresponding ThreeTriosStrategy instance, or null if invalid.
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
   * Checks if a player type is valid.
   *
   * @param playerType the player type to check.
   * @return true if the player type is valid, false otherwise.
   */
  private static boolean isValidPlayerType(String playerType) {
    return playerType.equalsIgnoreCase("human") ||
            playerType.equalsIgnoreCase("strategy1") ||
            playerType.equalsIgnoreCase("strategy2") ||
            playerType.equalsIgnoreCase("strategy3") ||
            playerType.equalsIgnoreCase("strategy4") ||
            playerType.equalsIgnoreCase("chainstrategy");
  }
}