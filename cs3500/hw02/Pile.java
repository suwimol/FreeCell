package cs3500.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pile with its type, a list of cards, and a name.
 * This class has been revised in homework 4 to refactor the complication of code in the move
 * method in FreeCellModel. The methods canAddCard and canAddManyCards are made for the move
 * methods in both FreeCellModel and FreeCellMultiMoveModel.
 */
public abstract class Pile {
  protected List<Card> listOfCards;
  protected String name;

  /**
   * Constructs a pile with its type and name.
   * @param name      name of the pile
   */
  public Pile(String name) {
    this.listOfCards = new ArrayList<Card>();
    this.name = name;
  }

  /**
   * Checks whether a given card can be added to a specific pile or not.
   * @param card  a card to be added
   * @return true if the given card can be added to the specific pile, false otherwise.
   */
  abstract public boolean canAddCard(Card card);

  /**
   * Checks whether a list of cards can be added to a specific pile or not.
   * @param cards  a list of cards to be added
   * @return true if the given list of cards can be added to the specific pile, false otherwise.
   */
  abstract public boolean canAddManyCards(List<Card> cards);

  /**
   * Gets the last card of the list of cards of the pile.
   * @return the last card of the list of cards of the pile.
   */
  public Card getTop() {
    return listOfCards.get(listOfCards.size() - 1);
  }

  /**
   * Checks whether this pile is empty or not.
   * The pile is empty when its list of cards contains no cards at all.
   * @return true if the pile is empty (has no cards), false otherwise.
   */
  public boolean isEmpty() {
    return listOfCards.isEmpty();
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

  /**
   * Gets the size of this pile.
   * @return the size of this pile in integer
   */
  public int getPileSize() {
    return listOfCards.size();
  }

  /**
   * Gets a sublist of this list.
   * @param cardIndex  the index to begin with the sublist.
   * @return a sublist of this list beginning with the given card index.
   */
  public List<Card> getSublist(int cardIndex) {
    return this.listOfCards.subList(cardIndex, listOfCards.size());
  }

  /**
   * Gets this list of cards.
   * @return this list of cards.
   */
  public List<Card> getListOfCards() {
    return this.listOfCards;
  }
}
