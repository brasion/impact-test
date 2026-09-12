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

    String buffer = "";
    for (int pos = 0; pos < input.length(); pos++) {
      Character ch = input.charAt(pos);
      switch (ch) {
        case '-':
          if (buffer.equals("")) {
            buffer += ch;
          }
          break;
        case '0','1','2','3','4','5','6','7','8','9':
          buffer += ch;
          break;
        case ',':
          if (buffer != "" && buffer != "-") {
            numberSequence.add(Integer.valueOf(buffer));
          }
          buffer = "";
          break;
        default:
          break;
      }
    }
    // Add last section of buffer to sequence
    if (!buffer.equals("") && !buffer.equals("-")) {
      numberSequence.add(Integer.valueOf(buffer));
    }
    return numberSequence;
  }

  /**
   * Summarizes a collection into a set of ranges
   * XXX define a range as strictly 3 consecutive numbers
   * 
   * @param input the input collection to be summarized
   */
  @Override
  public String summarizeCollection(Collection<Integer> input) {
    String s = "";
    // prev == null denotes the first iteration
    Integer prev = null;
    Integer curr = null;
    Boolean isRange = false;

    Iterator<Integer> elements = input.iterator();

    while (elements.hasNext()) {
      // Get next element
      curr = elements.next();
      // Unecessarily complex
      while (elements.hasNext() && prev != null &&  curr == prev + 1) {
        isRange = true;
        prev = curr;
        curr = elements.next();
      }
      if (isRange) {
        // if there is no next, end the range right here
        if (!elements.hasNext()) {
          s += "-" + String.valueOf(curr);
        } else {
          // end the range at the previous value, write current element
          s += "-" + String.valueOf(prev) + ", " + String.valueOf(curr);
        }
      } else {
        // Do not add comma on first iteration
        if (prev != null) {
          s += ", ";
        }
        s += String.valueOf(curr);
      }
      isRange = false;
      prev = curr;
    }
    return s;
  }
}
