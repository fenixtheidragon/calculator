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
        } else if (input.equals("/help")||input.equals("/h")) {
            return Messages.HELP_MSG.getResult();
        } else if (input.equals("/variables")||input.equals("/v")) {
            return calculator.getVariablesAsString();
        } else if (input.equals("/quit")) {
            return "Bye!";
        } else if (input.matches(Regexes.UNKNOWN_COMMAND.getRegex())) {
            return "Unknown command";
        } else if (input.matches(Regexes.NUMBER.getRegex())) {
            return input;
        } else if (input.matches(Regexes.VARIABLE.getRegex())) {
            return calculator.getValueOfVariableOrUnknownVariableException(input);
        } else if (input.matches(Regexes.EXPRESSION.getRegex()) && !input.matches(".*--.*")) {
            return calculator.calculateExpression(parser.parseExpression(input));
        } else if (input.matches(Regexes.EQUATION.getRegex())) {
            return this.matchInputWithEquation();
        } else {
            return "Invalid expression. Enter /help to receive help.";
        }
    }

    private String matchInputWithEquation() {
        if (!input.matches(Regexes.VARIABLE_EQUALS_SOMETHING.getRegex())) {
            return "Invalid identifier";
        } else {
            if (input.matches(Regexes.VARIABLE_EQUALS_EXPRESSION.getRegex())) {
                String[] variableAndExpression = input.split("=");
                String valueOfExpression = calculator.calculateExpression(parser.parseExpression(variableAndExpression[1]));
                return calculator.calculateEquation(new String[]{variableAndExpression[0], valueOfExpression});
            } else {
                return "invalid assignment";
            }
        }
    }
}

