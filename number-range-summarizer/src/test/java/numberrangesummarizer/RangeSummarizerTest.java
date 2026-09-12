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
      assertEquals(7, arr.size());
      for (int i =0; i < arr.size(); i++) {
        assertEquals(i, arr.get(i));
      }
    }

    /**
     * Should return an empty list when the string is empty
     */
    @Test
    public void parseEmpty() {
      String s = "";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(0, arr.size());
    }

    /**
     * Should parse a list of negative integers correctly
     */
    @Test
    public void parseNegative() {
      String s = "-4,-3,-2,-1";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(4, arr.size());
      for (int i =0; i < arr.size(); i++) {
        assertEquals((i-4), arr.get(i));
      }
    }

    /**
     * Should ignore the leading 0 in numbers
     */
    @Test
    public void parseLeadingZero() {
      String s = "-001, 0000, 001,02,003,";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(5, arr.size());
      for (int i = 0 ; i < arr.size(); i++) {
        assertEquals((i-1), arr.get(i));
      }
    }

    /**
     * Should ignore non-numeric characters
     */
    @Test
    public void parseNonNumeric() {
      String s = "1a,2b,3,al4bet";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(4, arr.size());
      for (int i =0; i < arr.size(); i++) {
        assertEquals((i+1), arr.get(i));
      }
    }

    /**
     * Should ignore incorrectly placed negative signs
     * 
     * Note it does not do arithmetic
     */
    @Test
    public void parseNegativeSign() {
      String s = "--1,2-,3-4,2-3-4";
      ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(-1, 2, 34, 234));
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(4, arr.size());
      for (int i =0; i < arr.size(); i++) {
        assertEquals(expected.get(i), arr.get(i));
      }
    }

    /**
     * If a negative sign is followed by no numbers, it should also be ignored
     */
    @Test
    public void parseOnlyNegative() {
      String s = "-";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(0, arr.size());
    }

    /**
     * A single number should parse correctly
     */
    @Test
    public void parseOnlyNumber() {
      String s = "20";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(1, arr.size());
      assertEquals(20, arr.get(0));
    }

    /**
     * A single number should parse correctly
     */
    @Test
    public void parseOnlyNegativeNumber() {
      String s = "-20";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(1, arr.size());
      assertEquals(-20, arr.get(0));
    }

    /**
     * Should ignore commas with nothing or only numbers between them
     * 
     * For this test we combine a few different ways that we could get nothing
     */
    @Test
    public void parseEmptySections() {
      String s = ",";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(0, arr.size());

      s = ",,";
      arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(0, arr.size());

      s = "wait, this is, a sentence, not a sequence";
      arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(0, arr.size());
    }

    /**
     * Should unicode as it does other character, especiall when that unicode is a number
     */
    @Test
    public void parseUnicode() {
      String s = "\u0021";
      ArrayList<Integer> arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(0, arr.size());

      s = "\u1200,\u0030,";
      arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(1, arr.size());
      assertEquals(0, arr.get(0));

      s = "\u4785\u3902,\u0031\u0033";
      arr = (ArrayList<Integer>) rs.collect(s);
      assertEquals(1, arr.size());
      assertEquals(13, arr.get(0));
    }
  

    /**
     * Test a set of conventional string
     */
    @Test
    public void shouldHaveCorrectString() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1, 2, 4, 6, 8, 9, 12, 14, 18, 20, 21, 22));
        String s = rs.summarizeCollection(arr);
        assertEquals("1-2, 4, 6, 8-9, 12, 14, 18, 20-22", s);
    }

    /**
     * An empty array should return the empty string
     */
    @Test
    public void shouldReturnEmptyString() {
        ArrayList<Integer> arr = new ArrayList<>();
        String s = rs.summarizeCollection(arr);
        assertEquals("", s);
    }

    /**
     * Functions correctly with a single elemebt
     */
    @Test
    public void shouldReturnSingleNumber() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1));
        String s = rs.summarizeCollection(arr);
        assertEquals("1", s);
    }
    
    /**
     * Functions correctly with a complete range
     */
    @Test
    public void shouldReturnSingleRange() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        String s = rs.summarizeCollection(arr);
        assertEquals("1-10", s);
    }

    /**
     * Functions correctly with negative ranges
     */
    @Test
    public void shouldReturnNegativeString() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(0, -1, -2, -3, -4, 4));
        String s = rs.summarizeCollection(arr);
        assertEquals("0, -1, -2, -3, -4, 4", s);

        arr = new ArrayList<>(Arrays.asList(-4, -3, -2, -1));
        s = rs.summarizeCollection(arr);
        assertEquals("-4--1", s);
    }

    /**
     * Should still detect ranges when string is out of order
     */
    @Test
    public void shouldReturnSequencesString() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(25,4,5,6,23,3,-5,-6,-7,10));
        String s = rs.summarizeCollection(arr);
        assertEquals("25, 4-6, 23, 3, -5, -6, -7, 10", s);
    }


}
