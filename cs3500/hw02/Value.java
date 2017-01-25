package cs3500.hw02;

/**
 * A finite set of all possible Values a Card can have. Each card must be one of these Values.
 */
public enum Value {
  ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;

  /**
   * Converts types of Values into String.
   *
   * @return a String of a type of Value
   */
  @Override
  public String toString() {
    if (this.equals(ACE)) {
      return "A";
    } else if (this.equals(JACK)) {
      return "J";
    } else if (this.equals(QUEEN)) {
      return "Q";
    } else if (this.equals(KING)) {
      return "K";
    } else {
      return "" + (this.ordinal() + 1);
    }
  }

  /**
   * This is a method to convert the types of Value to an integer.
   *
   * @return the integer value of a Value.
   */
  public int toInt() {
    return this.ordinal() + 1;
  }
}
