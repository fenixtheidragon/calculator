package back;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class InputManipulator {
    private final Map<String, Double> variables;
    private ArrayList<String> parsedInput;

    public InputManipulator() {
        variables = new HashMap<>();
    }

    public String getVariablesAsString() {
        return variables.toString();
    }

    public void setInput(String input) {
        parsedInput = new ArrayList<>();
        String[] numbersAndSigns = input.splitWithDelimiters("-?\\d+(\\.\\d+)?", 0);
        for (String element : numbersAndSigns) {
            element = element.trim();
            if (element.matches("-?\\d+(\\.\\d+)?")) {
                if (element.matches("-\\d+(\\.\\d+)?") && !parsedInput.isEmpty() && !parsedInput.get(0).matches("[a-zA-Z]+")) {
                    parsedInput.add("+");
                }
                parsedInput.add(element);
            } else {
                String[] helper = element.split("");
                for (String str : helper) {
                    str = str.trim();
                    if (!str.isBlank()) {
                        parsedInput.add(str);
                    }
                }
            }
        }
    }

    public String sumOfInput() {
        ChangerFromInfixToPostfix changer = new ChangerFromInfixToPostfix(parsedInput);
        Deque<Double> stack = new ArrayDeque<>();
        ArrayBlockingQueue<String> queue = changer.fromInfixToPostfix();
        if (queue.contains("(") || queue.contains(")")) {
            return "Invalid expression";
        }
        for (String element : queue) {
            if (element.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.valueOf(element));
            } else if (element.matches("-?[a-zA-Z]+")) {
                if (this.variables.containsKey(element)) {
                    stack.push(this.variables.get(element));
                } else {
                    return "Unknown variable || " + parsedInput;
                }
            } else if (element.matches("[-+*/]")) {
                double firstOperand;
                double secondOperand = stack.pop();
                if (element.matches("-") && stack.isEmpty()) {
                    firstOperand = 0;
                } else {
                    firstOperand = stack.pop();
                }
                double result = switch (element) {
                    case "+" -> firstOperand + secondOperand;
                    case "-" -> firstOperand - secondOperand;
                    case "*" -> firstOperand * secondOperand;
                    case "/" -> firstOperand / secondOperand;
                    default -> 0;
                };
                stack.push(result);
            } else {
                return "oops";
            }
        }
        return String.valueOf(stack.pop());
    }

    public String manipulateInputWithVariables() {
        String variable = parsedInput.get(0);
        if (parsedInput.size() == 1 && this.variables.containsKey(variable)) {
            return String.valueOf(this.variables.get(variable));
        }
        String value = parsedInput.get(2);
        System.out.println(value);
        if (value.matches("-?\\d+(\\.\\d+)?")) {
            double valueDouble = Double.parseDouble(value);
            if (this.variables.containsKey(variable)) {
                this.variables.replace(variable, valueDouble);
                return "Variable's value changed";
            }
            this.variables.put(variable, valueDouble);
            return "Variable created";
        } else {
            if (this.variables.containsKey(value)) {
                this.variables.replace(variable, this.variables.get(value));
                return "Variable's value changed with another variable's value";
            } else {
                return "Unknown variable";
            }
        }
    }
}
