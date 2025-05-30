package cs3500.threetrios.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.network.messages.JoinGameMessage;
import cs3500.threetrios.network.messages.PlayCardMessage;
import cs3500.threetrios.network.messages.GameStateUpdateMessage;
import cs3500.threetrios.network.messages.PlayerAssignedMessage;
import cs3500.threetrios.network.messages.SerializableGameState;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.controller.NetworkThreeTriosController;

/**
 * Network client for Three Trios game that connects to a game server.
 * Handles sending player actions to the server and receiving game state
 * updates.
 */
public class ThreeTriosClient {
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private boolean connected;
  private TeamColor assignedColor;
  private final List<NetworkGameListener> listeners;
  private Thread messageListener;

  /**
   * Creates a new Three Trios network client.
   */
  public ThreeTriosClient() {
    this.connected = false;
    this.listeners = new ArrayList<>();
  }

  /**
   * Connects to the game server at the specified host and port.
   * 
   * @param host       the server host
   * @param port       the server port
   * @param playerName the name of the player
   * @throws IOException if connection fails
   */
  public void connect(String host, int port, String playerName) throws IOException {
    socket = new Socket(host, port);
    out = new ObjectOutputStream(socket.getOutputStream());
    in = new ObjectInputStream(socket.getInputStream());
    connected = true;

    // Send join game message
    sendMessage(new JoinGameMessage(playerName));

    // Start listening for messages from server
    startMessageListener();
  }

  /**
   * Sends a play card message to the server.
   * 
   * @param row       the row to play the card to
   * @param col       the column to play the card to
   * @param handIndex the index of the card in the player's hand
   */
  public void playCard(int row, int col, int handIndex) {
    if (connected) {
      sendMessage(new PlayCardMessage(row, col, handIndex));
    }
  }

  /**
   * Disconnects from the server.
   */
  public void disconnect() {
    connected = false;
    try {
      if (messageListener != null) {
        messageListener.interrupt();
      }
      if (out != null) {
        out.close();
      }
      if (in != null) {
        in.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      // Log error but don't throw - we're disconnecting anyway
      System.err.println("Error during disconnect: " + e.getMessage());
    }
  }

  /**
   * Adds a listener for network game events.
   * 
   * @param listener the listener to add
   */
  public void addNetworkGameListener(NetworkGameListener listener) {
    listeners.add(listener);
  }

  /**
   * Gets the team color assigned to this client.
   * 
   * @return the assigned team color, or null if not yet assigned
   */
  public TeamColor getAssignedColor() {
    return assignedColor;
  }

  /**
   * Checks if the client is connected to the server.
   * 
   * @return true if connected, false otherwise
   */
  public boolean isConnected() {
    return connected;
  }

  /**
   * Sends a message to the server.
   * 
   * @param message the message to send
   */
  private void sendMessage(GameMessage message) {
    try {
      out.writeObject(message);
      out.flush();
    } catch (IOException e) {
      System.err.println("Error sending message: " + e.getMessage());
      connected = false;
      notifyConnectionLost();
    }
  }

  /**
   * Starts a background thread to listen for messages from the server.
   */
  private void startMessageListener() {
    messageListener = new Thread(() -> {
      while (connected) {
        try {
          GameMessage message = (GameMessage) in.readObject();
          handleMessage(message);
        } catch (IOException | ClassNotFoundException e) {
          if (connected) {
            System.err.println("Error receiving message: " + e.getMessage());
            connected = false;
            notifyConnectionLost();
          }
          break;
        }
      }
    });
    messageListener.start();
  }

  /**
   * Handles incoming messages from the server.
   * 
   * @param message the message received from the server
   */
  private void handleMessage(GameMessage message) {
    switch (message.getType()) {
      case PLAYER_ASSIGNED:
        PlayerAssignedMessage assignedMsg = (PlayerAssignedMessage) message;
        assignedColor = assignedMsg.getAssignedColor();
        notifyPlayerAssigned(assignedColor);
        break;
      case GAME_STATE_UPDATE:
        GameStateUpdateMessage stateMsg = (GameStateUpdateMessage) message;
        notifyGameStateUpdate(stateMsg.getGameState());
        break;
      case GAME_STARTED:
        notifyGameStarted();
        break;
      case GAME_OVER:
        notifyGameOver();
        break;
      case PLAYER_TURN_CHANGE:
        notifyPlayerTurnChange();
        break;
      case INVALID_MOVE:
        notifyInvalidMove();
        break;
      default:
        System.err.println("Unknown message type: " + message.getType());
        break;
    }
  }

  /**
   * Notifies listeners that a player has been assigned a color.
   * 
   * @param color the assigned color
   */
  private void notifyPlayerAssigned(TeamColor color) {
    for (NetworkGameListener listener : listeners) {
      listener.onPlayerAssigned(color);
    }
  }

  /**
   * Notifies listeners of a game state update.
   * 
   * @param gameState the updated serializable game state
   */
  private void notifyGameStateUpdate(SerializableGameState gameState) {
    for (NetworkGameListener listener : listeners) {
      if (listener instanceof NetworkThreeTriosController) {
        ((NetworkThreeTriosController) listener).onGameStateUpdate(gameState);
      } else {
        // For other listeners, convert to adapter
        listener.onGameStateUpdate(new SerializableGameStateAdapter(gameState));
      }
    }
  }

  /**
   * Notifies listeners that the game has started.
   */
  private void notifyGameStarted() {
    for (NetworkGameListener listener : listeners) {
      listener.onGameStarted();
    }
  }

  /**
   * Notifies listeners that the game is over.
   */
  private void notifyGameOver() {
    for (NetworkGameListener listener : listeners) {
      listener.onGameOver();
    }
  }

  /**
   * Notifies listeners that the player turn has changed.
   */
  private void notifyPlayerTurnChange() {
    for (NetworkGameListener listener : listeners) {
      listener.onPlayerTurnChange();
    }
  }

  /**
   * Notifies listeners of an invalid move.
   */
  private void notifyInvalidMove() {
    for (NetworkGameListener listener : listeners) {
      listener.onInvalidMove();
    }
  }

  /**
   * Notifies listeners that the connection was lost.
   */
  private void notifyConnectionLost() {
    for (NetworkGameListener listener : listeners) {
      listener.onConnectionLost();
    }
  }
}
