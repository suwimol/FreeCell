package cs3500.hw04;

import cs3500.hw02.FreeCellModel;

/**
 * Represents a Freecell model creator.
 * This is a factory class which gives the player options if one likes a single-move or
 * multi-move free cell game.
 */
public class FreeCellModelCreator {

  /**
   * Enumeration for a game type; single-move or multi-move Freecell model.
   */
  public enum GameType {
    SINGLEMOVE,
    MULTIMOVE;
  }

  /**
   * A static factory method which creates a Freecell model of the given game type.
   *
   * @param type game type; SINGLEMOVE or MULTIMOVE.
   * @return a FreeCellModel of a specific type. A FreeCellModel for SINGLEMOVE or FreeCellMulti
   */
  public static FreeCellModel create(GameType type) {
    if (type.equals(GameType.SINGLEMOVE)) {
      return new FreeCellModel();
    } else {
      return new FreeCellMultiMoveModel();
    }
  }

}
