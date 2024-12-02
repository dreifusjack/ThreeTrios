package cs3500.threetrios.provider.model;

/**
 * Enum for player type, either red or blue.
 */
public enum PlayerType {
  RED,
  BLUE,
  OVER;

  @Override
  public String toString() {
    if (this == RED) {
      return "RED";
    } else if (this == BLUE) {
      return "BLUE";
    } else {
      return "OVER";
    }
  }
}
