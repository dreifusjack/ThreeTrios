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

/**
 * Responsible for testing the behaviors of the ThreeTrioModel interface.
 */
public abstract class AbstractThreeTriosModelTest {
  protected abstract ThreeTriosModel createModel(String gridFileName, String cardFileName);

  protected abstract ThreeTriosModel createModelWithRandom(String gridFileName,
                                                           String cardFileName, Random random);

  protected ThreeTriosModel model5x7;

  protected ThreeTriosModel model2x2;

  protected ThreeTriosModel model3x3;

  protected ThreeTriosModel modelWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;

  @Before
  public void setUp() {
    Random rand1 = new Random(2);

    model5x7 = createModelWithRandom("world1.txt", "card1.txt", rand1);
    model2x2 = createModelWithRandom("world2x2.txt", "cards2x2.txt", rand1);
    model3x3 = createModelWithRandom("world3x3.txt", "cards3x3.txt", rand1);
    modelWithNotEnoughCards = createModelWithRandom("world3x3.txt",
            "3cardsonly.txt", rand1);
    model2x2SameValueOf1 = createModelWithRandom("world2x2ver2.txt",
            "cardswithsamevalueof1.txt", rand1);
    model2x2SameValueOf1Ver2 = createModelWithRandom("world2x2ver3.txt",
            "cardswithsamevalueof1.txt", rand1);


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
    model5x7.getGrid();
  }


  // Part 2: --------------------Method tests------------------------------

  //---------startGame---------------
  //Test if we can startGame successfully.
  @Test
  public void testStartGameSuccessfully() {
    model3x3.startGame();

    List<List<ReadOnlyGridCell>> expectedGrid = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      List<ReadOnlyGridCell> row = new ArrayList<>();
      for (int j = 0; j < 2; j++) {
        row.add(new CardCell());
      }
      expectedGrid.add(row);
    }

    // compare each cell in the grid individually
    List<List<ReadOnlyGridCell>> actualGrid = model3x3.getGrid();
    for (int row = 0; row < expectedGrid.size(); row++) {
      for (int col = 0; col < expectedGrid.get(row).size(); col++) {
        ReadOnlyGridCell expectedCell = expectedGrid.get(row).get(col);
        ReadOnlyGridCell actualCell = actualGrid.get(row).get(col);
        Assert.assertEquals(expectedCell.getClass(), actualCell.getClass());
      }
    }

