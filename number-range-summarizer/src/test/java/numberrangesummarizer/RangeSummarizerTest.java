package numberrangesummarizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class RangeSummarizerTest {

    // rs has no internal state, so no reason to initialise it every time
    private static RangeSummarizer rs = new RangeSummarizer();

    /**
     * Parses a basic string correctly
     */
    @Test
    public void parseOrderedString() {
      String s = "0,1,2,3,4,5,6";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      for (int i =0; i < arr.size(); i++) {
        assertEquals(i, arr.get(i));
      }
    }

    /**
     * Test a set of conventional string
     */
    @Test
    public void shouldHaveCorrectString() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1, 2, 4, 6, 8, 9, 12, 14, 18, 20, 21, 22));
        String s = rs.summarizeCollection(arr);
        assertEquals("1-2,4,6,8-9,12,14,18,20-22", s);
    }

}
