package cs3500.threetrios.model;

import java.util.Objects;

/**
 * Implements the behaviors of a playing card in Three Trios. Three Trios Cards
 * have a unique identifier, a team color, and four attack values for each direction.
 */
public class ThreeTrioCard implements Card {

  /**
   * Enumeration of all possible attack values for cards.
   */
  public enum CardValue {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), A(10);
    private final int value;

    /**
     * Constructs a value based off the given value.
     *
     * @param value CardValue the is given
     * @throws IllegalArgumentException if given value is not in [1,10]
     */
    CardValue(int value) {
      if (value < 1 || value > 10) {
        throw new IllegalArgumentException();
      }
      this.value = value;
    }

    /**
     * Returns the value of this CardValue.
     *
     * @return the int the represents this CardValue's value.
     */
    public int getValue() {
      return value;
    }

    /**
     * Used for reading from a file, converts String value (in int form) to a CardValue.
     *
     * @param value String in integer form
     * @return converted value
     * @throws IllegalArgumentException if the given value is not a CardValue
     */
    public static CardValue fromString(String value) throws IllegalArgumentException {
      switch (value.toUpperCase()) {
        case "1":
          return ONE;
        case "2":
          return TWO;
        case "3":
          return THREE;
        case "4":
          return FOUR;
        case "5":
          return FIVE;
        case "6":
          return SIX;
        case "7":
          return SEVEN;
        case "8":
          return EIGHT;
        case "9":
          return NINE;
        case "A":
          return A;
        default:
          throw new IllegalArgumentException("Invalid CardValue");
      }
    }

    /**
     * Returns the string representation of this CardValue's value.
     *
     * @return String representing this CardValue
     */
    public String toString() {
      if (this.value == A.value) {
        return "A";
      }
      return String.valueOf(this.value);
    }
  }

  private final String name;
  private TeamColor color;
  private final CardValue north;
  private final CardValue east;
  private final CardValue south;
  private final CardValue west;

  /**
   * Constructs a ThreeTrioCard in terms of its unique name, color, and CardValues for each
   * direction.
   *
   * @param name  unique string (assumed handle uniqueness in cardFileReader)
   * @param color TeamColor (red or blue)
   * @param north CardValue
   * @param east  CardValue
   * @param south CardValue
   * @param west  CardValue
   * @throws IllegalArgumentException if name is null or any CardValues are null
   *                                  TeamColor can be null so that the cardFileReader can
   *                                  create instances of card without specifying teams.
   */
  public ThreeTrioCard(String name, TeamColor color, CardValue north,
                       CardValue east, CardValue south, CardValue west) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (north == null || east == null || south == null || west == null) {
      throw new IllegalArgumentException("CardValues cannot be null");
    }
    this.name = name;
    this.color = color;
    this.north = north;
    this.east = east;
    this.south = south;
    this.west = west;
  }

  @Override
  public String toString() {
    return this.name
            + " " + this.north
            + " " + this.south
            + " " + this.east
            + " " + this.west;
  }


  @Override
  public boolean compare(Card other, Direction direction) {
    if (other == null || direction == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    if (!(other instanceof ThreeTrioCard)) {
      return false;
    }
    ThreeTrioCard otherCard = (ThreeTrioCard) other;
    if (this.color == otherCard.color) {
      return false; //on the same team, no need to battle
    }
    switch (direction) {
      case SOUTH:
        return this.south.getValue() > otherCard.north.getValue();
      case NORTH:
        return this.north.getValue() > otherCard.south.getValue();
      case WEST:
        return this.west.getValue() > otherCard.east.getValue();
      case EAST:
        return this.east.getValue() > otherCard.west.getValue();
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }

  @Override
  public TeamColor getColor() {
    return this.color;
  }

  @Override
  public void toggleColor() {
    this.color = this.color == TeamColor.RED ? TeamColor.BLUE : TeamColor.RED;
  }

  @Override
  public void setColor(TeamColor color) {
    this.color = color;
  }

  @Override
  public CardValue getEast() {
    return this.east;
  }

  @Override
  public CardValue getWest() {
    return this.west;
  }

  @Override
  public CardValue getSouth() {
    return this.south;
  }

  @Override
  public CardValue getNorth() {
    return this.north;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ThreeTrioCard card = (ThreeTrioCard) obj;
    return name.equals(card.name)
            && color == card.color
            && north == card.north
            && east == card.east
            && south == card.south
            && west == card.west;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, color, north, east, south, west);
  }
}