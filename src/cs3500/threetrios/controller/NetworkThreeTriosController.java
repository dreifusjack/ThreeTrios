package cs3500.threetrios.controller;

import javax.swing.JOptionPane;

import cs3500.threetrios.network.NetworkGameListener;
import cs3500.threetrios.network.ThreeTriosClient;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.network.SerializableGameStateAdapter;
import cs3500.threetrios.network.messages.SerializableGameState;

/**
 * Controller for networked Three Trios games that coordinates between the
 * network client,
 * view, and game state. This controller handles network events and user
 * interactions
 * for multiplayer games.
 */
public class NetworkThreeTriosController implements PlayerActionListener, NetworkGameListener {
  private final ThreeTriosClient client;
  private final TTGUIView view;
  private ReadOnlyThreeTriosModel currentGameState;
  private TeamColor assignedColor;
  private int selectedCardIndex;
  private boolean gameStarted;

  /**
   * Creates a new network controller.
   * 
   * @param client the network client
   * @param view   the game view
   */
  public NetworkThreeTriosController(ThreeTriosClient client, TTGUIView view) {
    this.client = client;
    this.view = view;
    this.selectedCardIndex = -1;
    this.gameStarted = false;

    // Set up listeners
    this.client.addNetworkGameListener(this);
    this.view.addPlayerActionListener(this);

    // Hide the view initially until we get game state
    this.view.setVisible(false);
  }

  @Override
  public void handleCardSelection(TeamColor playerColor, int cardIndex) {
    if (!gameStarted || currentGameState == null) {
      return;
    }

    if (currentGameState.isGameOver()) {
      return;
    }

    // Check if it's the player's turn
    if (currentGameState.getCurrentPlayer().getColor() != assignedColor) {
      JOptionPane.showMessageDialog(view, "It's not your turn!");
      return;
    }

    if (playerColor == assignedColor) {
      selectedCardIndex = cardIndex;
      view.refreshPlayingBoard(cardIndex);
    } else {
      JOptionPane.showMessageDialog(view, "You can only select cards from your hand.");
    }
  }

  @Override
  public void handleBoardSelection(int row, int col) {
    if (!gameStarted || currentGameState == null) {
      return;
    }

    if (currentGameState.isGameOver()) {
      return;
    }

    // Check if it's the player's turn
    if (currentGameState.getCurrentPlayer().getColor() != assignedColor) {
      JOptionPane.showMessageDialog(view, "It's not your turn!");
      return;
    }

    if (selectedCardIndex >= 0) {
      // Send play card message to server
      client.playCard(row, col, selectedCardIndex);
      selectedCardIndex = -1;
    } else {
      JOptionPane.showMessageDialog(view, "Please select a card first.");
    }
  }

  @Override
  public void onPlayerAssigned(TeamColor color) {
    this.assignedColor = color;
    view.updateTitle(color + " Player: Waiting for game to start...");

    // Position the window based on team color
    if (color == TeamColor.RED) {
      view.setLocation(0, 0);
    } else {
      view.setLocation(view.getSize().width, 0);
    }
    view.setVisible(true);
  }

  @Override
  public void onGameStateUpdate(ReadOnlyThreeTriosModel gameState) {
    this.currentGameState = gameState;

    // Make sure the view is visible now that we have game state
    if (!view.isVisible()) {
      view.setVisible(true);
    }

    view.refreshPlayingBoard(selectedCardIndex);

    if (gameStarted) {
      updateTurnDisplay();
    }
  }

  /**
   * Handles game state updates from serializable game state.
   * 
   * @param gameState the serializable game state
   */
  public void onGameStateUpdate(SerializableGameState gameState) {
    // Convert to adapter and call the main method
    onGameStateUpdate(new SerializableGameStateAdapter(gameState));
  }

  @Override
  public void onGameStarted() {
    this.gameStarted = true;
    updateTurnDisplay();
  }

  @Override
  public void onGameOver() {
    if (currentGameState != null) {
      String message = "Game Over! ";
      if (currentGameState.getWinner() != null) {
        TeamColor winnerColor = currentGameState.getWinner().getColor();
        int winnerScore = currentGameState.getPlayerScore(winnerColor);
        message += "Winner: " + winnerColor + " with score: " + winnerScore;
      } else {
        int score = currentGameState.getPlayerScore(TeamColor.RED);
        message += "It's a tie! Score: " + score;
      }

      view.updateTitle(assignedColor + " Player: Game Over!");
      JOptionPane.showMessageDialog(view, message);
    }
  }

  @Override
  public void onPlayerTurnChange() {
    updateTurnDisplay();
  }

  @Override
  public void onInvalidMove() {
    JOptionPane.showMessageDialog(view, "Invalid move! Please try again.");
    selectedCardIndex = -1;
    view.refreshPlayingBoard(selectedCardIndex);
  }

  @Override
  public void onConnectionLost() {
    JOptionPane.showMessageDialog(view, "Connection to server lost!");
    view.updateTitle(assignedColor + " Player: Disconnected");
  }

  /**
   * Updates the title display based on whose turn it is.
   */
  private void updateTurnDisplay() {
    if (currentGameState != null && gameStarted) {
      TeamColor currentPlayerColor = currentGameState.getCurrentPlayer().getColor();
      if (currentPlayerColor == assignedColor) {
        view.updateTitle(assignedColor + " Player: Your Turn");
      } else {
        view.updateTitle(assignedColor + " Player: Waiting for opponent");
      }
    }
  }

  /**
   * Connects to the game server.
   * 
   * @param host       the server host
   * @param port       the server port
   * @param playerName the player name
   * @throws Exception if connection fails
   */
  public void connectToServer(String host, int port, String playerName) throws Exception {
    client.connect(host, port, playerName);
  }

  /**
   * Disconnects from the server.
   */
  public void disconnect() {
    client.disconnect();
  }
}
