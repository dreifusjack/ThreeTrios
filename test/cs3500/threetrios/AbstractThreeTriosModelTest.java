package cs3500.threetrios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.ReadOnlyGridCell;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTrioCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosPlayer;

/**
 * Responsible for testing the behaviors of the ThreeTrioModel interface.
 */
public abstract class AbstractThreeTriosModelTest {
  protected abstract ThreeTriosModel createModel(String gridFileName, String cardFileName);

  protected abstract ThreeTriosModel createModelWithRandom(String gridFileName,
                                                           String cardFileName, Random random);

  protected ThreeTriosModel model5x7;

  protected ThreeTriosModel model2x2;

  protected ThreeTriosModel model4x3;

  protected ThreeTriosModel modelWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;

  protected ThreeTrioCard nerfedCard;

  protected ThreeTrioCard opCard;

  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model5x7 = createModelWithRandom("world1.txt", "card1.txt", rand1);
    model2x2 = createModelWithRandom("world2x2.txt", "cards2x2.txt", rand1);
    model4x3 = createModelWithRandom("world4x3.txt", "cards3x3.txt", rand1);
    modelWithNotEnoughCards = createModelWithRandom("world4x3.txt",
            "3cardsonly.txt", rand1);
    model2x2SameValueOf1 = createModelWithRandom("world2x2ver2.txt",
            "cardswithsamevalueof1.txt", rand1);
    model2x2SameValueOf1Ver2 = createModelWithRandom("world2x2ver3.txt",
            "cardswithsamevalueof1.txt", rand1);
    nerfedCard = new ThreeTrioCard("", ThreeTrioCard.AttackValue.ONE,
            ThreeTrioCard.AttackValue.ONE, ThreeTrioCard.AttackValue.ONE,
            ThreeTrioCard.AttackValue.ONE);
    opCard = new ThreeTrioCard("poo", ThreeTrioCard.AttackValue.A,
            ThreeTrioCard.AttackValue.A, ThreeTrioCard.AttackValue.A,
            ThreeTrioCard.AttackValue.A);

  }

  // Part 1: --------------------Exception tests------------------------------

  //---------startGame---------------
  //Throw exception when do startGame when the game has started.
  @Test(expected = IllegalStateException.class)
  public void testStartGameWhenGameHasStarted() {
    model5x7.startGame();
    model5x7.startGame();
  }

  //Throw exception when do startGame when the game does not have enough of cards.
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCards() {
    modelWithNotEnoughCards.startGame();
  }

  //--------playToGrid------------------------
  //Throw exception when do playToGrid when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testPlayToGridWhenGameNotStarted() {
    model5x7.playToGrid(5, 2, 2);
  }

  //Throw exception when handIdx is larger than the size of player's hand size.
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToGridHandOutBound() {
    model5x7.startGame();
    model5x7.playToGrid(0, 0, 20);
  }

  //Throw exception when playing to a cell that is out of bound.
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToGridCoordinateOutBound() {
    model5x7.startGame();
    model5x7.playToGrid(-5, 5, 3);
  }

  //Throw exception when playToGrid to a Hole cell
  @Test(expected = IllegalStateException.class)
  public void testPlayToGridToHoleCell() {
    model5x7.startGame();
    model5x7.playToGrid(0, 2, 3);
  }

  //Throw exception when playToGrid to a cell that has a card already.
  @Test(expected = IllegalStateException.class)
  public void testPlayToGridOnAnotherCard() {
    model5x7.startGame();
    model5x7.playToGrid(0, 0, 3);
    model5x7.playToGrid(0, 0, 5);
  }

  //----------battleCards-------------
  //Throw exception when do battleCards when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testBattleCardsWhenGameNotStarted() {
    model5x7.battleCards(5, 2);
  }

  //-----------isGameOver-----------
  //Throw exception when do isGameOver when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testIsGameOverWhenGameNotStarted() {
    model5x7.isGameOver();
  }

  //--------------getWinner--------------

  //Throw exception when the game is not over yet
  @Test(expected = IllegalStateException.class)
  public void testGetWinnerWhenGameNotOver() {
    model5x7.startGame();
    model5x7.getWinner();
  }

  //Throw exception when do getWinner when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testGetWinnerWhenGameNotStarted() {
    model5x7.getWinner();
  }

  //--------------getCurrentPlayer--------
  //Throw exception when do getCurrentPlayer when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testGetCurrentPlayerWhenGameNotStarted() {
    model5x7.getCurrentPlayer();
  }

  //--------------getGrid---------------
  //Throw exception when do getGrid when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testGetGridWhenGameNotStarted() {
    model5x7.getGridReadOnly();
  }


  // Part 2: --------------------Method tests------------------------------

  //---------startGame---------------
  //Test if we can startGame successfully.
  @Test
  public void testStartGameSuccessfully() {
    model4x3.startGame();

    List<List<ReadOnlyGridCell>> expectedGrid = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      List<ReadOnlyGridCell> row = new ArrayList<>();
      for (int j = 0; j < 2; j++) {
        row.add(new CardCell());
      }
      expectedGrid.add(row);
    }

    // compare each cell in the grid individually
    List<List<ReadOnlyGridCell>> actualGrid = model4x3.getGridReadOnly();
    for (int row = 0; row < expectedGrid.size(); row++) {
      for (int col = 0; col < expectedGrid.get(row).size(); col++) {
        ReadOnlyGridCell expectedCell = expectedGrid.get(row).get(col);
        ReadOnlyGridCell actualCell = actualGrid.get(row).get(col);
        Assert.assertEquals(expectedCell.getClass(), actualCell.getClass());
      }
    }

    // check the hands of the red player
    List<Card> expectedRedHand = Arrays.asList(
            new ThreeTrioCard("WorldDragon", ThreeTrioCard.AttackValue.ONE,
                    ThreeTrioCard.AttackValue.FIVE, ThreeTrioCard.AttackValue.SIX,
                    ThreeTrioCard.AttackValue.ONE),
            new ThreeTrioCard("HeroKnight", ThreeTrioCard.AttackValue.A,
                    ThreeTrioCard.AttackValue.FOUR,
                    ThreeTrioCard.AttackValue.FOUR, ThreeTrioCard.AttackValue.TWO),
            new ThreeTrioCard("CorruptKing", ThreeTrioCard.AttackValue.THREE,
                    ThreeTrioCard.AttackValue.ONE, ThreeTrioCard.AttackValue.ONE,
                    ThreeTrioCard.AttackValue.TWO),
            new ThreeTrioCard("FirePhoenix",
                    ThreeTrioCard.AttackValue.TWO, ThreeTrioCard.AttackValue.FOUR,
                    ThreeTrioCard.AttackValue.THREE, ThreeTrioCard.AttackValue.TWO));
    Assert.assertEquals(expectedRedHand, model4x3.getCurrentPlayer().getHand());
  }

  //--------playToGrid--------------
  // playToGrid on a CardCell without any battles
  @Test
  public void testPlayToGridOnEmptyCardCell() {
    model4x3.startGame();
    model4x3.playToGrid(0, 0, 2);
    ThreeTrioCard corruptKing = new ThreeTrioCard("CorruptKing",
            ThreeTrioCard.AttackValue.THREE, ThreeTrioCard.AttackValue.ONE,
            ThreeTrioCard.AttackValue.ONE, ThreeTrioCard.AttackValue.TWO);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString());
  }

  // playToGrid and trigger a color change on an adjacent card
  @Test
  public void testPlayToGridChangeAdjacentColor() {
    model4x3.startGame();
    model4x3.playToGrid(0, 0, 0);

    model4x3.playToGrid(0, 1, 1);

    Assert.assertEquals("B", model4x3.getGridReadOnly().get(0).get(0).toString()); // A > 1
  }

  // playToGrid without changing the color of an adjacent card
  @Test
  public void testPlayToGridNoAdjacentColorChange() {
    model4x3.startGame();
    model4x3.playToGrid(0, 0, 0);
    System.out.println(model4x3.getCurrentPlayer().getHand());

    model4x3.playToGrid(0, 1, 0);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString());
    // 2 is not larger than 5
  }

  // playToGrid with tie in value, ensuring no color change
  @Test
  public void testPlayToGridTieNoColorChange() {
    model4x3.startGame();
    model4x3.playToGrid(0, 0, 1);

    model4x3.playToGrid(0, 1, 0);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString());
    // 4 is not larger than 4

  }

  // playToGrid triggers a combo move.
  @Test
  public void testPlayToGridChainReaction() {
    model4x3.startGame();

    // Player 1 (Red)
    model4x3.playToGrid(0, 0, 1);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 2, 1);
    // Player 1 (Red)
    model4x3.playToGrid(0, 1, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(1, 0, 1);
    List<List<ReadOnlyGridCell>> grid = model4x3.getGridReadOnly();

    Assert.assertEquals("B", grid.get(0).get(0).toString()); // Was Red
    Assert.assertEquals("B", grid.get(0).get(1).toString()); // Was Red
  }


  //----------battleCards-------------
  // battleCards with no adjacent opponent cards.
  @Test
  public void testBattleCardsNoAdjacentOpponents() {
    model4x3.startGame();

    model4x3.playToGrid(0, 0, 0);

    model4x3.battleCards(0, 0);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString());
  }

  // battleCards triggers a color change on a weaker adjacent card.
  @Test
  public void testBattleCardsWeakerAdjacentOpponent() {
    model4x3.startGame();

    model4x3.playToGrid(0, 0, 0); //RED 1 6 5 1
    model4x3.playToGrid(0, 1, 3); //BLUE 3 2 1 1

    model4x3.battleCards(0, 0);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(1).toString());
  }

  // battleCards does not change color on a stronger adjacent card.
  @Test
  public void testBattleCardsStrongerAdjacentOpponent() {
    model4x3.startGame();


    model4x3.playToGrid(0, 0, 0); //RED 1 6 5 1
    model4x3.playToGrid(0, 1, 3); //BLUE 3 2 1 1

    model4x3.battleCards(0, 1);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString());
  }

  // battleCards encounters a tie with an adjacent card.
  @Test
  public void testBattleCardsTieWithAdjacentOpponent() {
    model4x3.startGame();

    model4x3.playToGrid(0, 0, 1); //RED A 4 4 1
    model4x3.playToGrid(0, 1, 0); //BLUE 3 9 5 4

    model4x3.battleCards(0, 0);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString());
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(0).get(1).toString());
  }

  // battleCards triggers a chain reaction of color changes.
  @Test
  public void testBattleCardsChainReaction() {
    model4x3.startGame();

    model4x3.playToGrid(2, 0, 3); //RED 2 3 2 4
    model4x3.playToGrid(0, 1, 3); //BLUE 3 2 1 1
    model4x3.playToGrid(1, 0, 0); //RED 1 6 5 1
    model4x3.playToGrid(1, 1, 0); //BLUE 3 9 5 4

    model4x3.battleCards(1, 0);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(1).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(1).get(1).toString());
  }

  // battleCards trigger a chain reaction of color changes and ends and the reaction
  // ends when there is a card that is higher in value

  @Test
  public void testBattleCardsChainReactionSpecial() {
    model4x3.startGame();

    model4x3.playToGrid(0, 0, 2);
    model4x3.playToGrid(1, 1, 1);
    model4x3.playToGrid(1, 0, 0);
    model4x3.playToGrid(2, 2, 0);
    model4x3.playToGrid(2, 0, 1);
    model4x3.battleCards(1, 1);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString()); // Still red
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(1).get(0).toString()); // Was red
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(1).get(1).toString());
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(2).get(0).toString()); // Was red
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(2).get(2).toString());
  }

  //-----------isGameOver--------------
  // Test isGameOver when one player's hand is empty and all the cells are filled
  @Test
  public void testIsGameOverWhenHandEmpty() {
    model2x2SameValueOf1.startGame();

    model2x2SameValueOf1.playToGrid(0, 0, 0);
    model2x2SameValueOf1.playToGrid(0, 1, 0);
    model2x2SameValueOf1.playToGrid(1, 0, 0);

    Assert.assertEquals(true, model2x2SameValueOf1.isGameOver());
  }

  // Test isGameOver when the grid is full (no empty card cells left)
  @Test
  public void testIsGameOverWhenGridIsFull() {
    model2x2SameValueOf1Ver2.startGame();

    model2x2SameValueOf1Ver2.playToGrid(0, 0, 0);

    Assert.assertEquals(true, model2x2SameValueOf1Ver2.isGameOver());
  }

  // Test isGameOver when both players still have cards and there are empty cells
  @Test
  public void testIsGameOverWhenMovesRemain() {
    model5x7.startGame();

    model5x7.playToGrid(0, 0, 0);

    Assert.assertEquals(false, model5x7.getCurrentPlayer().getHand().isEmpty());
    Assert.assertFalse(model5x7.isGameOver());
  }


  // Test isGameOver after chain reaction but with remaining cards
  @Test
  public void testIsGameOverAfterChainReactionWithMovesLeft() {
    model4x3.startGame();

    model4x3.playToGrid(2, 2, 0);
    model4x3.playToGrid(1, 1, 1);

    Assert.assertFalse(model4x3.isGameOver());
  }

  // Test isGameOver when it's a tie situation (both hands empty, grid full)
  @Test
  public void testIsGameOverWhenPlayersTie() {
    model4x3.startGame();

    // Player 1 (Red)
    model4x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 2, 0);
    // Player 1 (Red)
    model4x3.playToGrid(1, 0, 1);
    // Player 2 (Blue)
    model4x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model4x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model4x3.playToGrid(3, 1, 0);

    Assert.assertEquals(true, model4x3.isGameOver());
    System.out.println(model4x3.getWinner());
  }

  //--------------getWinner--------------
  //When there is a tie.
  @Test
  public void testTie() {
    model4x3.startGame();

    // Player 1 (Red)
    model4x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 2, 0);
    // Player 1 (Red)
    model4x3.playToGrid(1, 0, 1);
    // Player 2 (Blue)
    model4x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model4x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model4x3.playToGrid(3, 1, 0);

    Assert.assertEquals(true, model4x3.isGameOver());
    Assert.assertEquals(null, model4x3.getWinner());
  }

  // When red wins.
  @Test
  public void testRedWins() {
    model4x3.startGame();

    // Player 1 (Red)
    model4x3.playToGrid(0, 0, 2);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 2, 2);
    // Player 1 (Red)
    model4x3.playToGrid(1, 0, 1);
    // Player 2 (Blue)
    model4x3.playToGrid(1, 1, 1);
    // Player 1 (Red)
    model4x3.playToGrid(0, 1, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model4x3.playToGrid(3, 1, 0);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(1).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(1).get(0).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(1).get(1).toString());
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(2).get(0).toString());
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(2).get(2).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(3).get(1).toString());

    Assert.assertEquals(true, model4x3.isGameOver());
    Assert.assertEquals(TeamColor.RED, model4x3.getWinner().getColor());
  }

  //When all cells are marked as red player's cards
  // (a player wins with all cells fiiled with their color)
  @Test
  public void testRedWinsAllCells() {
    model4x3.startGame();

    // Player 1 (Red)
    model4x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model4x3.playToGrid(2, 2, 1);
    // Player 2 (Blue)
    model4x3.playToGrid(1, 0, 1);
    // Player 1 (Red)
    model4x3.playToGrid(3, 1, 1);
    // Player 2 (Blue)
    model4x3.playToGrid(1, 1, 1);
    // Player 1 (Red)
    model4x3.playToGrid(0, 1, 0);

    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(0).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(0).get(1).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(1).get(0).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(1).get(1).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(2).get(0).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(2).get(2).toString());

    Assert.assertEquals(true, model4x3.isGameOver());
    Assert.assertEquals(TeamColor.RED, model4x3.getWinner().getColor());
  }

  //When blue player wins
  @Test
  public void testBlueWins() {
    model4x3.startGame();
    // Player 1 (Red)
    model4x3.playToGrid(1, 0, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model4x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model4x3.playToGrid(2, 0, 0);
    // Player 2 (Blue)
    model4x3.playToGrid(1, 1, 0);
    // Player 1 (Red)
    model4x3.playToGrid(3, 1, 0);

    Assert.assertEquals("B", model4x3.getGridReadOnly().get(0).get(0).toString());
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(0).get(1).toString());
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(1).get(0).toString());
    Assert.assertEquals("B", model4x3.getGridReadOnly().get(1).get(1).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(2).get(0).toString());
    Assert.assertEquals("R", model4x3.getGridReadOnly().get(2).get(2).toString());

    Assert.assertEquals(true, model4x3.isGameOver());
    Assert.assertEquals(TeamColor.BLUE, model4x3.getWinner().getColor());
  }

  //--------------getCurrentPlayer--------
  // Test for getCurrentPlayer when red player is the current player
  @Test
  public void testGetCurrentPlayerRed() {
    model4x3.startGame();

    Assert.assertEquals(TeamColor.RED, model4x3.getCurrentPlayer().getColor());
  }

  // Test for getCurrentPlayer when blue player is the current player
  @Test
  public void testGetCurrentPlayerBlue() {
    model4x3.startGame();
    model4x3.playToGrid(2, 2, 0);

    Assert.assertEquals(TeamColor.BLUE, model4x3.getCurrentPlayer().getColor());
  }

  //--------------getGrid---------------
  // Get a copy of the grid of the game.
  @Test
  public void testGetGrid() {
    model2x2.startGame();

    List<List<ReadOnlyGridCell>> expectedGrid = new ArrayList<>();
    expectedGrid.add(Arrays.asList(new CardCell(), new Hole()));
    expectedGrid.add(Arrays.asList(new CardCell(), new CardCell()));

    List<List<ReadOnlyGridCell>> actualGrid = model2x2.getGridReadOnly();
    for (int row = 0; row < expectedGrid.size(); row++) {
      for (int col = 0; col < expectedGrid.get(row).size(); col++) {
        Assert.assertEquals(expectedGrid.get(row).get(col).getClass(),
                actualGrid.get(row).get(col).getClass());
      }
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumRowsWhenGameNotStarted() {
    model5x7.numRows();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumColsWhenGameNotStarted() {
    model5x7.numCols();
  }

  @Test
  public void testGetNumRowsAndCols() {
    model5x7.startGame();
    Assert.assertEquals(5, model5x7.numRows());
    Assert.assertEquals(7, model5x7.numCols());
    model4x3.startGame();
    Assert.assertEquals(4, model4x3.numRows());
    Assert.assertEquals(3, model4x3.numCols());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetRedPlayerWhenGameNotStarted() {
    model4x3.getRedPlayer();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetBluePlayerWhenGameNotStarted() {
    model4x3.getBluePlayer();
  }

  @Test
  public void testGetRedAndBluePlayer() {
    model4x3.startGame();
    Assert.assertEquals(TeamColor.RED, model4x3.getRedPlayer().getColor());
    Assert.assertEquals(TeamColor.BLUE, model4x3.getBluePlayer().getColor());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCellWhenGameHasNotStarted() {
    model4x3.getCell(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCellWithIndexOutOfGridBounds() {
    model4x3.startGame();
    model4x3.getCell(-101010021, 109);
  }

  @Test
  public void testGetCell() {
    model4x3.startGame();
    Assert.assertEquals(new CardCell().getClass(), model4x3.getCell(0, 0).getClass());
    Assert.assertEquals(new Hole().getClass(), model4x3.getCell(0, 2).getClass());
    model4x3.playToGrid(0, 0, 0); // Red player plays a card to 0,0
    Assert.assertEquals(TeamColor.RED, model4x3.getCell(0, 0).getColor());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPlayerScoreWhenGameHasNotStarted() {
    model4x3.getPlayerScore(TeamColor.BLUE);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPlayerScoreWhenPassedNullTeamColor() {
    model4x3.getPlayerScore(null);
  }

  @Test
  public void testGetPlayerScore() {
    model4x3.startGame();
    // Player 1 (Red)
    model4x3.playToGrid(1, 0, 0);
    // Both players started with 4 cards in their hand
    Assert.assertEquals(4, model4x3.getPlayerScore(TeamColor.RED));
    Assert.assertEquals(4, model4x3.getPlayerScore(TeamColor.BLUE));
    // Player 2 (Blue)
    model4x3.playToGrid(0, 0, 0);
    // Blue plays and flips a red card
    Assert.assertEquals(3, model4x3.getPlayerScore(TeamColor.RED));
    Assert.assertEquals(5, model4x3.getPlayerScore(TeamColor.BLUE));
    // Player 1 (Red)
    model4x3.playToGrid(2, 2, 0);
    Assert.assertEquals(3, model4x3.getPlayerScore(TeamColor.RED));
    Assert.assertEquals(5, model4x3.getPlayerScore(TeamColor.BLUE));
  }

  @Test(expected = IllegalStateException.class)
  public void testNumCardFlipsWhenGameHasNotStarted() {
    model4x3.numCardFlips(nerfedCard, 0, 0,
            new ThreeTriosPlayer(TeamColor.RED));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNumCardFlipsWithOutOfBoundsCoordinates() {
    model4x3.startGame();
    model4x3.numCardFlips(nerfedCard, -10, 1000, new ThreeTriosPlayer(TeamColor.RED));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNumCardFlipsWithNullObjects() {
    model4x3.startGame();
    model4x3.numCardFlips(null, -10, 1000, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNumCardFlipsWhenCoordsMapToHole() {
    model4x3.startGame();
    model4x3.numCardFlips(nerfedCard, 0, 2, new ThreeTriosPlayer(TeamColor.RED));
  }

  @Test
  public void testNumCardFlips() {
    model4x3.startGame();
    // 0 because no cards have been placed
    Assert.assertEquals(0,
            model4x3.numCardFlips(nerfedCard, 0, 0, new ThreeTriosPlayer(TeamColor.RED)));
    // Player 1 (Red)
    model4x3.playToGrid(1, 0, 0);
    // If blue plays a move the flips red
    Assert.assertEquals(1,
            model4x3.numCardFlips(model4x3.getCurrentPlayer().getHand().get(0), 0, 0,
                    model4x3.getCurrentPlayer()));
    // If blue plays a move that doesn't flip red
    Assert.assertEquals(0,
            model4x3.numCardFlips(model4x3.getCurrentPlayer().getHand().get(0), 0, 1,
                    model4x3.getCurrentPlayer()));
  }

  @Test
  public void testNumCardFlipsDFS() {
    model4x3.startGame();
    model4x3.playToGrid(1, 0, 1); // Red
    model4x3.playToGrid(3, 1, 0); // Blue
    model4x3.playToGrid(0, 0, 2); // Red
    model4x3.playToGrid(2, 2, 0); // Blue
    model4x3.playToGrid(0, 1, 1); // Red
    // best blue team play flips 3 cards (chaining reaction dfs)
    Assert.assertEquals(3,
            model4x3.numCardFlips(model4x3.getCurrentPlayer().getHand().get(0), 2, 0,
                    model4x3.getCurrentPlayer()));
    // subpar blue team play flips
   Assert.assertEquals(1,
            model4x3.numCardFlips(model4x3.getCurrentPlayer().getHand().get(0), 1, 1,
                    model4x3.getCurrentPlayer()));
    Assert.assertEquals(1,
            model4x3.numCardFlips(model4x3.getCurrentPlayer().getHand().get(1), 1, 1,
                    model4x3.getCurrentPlayer()));
    // worst possible blue team play
    Assert.assertEquals(0,
            model4x3.numCardFlips(model4x3.getCurrentPlayer().getHand().get(1), 2, 0,
                    model4x3.getCurrentPlayer()));
  }
}