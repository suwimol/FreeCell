package cs3500.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a FreeCellModel of type Card in the FreeCell game.
 * This class controls the state of the game, allows a client to start a game and move cards.
 */
public class FreeCellModel implements IFreeCellModel<Card> {
  private List<Card> aDeck;
  private List<Pile> listOfCascadePiles;
  private List<Pile> listOfOpenPiles;
  private List<Pile> listOfFoundationPiles;
  private int numCascades;
  private int numOpens;

  /**
   * A default constructor of FreeCellModel with no arguments.
   */
  public FreeCellModel() {
    this.aDeck = new ArrayList<Card>();
    this.listOfFoundationPiles = pilesGenerator(4, PileType.FOUNDATION);
    this.listOfOpenPiles = new ArrayList<Pile>();
    this.listOfCascadePiles = new ArrayList<Pile>();
  }

  /**
   * Constructs a free cell model of the free cell game with a list of cards, list of foundation
   * piles, list of open piles, and list of cascade piles. These lists of piles are initialized to
   * an empty list of pile.
   *
   * @param aDeck       deck of cards for the game to start with
   * @param isShuffled  ask the player whether one wants the deck to be shuffled
   * @param numCascades number of Cascade piles to be constructed (has to satisfy the rules)
   * @param numOpens    number of Open piles to be constructed (has to satisfy the rules)
   * @throws IllegalArgumentException if the number of Cascade or Open piles is invalid
   */
  public FreeCellModel(List<Card> aDeck, boolean isShuffled, int numCascades, int numOpens) {
    this.aDeck = new ArrayList<Card>();
    // create the list of Foundation piles (each one is initially empty)
    this.listOfFoundationPiles = pilesGenerator(4, PileType.FOUNDATION);
    this.listOfOpenPiles = new ArrayList<Pile>();
    this.listOfCascadePiles = new ArrayList<Pile>();
    this.numCascades = numCascades;
    this.numOpens = numOpens;
    if (this.numCascades < 4) {
      throw new IllegalArgumentException("Invalid no. of Cascade piles.");
    } else {
      this.listOfCascadePiles = pilesGenerator(this.numCascades, PileType.CASCADE);
    }
    if (this.numOpens < 1) {
      throw new IllegalArgumentException("Invalid no. of Open piles.");
    } else {
      this.listOfOpenPiles = pilesGenerator(this.numOpens, PileType.OPEN);
    }
  }

  /**
   * Return a valid and complete deck of cards for a game of Freecell. There is
   * no restriction imposed on the ordering of these cards in the deck.
   *
   * @return the deck of cards as a list
   */
  @Override
  public List<Card> getDeck() {
    List<Card> listOfCards = new ArrayList<Card>();

    for (Suit suit : Suit.values()) {
      for (Value val : Value.values()) {
        listOfCards.add(new Card(val, suit));
      }
    }
    return listOfCards;
  }

  /**
   * The helper method for the startGame method to generate a number of lists of the given pileType.
   *
   * @return a List of piles of a particular type of pile.
   */
  public List<Pile> pilesGenerator(int numOfPiles, PileType pileType) {
    List<Pile> listOfPiles = new ArrayList<Pile>();
    for (int i = 0; i < numOfPiles; i++) {
      if (pileType == PileType.CASCADE) {
        listOfPiles.add(new CascadePile("C" + (i + 1)));
      } else if (pileType == PileType.OPEN) {
        listOfPiles.add(new OpenPile("O" + (i + 1)));
      } else if (pileType == PileType.FOUNDATION) {
        listOfPiles.add(new FoundationPile("F" + (i + 1)));
      }
    }
    return listOfPiles;
  }

