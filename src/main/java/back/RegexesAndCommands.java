package back;

public enum RegexesAndCommands {
    HELP("""
            Calculator.
            Enter expression to calculate result.
            Decimals are allowed. Variables are allowed but must be composed with latin letters only.
            Commands:
            /help - to show this message;
            /variables - to show list of variables;
            /exit - to quit calculator;"""),
    NUMBER("\\d+(\\.\\d+)?"),
    VARIABLE("[a-zA-Z]+"),
    NUMBER_OR_VARIABLE("(" + NUMBER.getResult() + "|" + VARIABLE.getResult() + ")"),
    POTENTIAL_MINUS_AND_NUMBER_OR_VARIABLE("-?" + NUMBER_OR_VARIABLE.getResult()),
    POTENTIAL_SPACES_AND_OPERATORS("\\s*[-+*/]\\s*"),
    POTENTIAL_SPACES_AND_EQUALITY("\\s*=\\s*"),
    EXPRESSION("-?\\(*" + POTENTIAL_MINUS_AND_NUMBER_OR_VARIABLE.getResult() + "\\)*" +
               "(" + POTENTIAL_SPACES_AND_OPERATORS.getResult() + "\\(*" + POTENTIAL_MINUS_AND_NUMBER_OR_VARIABLE.getResult() + "\\)*)*"),
    UNKNOWN_COMMAND("/.*"),
    VARIABLE_EQUALS(".*" + VARIABLE.getResult() + ".*=.*"),
    VARIABLE_EQUALS_NUMBER_OR_ANOTHER_VARIABLE(VARIABLE.getResult() +
                                               "(" + POTENTIAL_SPACES_AND_EQUALITY.getResult() + "\\(?" + NUMBER_OR_VARIABLE.getResult() + "\\)?)*"),
    VARIABLE_EQUALS_EXPRESSION(VARIABLE.getResult() + POTENTIAL_SPACES_AND_EQUALITY.getResult() + EXPRESSION.getResult()),
    INVALID_IDENTIFIER(".*((([^a-zA-Z\\-\\+\\/\\* ]+)([a-zA-Z]+))|(([a-zA-Z]+)([^a-zA-Z\\-\\+\\/\\* ]+))).*=.*");
    private final String result;

    RegexesAndCommands(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
