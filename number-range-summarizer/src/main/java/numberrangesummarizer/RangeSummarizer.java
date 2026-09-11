package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Philip Loubser
 */
public class RangeSummarizer implements NumberRangeSummarizer {

  @Override
  public Collection<Integer> collect(String input) {
    ArrayList<Integer> numberSequence = new ArrayList<>();

    return numberSequence;
  }

  @Override
  public String summarizeCollection(Collection<Integer> input) {
    String s = "";
    Integer prev = null;
    Integer curr = null;
    // Sanity check for empty list
    if (input.size() == 0) {
      return s;
    }
    // TODO Sort the input collection
    Iterator<Integer> elements = input.iterator();
    // If empty no error :)
    if (elements.hasNext()) {
      curr = elements.next();
      s += String.valueOf(curr);
      prev = curr;
    }
    Boolean range = false;
    while (elements.hasNext()) {
      curr = elements.next();
      if (curr == prev + 1) {
        range = true;
      }
      while (elements.hasNext() && curr == prev + 1) {
        range = true;
        prev = curr;
        curr = elements.next();
      }
      if (range) {
        if (!elements.hasNext()) {
          s += "-" + String.valueOf(curr);
        } else {
          s += "-" + String.valueOf(prev) + "," + String.valueOf(curr);
        }
      } else {
        s += "," + String.valueOf(curr);
      }
      range = false;
      prev = curr;
    }

    return s;
  }
}
