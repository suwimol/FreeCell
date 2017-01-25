package cs3500.hw04;

import cs3500.hw02.FreeCellModel;
import cs3500.hw02.PileType;
import cs3500.hw02.Card;
import cs3500.hw02.Pile;

import java.util.List;

/**
 * Represents both variations of the Freecell game (single and multiple card moves).
 * This class and FreeCellModel co-exist, giving players options to choose from single or multiple
 * card moves.
 */
public class FreeCellMultiMoveModel extends FreeCellModel {

  /**
   * A default constructor of FreeCellMultiMoveModel with no arguments.
   */
  public FreeCellMultiMoveModel() {
    // default constuctor for FreeCellMultiMoveModel with no arguments.
  }

  /**
   * Checks whether the cards can be moved to another pile or not.
   *
   * @param loc list of cards
   * @return true if the given cards can be moved to another pile, false otherwise.
   */
  private boolean isAbleToMove(List<Card> loc) {
    // checking for no. of empty open piles
    List<Pile> openPiles = getPileList(PileType.OPEN);
    double numFreeOpen = 0;
    for (int i = 0; i < openPiles.size(); i++) {
      if (openPiles.get(i).getPileSize() == 0) {
        numFreeOpen = numFreeOpen + 1;
      }
    }

    List<Pile> cascadePiles = getPileList(PileType.CASCADE);
    double numFreeCascade = 0;
    for (int j = 0; j < cascadePiles.size(); j++) {
      if (cascadePiles.get(j).getPileSize() == 0) {
        numFreeCascade = numFreeCascade + 1;
      }
    }

    double maxNumCards = ((numFreeOpen + 1) * Math.pow(2, numFreeCascade));

    return (loc.size() <= maxNumCards);

  }

  /**
   * Implements moving a card according to the rules. PileType is an enumerated type with values
   * OPEN, FOUNDATION and CASCADE. It moves cards beginning at index cardIndex from the pile number
   * sourcePileNumber of type sourceType to the pile number destPileNumber of type destinationType.
   * All indices and pile numbers in this method start at 0.
   * <p> </p>
   * This move method allows the player to move multiple cards at a time (if the move is valid).
   *
   * @param sourceType       the type of the source pile (see {@link PileType})
   * @param sourcePileNumber the pile number of the given type, starting at 0
   * @param cardIndex        the index of the card to be moved from the source pile, starting at 0
   * @param destType         the type of the destination pile (see {@link PileType})
   * @param destPileNumber   the pile number of the given type, starting at 0
   */
  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destType,
                   int destPileNumber) {

    isValid(sourcePileNumber, destPileNumber, sourceType, destType);

    Pile sourcePile = getPile(sourceType, sourcePileNumber);
    Pile destPile = getPile(destType, destPileNumber);
    List<Card> toBeAdded = sourcePile.getSublist(cardIndex);

    if (!(isAbleToMove(toBeAdded))) {
      throw new IllegalArgumentException("Not able to move.");
    }

    if (cardIndex == sourcePile.getPileSize() - 1) {
      super.move(sourceType, sourcePileNumber, cardIndex, destType, destPileNumber);
    } else if (destPile.canAddManyCards(toBeAdded)) {
      destPile.getListOfCards().addAll(toBeAdded);
      sourcePile.getListOfCards().removeAll(toBeAdded);
    } else {
      throw new IllegalArgumentException("Invalid multi-move!");
    }
  }

}
