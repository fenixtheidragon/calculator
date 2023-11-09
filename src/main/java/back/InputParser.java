package back;

public class InputParser {
    private String input;
    private InputManipulator inputManipulator;

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
            return "The program calculates the result of numbers";
        } else if (input.matches("/exit")) {
            return "Bye!";
        } else if (input.matches("/.*")) {
            return "Unknown command";
        } else if (input.matches("-?\\d+(\\.\\d+)?")) {
            return input;
        } else if (input.matches("\\(?(-?((\\d+(\\.\\d+)?)|[a-zA-Z]+))(\\s*[-+*/]\\s*\\(?(-?(\\d+(\\.\\d+)?)|[a-zA-Z]+)\\)?)+\\)?")) {
            return this.inputManipulator.sumOfInput();
        } else if (input.matches("[a-zA-Z]+.*")) {
            return this.parseInputWithVariables();
        } else {
            return "Invalid expression";
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

