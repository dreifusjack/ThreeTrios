package cs3500.threetrios.model;

/**
 * Enumeration for the two team colors in ThreeTrios.
 */
public enum TeamColor {
  RED, BLUE;

  /**
   * Abbreviation of this enumerations name, if Red => R.
   *
   * @return single Char representing a TeamColor
   */
  public String toStringAbbreviation() {
    return this.name().substring(0, 1);
  }
}
