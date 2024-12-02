package cs3500.threetrios.provider.view;

import java.io.IOException;

/**
 * Interface of the Text View. Holds the render method to render a text version of the game
 * state.
 */
public interface ThreeTriosView {
  /**
   * Renders the current view.
   *
   * @throws IOException if there is an error adding to the appendable.
   */
  void render() throws IOException;
}
