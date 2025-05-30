package cs3500.threetrios;

import javax.swing.JOptionPane;

import cs3500.threetrios.controller.NetworkThreeTriosController;
import cs3500.threetrios.network.ThreeTriosClient;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.model.BasicThreeTriosModel;

/**
 * Main class for running a networked Three Trios client.
 * This allows players to connect to a game server and play against other
 * players
 * on different devices.
 */
public class NetworkThreeTrios {

  /**
   * Main method to start a networked Three Trios client.
   * 
   * @param args command line arguments (optional: host port playerName)
   */
  public static void main(String[] args) {
    String host = "localhost";
    int port = 8080;
    String playerName = "Player";

    // Parse command line arguments
    if (args.length >= 1) {
      host = args[0];
    }
    if (args.length >= 2) {
      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        System.err.println("Invalid port number, using default: " + port);
      }
    }
    if (args.length >= 3) {
      playerName = args[2];
    }

    // If no command line arguments, prompt user for connection details
    if (args.length == 0) {
      host = promptForInput("Enter server host (default: localhost):", "localhost");
      String portStr = promptForInput("Enter server port (default: 8080):", "8080");
      try {
        port = Integer.parseInt(portStr);
      } catch (NumberFormatException e) {
        port = 8080;
      }
      playerName = promptForInput("Enter your player name:", "Player");
    }

    try {
      // Create network client and view
      ThreeTriosClient client = new ThreeTriosClient();

      // Create a dummy model for the view (the real game state comes from the server)
      // We need to start the dummy model to avoid the "game not started" error
      BasicThreeTriosModel dummyModel = new BasicThreeTriosModel();
      try {
        // Initialize with minimal grid and deck to avoid errors
        dummyModel.startGame(createMinimalGrid(), createMinimalDeck(), 1);
      } catch (Exception e) {
        // If initialization fails, create a simpler dummy
        System.err.println("Warning: Could not initialize dummy model: " + e.getMessage());
      }
      TTGUIView view = new TTGUIView(dummyModel);

      // Create network controller
      NetworkThreeTriosController controller = new NetworkThreeTriosController(client, view);

      // Connect to server
      System.out.println("Connecting to server at " + host + ":" + port + " as " + playerName);
      controller.connectToServer(host, port, playerName);

      System.out.println("Connected! Waiting for another player...");

      // Add shutdown hook to disconnect gracefully
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        System.out.println("Disconnecting from server...");
        controller.disconnect();
      }));

    } catch (Exception e) {
      System.err.println("Failed to connect to server: " + e.getMessage());
      JOptionPane.showMessageDialog(null,
          "Failed to connect to server: " + e.getMessage(),
          "Connection Error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
  }

  /**
   * Prompts the user for input with a default value.
   * 
   * @param message      the prompt message
   * @param defaultValue the default value if user enters nothing
   * @return the user input or default value
   */
  private static String promptForInput(String message, String defaultValue) {
    String input = JOptionPane.showInputDialog(null, message, defaultValue);
    if (input == null || input.trim().isEmpty()) {
      return defaultValue;
    }
    return input.trim();
  }

  /**
   * Creates a minimal grid for the dummy model.
   * 
   * @return a minimal 1x1 grid
   */
  private static cs3500.threetrios.model.GridCell[][] createMinimalGrid() {
    cs3500.threetrios.model.GridCell[][] grid = new cs3500.threetrios.model.GridCell[1][1];
    grid[0][0] = new cs3500.threetrios.model.CardCell();
    return grid;
  }

  /**
   * Creates a minimal deck for the dummy model.
   * 
   * @return a minimal deck with 2 cards
   */
  private static java.util.List<cs3500.threetrios.model.Card> createMinimalDeck() {
    java.util.List<cs3500.threetrios.model.Card> deck = new java.util.ArrayList<>();
    deck.add(new cs3500.threetrios.model.ThreeTriosCard(
        "DummyCard1",
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.ONE,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.ONE,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.ONE,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.ONE));
    deck.add(new cs3500.threetrios.model.ThreeTriosCard(
        "DummyCard2",
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.TWO,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.TWO,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.TWO,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.TWO));
    return deck;
  }
}
