package cs3500.threetrios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.TeamColor;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.PlayerActions;
import cs3500.threetrios.view.TTGUIView;
import cs3500.threetrios.view.ThreeTriosCardPanel;

public class MockThreeTriosFeaturesController extends  ThreeTriosFeaturesController{


  private final List<String> log;


  /**
   * Constructs a ThreeTriosController2 with the given model, view, and player actions.
   *
   * @param model         the model representing the game state
   * @param view          the view that displays the game interface
   * @param playerActions the actions associated with the player (AI or human)
   */
  public MockThreeTriosFeaturesController(ThreeTriosModel model, TTGUIView view, PlayerActions playerActions) {
    super(model, view, playerActions);
    this.log = new ArrayList<>();
  }

  /**
   * When the game has started or the model has notified the controller the player turn changed,
   * updates the title of the view to notify the player it's their turn. Additionally, if the
   * now current player is AI, their playerAction calls methods to play a move.
   */
  protected void handlePlayerTurn() {
    super.handlePlayerTurn();

  }

  /**
   * Calls player action methods to make a move that will play to the models grid. This only occurs
   * if playerActions is an AI player, if playActions is a human player these method calls will
   * be omitted as human player actions are handled with the user interacting with the GUI.
   */
  protected void handleAIMoveIfPresent() {
    super.handleAIMoveIfPresent();
  }

  /**
   * According to the model, determines if the current player's team is the team this controller
   * represents.
   *
   * @return true iff the controller representing team is the models current player's team
   */
  protected boolean outOfTurn() {
    return super.outOfTurn();
  }

  @Override
  public void handleCardSelection(TeamColor playerColor, int cardIndex, ThreeTriosCardPanel selectedCard, ThreeTriosCardPanel highlightedCard) {
    super.handleCardSelection(playerColor, cardIndex, selectedCard, highlightedCard);
  }

  @Override
  public void handleBoardSelection(int row, int col) {
    super.handleBoardSelection(row, col);
  }

  @Override
  public void onPlayerTurnChange() {
    super.onPlayerTurnChange();
  }

  @Override
  public void onGameOver() {
    super.onGameOver();
  }


  public List<String> getLog() {
    return new ArrayList<>(log);
  }

}
