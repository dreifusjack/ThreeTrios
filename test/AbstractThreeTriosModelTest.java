import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.threetrios.Card;
import cs3500.threetrios.CardCell;
import cs3500.threetrios.GridCell;
import cs3500.threetrios.TeamColor;
import cs3500.threetrios.ThreeTrioCard;
import cs3500.threetrios.ThreeTriosModel;

public abstract class AbstractThreeTriosModelTest {
  protected abstract ThreeTriosModel createModel(String gridFileName, String cardFileName);

  protected ThreeTriosModel model5x7;

  protected ThreeTriosModel model2x2;

  protected ThreeTriosModel model3x3;

  protected ThreeTriosModel modelWithNotEnoughCards;

  protected ThreeTriosModel model2x2SameValueOf1;

  protected ThreeTriosModel model2x2SameValueOf1Ver2;

  private ThreeTrioCard.CardValue ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, A;

  @Before
  public void setUp() {
    model5x7 = createModel("src/cs3500/threetrios/world1.txt", "src/cs3500/threetrios/card1.txt");
    model2x2 = createModel("src/cs3500/threetrios/world2x2.txt", "src/cs3500/threetrios/cards2x2.txt");
    model3x3 = createModel("src/cs3500/threetrios/world3x3.txt", "src/cs3500/threetrios/cards3x3.txt");
    modelWithNotEnoughCards = createModel("src/cs3500/threetrios/world3x3.txt", "src/cs3500/threetrios/3cardsonly.txt");
    model2x2SameValueOf1 = createModel("src/cs3500/threetrios/world2x2ver2.txt", "src/cs3500/threetrios/cardswithsamevalueof1.txt");
    model2x2SameValueOf1Ver2 = createModel("src/cs3500/threetrios/world2x2ver3.txt", "src/cs3500/threetrios/cardswithsamevalueof1.txt");

    ONE = ThreeTrioCard.CardValue.ONE;
    TWO = ThreeTrioCard.CardValue.TWO;
    THREE = ThreeTrioCard.CardValue.THREE;
    FOUR = ThreeTrioCard.CardValue.FOUR;
    FIVE = ThreeTrioCard.CardValue.FIVE;
    SIX = ThreeTrioCard.CardValue.SIX;
    SEVEN = ThreeTrioCard.CardValue.SEVEN;
    EIGHT = ThreeTrioCard.CardValue.EIGHT;
    NINE = ThreeTrioCard.CardValue.NINE;
    A = ThreeTrioCard.CardValue.A;
  }

  // Part 1: --------------------Exception tests------------------------------

  //---------startGame---------------
  //Throw exception when do startGame when the game has started.
  @Test(expected = IllegalStateException.class)
  public void testStartGameWhenGameHasStarted() throws Exception {
    model5x7.startGame();
    model5x7.startGame();
  }

  //Throw exception when do startGame when the game does not have enough of cards.
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCards() throws Exception {
    modelWithNotEnoughCards.startGame();
  }

  //--------playToGrid------------------------
  //Throw exception when do playToGrid when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testPlayToGridWhenGameNotStarted() throws Exception {
    model5x7.playToGrid(5, 2, 2);
  }

  //Throw exception when handIdx is larger than the size of player's hand size.
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToGridHandOutBound() throws Exception {
    model5x7.startGame();
    model5x7.playToGrid(0, 0, 20);
  }

  //Throw exception when playing to a cell that is out of bound.
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToGridCoordinateOutBound() throws Exception {
    model5x7.startGame();
    model5x7.playToGrid(-5, 5, 3);
  }

  //Throw exception when playToGrid to a Hole cell
  @Test(expected = IllegalStateException.class)
  public void testPlayToGridToHoleCell() throws Exception {
    model5x7.startGame();
    model5x7.playToGrid(0, 2, 3);
  }

  //Throw exception when playToGrid to a cell that has a card already.
  @Test(expected = IllegalStateException.class)
  public void testPlayToGridOnAnotherCard() throws Exception {
    model5x7.startGame();
    model5x7.playToGrid(0, 0, 3);
    model5x7.playToGrid(0, 0, 5);
  }

  //----------battleCards-------------
  //Throw exception when do battleCards when the game has not started.
