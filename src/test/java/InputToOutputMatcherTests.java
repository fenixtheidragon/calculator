import back.InputToOutputMatcher;
import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputToOutputMatcherTests {

    private String matchInputToOutputAndGetResult(String input) {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        iToOMatcher.setInput(input);
        return iToOMatcher.matchInput();
    }

    @Test
    void testSpaceInput() {
        String result = matchInputToOutputAndGetResult(" ");
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    void testNewLineInput() {
        String result = matchInputToOutputAndGetResult("\n");
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    void testTabInput() {
        String result = matchInputToOutputAndGetResult("\t");
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    void testCarriageReturnInput() {
        String result = matchInputToOutputAndGetResult("\r");
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    void testFormFeedInput() {
        String result = matchInputToOutputAndGetResult("\f");
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    void testSingleDigitInput() {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        for (int d = -9; d < 9; d++) {
            iToOMatcher.setInput(String.valueOf(d));
            String result = iToOMatcher.matchInput();
            String expected = String.valueOf(d);
            assertEquals(expected, result);
        }
    }

    @Test
    void testDoubleDigitInput() {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        for (int d = -9; d <= 9; d++) {
            for (int sd = 0; sd <= 9; sd++) {
                iToOMatcher.setInput(d + "." + sd);
                String result = iToOMatcher.matchInput();
                String expected = d + "." + sd;
                assertEquals(expected, result);
            }
        }
    }

    @Test
    void testSingleLetterLowerCaseInput() {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        for (char d = 'a'; d <= 'z'; d++) {
            iToOMatcher.setInput(d + "=1");
            iToOMatcher.matchInput();
            iToOMatcher.setInput(d + "");
            String result = iToOMatcher.matchInput();
            String expected = "1.0";
            assertEquals(expected, result);
        }
    }

    @Test
    void testExpressionInput() {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        ArrayList<String> results = new ArrayList<>();
        ArrayList<String> expected = new ArrayList<>();
        int num1 = 1;
        int num2 = 1;
        iToOMatcher.setInput(num1 + "+" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("2");
        iToOMatcher.setInput(num1 + "-" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("0");
        iToOMatcher.setInput(num1 + "*" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("1");
        iToOMatcher.setInput(num1 + "/" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("1");
        num1 = 2;
        num2 = 2;
        iToOMatcher.setInput(num1 + "+" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("4");
        iToOMatcher.setInput(num1 + "-" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("0");
        iToOMatcher.setInput(num1 + "*" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("4");
        iToOMatcher.setInput(num1 + "/" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("1");
        num1 = -1;
        num2 = 2;
        iToOMatcher.setInput(num1 + "+" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("1");
        iToOMatcher.setInput(num1 + "-" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("-3");
        iToOMatcher.setInput(num1 + "*" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("-2");
        iToOMatcher.setInput(num1 + "/" + num2);
        results.add(iToOMatcher.matchInput());
        expected.add("-0.5");

        assertEquals(expected, results);
    }
}
