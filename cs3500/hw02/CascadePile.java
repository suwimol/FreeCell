package cs3500.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Cascade pile in the game of solitaire.
 * This class is created in homework 4 to refactor the FreeCellModel and to simplify the
 * complication of code in the move method.
 */
public class CascadePile extends Pile {

  /**
   * Constructs a cascade pile with its name and list of cards.
   *
   * @param name name of the cascade pile
   */
  CascadePile(String name) {
    super(name);
  }

  /**
   * Checks whether a given card can be added to this cascade pile or not.
   *
   * @param toAdd a card to be added
   * @return true if the given card can be added to the cascade pile, false otherwise.
   */

  @Override
  public boolean canAddCard(Card toAdd) {
    if (isEmpty()) {
      return true;
    }
    return (getTop().suit.color != toAdd.suit.color) &&
            (getTop().val.toInt() == toAdd.val.toInt() + 1);
  }

  /**
   * Checks whether a list of cards can be added to this cascade pile or not.
   *
   * @param cards a list of cards to be added
   * @return true if the given list of cards can be added to the cascade pile, false otherwise.
   */
  @Override
  public boolean canAddManyCards(List<Card> cards) {
    if (canAddCard(cards.get(0))) {
      for (int i = 0; i < cards.size() - 1; i++) {
        if ((cards.get(i).suit.color != cards.get(i + 1).suit.color) &&
                (cards.get(i).val.toInt() == cards.get(i + 1).val.toInt() + 1)) {
          i++;
        } else {
          return false;
        }
      }
      return true;
    }
    return false;
  }
}
