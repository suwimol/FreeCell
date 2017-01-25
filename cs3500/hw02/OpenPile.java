package cs3500.hw02;

import java.util.List;

/**
 * Represents an open pile in the game of solitaire.
 * This class is created in homework 4 to refactor the FreeCellModel and to simplify the
 * complication of code in the move method.
 */
public class OpenPile extends Pile {
  /**
   * Constructs an open pile with its name and list of cards.
   * The list of cards in this pile contains only one card.
   */
  public OpenPile(String name) {
    super(name);
  }

  /**
   * Checks whether a given card can be added to this open pile or not.
   *
   * @param toAdd a card to be added
   * @return true if the given card can be added to the open pile, false otherwise.
   */
  @Override
  public boolean canAddCard(Card toAdd) {
    return isEmpty();
  }

  /**
   * Checks whether a list of cards can be added to this open pile or not.
   *
   * @param cards a list of cards to be added
   * @return false (always returns false as an open pile can only contain one card).
   */
  @Override
  public boolean canAddManyCards(List<Card> cards) {
    return false;
  }
}