    // check the hands of the red player
    List<Card> expectedRedHand = Arrays.asList(
            new ThreeTrioCard("WorldDragon", ThreeTrioCard.CardValue.ONE,
                    ThreeTrioCard.CardValue.FIVE, ThreeTrioCard.CardValue.SIX,
                    ThreeTrioCard.CardValue.ONE),
            new ThreeTrioCard("HeroKnight", ThreeTrioCard.CardValue.A,
                    ThreeTrioCard.CardValue.FOUR,
                    ThreeTrioCard.CardValue.FOUR, ThreeTrioCard.CardValue.ONE),
            new ThreeTrioCard("CorruptKing", ThreeTrioCard.CardValue.THREE,
                    ThreeTrioCard.CardValue.ONE, ThreeTrioCard.CardValue.ONE,
                    ThreeTrioCard.CardValue.TWO),
            new ThreeTrioCard("FirePhoenix",
                    ThreeTrioCard.CardValue.TWO, ThreeTrioCard.CardValue.FOUR,
                    ThreeTrioCard.CardValue.THREE, ThreeTrioCard.CardValue.TWO));
    Assert.assertEquals(expectedRedHand, model3x3.getCurrentPlayer().getHand());
  }

  //--------playToGrid--------------
  // playToGrid on a CardCell without any battles
  @Test
  public void testPlayToGridOnEmptyCardCell() {
    model3x3.startGame();
    model3x3.playToGrid(0, 0, 2);
    ThreeTrioCard corruptKing = new ThreeTrioCard("CorruptKing",
            ThreeTrioCard.CardValue.THREE, ThreeTrioCard.CardValue.ONE,
            ThreeTrioCard.CardValue.ONE, ThreeTrioCard.CardValue.TWO);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString());
  }

  // playToGrid and trigger a color change on an adjacent card
  @Test
  public void testPlayToGridChangeAdjacentColor() {
    model3x3.startGame();
    model3x3.playToGrid(0, 0, 0);

    model3x3.playToGrid(0, 1, 1);

    Assert.assertEquals("B", model3x3.getGrid().get(0).get(0).toString()); // A > 1
  }

  // playToGrid without changing the color of an adjacent card
  @Test
  public void testPlayToGridNoAdjacentColorChange() {
    model3x3.startGame();
    model3x3.playToGrid(0, 0, 0);
    System.out.println(model3x3.getCurrentPlayer().getHand());

    model3x3.playToGrid(0, 1, 0);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString());
    // 2 is not larger than 5
  }

  // playToGrid with tie in value, ensuring no color change
  @Test
  public void testPlayToGridTieNoColorChange() {
    model3x3.startGame();
    model3x3.playToGrid(0, 0, 1);

    model3x3.playToGrid(0, 1, 0);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString());
    // 4 is not larger than 4

  }

  // playToGrid triggers a combo move.
  @Test
  public void testPlayToGridChainReaction() {
    model3x3.startGame();

    // Player 1 (Red)
    model3x3.playToGrid(0, 0, 1);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 2, 1);
    // Player 1 (Red)
    model3x3.playToGrid(0, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 1);
    List<List<ReadOnlyGridCell>> grid = model3x3.getGrid();

    Assert.assertEquals("B", grid.get(0).get(0).toString()); // Was Red
    Assert.assertEquals("B", grid.get(0).get(1).toString()); // Was Red
  }


  //----------battleCards-------------
  // battleCards with no adjacent opponent cards.
  @Test
  public void testBattleCardsNoAdjacentOpponents() {
    model3x3.startGame();

    model3x3.playToGrid(0, 0, 0);

    model3x3.battleCards(0, 0);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString());
  }

  // battleCards triggers a color change on a weaker adjacent card.
  @Test
  public void testBattleCardsWeakerAdjacentOpponent() {
    model3x3.startGame();

    model3x3.playToGrid(0, 0, 0); //RED 1 6 5 1
    model3x3.playToGrid(0, 1, 3); //BLUE 3 2 1 1

    model3x3.battleCards(0, 0);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(1).toString());
  }

  // battleCards does not change color on a stronger adjacent card.
  @Test
  public void testBattleCardsStrongerAdjacentOpponent() {
    model3x3.startGame();


    model3x3.playToGrid(0, 0, 0); //RED 1 6 5 1
    model3x3.playToGrid(0, 1, 3); //BLUE 3 2 1 1

    model3x3.battleCards(0, 1);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString());
  }

  // battleCards encounters a tie with an adjacent card.
  @Test
  public void testBattleCardsTieWithAdjacentOpponent() {
    model3x3.startGame();

    model3x3.playToGrid(0, 0, 1); //RED A 4 4 1
    model3x3.playToGrid(0, 1, 0); //BLUE 3 9 5 4

    model3x3.battleCards(0, 0);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString());
    Assert.assertEquals("B", model3x3.getGrid().get(0).get(1).toString());
  }

  // battleCards triggers a chain reaction of color changes.
  @Test
  public void testBattleCardsChainReaction() {
    model3x3.startGame();

    model3x3.playToGrid(2, 0, 3); //RED 2 3 2 4
    model3x3.playToGrid(0, 1, 3); //BLUE 3 2 1 1
    model3x3.playToGrid(1, 0, 0); //RED 1 6 5 1
    model3x3.playToGrid(1, 1, 0); //BLUE 3 9 5 4

    model3x3.battleCards(1, 0);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(1).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(1).get(1).toString());
  }

  // battleCards trigger a chain reaction of color changes and ends and the reaction
  // ends when there is a card that is higher in value

  @Test
  public void testBattleCardsChainReactionSpecial() {
    model3x3.startGame();

    model3x3.playToGrid(0, 0, 2);
    model3x3.playToGrid(1, 1, 1);
    model3x3.playToGrid(1, 0, 0);
    model3x3.playToGrid(2, 2, 0);
    model3x3.playToGrid(2, 0, 1);
    model3x3.battleCards(1, 1);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString()); // Still red
    Assert.assertEquals("B", model3x3.getGrid().get(1).get(0).toString()); // Was red
    Assert.assertEquals("B", model3x3.getGrid().get(1).get(1).toString());
    Assert.assertEquals("B", model3x3.getGrid().get(2).get(0).toString()); // Was red
    Assert.assertEquals("B", model3x3.getGrid().get(2).get(2).toString());
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
    model3x3.startGame();

    model3x3.playToGrid(2, 2, 0);
    model3x3.playToGrid(1, 1, 1);

    Assert.assertFalse(model3x3.isGameOver());
  }

  // Test isGameOver when it's a tie situation (both hands empty, grid full)
  @Test
  public void testIsGameOverWhenPlayersTie() {
    model3x3.startGame();

    // Player 1 (Red)
    model3x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 2, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 0, 1);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(3, 1, 0);

    Assert.assertEquals(true, model3x3.isGameOver());
    System.out.println(model3x3.getWinner());
  }

  //--------------getWinner--------------
  //When there is a tie.
  @Test
  public void testTie() {
    model3x3.startGame();

    // Player 1 (Red)
    model3x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 2, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 0, 1);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(3, 1, 0);

    Assert.assertEquals(true, model3x3.isGameOver());
    Assert.assertEquals(null, model3x3.getWinner());
  }

  // When red wins.
  @Test
  public void testRedWins() {
    model3x3.startGame();

    // Player 1 (Red)
    model3x3.playToGrid(0, 0, 2);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 2, 2);
    // Player 1 (Red)
    model3x3.playToGrid(1, 0, 1);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 1, 1);
    // Player 1 (Red)
    model3x3.playToGrid(0, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(3, 1, 0);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(0).get(1).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(1).get(0).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(1).get(1).toString());
    Assert.assertEquals("B", model3x3.getGrid().get(2).get(0).toString());
    Assert.assertEquals("B", model3x3.getGrid().get(2).get(2).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(3).get(1).toString());

    Assert.assertEquals(true, model3x3.isGameOver());
    Assert.assertEquals(TeamColor.RED, model3x3.getWinner().getColor());
  }

  //When all cells are marked as red player's cards
  // (a player wins with all cells fiiled with their color)
  @Test
  public void testRedWinsAllCells() {
    model3x3.startGame();

    // Player 1 (Red)
    model3x3.playToGrid(0, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 1);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 1);
    // Player 1 (Red)
    model3x3.playToGrid(3, 1, 1);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 1, 1);
    // Player 1 (Red)
    model3x3.playToGrid(0, 1, 0);

    Assert.assertEquals("R", model3x3.getGrid().get(0).get(0).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(0).get(1).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(1).get(0).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(1).get(1).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(2).get(0).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(2).get(2).toString());

    Assert.assertEquals(true, model3x3.isGameOver());
    Assert.assertEquals(TeamColor.RED, model3x3.getWinner().getColor());
  }

  //When blue player wins
  @Test
  public void testBlueWins() {
    model3x3.startGame();
    // Player 1 (Red)
    model3x3.playToGrid(1, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model3x3.playToGrid(2, 0, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 1, 0);
    // Player 1 (Red)
    model3x3.playToGrid(3, 1, 0);

    Assert.assertEquals("B", model3x3.getGrid().get(0).get(0).toString());
    Assert.assertEquals("B", model3x3.getGrid().get(0).get(1).toString());
    Assert.assertEquals("B", model3x3.getGrid().get(1).get(0).toString());
    Assert.assertEquals("B", model3x3.getGrid().get(1).get(1).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(2).get(0).toString());
    Assert.assertEquals("R", model3x3.getGrid().get(2).get(2).toString());

    Assert.assertEquals(true, model3x3.isGameOver());
    Assert.assertEquals(TeamColor.BLUE, model3x3.getWinner().getColor());
  }

  //--------------getCurrentPlayer--------
  // Test for getCurrentPlayer when red player is the current player
  @Test
  public void testGetCurrentPlayerRed() {
    model3x3.startGame();

    Assert.assertEquals(TeamColor.RED, model3x3.getCurrentPlayer().getColor());
  }

  // Test for getCurrentPlayer when blue player is the current player
  @Test
  public void testGetCurrentPlayerBlue() {
    model3x3.startGame();
    model3x3.playToGrid(2, 2, 0);

    Assert.assertEquals(TeamColor.BLUE, model3x3.getCurrentPlayer().getColor());
  }

  //--------------getGrid---------------
  // Get a copy of the grid of the game.
  @Test
  public void testGetGrid() {
    model2x2.startGame();

    List<List<ReadOnlyGridCell>> expectedGrid = new ArrayList<>();
    expectedGrid.add(Arrays.asList(new CardCell(), new Hole()));
    expectedGrid.add(Arrays.asList(new CardCell(), new CardCell()));

    List<List<ReadOnlyGridCell>> actualGrid = model2x2.getGrid();
    for (int row = 0; row < expectedGrid.size(); row++) {
      for (int col = 0; col < expectedGrid.get(row).size(); col++) {
        Assert.assertEquals(expectedGrid.get(row).get(col).getClass(),
                actualGrid.get(row).get(col).getClass());
      }
    }
  }
}