  /**
   * Starts the FreeCell game with the given deck, number of Cascade piles, number of Open piles and
   * an option to shuffle the deck.
   *
   * @param deck            the deck to be dealt
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   */
  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
          throws IllegalArgumentException {

    if (deck.size() != 52) {
      throw new IllegalArgumentException("Invalid deck!");
    } else {

      List<Card> validDeck = this.getDeck();
      for (int i = 0; i < validDeck.size(); i++) {
        if (!deck.contains(validDeck.get(i))) {
          throw new IllegalArgumentException("Invalid deck!");
        }
      }

      /**
       * Checks for card duplicates in the deck by indirectly copying the given deck and
       * assigning the card 'c' to be the first card of the copied deck.
       * This way it's going to check whether the same card appears in the given deck or not.
       * If it does, throws an exception. Otherwise, progresses startGame method.
       */
      List<Card> deckCopy = new ArrayList<Card>();
      for (int j = 0; j < deck.size(); j++) {
        deckCopy.add(deck.get(j));
      }

      while (!deckCopy.isEmpty()) {
        Card c = deckCopy.remove(0);
        for (int k = 0; k < deckCopy.size(); k++) {
          if (deckCopy.contains(c)) {
            throw new IllegalArgumentException("Invalid deck!");
          } else {
            k++;
          }
        }
      }

      /**
       * Checks whether the player wants to shuffle the deck of cards or not.
       * If one does, then shuffle it. This will update the deck to be the shuffled one.
       */

      if (!(shuffle)) {
        this.aDeck = deck;
      } else {
        this.aDeck = deck;
        Collections.shuffle(deck);
      }
    }

    /**
     * Generates the list of foundation piles when game starts.
     */

    this.listOfFoundationPiles = this.pilesGenerator(4, PileType.FOUNDATION);

    /**
     * Generates the list of Cascade piles when game starts.
     */
    if (numCascadePiles >= 4) {
      this.numCascades = numCascadePiles;
      this.listOfCascadePiles = this.pilesGenerator(numCascadePiles, PileType.CASCADE);
    } else {
      throw new IllegalArgumentException("Invalid No. of Cascade Piles!");
    }

    // create the list of Open piles
    if (numOpenPiles >= 1) {
      this.numOpens = numOpenPiles;
      this.listOfOpenPiles = this.pilesGenerator(numOpenPiles, PileType.OPEN);
    } else {
      throw new IllegalArgumentException("Invalid No. of Open Piles!");
    }

    for (int i = 0; i < numCascadePiles; i++) {
      for (int j = i; j < deck.size(); j = j + numCascadePiles) {
        if (i < deck.size()) {
          this.listOfCascadePiles.get(i).listOfCards.add(deck.get(j));
        }
      }
    }

  }

