# Three Trios Network Multiplayer

This document describes how to use the new network multiplayer functionality for the Three Trios card game.

## Overview

The network multiplayer feature allows players on different devices to play Three Trios together over a network connection. The system uses a client-server architecture where:

- One instance runs as a **server** that manages the game state
- Multiple instances run as **clients** that connect to the server to play

## Quick Start

### Starting a Server

To start a game server on the default port (8080):

```bash
java cs3500.threetrios.ThreeTrios network server
```

To start a server on a specific port:

```bash
java cs3500.threetrios.ThreeTrios network server 9000
```

### Connecting as a Client

To connect to a server running on localhost:

```bash
java cs3500.threetrios.ThreeTrios network client
```

To connect to a specific server:

```bash
java cs3500.threetrios.ThreeTrios network client 192.168.1.100 8080 "Player1"
```

### Alternative Client Launcher

You can also use the dedicated network client launcher:

```bash
java cs3500.threetrios.NetworkThreeTrios
```

Or with specific connection details:

```bash
java cs3500.threetrios.NetworkThreeTrios 192.168.1.100 8080 "Player1"
```

## How It Works

### Game Flow

1. **Server Setup**: One player starts a server instance
2. **Client Connection**: Two players connect to the server as clients
3. **Team Assignment**: The server automatically assigns RED and BLUE teams
4. **Game Start**: Once two players are connected, the game begins automatically
5. **Gameplay**: Players take turns making moves through their client interfaces
6. **Synchronization**: The server keeps all clients synchronized with the current game state

### Network Architecture

- **Server (`ThreeTriosServer`)**: Manages game sessions, validates moves, and broadcasts state updates
- **Client (`ThreeTriosClient`)**: Handles network communication and user input
- **Controller (`NetworkThreeTriosController`)**: Coordinates between the client, view, and network events
- **Messages**: Serializable objects that carry game data between client and server

### Key Features

- **Automatic Matchmaking**: The server pairs players automatically
- **Real-time Synchronization**: Game state is synchronized across all clients
- **Move Validation**: The server validates all moves to prevent cheating
- **Graceful Disconnection**: Handles client disconnections appropriately
- **Same Game Logic**: Uses the exact same game rules as the local version

## Network Protocol

The system uses TCP sockets with Java object serialization for communication. Key message types include:

- `JoinGameMessage`: Client requests to join a game
- `PlayerAssignedMessage`: Server assigns a team color to a client
- `PlayCardMessage`: Client sends a move to the server
- `GameStateUpdateMessage`: Server broadcasts current game state
- `GameStartedMessage`: Server notifies clients that the game has begun
- `GameOverMessage`: Server notifies clients that the game has ended

## Configuration

### Default Settings

- **Default Port**: 8080
- **Default Host**: localhost
- **Grid**: 4x3 with predefined holes
- **Deck**: Standard 12-card deck
- **Hand Size**: 7 cards per player (N+1)/2 where N is the number of card cells

### Customization

The network version currently uses the default game configuration. To customize:

1. Modify the `createDefaultGrid()` and `createDefaultDeck()` methods in `ThreeTriosServer.GameSession`
2. Recompile and run the server

## Troubleshooting

### Common Issues

**Connection Refused**

- Ensure the server is running before clients try to connect
- Check that the port is not blocked by firewall
- Verify the correct host and port are being used

**Game Not Starting**

- Ensure exactly two clients are connected
- Check server console for error messages

**Moves Not Working**

- Verify it's your turn (check window title)
- Ensure you've selected a card before clicking the board
- Check for invalid move messages

### Network Requirements

- **Firewall**: Ensure the server port is open for incoming connections
- **Network**: Clients must be able to reach the server's IP address
- **Java**: All instances must be running compatible Java versions

## Example Session

1. **Player 1 (Server Host)**:

   ```bash
   java cs3500.threetrios.ThreeTrios network server 8080
   ```

2. **Player 2 (Client)**:

   ```bash
   java cs3500.threetrios.ThreeTrios network client 192.168.1.100 8080 "Alice"
   ```

3. **Player 3 (Client)**:

   ```bash
   java cs3500.threetrios.ThreeTrios network client 192.168.1.100 8080 "Bob"
   ```

4. The game will start automatically once both clients are connected.

## Differences from Local Mode

### What's the Same

- All game rules and logic remain identical
- Same card mechanics and battle system
- Same win conditions and scoring
- Same user interface and controls

### What's Different

- Game state comes from the server instead of a local model
- Players can only make moves on their turn
- Network latency may cause slight delays
- Disconnection handling replaces local player management

## Technical Details

### Architecture Components

- **Network Layer**: Handles TCP socket communication
- **Message Protocol**: Serialized Java objects for type-safe communication
- **Game Session Management**: Server manages multiple concurrent games
- **State Synchronization**: Ensures all clients have consistent game state
- **Event-Driven Design**: Uses listeners for network events and game state changes

### Thread Safety

The server uses thread-safe collections and proper synchronization to handle multiple concurrent clients and game sessions safely.

### Error Handling

- Network errors are caught and logged appropriately
- Invalid moves are rejected with feedback to the client
- Client disconnections are handled gracefully
- Server errors don't crash the entire system

## Future Enhancements

Potential improvements for the network system:

- Support for more than 2 players per game
- Spectator mode for watching games
- Game replay functionality
- Custom game configurations over the network
- Reconnection support for dropped connections
- Tournament/lobby system for multiple games
