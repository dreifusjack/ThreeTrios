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

  String name;
  TeamColor color;
  CardValue north;
  CardValue east;
  CardValue south;
  CardValue west;

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
    return "";
  }


  @Override
  public boolean compare(Card other, Direction direction) {
    return false;
  }
}