  /**
   * Gets a pile of a specific type and its index.
   *
   * @param pileType pile type
   * @param pileNum  pile index starting at index 0
   * @return a pile with its name.
   */
  public Pile getPile(PileType pileType, int pileNum) {
    try {
      if (pileType == PileType.CASCADE) {
        return this.listOfCascadePiles.get(pileNum);
      } else if (pileType == PileType.FOUNDATION) {
        return this.listOfFoundationPiles.get(pileNum);
      } else {
        return this.listOfOpenPiles.get(pileNum);
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid pile number.");
    }

  }

  /**
   * Gets the list of piles of a specified type.
   *
   * @param pileType pile type
   * @return list of piles of this free cell model.
   */
  public List<Pile> getPileList(PileType pileType) {
    if (pileType == PileType.CASCADE) {
      return this.listOfCascadePiles;
    } else if (pileType == PileType.FOUNDATION) {
      return this.listOfFoundationPiles;
    } else {
      return this.listOfOpenPiles;
    }
  }

  /**
   * Checks whether source pile and destination pile indices are valid.
   *
   * @param sourceNum      source pile index
   * @param destPileNumber destination pile index
   * @param sourceType     source pile type
   * @param destType       destination pile type
   */
  public void isValid(int sourceNum, int destPileNumber, PileType sourceType, PileType destType) {

    List<Pile> sourceList = getPileList(sourceType);
    List<Pile> destList = getPileList(destType);

    if (sourceNum < 0 || sourceNum >= sourceList.size()) {
      throw new IllegalArgumentException("Invalid source pile number!");
    } else if (destPileNumber < 0 || destPileNumber >= destList.size()) {
      throw new IllegalArgumentException("Invalid destination pile number!");
    } else {
      return;
    }
  }

  /**
   * Implements moving a card according to the rules. PileType is an enumerated type with values
   * OPEN, FOUNDATION and CASCADE. It moves cards beginning at index cardIndex from the pile number
   * sourcePileNumber of type sourceType to the pile number destPileNumber of type destinationType.
   * All indices and pile numbers in this method start at 0.
   * This method has been revised in homework 4 to refactor the FreeCellModel and to simplify the
   * complication of code in the move method.
   *
   * @param sourceType       the type of the source pile (see {@link PileType})
   * @param sourcePileNumber the pile number of the given type, starting at 0
   * @param cardIndex        the index of the card to be moved from the source pile, starting at 0
   * @param destType         the type of the destination pile (see {@link PileType})
   * @param destPileNumber   the pile number of the given type, starting at 0
   */
  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destType,
                   int destPileNumber) throws IllegalArgumentException {

    if (this.isGameOver()) {
      throw new IllegalArgumentException("Game is over!!");
    }

    isValid(sourcePileNumber, destPileNumber, sourceType, destType);

    List<Pile> sourceList = getPileList(sourceType);

    if (cardIndex < 0 && cardIndex != sourceList.size() - 1) {
      throw new IllegalArgumentException("Invalid card index!");
    }

    Pile sourcePile = getPile(sourceType, sourcePileNumber);
    Pile destPile = getPile(destType, destPileNumber);

    if (sourcePile.isEmpty()) {
      throw new IllegalArgumentException("Invalid source pile. There are no cards available.");
    }

    Card toAdd = sourcePile.getTop();

    if (destPile.canAddCard(toAdd)) {
      destPile.listOfCards.add(toAdd);
      sourcePile.listOfCards.remove(toAdd);
    } else {
      throw new IllegalArgumentException("Invalid move!");
    }
  }


  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise.
   */
  @Override
  public boolean isGameOver() {
    int result = 0;
    for (int i = 0; i < this.listOfFoundationPiles.size(); i++) {
      result = result + this.listOfFoundationPiles.get(i).listOfCards.size();
    }
    return result == 52;
  }

  /**
   * Return the present state of the game as a string. The string is formatted
   * as follows:
   *
   * <pre>
   * F1:[b]f11,[b]f12,[b],...,[b]f1n1[n] (Cards in foundation pile 1 in order)
   * F2:[b]f21,[b]f22,[b],...,[b]f2n2[n] (Cards in foundation pile 2 in order)
   * ...
   * Fm:[b]fm1,[b]fm2,[b],...,[b]fmnm[n] (Cards in foundation pile m in order)
   * O1:[b]o11[n]                        (Cards in open pile 1)
   * O2:[b]o21[n]                        (Cards in open pile 2)
   * ...
   * Ok:[b]ok1[n]                        (Cards in open pile k)
   * C1:[b]c11,[b]c12,[b]...,[b]c1p1[n]  (Cards in cascade pile 1 in order)
   * C2:[b]c21,[b]c22,[b]...,[b]c2p2[n]  (Cards in cascade pile 2 in order)
   * ...
   * Cs:[b]cs1,[b]cs2,[b]...,[b]csps     (Cards in cascade pile s in order)
   * </pre>
   * where [b] is a single space, [n] is newline. Note that there is no
   * newline on the last line.  If a pile has no cards in it, there should be no
   * space character after the colon and before the newline.
   *
   * @return a String informing the game state.
   */
  @Override
  public String getGameState() {
    try {
      if (this.aDeck.size() == 0) {
        return "";
      } else {
        return this.toString();
      }
    } catch (IllegalArgumentException e) {
      return "";
    }
  }

  /**
   * Return the present state of the started game as a string.
   *
   * @return String of game state of the started game.
   */
  @Override
  public String toString() {
    String result = "";

    for (int i = 0; i < listOfFoundationPiles.size(); i++) {
      result = result + listOfFoundationPiles.get(i).toString();
    }

    for (int i = 0; i < listOfOpenPiles.size(); i++) {
      result = result + listOfOpenPiles.get(i).toString();
    }

    for (int i = 0; i < listOfCascadePiles.size(); i++) {
      result = result + listOfCascadePiles.get(i).toString();
    }

    result = result.substring(0, result.lastIndexOf("\n"));

    return result;
  }
}