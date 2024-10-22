package cs3500.threetrios;

public enum TeamColor {
  RED, BLUE;

  /**
   * Abbreviation of this enumerations name, if Red => R.
   *
   * @return single Char representing a TeamColor
   * TODO: use this when creating textual view for the view of the grid
   */
  public String toStringAbbreviation() {
    return this.name().substring(0, 1);
  }
}