//  @Test(expected = IllegalStateException.class)
//  public void testBattleCardsWhenGameNotStarted() throws Exception {
//    model5x7.battleCards(5, 2);
//  }

  //-----------isGameOver-----------
  //Throw exception when do isGameOver when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testIsGameOverWhenGameNotStarted() throws Exception {
    model5x7.isGameOver();
  }

  //--------------getWinner--------------

  //Throw exception when the game is not over yet
  @Test(expected = IllegalStateException.class)
  public void testGetWinnerWhenGameNotOver() throws Exception {
    model5x7.startGame();
    model5x7.getWinner();
  }

  //Throw exception when do getWinner when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testGetWinnerWhenGameNotStarted() throws Exception {
    model5x7.getWinner();
  }

  //--------------getCurrentPlayer--------
  //Throw exception when do getCurrentPlayer when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testGetCurrentPlayerWhenGameNotStarted() throws Exception {
    model5x7.getCurrentPlayer();
  }

  //--------------getGrid---------------
  //Throw exception when do getGrid when the game has not started.
  @Test(expected = IllegalStateException.class)
  public void testGetGridWhenGameNotStarted() throws Exception {
    model5x7.getGrid();
  }


// Part 2: --------------------Method tests------------------------------

  //---------startGame---------------
//Test if we can startGame successfully.
  @Test
  public void testStartGameSuccessfully() throws Exception {
    model2x2.startGame();

    List<List<GridCell>> expectedGrid = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      List<GridCell> row = new ArrayList<>();
      for (int j = 0; j < 2; j++) {
        row.add(new CardCell());
      }
      expectedGrid.add(row);
    }

    // compare each cell in the grid individually
    List<List<GridCell>> actualGrid = model2x2.getGrid();
    for (int row = 0; row < expectedGrid.size(); row++) {
      for (int col = 0; col < expectedGrid.get(row).size(); col++) {
        GridCell expectedCell = expectedGrid.get(row).get(col);
        GridCell actualCell = actualGrid.get(row).get(col);
        Assert.assertEquals(expectedCell.getClass(), actualCell.getClass());
      }
    }

    // check the hands of the red player
    List<Card> expectedRedHand = Arrays.asList(
            new ThreeTrioCard("CorruptKing", TeamColor.RED, SEVEN, NINE, THREE, A),
            new ThreeTrioCard("AngryDragon", TeamColor.RED, TWO, EIGHT, NINE, NINE),
            new ThreeTrioCard("WindBird", TeamColor.RED, SEVEN, FIVE, TWO, THREE),
            new ThreeTrioCard("HeroKnight", TeamColor.RED, A, FOUR, TWO, FOUR));
    Assert.assertEquals(expectedRedHand, model2x2.getCurrentPlayer().getHand());
  }

//--------playToGrid--------------


  // playToGrid on a CardCell without any battles
  @Test
  public void testPlayToGridOnEmptyCardCell() throws Exception {
    model2x2.startGame();
    model2x2.playToGrid(0, 0, 0);
    ThreeTrioCard corruptKing = new ThreeTrioCard("CorruptKing", TeamColor.RED, SEVEN, NINE, THREE, A);

    Assert.assertEquals(corruptKing, model2x2.getGrid().get(0).get(0).getCard());
  }

  // playToGrid and trigger a color change on an adjacent card
  @Test
  public void testPlayToGridChangeAdjacentColor() throws Exception {
    model3x3.startGame();
    model3x3.playToGrid(0, 0, 0);

    model3x3.playToGrid(0, 1, 2);

    Assert.assertEquals(TeamColor.BLUE, model3x3.getGrid().get(0).get(0).getCard().getColor()); // 2 > 1
  }

  // playToGrid without changing the color of an adjacent card
  @Test
  public void testPlayToGridNoAdjacentColorChange() throws Exception {
    model3x3.startGame();
    model3x3.playToGrid(0, 0, 2);

    model3x3.playToGrid(0, 1, 0);

    Assert.assertEquals(TeamColor.RED, model3x3.getGrid().get(0).get(0).getCard().getColor()); // 1 is not larger than 3
  }

  // playToGrid with tie in value, ensuring no color change
  @Test
  public void testPlayToGridTieNoColorChange() throws Exception {
    model3x3.startGame();
    model3x3.playToGrid(0, 0, 0);

    model3x3.playToGrid(0, 1, 0);

    Assert.assertEquals(TeamColor.RED, model3x3.getGrid().get(0).get(0).getCard().getColor()); // 1 is not larger than 1
  }

  // playToGrid triggers a combo move.
  @Test
  public void testPlayToGridChainReaction() throws Exception {
    model3x3.startGame();

    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 1);
    List<List<GridCell>> grid = model3x3.getGrid();

    Assert.assertEquals(TeamColor.RED, grid.get(0).get(0).getCard().getColor());
    Assert.assertEquals(TeamColor.RED, grid.get(1).get(0).getCard().getColor());
  }


