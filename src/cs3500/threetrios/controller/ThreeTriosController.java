package cs3500.threetrios.controller;

import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Controls how both the user and view interact with model. Responsible for calling operations on
 * the model based on user input and passing that information to the view to be rendered back to
 * the user.
 */
public interface ThreeTriosController {
  /**
   * Initializes the given model with this controller's file configurations. Initializes this
   * controller's view with the newly initialized model. More implementation in the future.
   *
   * @param model model to operate on for internal behaviors
   * @throws IllegalArgumentException if model is null
   * @throws IllegalArgumentException if the model has already been initialized
   * @throws IllegalArgumentException if either file reader reads invalid data.
   */
  void playGame(ThreeTriosModel model);
}
