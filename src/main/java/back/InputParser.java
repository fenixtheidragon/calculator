package back;

public class InputParser {
    private String input;
    private InputManipulator inputManipulator;

    public void setInput(String input) {
        this.input = input;
    }

    public void setInputManipulator(){
        this.inputManipulator = new InputManipulator();
    }

    public InputManipulator getInputManipulator() {
        return this.inputManipulator;
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
        } else if (input.matches("\\d+")) {
            return input;
        } else if (input.matches("\\(?-?(\\d|[a-zA-Z])+(\\s*[-+*/]\\s*\\(?-?(\\d|[a-zA-Z])+\\)?)*")) {
            return this.inputManipulator.sumOfInput();
        } else if (input.matches("\\w+.*")) {
            return this.parseInputWithVariables();
        } else if (input.matches(".*")) {
            return "Invalid expression";
        } else {
            return "sorry";
        }
    }

    private String parseInputWithVariables() {
        if (!input.matches("[a-zA-Z]+.*")) {
            return "Invalid identifier";
        } else {
            if (input.matches("[a-zA-Z]+(\\s*=\\s*-?(\\d|[a-zA-Z])+)*")) {
                return this.inputManipulator.manipulateInputWithVariables();
            } else {
                return "invalid assignment";
            }
        }
    }
}

