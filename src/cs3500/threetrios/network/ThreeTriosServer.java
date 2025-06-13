package cs3500.threetrios.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import cs3500.threetrios.network.messages.*;
import cs3500.threetrios.model.BasicThreeTriosModel;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.GridCell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.controller.ModelStatusListener;

/**
 * Network server for Three Trios game that manages networked multiplayer games.
 * Handles client connections, game state synchronization, and move validation.
 */
public class ThreeTriosServer implements ModelStatusListener {
  private ServerSocket serverSocket;
  private final int port;
  private boolean running;
  private final ConcurrentHashMap<Integer, GameSession> gameSessions;
  private final AtomicInteger nextGameId;
  private final AtomicInteger nextClientId;

  public ThreeTriosServer(int port) {
    this.port = port;
    this.running = false;
    this.gameSessions = new ConcurrentHashMap<>();
    this.nextGameId = new AtomicInteger(1);
    this.nextClientId = new AtomicInteger(1);
  }

  public void start() throws IOException {
    serverSocket = new ServerSocket(port);
    running = true;
    System.out.println("Three Trios server started on port " + port);

    while (running) {
      try {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected: " + clientSocket.getInetAddress());
        handleNewClient(clientSocket);
      } catch (IOException e) {
        if (running) {
          System.err.println("Error accepting client connection: " + e.getMessage());
        }
      }
    }
  }

  public void stop() {
    running = false;
    try {
      if (serverSocket != null) {
        serverSocket.close();
      }
    } catch (IOException e) {
      System.err.println("Error stopping server: " + e.getMessage());
    }
  }

  private void handleNewClient(Socket clientSocket) {
    Thread clientThread = new Thread(() -> {
      try {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        int clientId = nextClientId.getAndIncrement();
        ClientHandler clientHandler = new ClientHandler(clientId, clientSocket, out, in);

        // Wait for join game message
        GameMessage message = (GameMessage) in.readObject();
        if (message.getType() == GameMessage.MessageType.JOIN_GAME) {
          JoinGameMessage joinMsg = (JoinGameMessage) message;
          handleJoinGame(clientHandler, joinMsg.getPlayerName());
        }

        // Handle subsequent messages
        while (clientSocket.isConnected()) {
          try {
            message = (GameMessage) in.readObject();
            handleClientMessage(clientHandler, message);
          } catch (IOException | ClassNotFoundException e) {
            break; // Client disconnected
          }
        }
      } catch (IOException | ClassNotFoundException e) {
        System.err.println("Error handling client: " + e.getMessage());
      } finally {
        try {
          clientSocket.close();
        } catch (IOException e) {
          System.err.println("Error closing client socket: " + e.getMessage());
        }
      }
    });
    clientThread.start();
  }

  private void handleJoinGame(ClientHandler clientHandler, String playerName) {
    // Find an existing game waiting for a player or create a new one
    GameSession gameSession = findOrCreateGameSession();

    if (gameSession.addPlayer(clientHandler, playerName)) {
      clientHandler.setGameSession(gameSession);

      // Assign team color and notify client
      TeamColor assignedColor = gameSession.getPlayerColor(clientHandler);
      clientHandler.sendMessage(new PlayerAssignedMessage(assignedColor));
      System.out.println("Player " + playerName + " assigned color: " + assignedColor);

      // If game is full, start it
      if (gameSession.isFull()) {
        System.out.println("Starting game with two players");
        gameSession.startGame();
      }
    }
  }

  private void handleClientMessage(ClientHandler clientHandler, GameMessage message) {
    GameSession gameSession = clientHandler.getGameSession();
    if (gameSession == null) {
      return;
    }

    switch (message.getType()) {
      case PLAY_CARD:
        PlayCardMessage playMsg = (PlayCardMessage) message;
        gameSession.handlePlayCard(clientHandler, playMsg.getRow(), playMsg.getCol(),
            playMsg.getHandIndex());
        break;
      case DISCONNECT:
        gameSession.removePlayer(clientHandler);
        break;
      default:
        System.err.println("Unknown message type from client: " + message.getType());
        break;
    }
  }

  private GameSession findOrCreateGameSession() {
    // Look for existing game waiting for players
    for (GameSession session : gameSessions.values()) {
      if (!session.isFull()) {
        return session;
      }
    }

    // Create new game session
    int gameId = nextGameId.getAndIncrement();
    GameSession newSession = new GameSession(gameId, this);
    gameSessions.put(gameId, newSession);
    System.out.println("Created new game session: " + gameId);
    return newSession;
  }

  @Override
  public void onPlayerTurnChange() {
    // This will be called by individual game sessions
  }

  @Override
  public void onGameOver() {
    // This will be called by individual game sessions
  }

  /**
   * Represents a client handler for managing communication with a single client.
   */
  private static class ClientHandler {
    private final int clientId;
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private GameSession gameSession;
    private String playerName;

    public ClientHandler(int clientId, Socket socket, ObjectOutputStream out,
        ObjectInputStream in) {
      this.clientId = clientId;
      this.socket = socket;
      this.out = out;
      this.in = in;
    }

    public void setGameSession(GameSession gameSession) {
      this.gameSession = gameSession;
    }

    public GameSession getGameSession() {
      return gameSession;
    }

    public void setPlayerName(String playerName) {
      this.playerName = playerName;
    }

