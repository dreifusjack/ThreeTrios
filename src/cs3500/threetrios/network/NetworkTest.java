package cs3500.threetrios.network;

import cs3500.threetrios.controller.NetworkThreeTriosController;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.view.TTGUIView;

/**
 * Simple test class to verify network functionality.
 * This is not a unit test but rather a manual test to check that the network
 * components can be instantiated and basic operations work.
 */
public class NetworkTest {

  /**
   * Main method to run basic network tests.
   * 
   * @param args command line arguments (not used)
   */
  public static void main(String[] args) {
    System.out.println("Starting Three Trios Network Tests...");

    try {
      testClientCreation();
      testServerCreation();
      testControllerCreation();
      testMessageCreation();

      System.out.println("All basic tests passed!");
      System.out.println("\nTo test full functionality:");
      System.out.println("1. Run: java cs3500.threetrios.ThreeTrios network server");
      System.out.println("2. Run: java cs3500.threetrios.ThreeTrios network client");
      System.out.println("3. Run: java cs3500.threetrios.ThreeTrios network client");
      System.out.println("4. Play the game!");

    } catch (Exception e) {
      System.err.println("Test failed: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Tests that a client can be created.
   */
  private static void testClientCreation() {
    System.out.print("Testing client creation... ");
    ThreeTriosClient client = new ThreeTriosClient();
    if (client != null && !client.isConnected()) {
      System.out.println("PASS");
    } else {
      throw new RuntimeException("Client creation failed");
    }
  }

  /**
   * Tests that a server can be created.
   */
  private static void testServerCreation() {
    System.out.print("Testing server creation... ");
    ThreeTriosServer server = new ThreeTriosServer(8081); // Use different port
    if (server != null) {
      System.out.println("PASS");
    } else {
      throw new RuntimeException("Server creation failed");
    }
  }

  /**
   * Tests that a network controller can be created.
   */
  private static void testControllerCreation() {
    System.out.print("Testing controller creation... ");
    ThreeTriosClient client = new ThreeTriosClient();
    BasicThreeTriosModel dummyModel = new BasicThreeTriosModel();

    try {
      // Initialize the dummy model to avoid "game not started" error
      dummyModel.startGame(createMinimalGrid(), createMinimalDeck(), 1);
    } catch (Exception e) {
      System.err.println("Warning: Could not initialize dummy model: " + e.getMessage());
    }

    TTGUIView view = new TTGUIView(dummyModel);

    NetworkThreeTriosController controller = new NetworkThreeTriosController(client, view);
    if (controller != null) {
      System.out.println("PASS");
    } else {
      throw new RuntimeException("Controller creation failed");
    }
  }

  /**
   * Tests that network messages can be created.
   */
  private static void testMessageCreation() {
    System.out.print("Testing message creation... ");

    // Test various message types
    cs3500.threetrios.network.messages.JoinGameMessage joinMsg = new cs3500.threetrios.network.messages.JoinGameMessage(
        "TestPlayer");

    cs3500.threetrios.network.messages.PlayCardMessage playMsg = new cs3500.threetrios.network.messages.PlayCardMessage(
        0, 1, 2);

    cs3500.threetrios.network.messages.PlayerAssignedMessage assignMsg = new cs3500.threetrios.network.messages.PlayerAssignedMessage(
        cs3500.threetrios.model.TeamColor.RED);

    if (joinMsg.getType() == GameMessage.MessageType.JOIN_GAME &&
        playMsg.getType() == GameMessage.MessageType.PLAY_CARD &&
        assignMsg.getType() == GameMessage.MessageType.PLAYER_ASSIGNED) {
      System.out.println("PASS");
    } else {
      throw new RuntimeException("Message creation failed");
    }
  }

  /**
   * Creates a minimal grid for testing.
   * 
   * @return a minimal 1x1 grid
   */
  private static cs3500.threetrios.model.GridCell[][] createMinimalGrid() {
    cs3500.threetrios.model.GridCell[][] grid = new cs3500.threetrios.model.GridCell[1][1];
    grid[0][0] = new cs3500.threetrios.model.CardCell();
    return grid;
  }

  /**
   * Creates a minimal deck for testing.
   * 
   * @return a minimal deck with 2 cards
   */
  private static java.util.List<cs3500.threetrios.model.Card> createMinimalDeck() {
    java.util.List<cs3500.threetrios.model.Card> deck = new java.util.ArrayList<>();
    deck.add(new cs3500.threetrios.model.ThreeTriosCard(
        "TestCard1",
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.ONE,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.ONE,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.ONE,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.ONE));
    deck.add(new cs3500.threetrios.model.ThreeTriosCard(
        "TestCard2",
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.TWO,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.TWO,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.TWO,
        cs3500.threetrios.model.ThreeTriosCard.AttackValue.TWO));
    return deck;
  }
}
