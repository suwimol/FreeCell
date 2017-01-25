package cs3500.hw03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.hw02.Card;
import cs3500.hw02.IFreeCellModel;
import cs3500.hw02.PileType;

/**
 * Represents a Controller for a FreeCell game.
 * This class controls the inputs given by the user, and output that is then transmitted to the
 * user.
 * <p> </p>
 * Changes made: added public to both readable and appendable fields in order to test this class
 * in FreeCellMultiMoveModelTest class.
 */
public class FreeCellController implements IFreeCellController<Card> {
  public Readable rd;
  public Appendable ap;

  /**
   * Constructs a free cell controller of the free cell game with a readable object to read user's
   * inputs, and an appendable object to transmit the message/game state to the user.
   *
   * @param rd readable object for reading user's inputs
   * @param ap appendable object for transmitting message/game state
   */
  public FreeCellController(Readable rd, Appendable ap) {
    this.rd = rd;
    this.ap = ap;
  }

  private void appendTryCatch(String s) {
    try {
      ap.append(s);
    } catch (IOException o) {
      // need this empty catch
    }
  }

  /**
   * Checks if the given string is an integer string.
   *
   * @param s a string
   * @return true if the string can be parsed as an integer, false otherwise.
   */
  private boolean isInteger(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    } catch (NullPointerException e) {
      return false;
    }
    // only got here if we didn't return false
    return true;
  }

  /**
   * Checks if the given string is one of "C", "F", or "O".
   * This is the helper method for playGame. It checks whether the user has entered a valid source
   * pile/destination pile or not.
   *
   * @return true if the string is one of "C", "F", or "O", false otherwise.
   */
  private boolean isValidPile(String c) {
    String firstChar = c.substring(0, 1);
    String index = c.substring(1);
    if (!(firstChar.equals("C") || firstChar.equals("F") || firstChar.equals("O"))) {
      throw new IllegalArgumentException();
    } else {
      try {
        Integer.parseInt(index);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid pile. Try again.");
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("The pile needs to be followed by number. Try again.");
      }
    }
    // only got here if we didn't return false
    return true;
  }

  /**
   * Checks if the value of integer in the given string is greater than 1.
   * Helper method for playGame to check whether the given card index (as a string) is valid or not.
   *
   * @param c a string
   * @return true if the value of integer in the given string is greater than 1, false otherwise.
   * @throws IllegalArgumentException if the string cannot be parsed as an integer or
   * @throws NullPointerException     if it's null.
   */
  private boolean isValidCardIndex(String c) {
    try {
      return Integer.parseInt(c) >= 1;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("\n" + "Invalid card index. Try again.");
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("The card index cannot be null. Try again.");
    }
  }

  /**
   * Gets the pile type according to the given string.
   * "C" = Cascade pile
   * "F" = Foundation pile
   * "O" = Open pile
   *
   * @param c a string
   * @return a specific pile type.
   */
  private PileType getPileType(String c) {
    String firstChar = c.substring(0, 1);
    PileType result = null;
    switch (firstChar) {
      case "C":
        result = PileType.CASCADE;
        break;
      case "F":
        result = PileType.FOUNDATION;
        break;
      case "O":
        result = PileType.OPEN;
        break;
      default:
        throw new IllegalArgumentException("Invalid pile.");
    }
    return result;
  }

  /**
   * Gets the value of the card index or index of the pile.
   * If the first character in string is not an integer, get the next character and parse it as an
   * integer. Otherwise, assign this string to index.
   *
   * @param c a string
   * @return an an index of either card or pile.
   */
  private int getIndex(String c) {
    String index;
    int result;

    if (isInteger(Character.toString(c.charAt(0)))) {
      index = c;
    } else {
      index = c.substring(1);
    }

    result = Integer.parseInt(index);
    return result;
  }

  /**
   * Plays the free cell game based on user's inputs.
   * It asks the provided model to start a game with the provided parameters, and then “run”
   * the game in the following sequence until the game is over. However, if the user wants the quit
   * the game at any point, it will just end the game.
   *
   * @param deck        deck of cards provided by the user.
   * @param model       model of free cell provided by the user.
   * @param numCascades number of cascade piles provided by the user.
   * @param numOpens    number of open piles provided by the user.
   * @param shuffle     true if one wants the deck to be shuffled, false otherwise.
   */
  @Override
  public void playGame(List<Card> deck, IFreeCellModel<Card> model, int numCascades, int numOpens,
                       boolean shuffle) {

    if ((deck == null) || (model == null)) {
      throw new IllegalArgumentException("The deck cannot be null.");
    }
    if ((rd == null) || (ap == null)) {
      throw new IllegalStateException("Readable or appendable objects cannot be null.");
    }

    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      appendTryCatch("Could not start game.");
      return;
    }

    Scanner scan = new Scanner(this.rd);
    ArrayList<String> commandSeq = new ArrayList<String>();

    appendTryCatch(model.getGameState());

    while (scan.hasNext()) {

      String s = scan.next();

      // checks if the input is 'q' or 'Q'
      if (s.startsWith("q") || s.startsWith("Q")) {
        appendTryCatch("\n" + "Game quit prematurely.");
        return;
      }

      // checks card index, source pile and destination pile here

      if (commandSeq.size() == 1) {
        try {
          if (isValidCardIndex(s)) {
            commandSeq.add(s);
          }
        } catch (IllegalArgumentException e) {
          appendTryCatch("\n" + e.getMessage());
        }
      } else if ((commandSeq.size() == 0) || (commandSeq.size() == 2)) {
        try {
          if (isValidPile(s)) {
            commandSeq.add(s);
          }
        } catch (IllegalArgumentException e) {
          if (commandSeq.size() == 2) {
            appendTryCatch("\n" + "Invalid destination pile. Try again.");
          } else {
            appendTryCatch("\n" + "Invalid source pile. Try again.");
          }
        }
      }

      // Once the commandSeq contains all the valid inputs for a move, come here.
      if (commandSeq.size() == 3) {
        PileType sourcePile = getPileType(commandSeq.get(0));
        int sourcePileIndex = getIndex(commandSeq.get(0));
        int cardIndex = getIndex(commandSeq.get(1));
        PileType destPile = getPileType(commandSeq.get(2));
        int destPileIndex = getIndex(commandSeq.get(2));

        try {
          model.move(sourcePile, sourcePileIndex - 1, cardIndex - 1, destPile,
                  destPileIndex - 1);
        } catch (IllegalArgumentException e) {
          appendTryCatch("\n" + e.getMessage());
        }

        appendTryCatch("\n" + model.getGameState());
        commandSeq.clear();
      }
    }

    if (model.isGameOver()) {
      appendTryCatch("\n" + "Game over.");
      return;
    }

    return;
  }
}