//----------battleCards-------------

  // battleCards with no adjacent opponent cards.
  @Test
  public void testBattleCardsNoAdjacentOpponents() throws Exception {
    model3x3.startGame();

    Card redCard = new ThreeTrioCard("HeroKnight", TeamColor.RED, SEVEN, FIVE, TWO, THREE);
    model3x3.getGrid().get(1).get(1).addCard(redCard);

    model3x3.battleCards(1, 1);

    Assert.assertEquals(TeamColor.RED, model3x3.getGrid().get(1).get(1).getCard().getColor());
  }

  // battleCards triggers a color change on a weaker adjacent card.
  @Test
  public void testBattleCardsWeakerAdjacentOpponent() throws Exception {
    model3x3.startGame();

    Card redCard = new ThreeTrioCard("HeroKnight", TeamColor.RED, SEVEN, FIVE, TWO, THREE);
    Card blueCard = new ThreeTrioCard("ShadowBeast", TeamColor.BLUE, THREE, TWO, FIVE, FOUR);
    model3x3.getGrid().get(1).get(1).addCard(redCard);
    model3x3.getGrid().get(1).get(2).addCard(blueCard);

    model3x3.battleCards(1, 1);

    Assert.assertEquals(TeamColor.RED, model3x3.getGrid().get(1).get(2).getCard().getColor());
  }

  // battleCards does not change color on a stronger adjacent card.
  @Test
  public void testBattleCardsStrongerAdjacentOpponent() throws Exception {
    model3x3.startGame();

    Card redCard = new ThreeTrioCard("HeroKnight", TeamColor.RED, THREE, FIVE, TWO, THREE);
    Card blueCard = new ThreeTrioCard("CorruptKing", TeamColor.BLUE, SEVEN, NINE, THREE, A);
    model3x3.getGrid().get(1).get(1).addCard(redCard);

    model3x3.getGrid().get(1).get(0).addCard(blueCard);

    model3x3.battleCards(1, 1);

    Assert.assertEquals(TeamColor.BLUE, model3x3.getGrid().get(1).get(0).getCard().getColor());
  }

  // battleCards encounters a tie with an adjacent card.
  @Test
  public void testBattleCardsTieWithAdjacentOpponent() throws Exception {
    model3x3.startGame();

    Card redCard = new ThreeTrioCard("HeroKnight", TeamColor.RED, FIVE, FIVE, FIVE, FIVE);
    Card blueCard = new ThreeTrioCard("ShadowBeast", TeamColor.BLUE, FIVE, FIVE, FIVE, FIVE);
    model3x3.getGrid().get(1).get(1).addCard(redCard);

    model3x3.getGrid().get(1).get(0).addCard(blueCard);

    model3x3.battleCards(1, 1);

    Assert.assertEquals(TeamColor.RED, model3x3.getGrid().get(1).get(1).getCard().getColor());
    Assert.assertEquals(TeamColor.BLUE, model3x3.getGrid().get(1).get(0).getCard().getColor());
  }

  // battleCards triggers a chain reaction of color changes.
  @Test
  public void testBattleCardsChainReaction() throws Exception {
    model3x3.startGame();

    Card redCard = new ThreeTrioCard("HeroKnight", TeamColor.RED, SEVEN, SEVEN, SEVEN, SEVEN);
    Card blueCard1 = new ThreeTrioCard("ShadowBeast", TeamColor.BLUE, THREE, THREE, THREE, THREE);
    Card blueCard2 = new ThreeTrioCard("WindBird", TeamColor.BLUE, THREE, THREE, THREE, THREE);
    model3x3.getGrid().get(1).get(1).addCard(redCard);
    model3x3.getGrid().get(1).get(0).addCard(blueCard1);
    model3x3.getGrid().get(0).get(1).addCard(blueCard2);

    model3x3.battleCards(1, 1);

    Assert.assertEquals(TeamColor.RED, model3x3.getGrid().get(1).get(0).getCard().getColor());
    Assert.assertEquals(TeamColor.RED, model3x3.getGrid().get(0).get(1).getCard().getColor());
  }


