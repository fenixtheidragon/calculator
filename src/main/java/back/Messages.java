package back;

import lombok.Getter;

@Getter
public enum Messages {
    HELP_MSG("""
            Calculator.
            Enter expression to calculate result.
            Decimals are allowed. Variables are allowed but must be composed with latin letters only.
            Commands:
            "/help" or "/h" - to show this message;
            "/variables or "/v" - to show list of variables;
            "/quit" - to quit calculator;"""),
    VARIABLES_MSG("""
            No variables yet.
            You can create one like this: "a=5".
            Instead of "a" you may use any lowercase or uppercase latin letters or combinations of them.
            Instead of "5" you may use any numbers or allowed expressions""");
    private final String result;
    Messages(String result) {
        this.result = result;
    }
}
