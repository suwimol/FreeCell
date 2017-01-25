package cs3500.hw02;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * This Util class contains all the methods that might be used publicly.
 */
public class Util<T> {
  /**
   * Constructs the method listEqualsNoOrder.
   *
   * @param l1  list of type T
   * @param l2  list of type T
   * @param <T> any types of object
   * @return a boolean value
   */
  public static <T> boolean listEqualsNoOrder(List<T> l1, List<T> l2) {
    final Set<T> SET_1 = new HashSet<>(l1);
    final Set<T> SET_2 = new HashSet<>(l2);

    return SET_1.equals(SET_2);
  }
}
