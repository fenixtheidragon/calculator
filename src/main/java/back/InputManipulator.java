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

    public String getVariablesValueOrUnknownVariableException(String name) {
        if (variables.containsKey(name)) {
            return String.valueOf(variables.get(name));
        } else {
            return "Unknown variable";
        }
    }

    public void setInput(String input) {
        parsedInput = new ArrayList<>();
        String[] numbersAndSigns = input.splitWithDelimiters(RegexesAndCommands.POTENTIAL_MINUS_AND_NUMBER_OR_VARIABLE.getResult(), -1);
        for (String element : numbersAndSigns) {
            element = element.trim();
            if (element.matches(RegexesAndCommands.POTENTIAL_MINUS_AND_NUMBER_OR_VARIABLE.getResult())) {
                if (element.matches("-" + RegexesAndCommands.NUMBER_OR_VARIABLE.getResult()) && !parsedInput.isEmpty() &&
                    !parsedInput.get(0).matches("[a-zA-Z]+") && !parsedInput.getLast().matches("[-+*/(]")) {
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
            if (element.matches("-?" + RegexesAndCommands.NUMBER.getResult())) {
                stack.push(Double.parseDouble(element));
            } else if (element.matches("-?" + RegexesAndCommands.VARIABLE.getResult())) {
                if (variables.containsKey(element)) {
                    stack.push(variables.get(element));
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
        String value;
        if (parsedInput.size() == 1) {
            if (variables.containsKey(variable)) {
                return String.valueOf(variables.get(variable));
            } else {
                return "Unknown variable";
            }
        } else if (parsedInput.size() == 3) {
            value = parsedInput.get(2);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 2; i < parsedInput.size(); i++) {
                stringBuilder.append(parsedInput.get(i));
            }
            value = stringBuilder.toString();
        }
        double valueDouble;
        if (value.matches("-?" + RegexesAndCommands.NUMBER.getResult())) {
            valueDouble = Double.parseDouble(value);
        } else if (value.matches("-?" + RegexesAndCommands.VARIABLE.getResult())) {
            return this.getVariablesValueOrUnknownVariableException(value);
        } else if (value.matches(RegexesAndCommands.EXPRESSION.getResult())) {
            InputManipulator inputManipulator = new InputManipulator();
            inputManipulator.setInput(value);
            String valueDoubleStr = inputManipulator.sumOfInput();
            if (valueDoubleStr.matches(RegexesAndCommands.NUMBER.getResult())) {
                valueDouble = Double.parseDouble(valueDoubleStr);
            } else {
                return valueDoubleStr;
            }
        } else {
            return "Invalid expression";
        }
        if (this.variables.containsKey(variable)) {
            if (variables.containsKey(value)) {
                this.variables.replace(variable, this.variables.get(value));
                return "Variable's value changed with another variable's value";
            } else {
                this.variables.replace(variable, valueDouble);
                return "Variable's value changed";
            }
        } else {
            if (value.matches("[a-zA-Z]+")) {
                return "Unknown variable";
            } else {
                this.variables.put(variable, valueDouble);
                return "Variable created";
            }
        }
    }
}
