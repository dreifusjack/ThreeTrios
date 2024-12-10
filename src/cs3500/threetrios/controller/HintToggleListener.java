package cs3500.threetrios.controller;

/**
 * Represents an observer that listens for hint toggle commands, which are called by user typing
 * "h".
 */
public interface HintToggleListener {
  /**
   * Handles updating the view when the user has made a hint request.
   */
  void onHintToggleRequested();
}
