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

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldHaveCorrectString() {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1, 2, 4, 6, 8, 9, 12, 14, 18, 20, 21, 22));
        RangeSummarizer rs = new RangeSummarizer();
        String s = rs.summarizeCollection(arr);
        assertEquals("1-2,4,6,8-9,12,14,18,20-22", s);
    }
}
