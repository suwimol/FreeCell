package cs3500.hw03;

import java.util.List;

import cs3500.hw02.IFreeCellModel;

/**
 * This is the interface of the Freecell controller. It is parameterized over the card type.
 */
public interface IFreeCellController<K> {
  void playGame(List<K> deck, IFreeCellModel<K> model, int numCascades,
                int numOpens, boolean shuffle);
}
