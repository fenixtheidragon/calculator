package back;

public class InputToOutputMatcher {
    private final Parser parser;
    private final Calculator calculator;
    private String input;

    public InputToOutputMatcher() {
        this.parser = new Parser();
        this.calculator = new Calculator();
    }

    public String matchInput(String input) {
        this.input = parser.returnInputWithoutSpaces(input);
        if (input.isBlank()) {
            return "";
        } else if (input.equals("/help")||input.equals("/h")) {
            return Messages.HELP_MSG.getMessage();
        } else if (input.equals("/variables")||input.equals("/v")) {
            return calculator.getVariablesAsString();
        } else if (input.equals("/quit")) {
            return "Bye!";
        } else if (input.matches(Regexes.UNKNOWN_CMD.getRegex())) {
            return "Unknown command";
        } else if (input.matches(Regexes.EXPRESSION.getRegex())) {
            return calculator.calculateExpression(parser.parseExpression(input));
        } else if (input.matches(Regexes.EQUATION.getRegex())) {
            return this.matchInputWithEquation();
        } else {
            return Messages.INVALID_EXPRESSION_MSG.getMessage();
        }
    }

    private String matchInputWithEquation() {
        if (!input.matches(Regexes.VAR_EQUALS.getRegex())) {
            return "Invalid identifier";
        } else {
            if (input.matches(Regexes.VAR_EQUALS_EXP.getRegex())) {
                String[] variableAndValue = parser.parseEquation(input);
                variableAndValue[1] = calculator.calculateExpression(parser.parseExpression(variableAndValue[1]));
                return calculator.calculateEquation(variableAndValue);
            } else {
                return "invalid assignment";
            }
        }
    }
}

