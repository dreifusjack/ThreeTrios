# Step-by-Step Testing Guide for Three Trios Network Multiplayer

This guide will walk you through testing the network multiplayer functionality step by step.

## Prerequisites

1. **Java Environment**: Ensure you have Java installed and can compile/run Java programs
2. **Project Compilation**: Make sure the project compiles without errors
3. **Multiple Terminals**: You'll need 3 terminal windows/tabs for this test

## Step 1: Compile the Project

First, compile all the Java files:

```bash
# Navigate to your project root directory
cd /Users/jackdreifus/Code/ThreeTrios

# Compile all Java files
javac -d . src/cs3500/threetrios/**/*.java
```

**Note**: The compilation should complete without errors. If you see any errors, please check that all files are present and properly formatted.

## Step 2: Basic Network Test (Optional)

Run the basic network test to verify components work:

```bash
java cs3500.threetrios.network.NetworkTest
```

You should see output like:

```
Starting Three Trios Network Tests...
Testing client creation... PASS
Testing server creation... PASS
Testing controller creation... PASS
Testing message creation... PASS
All basic tests passed!

To test full functionality:
1. Run: java cs3500.threetrios.ThreeTrios network server
2. Run: java cs3500.threetrios.ThreeTrios network client
3. Run: java cs3500.threetrios.ThreeTrios network client
4. Play the game!
```

**If you see this output, the network components are working correctly!**

**Note**: The serialization issues have been fixed. The server now properly sends game state updates using a serializable format, and clients can receive and display the game state correctly.

## Step 3: Start the Game Server

Open **Terminal 1** and start the server:

```bash
java cs3500.threetrios.ThreeTrios network server
```

Expected output:

```
Starting Three Trios in network mode...
Three Trios server started on port 8080
```

**Important**: Keep this terminal open - the server needs to stay running!

## Step 4: Connect First Client (Player 1)

Open **Terminal 2** and connect the first client:

```bash
java cs3500.threetrios.ThreeTrios network client localhost 8080 "Alice"
```

Expected behavior:

- A game window should open
- Window title should show "RED Player: Waiting for game to start..."
- The window should be positioned on the left side of your screen
- Console should show: "Connected! Waiting for another player..."

## Step 5: Connect Second Client (Player 2)

Open **Terminal 3** and connect the second client:

```bash
java cs3500.threetrios.ThreeTrios network client localhost 8080 "Bob"
```

Expected behavior:

- A second game window should open
- Window title should show "BLUE Player: Waiting for game to start..."
- The window should be positioned on the right side of your screen
- **Both windows should now update** to show the game has started
- The RED player's window should show "RED Player: Your Turn"
- The BLUE player's window should show "BLUE Player: Waiting for opponent"

## Step 6: Play the Game

Now you can play the game across the network:

### RED Player's Turn (Alice):

1. In the RED player window, click on a card in your hand to select it
2. Click on an empty cell on the game board to place the card
3. The move should be sent to the server and both windows should update
4. The turn should switch to BLUE player

### BLUE Player's Turn (Bob):

1. In the BLUE player window, click on a card in your hand
2. Click on an empty cell on the game board
3. Both windows should update with the new move
4. Turn switches back to RED player

### Continue Playing:

- Take turns until the game board is full
- Watch as cards battle and flip colors
- Game ends when all cells are filled
- A dialog will show the winner and final scores

## Step 7: Test Error Handling

Try these scenarios to test robustness:

### Invalid Moves:

- Try clicking the board without selecting a card first
- Try playing when it's not your turn
- Try placing a card on an occupied cell

### Network Disconnection:

- Close one of the client windows
- The other player should see a connection lost message
- The server should handle the disconnection gracefully

## Step 8: Test with Different Configurations

### Different Port:

```bash
# Terminal 1 - Server on port 9000
java cs3500.threetrios.ThreeTrios network server 9000

# Terminal 2 - Client connecting to port 9000
java cs3500.threetrios.ThreeTrios network client localhost 9000 "Player1"

# Terminal 3 - Second client
java cs3500.threetrios.ThreeTrios network client localhost 9000 "Player2"
```

### Using Alternative Client Launcher:

```bash
# Instead of the full command, you can use:
java cs3500.threetrios.NetworkThreeTrios localhost 8080 "PlayerName"

# Or just run it and enter details in the dialog:
java cs3500.threetrios.NetworkThreeTrios
```

## Expected Game Flow

1. **Server starts** and listens for connections
2. **First client connects** → Gets assigned RED team → Waits for second player
3. **Second client connects** → Gets assigned BLUE team → Game starts automatically
4. **Game plays normally** with the same rules as local version
5. **Moves are synchronized** across both clients in real-time
6. **Game ends** with winner announcement on both clients

## Troubleshooting

### If the server won't start:

- Check if port 8080 is already in use
- Try a different port: `java cs3500.threetrios.ThreeTrios network server 8081`

### If clients can't connect:

- Make sure the server is running first
- Check the host and port are correct
- Verify firewall isn't blocking the connection

### If game windows don't appear:

- Check for Java GUI/Swing support on your system
- Look for error messages in the terminal

### If moves don't work:

- Make sure it's your turn (check window title)
- Select a card first, then click the board
- Check for error dialogs

## Success Criteria

The test is successful if:

- ✅ Server starts without errors
- ✅ Two clients can connect successfully
- ✅ Game starts automatically when both players join
- ✅ Players can take turns making moves
- ✅ Game state stays synchronized between both windows
- ✅ Game ends properly with winner announcement
- ✅ All game rules work the same as the local version

## Multiple Games

The server can handle multiple game sessions. If you want to test this:

1. Keep the server running
2. After one game finishes, start two new clients
3. They should automatically be paired for a new game

This demonstrates the server's ability to manage multiple concurrent games!
