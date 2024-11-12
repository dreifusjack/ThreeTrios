package cs3500.threetrios.controller;

import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Controls how both the user and view interact with model. Responsible for calling operations on
 * the model based on user input and passing that information to the view to be rendered back to
 * the user.
 */
public interface ThreeTriosController {
  /**
   * Plays a new game of ThreeTrios with this controller's model and renders to the user with this
   * controller's view.
   *
   * @param model model to operate on for internal behaviors
   * @throws IllegalArgumentException if model is null
   */
  void playGame(ThreeTriosModel model);
}
