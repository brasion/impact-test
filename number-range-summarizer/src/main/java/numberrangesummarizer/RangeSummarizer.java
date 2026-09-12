package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Philip Loubser
 * 
 * Provides methods to parse comma-delimited integers from
 * a collection, and converting an integer collection into
 * a comma delimited string, while summarizing the ranges.
 */
public class RangeSummarizer implements NumberRangeSummarizer {
    /**
     * Converts a comma-delimited string into an arraylist of integers.
     * If a '-' is encountered before any numerical value, the number
     * will be treated as a negative. All other non-numeric values will
     * be ignored, and empty sections will not be added to the collection.
     * 
     * @param input the input string
     * 
     * @return A collection of the comma-delimited integers
     */
    @Override
    public Collection<Integer> collect(String input) {
        String buffer = "";
        ArrayList<Integer> numberSequence = new ArrayList<>();

        // Return empty array if the input is null
        if (input == null) {
            return numberSequence;
        }

        for (int pos = 0; pos < input.length(); pos++) {
            Character ch = input.charAt(pos);
            switch (ch) {
                case '-':
                    // Only add a '-' to the integer if it is the first thing encountered
                    if (buffer.equals("")) {
                        buffer += ch;
                    }
                    break;
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                    buffer += ch;
                    break;
                case ',':
                    // Only add the number to the array if it contains some numeric value
                    // This should by construction never be able to throw an error
                    if (buffer != "" && buffer != "-") {
                        numberSequence.add(Integer.valueOf(buffer));
                    }
                    buffer = "";
                    break;
                // Any other character should be ignored
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
     * Returns a string with the collection summarized into a set of ranges.
     * The function operates over the order the collection is given in. If
     * the intention is for it to group all ranges in an arbitrary collection,
     * the collection must be sorted before it is used as a parameter.
     * 
     * @param input the input collection to be summarized
     * 
     * @return A string representing the summarized collection
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        String s = "";
        // prev == null denotes the first iteration
        Integer prev = null;
        Integer curr = null;
        Boolean couldBeRange = false;
        Boolean isRange = false;

        // If the input is null return the empty string
        if (input == null) {
            return s;
        }

        Iterator<Integer> elements = input.iterator();

        // Initialise elements, this way 'prev' is guarunteed to
        // not be null in main while loop
        if (elements.hasNext()) {
            curr = elements.next();
            s += String.valueOf(curr);
            prev = curr;
        }

        while (elements.hasNext()) {
            curr = elements.next();
            if (curr == prev + 1) {
                if (couldBeRange) {
                    // Three consecutive numbers have been found
                    isRange = true;
                } else {
                    // Two consecutive numbers have been found
                    couldBeRange = true;
                }
            } else {
                if (isRange) {
                    // Append the end of the previous range
                    s += "-" + String.valueOf(prev);
                    isRange = false;
                    couldBeRange = false;
                } else if (couldBeRange) {
                    // Append the element that could have been a range but wasn't
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
