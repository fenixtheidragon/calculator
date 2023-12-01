package back;
public enum Messages {
    HELP_MSG("""
            Calculator.
            Enter expression to calculate result.
            Decimals are allowed. Variables are allowed but must be composed with latin letters only.
            Commands:
            "/help" or "/h" - to show this message;
            "/variables" or "/v" - to show list of variables;
            "/quit" - to quit calculator;"""),
    VARIABLES_MSG("""
            No variables yet.
            You can create one like this: "a=5".
            Instead of "a" you may use any lowercase or uppercase latin letters or combinations of them.
            Instead of "5" you may use any numbers or allowed expressions"""),
    INVALID_EXPRESSION_MSG("Invalid expression. Enter /help to receive help."),
    NUMBER_IS_TOO_LARGE_MSG("""
            Sorry, but resulting number is larger than 10^15.
            This calculator doesn't work with numbers
            larger than 1 000 000 000 000 000(10^15 or 1 quadrillion)"""),
    NUMBER_IS_TOO_SMALL_MSG("""
            Sorry, but resulting number is smaller than -10^15.
            This calculator doesn't work with numbers
            smaller than -1 000 000 000 000 000(-10^15 or -1 quadrillion)""");
    private final String message;
    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
