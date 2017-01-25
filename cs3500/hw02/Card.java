package cs3500.hw02;

/**
 * Represents a Card with its Suit and Value.
 */
public class Card {
  Value val;
  Suit suit;

  public Card(Value val, Suit suit) {
    this.val = val;
    this.suit = suit;
  }

  /**
   * Constructs a Card with no arguments.
   * This is used for initialization.
   */
  public Card() {
    /**
     * An empty Card constructor. This is for initialization.
     */
  }

  @Override
  public String toString() {
    return this.val.toString() + this.suit.toString();
  }

  @Override
  public boolean equals(Object obj) {
    Card c = (Card) obj;
    return c.val == this.val && c.suit == this.suit;
  }

  @Override
  public int hashCode() {
    return 10000 * val.ordinal() + suit.hashCode();
  }

}
