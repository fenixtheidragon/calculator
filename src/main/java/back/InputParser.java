package back;

public class InputParser {
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
            return RegexesAndCommands.HELP.getResult();
        } else if (input.matches("/variables")) {
            return inputManipulator.getVariablesAsString();
        } else if (input.matches("/exit")) {
            return "Bye!";
        } else if (input.matches(RegexesAndCommands.UNKNOWN_COMMAND.getResult())) {
            return "Unknown command";
        } else if (input.matches("-?" + RegexesAndCommands.NUMBER.getResult())) {
            return input;
        } else if (input.matches(RegexesAndCommands.VARIABLE.getResult())) {
            return this.inputManipulator.getVariablesValueOrUnknownVariableException(input);
        } else if (input.matches(RegexesAndCommands.EXPRESSION.getResult())) {
            return this.inputManipulator.sumOfInput();
        } else if (input.matches(RegexesAndCommands.VARIABLE_EQUALS.getResult())) {
            return this.parseInputWithVariables();
        } else {
            return "Invalid expression. Enter /help to receive help.";
        }
    }

    private String parseInputWithVariables() {
        if (input.matches(RegexesAndCommands.INVALID_IDENTIFIER.getResult())) {
            return "Invalid identifier";
        } else {
            if (input.matches(RegexesAndCommands.VARIABLE_EQUALS_NUMBER_OR_ANOTHER_VARIABLE.getResult()) ||
                input.matches(RegexesAndCommands.VARIABLE_EQUALS_EXPRESSION.getResult())) {
                return this.inputManipulator.manipulateInputWithVariables();
            } else {
                return "invalid assignment";
            }
        }
    }
}

