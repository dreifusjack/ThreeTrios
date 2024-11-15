package cs3500.threetrios.player;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.TeamColor;

/**
 * Represents an interface of actions a player can carry out in ThreeTrios. A player is one
 * of a human or machine players. Human players are expected to play moves based on their inputs,
 * which occur by mouse clicks on the UI. Machine players are expected to play moves based on
 * their strategy, where a strategy is part of the construction of a player.
 */
public interface PlayerActions {

  void selectCard(ReadOnlyThreeTriosModel model);

  void makeMove(ReadOnlyThreeTriosModel model);

  TeamColor getColor();
}
