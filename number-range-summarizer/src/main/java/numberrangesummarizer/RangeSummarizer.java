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
    Boolean couldBeRange = false;
    Boolean isRange = false;
    
    Iterator<Integer> elements = input.iterator();

    if (elements.hasNext()) {
      curr = elements.next();
      s += String.valueOf(curr);
      prev = curr;
    }

    while (elements.hasNext()) {
      curr = elements.next();
      if (curr == prev + 1) {
        if (couldBeRange) {
          isRange = true;
        } else {
          couldBeRange = true;
        }
      } else {
        if (isRange) {
          s += "-" + String.valueOf(prev);
          isRange = false;
          couldBeRange = false;
        } else if (couldBeRange) {
          s += ", " + String.valueOf(prev);
          couldBeRange = false;
        }
        s += ", " + String.valueOf(curr);
      }
      prev = curr;
    }

    // If array terminated before range status could be determined, add last range element
    if (isRange) {
      s += "-" + String.valueOf(curr);
    } else if (couldBeRange) {
      s += ", " + String.valueOf(curr);
    }

    return s;
  }
}
