package cs3500.threetrios;

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
      case NORTH:
        return this.south.getValue() > otherCard.north.getValue();
      case SOUTH:
        return this.north.getValue() > otherCard.south.getValue();
      case EAST:
        return this.west.getValue() > otherCard.east.getValue();
      case WEST:
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
  public String getName() {
    return this.name;
  }

  @Override
  public void changeColor() {
    this.color = this.color == TeamColor.RED ? TeamColor.BLUE : TeamColor.RED;
  }

  @Override
  public Card clone() {
    return new ThreeTrioCard(this.name, this.color, this.north, this.east, this.south, this.west);
  }
}