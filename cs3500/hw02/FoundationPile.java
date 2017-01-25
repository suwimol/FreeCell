package cs3500.hw02;

import java.util.List;

/**
 * Represents a foundation pile in the game of solitaire.
 * This class is created in homework 4 to refactor the FreeCellModel and to simplify the
 * complication of code in the move method.
 */
public class FoundationPile extends Pile {

  /**
   * Constructs a foundation pile with its name and list of cards.
   *
   * @param name a name of this foundation pile.
   */
  FoundationPile(String name) {
    super(name);
  }

  /**
   * Checks whether a given card can be added to this foundation pile or not.
   *
   * @param toAdd a card to be added
   * @return true if the given card can be added to the foundation pile, false otherwise.
   */
  @Override
  public boolean canAddCard(Card toAdd) {
    if (listOfCards.size() > 13) {
      return false;
    } else if (isEmpty()) {
      return true;
    }
    return (getTop().suit == toAdd.suit) &&
            (getTop().val.toInt() == toAdd.val.toInt() - 1);
  }

  /**
   * Checks whether a list of cards can be added to this foundation pile or not.
   *
   * @param cards a list of cards to be added
   * @return true if the given list of cards can be added to the foundation pile, false otherwise.
   */
  @Override
  public boolean canAddManyCards(List<Card> cards) {
    if (canAddCard(cards.get(0))) {
      for (int i = 0; i < cards.size() - 1; i++) {
        if ((cards.get(i).suit == cards.get(i + 1).suit) &&
                (cards.get(i).val.toInt() == cards.get(i + 1).val.toInt() - 1)) {
          i++;
        } else {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String toString() {

    String result = "";
    if (listOfCards.size() == 0) {
      result = result + this.name + ":" + "\n";
    } else {
      result = result + this.name + ": ";
    }

    for (int k = 0; k < listOfCards.size(); k++) {
      if (k != listOfCards.size() - 1) {
        result = result
                + listOfCards.get(k).toString() + ", ";
      } else {
        result = result
                + listOfCards.get(k).toString() + "\n";
      }
    }
    return result;
  }
}
