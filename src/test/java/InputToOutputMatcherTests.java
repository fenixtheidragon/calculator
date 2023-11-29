import back.InputToOutputMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputToOutputMatcherTests {

    private String matchInputToOutputAndGetResult(String input) {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        iToOMatcher.setInput(input);
        return iToOMatcher.matchInput();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\n", "\t","\r","\f"})
    void testEmptyInputs(String input) {
        String expected = "";
        String result = matchInputToOutputAndGetResult(input);
        assertEquals(expected, result);
    }

    @Test
    void testExpressionInput() {
       String input = new StringGeneratorForCalculator().generateValidExpression(10);
        System.out.println(input);
        String result = matchInputToOutputAndGetResult(input);
        System.out.println(result);
       // assertEquals(expected, result);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/NumbersOutOfBounds.csv")
    void testNumbersOutOfBounds(String given, String expected) {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        iToOMatcher.setInput("a = 999999999999999");
        iToOMatcher.matchInput();
        iToOMatcher.setInput(given);
        assertEquals(expected,iToOMatcher.matchInput());
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/Variables.csv")
    void testVariables(String given, String expected) {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        iToOMatcher.setInput("a = 1");
        iToOMatcher.matchInput();
        iToOMatcher.setInput("b = 2");
        iToOMatcher.matchInput();
        iToOMatcher.setInput("c = a - b");
        iToOMatcher.matchInput();
        iToOMatcher.setInput(given);
        assertEquals(expected,iToOMatcher.matchInput());
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/SimpleExpressions.csv")
    void testSimpleExpressions(String given, String expected) {
        assertEquals(expected,matchInputToOutputAndGetResult(given));
    }
}