    public String getPlayerName() {
      return playerName;
    }

    public int getClientId() {
      return clientId;
    }

    public void sendMessage(GameMessage message) {
      try {
        out.writeObject(message);
        out.flush();
      } catch (IOException e) {
        System.err.println("Error sending message to client " + clientId + ": " + e.getMessage());
      }
    }

    public boolean isConnected() {
      return socket.isConnected() && !socket.isClosed();
    }
  }

  /**
   * Represents a game session between two players.
   * IMPORTANT: Made static to avoid serialization issues with outer class
   * reference.
   */
  private static class GameSession implements ModelStatusListener {
    private final int gameId;
    private final ThreeTriosModel model;
    private ClientHandler redPlayer;
    private ClientHandler bluePlayer;
    private boolean gameStarted;

    public GameSession(int gameId, ThreeTriosServer server) {
      this.gameId = gameId;
      this.model = new BasicThreeTriosModel(new Random());
      this.model.addModelStatusListener(this);
      this.gameStarted = false;
    }

    public boolean addPlayer(ClientHandler clientHandler, String playerName) {
      clientHandler.setPlayerName(playerName);

      if (redPlayer == null) {
        redPlayer = clientHandler;
        System.out.println("Added red player: " + playerName);
        return true;
      } else if (bluePlayer == null) {
        bluePlayer = clientHandler;
        System.out.println("Added blue player: " + playerName);
        return true;
      }
      return false; // Game is full
    }

    public void removePlayer(ClientHandler clientHandler) {
      if (redPlayer == clientHandler) {
        redPlayer = null;
      } else if (bluePlayer == clientHandler) {
        bluePlayer = null;
      }

      // Notify remaining player if any
      if (redPlayer != null) {
        // Send game over message or handle disconnection
      }
      if (bluePlayer != null) {
        // Send game over message or handle disconnection
      }
    }

    public boolean isFull() {
      return redPlayer != null && bluePlayer != null;
    }

    public TeamColor getPlayerColor(ClientHandler clientHandler) {
      if (clientHandler == redPlayer) {
        return TeamColor.RED;
      } else if (clientHandler == bluePlayer) {
        return TeamColor.BLUE;
      }
      return null;
    }

    public void startGame() {
      if (!isFull() || gameStarted) {
        return;
      }

      try {
        // Initialize the game with default grid and deck
        model.startGame(createDefaultGrid(), createDefaultDeck(), 7);
        gameStarted = true;
        System.out.println("Game started for session " + gameId);

        // Notify both players that the game has started - FIXED: Use proper message
        // class
        broadcastMessage(new GameStartedMessage());

        // Send initial game state
        broadcastGameState();
      } catch (Exception e) {
        System.err.println("Error starting game: " + e.getMessage());
        e.printStackTrace();
      }
    }

    public void handlePlayCard(ClientHandler clientHandler, int row, int col, int handIndex) {
      if (!gameStarted) {
        return;
      }

      // Verify it's the player's turn
      TeamColor currentPlayerColor = model.getCurrentPlayer().getColor();
      TeamColor clientColor = getPlayerColor(clientHandler);

      if (currentPlayerColor != clientColor) {
        // FIXED: Use proper message class instead of anonymous class
        clientHandler.sendMessage(new InvalidMoveMessage());
        return;
      }

      try {
        model.playToGrid(row, col, handIndex);
        broadcastGameState();
      } catch (IllegalArgumentException | IllegalStateException e) {
        // FIXED: Use proper message class instead of anonymous class
        clientHandler.sendMessage(new InvalidMoveMessage());
      }
    }

    private void broadcastMessage(GameMessage message) {
      if (redPlayer != null && redPlayer.isConnected()) {
        redPlayer.sendMessage(message);
      }
      if (bluePlayer != null && bluePlayer.isConnected()) {
        bluePlayer.sendMessage(message);
      }
    }

    private void broadcastGameState() {
      try {
        GameStateUpdateMessage stateMessage = new GameStateUpdateMessage(model);
        broadcastMessage(stateMessage);
      } catch (Exception e) {
        System.err.println("Error broadcasting game state: " + e.getMessage());
        e.printStackTrace();
      }
    }

    @Override
    public void onPlayerTurnChange() {
      // FIXED: Use proper message class instead of anonymous class
      broadcastMessage(new PlayerTurnChangeMessage());
    }

    @Override
    public void onGameOver() {
      // FIXED: Use proper message class instead of anonymous class
      broadcastMessage(new GameOverMessage());
    }

    private GridCell[][] createDefaultGrid() {
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

    private List<Card> createDefaultDeck() {
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

    private ThreeTriosCard createCard(String name, String attack1, String attack2,
        String attack3, String attack4) {
      return new ThreeTriosCard(
          name,
          ThreeTriosCard.AttackValue.fromString(attack1),
          ThreeTriosCard.AttackValue.fromString(attack2),
          ThreeTriosCard.AttackValue.fromString(attack3),
          ThreeTriosCard.AttackValue.fromString(attack4));
    }
  }

  public static void main(String[] args) {
    int port = 8080; // Default port
    if (args.length > 0) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        System.err.println("Invalid port number, using default: " + port);
      }
    }

    ThreeTriosServer server = new ThreeTriosServer(port);
    try {
      server.start();
    } catch (IOException e) {
      System.err.println("Failed to start server: " + e.getMessage());
    }
  }
}