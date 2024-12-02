package cs3500.threetrios.provider.model;

/**
 * Enum used to represent the attack value of a card at a given direction.
 */
public enum AttackValue {
  ONE(1, "1"),
  TWO(2, "2"),
  THREE(3, "3"),
  FOUR(4, "4"),
  FIVE(5, "5"),
  SIX(6, "6"),
  SEVEN(7, "7"),
  EIGHT(8, "8"),
  NINE(9, "9"),
  A(10, "A");

  public final int value;
  private final String renderValue;

  AttackValue(int value, String renderValue) {
    this.value = value;
    this.renderValue = renderValue;
  }

  /**
   * Gets the attack value given a value string.
   *
   * @param value the string being checked
   * @return the value corresponding to that string if it exists
   * @throws IllegalArgumentException if string doesn't correspond to an attack value
   */
  public static AttackValue getAttackValue(String value) {
    for (AttackValue val : values()) {
      if (val.renderValue.equals(value)) {
        return val;
      }
    }
    throw new IllegalArgumentException("No AttackValue with value " + value);
  }

  @Override
  public String toString() {
    return renderValue;
  }
}
