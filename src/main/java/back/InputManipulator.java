package back;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class InputManipulator {
    private ArrayList<String> parsedInput;
    private Map<String, Double> variables;

    public InputManipulator() {
        this.variables = new HashMap<>();
    }

    public void setInput(String input) {
        this.parsedInput = new ArrayList<>();
        String[] doublesAndEverythingElse = input.splitWithDelimiters("-?\\d+(\\.\\d+)?", 0);
        for (String element : doublesAndEverythingElse) {
            element = element.trim();
            if (!element.matches("-?\\d+(\\.\\d+)?")) {
                String[] helper = element.split("");
                for (String str : helper) {
                    str = str.trim();
                    if (!str.isBlank()) {
                        this.parsedInput.add(str);
                    }
                }
            } else {
                if (element.matches("-\\d+(\\.\\d+)?") && !this.parsedInput.isEmpty()) {
                    this.parsedInput.add("+");
                }
                this.parsedInput.add(element);
            }
        }
        this.parsedInput = this.parsedInput.stream().filter(a -> !a.isBlank()).collect(Collectors.toCollection(ArrayList::new));
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
        if (value.matches("-?\\d+(\\.\\d+)?")) {
            double valueInt = Double.parseDouble(value);
            if (this.variables.containsKey(variable)) {
                this.variables.replace(variable, valueInt);
                return "Variable's value changed";
            }
            this.variables.put(variable, valueInt);
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