//-----------isGameOver--------------


  // Test isGameOver when one player's hand is empty
  @Test
  public void testIsGameOverWhenHandEmpty() throws Exception {
    model2x2SameValueOf1.startGame();

    model2x2SameValueOf1.playToGrid(0, 0, 0);
    model2x2SameValueOf1.playToGrid(0, 1, 0);
    model2x2SameValueOf1.playToGrid(1, 0, 0);

    Assert.assertEquals(true, model2x2SameValueOf1.isGameOver());
  }

  // Test isGameOver when the grid is full (no empty card cells left)
  @Test
  public void testIsGameOverWhenGridIsFull() throws Exception {
    model2x2SameValueOf1Ver2.startGame();

    model2x2SameValueOf1Ver2.playToGrid(0, 0, 0);
    model2x2SameValueOf1Ver2.playToGrid(0, 1, 0);
    model2x2SameValueOf1Ver2.playToGrid(1, 0, 0);
    model2x2SameValueOf1Ver2.playToGrid(1, 1, 0);

    Assert.assertEquals(true, model2x2SameValueOf1Ver2.isGameOver());
  }

  // Test isGameOver when both players still have cards and there are empty cells
  @Test
  public void testIsGameOverWhenMovesRemain() throws Exception {
    model5x7.startGame();

    model5x7.playToGrid(0, 0, 0);

    Assert.assertFalse(model5x7.isGameOver());
  }

  // Test isGameOver after playing all possible moves with alternating turns.
  @Test
  public void testIsGameOverAlternatingTurns() throws Exception {
    model3x3.startGame();

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (!model3x3.isGameOver()) {
          model3x3.playToGrid(row, col, 0);
        }
      }
    }

    Assert.assertTrue(model3x3.isGameOver());
  }

  // Test isGameOver after chain reaction but with remaining cards
  @Test
  public void testIsGameOverAfterChainReactionWithMovesLeft() throws Exception {
    model3x3.startGame();

    model3x3.playToGrid(2, 2, 0);
    model3x3.playToGrid(1, 1, 1);

    Assert.assertFalse(model3x3.isGameOver());
  }

  // Test isGameOver when it's a tie situation (both hands empty, grid full)
  @Test
  public void testIsGameOverWhenPlayersTie() throws Exception {
    model2x2SameValueOf1Ver2.startGame();

    model2x2SameValueOf1Ver2.playToGrid(0, 0, 0);
    model2x2SameValueOf1Ver2.playToGrid(0, 1, 0);
    model2x2SameValueOf1Ver2.playToGrid(1, 0, 0);
    model2x2SameValueOf1Ver2.playToGrid(1, 1, 0);

    Assert.assertEquals(true, model2x2SameValueOf1Ver2.isGameOver());
    System.out.println(model2x2SameValueOf1Ver2.getWinner());
  }

//--------------getWinner--------------

  //When there is a tie.
  @Test
  public void testTie() throws Exception {
    model3x3.startGame();
    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(2, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(0, 1, 0);

    Assert.assertEquals(true, model3x3.isGameOver());
    Assert.assertEquals(null, model3x3.getWinner());
  }

  // When red wins.
  @Test
  public void testRedWins() throws Exception {
    model3x3.startGame();

    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 2, 0);
    // Player 1 (Red)
    model3x3.playToGrid(2, 0, 0);

    Assert.assertEquals(true, model3x3.isGameOver());
    Assert.assertEquals(TeamColor.RED, model3x3.getWinner().getColor());
  }

  //When all cells are marked as red player's cards
  @Test
  public void testRedWinsAllCells() throws Exception {
    model3x3.startGame();

    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 2, 0);
    // Player 1 (Red)
    model3x3.playToGrid(0, 1, 0);

    Assert.assertEquals(true, model3x3.isGameOver());
    Assert.assertEquals(TeamColor.RED, model3x3.getWinner().getColor());
  }

  //When blue player wins
  @Test
  public void testBlueWins() throws Exception {
    model3x3.startGame();
    // Player 1 (Red)
    model3x3.playToGrid(2, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 2, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(1, 0, 0);
    // Player 1 (Red)
    model3x3.playToGrid(1, 1, 0);
    // Player 2 (Blue)
    model3x3.playToGrid(0, 1, 0);
    // Player 1 (Red)
    model3x3.playToGrid(0, 2, 0);

    Assert.assertEquals(true, model3x3.isGameOver());
    Assert.assertEquals(TeamColor.BLUE, model3x3.getWinner().getColor());
  }

  //--------------getCurrentPlayer--------
  // Test for getCurrentPlayer when red player is the current player
  @Test
  public void testGetCurrentPlayerRed() throws Exception {
    model3x3.startGame();

    Assert.assertEquals(TeamColor.RED, model3x3.getCurrentPlayer().getColor());
  }

  // Test for getCurrentPlayer when blue player is the current player
  @Test
  public void testGetCurrentPlayerBlue() throws Exception {
    model3x3.startGame();
    model3x3.playToGrid(2, 2, 0);

    Assert.assertEquals(TeamColor.BLUE, model3x3.getCurrentPlayer().getColor());
  }

  //--------------getGrid---------------
  // Get a copy of the grid of the game.
  @Test
  public void testGetGrid() throws Exception {
    model2x2.startGame();

    List<List<GridCell>> expectedGrid = new ArrayList<>();
    expectedGrid.add(Arrays.asList(new CardCell(), new CardCell()));
    expectedGrid.add(Arrays.asList(new CardCell(), new CardCell()));

    List<List<GridCell>> actualGrid = model2x2.getGrid();
    for (int row = 0; row < expectedGrid.size(); row++) {
      for (int col = 0; col < expectedGrid.get(row).size(); col++) {
        Assert.assertEquals(expectedGrid.get(row).get(col).getClass(), actualGrid.get(row).get(col).getClass());
      }
    }
  }
}