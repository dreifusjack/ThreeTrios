package cs3500.threetrios;

public enum TeamColor {
  RED, BLUE;

  @Override
  public String toString() {
    return this.name().substring(0, 1);
  }
}
