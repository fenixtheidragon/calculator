package back;

public class InputParser {
    private final String HELP_MESSAGE = """
                                        Calculator.
                                        Enter expression to calculate result.
                                        Decimals are allowed. Variables are allowed but must be composed with latin letters only.
                                        Commands:
                                        /help - to show this message;
                                        /variables - to show list of variables;
                                        /exit - to quit calculator;""";
    private final InputManipulator inputManipulator;
    private String input;

    public InputParser() {
        this.inputManipulator = new InputManipulator();
    }

    public void setInput(String input) {
        this.input = input.trim();
        this.inputManipulator.setInput(this.input);
    }

    public String parseInput() {
        if (input.matches("\\s*")) {
            return "";
        } else if (input.matches("/help")) {
            return HELP_MESSAGE;
        } else if (input.matches("/variables")) {
            return inputManipulator.getVariablesAsString();
        } else if (input.matches("/exit")) {
            return "Bye!";
        } else if (input.matches("/.*")) {
            return "Unknown command. Enter /help to receive help.";
        } else if (input.matches("-?\\d+(\\.\\d+)?")) {
            return input;
        } else if (input.matches("\\(?(-?((\\d+(\\.\\d+)?)|[a-zA-Z]+))(\\s*[-+*/]\\s*\\(?(-?(\\d+(\\.\\d+)?)|[a-zA-Z]+)\\)?)+\\)?")) {
            return this.inputManipulator.sumOfInput();
        } else if (input.matches("[a-zA-Z]+.*")) {
            return this.parseInputWithVariables();
        } else {
            return "Invalid expression. Enter /help to receive help.";
        }
    }

    private String parseInputWithVariables() {
        if (!input.matches("[a-zA-Z]+.*")) {
            return "Invalid identifier";
        } else {
            if (input.matches("[a-zA-Z]+(\\s*=\\s*-?((\\d\\.)?\\d|[a-zA-Z])+)*")) {
                return this.inputManipulator.manipulateInputWithVariables();
            } else {
                return "invalid assignment";
            }
        }
    }
}

