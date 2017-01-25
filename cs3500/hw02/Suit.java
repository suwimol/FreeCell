package cs3500.hw02;

/**
 * Enumeration for the four types of <i>suits</i> in a game of solitaire.
 * In Freecell, the goal is to fill the {@code FOUNDATION} piles with all the game cards.
 */

public enum Suit {
  /**
   * A finite set of types of Suit with its color. Each card must be in one of these Suits.
   */
  CLUBS(Color.BLACK),

  DIAMONDS(Color.RED),

  HEARTS(Color.RED),

  SPADES(Color.BLACK);

  public final Color color;

  Suit(Color color) {
    this.color = color;
  }

  /**
   * Converts types of Suits into String.
   *
   * @return a String of a type of Suit
   */
  @Override
  public String toString() {
    if (this.equals(CLUBS)) {
      return "♣";
    } else if (this.equals(DIAMONDS)) {
      return "♦";
    } else if (this.equals(HEARTS)) {
      return "♥";
    } else {
      return "♠";
    }
  }
}

