package numberrangesummarizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Unit tests for RangeSummarazer implementation.
 */
public class RangeSummarizerTest {
    /**
     * The RangeSummarize object has no internal state, so create static instance
     * since there
     * is no need to create and teardown every test.
     */
    private static RangeSummarizer rs = new RangeSummarizer();

    /**
     * It should parse a ordinary string correctly
     */
    @Test
    public void parseOrderedString() {
        String s = "0,1,2,3,4,5,6";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(7, arr.size());
        for (int i = 0; i < arr.size(); i++) {
            assertEquals(i, arr.get(i));
        }
    }

    /**
     * It should return an empty list when the string is empty
     */
    @Test
    public void parseEmpty() {
        String s = "";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(0, arr.size());
    }

    /**
     * It should parse a list of negative integers correctly
     */
    @Test
    public void parseNegative() {
        String s = "-4,-3,-2,-1";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(4, arr.size());
        for (int i = 0; i < arr.size(); i++) {
            assertEquals((i - 4), arr.get(i));
        }
    }

    /**
     * It should ignore the leading 0 in numbers
     */
    @Test
    public void parseLeadingZero() {
        String s = "-001, 0000, 001,02,003,";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(5, arr.size());
        for (int i = 0; i < arr.size(); i++) {
            assertEquals((i - 1), arr.get(i));
        }
    }

    /**
     * It should ignore non-numeric characters
     */
    @Test
    public void parseNonNumeric() {
        String s = "1a,2b,3,al4bet";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(4, arr.size());
        for (int i = 0; i < arr.size(); i++) {
            assertEquals((i + 1), arr.get(i));
        }
    }

    /**
     * It should ignore incorrectly placed negative signs
     */
    @Test
    public void parseNegativeSign() {
        String s = "--1,2-,3-4,2-3-4";
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(-1, 2, 34, 234));
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(4, arr.size());
        for (int i = 0; i < arr.size(); i++) {
            assertEquals(expected.get(i), arr.get(i));
        }
    }

    /**
     * If a negative sign is followed by no numbers it should also be ignored
     */
    @Test
    public void parseOnlyNegative() {
        String s = "-";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(0, arr.size());
    }

    /**
     * A single number should parse correctly
     */
    @Test
    public void parseOnlyNumber() {
        String s = "20";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(1, arr.size());
        assertEquals(20, arr.get(0));
    }

    /**
     * A single negative number should parse correctly
     */
    @Test
    public void parseOnlyNegativeNumber() {
        String s = "-20";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(1, arr.size());
        assertEquals(-20, arr.get(0));
    }

    /**
     * It should ignore commas with nothing or only non-numeric values between them
     */
    @Test
    public void parseEmptySections() {
        String s = ",";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(0, arr.size());

        s = ",,";
        arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(0, arr.size());

        s = "wait, this is, a sentence, not a sequence";
        arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(0, arr.size());
    }

    /**
     * It should parse unicode as it does other character, especially when that
     * unicode is a number
     */
    @Test
    public void parseUnicode() {
        String s = "\u0021";
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(0, arr.size());

        s = "\u1200,\u0030,";
        arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(1, arr.size());
        assertEquals(0, arr.get(0));

        s = "\u4785\u3902,\u0031\u0033";
        arr = new ArrayList<Integer>(rs.collect(s));
        assertEquals(1, arr.size());
        assertEquals(13, arr.get(0));
    }

    /**
     * It should return an empty array when the integer is null
     */
    @Test
    public void collectNullShouldBeEmpty() {
        ArrayList<Integer> arr = new ArrayList<Integer>(rs.collect(null));
        assertEquals(0, arr.size());
    }

    /**
     * It should return an empty array when the string is empty
     */
    @Test
    public void shouldReturnEmptyString() {
        ArrayList<Integer> arr = new ArrayList<>();
        String s = rs.summarizeCollection(arr);
        assertEquals("", s);
    }

    /**
     * It should convert a single element to a string correctly
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
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
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
     * Two consecutive numbers should not be grouped into a range. Test that this
     * works
     * when its in the beginning, middle, end, or isolated secion of the strin.
     */
    @Test
    public void shouldNotSequenceTwoConsecutiveNumbers() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(-2, -1, 1, 2, 4, 6, 8, 9));
        String s = rs.summarizeCollection(arr);
        assertEquals("-2, -1, 1, 2, 4, 6, 8, 9", s);

        arr = new ArrayList<>(Arrays.asList(1, 2));
        s = rs.summarizeCollection(arr);
        assertEquals("1, 2", s);
    }

    /**
     * It should still detect ranges when string is out of order
     */
    @Test
    public void shouldReturnSequencesString() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(25, 4, 5, 6, 23, 3, -5, -6, -7, 10));
        String s = rs.summarizeCollection(arr);
        assertEquals("25, 4-6, 23, 3, -5, -6, -7, 10", s);
    }

    /**
     * Duplicates are treated as distinct, and should therefore not be summarized if they are
     * concatenated
     */
    @Test
    public void shouldNotSummarizeDuplicates() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1, 1, 1, 2, 2, 3));
        String s = rs.summarizeCollection(arr);
        assertEquals("1, 1, 1, 2, 2, 3", s);
    }

    /**
     * It should work with any collection
     */
    @Test
    public void shouldReturnSequencesForAnyCollection() {
        LinkedList<Integer> ll = new LinkedList<>(Arrays.asList(25, 4, 5, 6, 23, 3, -5, -6, -7, 10));
        String s = rs.summarizeCollection(ll);
        assertEquals("25, 4-6, 23, 3, -5, -6, -7, 10", s);

        // A TreeSet is naturally sorted, so expect sorted ranges
        TreeSet<Integer> set = new TreeSet<>(Arrays.asList(25, 4, 5, 6, 23, 3, -5, -6, -7, 10));
        s = rs.summarizeCollection(set);
        assertEquals("-7--5, 3-6, 10, 23, 25", s);
    }

    /**
     * It should return the empty string when the collecction is null
     */
    @Test
    public void summarizetShouldBeEmpty() {
        String s = rs.summarizeCollection(null);
        assertEquals("", s);
    }
}
