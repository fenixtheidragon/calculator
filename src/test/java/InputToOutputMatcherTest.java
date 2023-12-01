import back.InputToOutputMatcher;
import back.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputToOutputMatcherTest {
    private final ExpressionGenerator expressionGenerator;
    private InputToOutputMatcher iToOMatcher;

    InputToOutputMatcherTest() {
        expressionGenerator = new ExpressionGenerator();
    }

    @BeforeEach
    void setUp() {
        iToOMatcher = new InputToOutputMatcher();
    }

    void createVariables(String[] variables) {
        setUp();
        for (String variable : variables) {
            iToOMatcher.matchInput(variable);
        }
    }

    String generateRandomExpression() {
        String expression = expressionGenerator.generateValidExpression(10);
        System.out.println(expression);
        return expression;
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\n", "\t", "\r", "\f"})
    void testEmptyInputs(String input) {
        String expected = "";
        String result = iToOMatcher.matchInput(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/NumbersOutOfBounds.csv")
    void testNumbersOutOfBounds(String given, String expected) {
        InputToOutputMatcher iToOMatcher = new InputToOutputMatcher();
        iToOMatcher.matchInput("a = 999999999999999");
        assertEquals(expected, iToOMatcher.matchInput(given));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Variables.csv")
    void testVariables(String given, String expected) {
        createVariables(new String[]{"a = 1", "b = 2", "c = -1"});
        assertEquals(expected, iToOMatcher.matchInput(given));
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/SimpleExpressions.csv", "/ExpressionsWithParentheses.csv", "/Commands.csv"})
    void testExpressions(String given, String expected) {
        assertEquals(expected, iToOMatcher.matchInput(given));
    }

    @RepeatedTest(100)
    void testExpressions() {
        createVariables(new String[]{"a=3", "b=5", "c=-7"});
        String result = iToOMatcher.matchInput(generateRandomExpression());
        System.out.println(result);
        assertTrue(result.matches("-?\\d+(\\.\\d+)?") || result.equals(Messages.NUMBER_IS_TOO_LARGE_MSG.getMessage()) ||
                   result.equals(Messages.NUMBER_IS_TOO_SMALL_MSG.getMessage()));
    }

    /*@ParameterizedTest
    @CsvFileSource(resources = {"/Equations.csv"})
    void testEquations(String given, String expected) {

    }*/
}
