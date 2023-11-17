package back;

public class InputToOutputMatcher {
    private final Parser parser;
    private final Calculator calculator;
    private String input;

    public InputToOutputMatcher() {
        this.parser = new Parser();
        this.calculator = new Calculator();
    }

    public void setInput(String input) {
        this.input = parser.parseInput(input);
    }

    public String matchInput() {
        if (input.isBlank()) {
            return "";
        } else if (input.equals("/help")) {
            return RegexesAndCommands.HELP.getResult();
        } else if (input.equals("/variables")) {
            return calculator.getVariablesAsString();
        } else if (input.equals("/exit")) {
            return "Bye!";
        } else if (input.matches(RegexesAndCommands.UNKNOWN_COMMAND.getResult())) {
            return "Unknown command";
        } else if (input.matches("-?" + RegexesAndCommands.NUMBER.getResult())) {
            return input;
        } else if (input.matches(RegexesAndCommands.VARIABLE.getResult())) {
            return calculator.getValueOfVariableOrUnknownVariableException(input);
        } else if (input.matches(RegexesAndCommands.EXPRESSION.getResult())) {
            return calculator.calculateExpression(parser.parseExpression(input));
        } else if (input.matches(RegexesAndCommands.VARIABLE_EQUALS.getResult())) {
            return this.matchInputWithEquation();
        } else {
            return "Invalid expression. Enter /help to receive help.";
        }
    }

    private String matchInputWithEquation() {
        if (input.matches(RegexesAndCommands.INVALID_IDENTIFIER.getResult())) {
            return "Invalid identifier";
        } else {
            if (input.matches(RegexesAndCommands.VARIABLE_EQUALS_NUMBER_OR_ANOTHER_VARIABLE.getResult())) {
                return calculator.calculateEquation(parser.parseEquation(input));
            } else if (input.matches(RegexesAndCommands.VARIABLE_EQUALS_EXPRESSION.getResult())) {
                String[] variableAndExpression = parser.parseEquation(input);
                String value = calculator.calculateExpression(parser.parseExpression(variableAndExpression[1]));
                return calculator.calculateEquation(new String[]{variableAndExpression[0],value});
            } else {
                return "invalid assignment";
            }
        }
    }
}

