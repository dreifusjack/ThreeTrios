package cs3500.threetrios.model;

import java.util.Objects;

public class ThreeTrioCard implements Card {

  public enum CardValue {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), A(10);

    private final int value;

    CardValue(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

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
  }

  private final String name;
  private TeamColor color;
  private final CardValue north;
  private final CardValue east;
  private final CardValue south;
  private final CardValue west;

  public ThreeTrioCard(String name, TeamColor color, CardValue north,
                       CardValue east, CardValue south, CardValue west) {
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
    ThreeTrioCard otherCard = (ThreeTrioCard) other;
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
  public void changeColor() {
    this.color = this.color == TeamColor.RED ? TeamColor.BLUE : TeamColor.RED;
  }

  @Override
  public void setColor(TeamColor color) {
    this.color = color;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    ThreeTrioCard card = (ThreeTrioCard) obj;
    return name.equals(card.name) &&
            color == card.color &&
            north == card.north &&
            east == card.east &&
            south == card.south &&
            west == card.west;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, color, north, east, south, west);
  }
}