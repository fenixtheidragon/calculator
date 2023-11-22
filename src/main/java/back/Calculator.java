package back;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Calculator {
    private final Map<String, Double> variables;

    public Calculator() {
        variables = new HashMap<>();
    }

    public String getVariablesAsString() {
        if (variables.isEmpty()) {
            return Messages.VARIABLES_MSG.getResult();
        }
        return variables.toString();
    }

    public String getValueOfVariableOrUnknownVariableException(String name) {
        if (variables.containsKey(name)) {
            return String.valueOf(variables.get(name));
        } else {
            return "Unknown variable";
        }
    }

    public String calculateExpression(ArrayList<String> parsedExpression) {
        InfixToPostfixConverter converter = new InfixToPostfixConverter(parsedExpression);
        Deque<Double> stack = new ArrayDeque<>();
        ArrayBlockingQueue<String> queue = converter.convert();
        if (queue.contains("(") || queue.contains(")")) {
            return "Invalid expression";
        }
        for (String element : queue) {
            if (element.matches("-?" + Regexes.NUMBER.getRegex())) {
                stack.push(Double.parseDouble(element));
            } else if (element.matches("-?" + Regexes.VARIABLE.getRegex())) {
                if (variables.containsKey(element)) {
                    stack.push(variables.get(element));
                } else {
                    return "Unknown variable " + element;
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
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(10);
        return df.format(stack.pop());
    }

    private String addVariable(String variable, Double value) {
        if (variables.containsKey(variable)) {
            if (variables.containsKey(String.valueOf(value))) {
                this.variables.replace(variable, this.variables.get(String.valueOf(value)));
                return "Variable " + variable + "'s value changed to variable " + variable + "'s value " + value;
            } else {
                this.variables.replace(variable, value);
                return "Variable " + variable + "'s value changed to " + value;
            }
        } else {
            this.variables.put(variable, value);
            return "Variable " + variable + " created with value " + value;
        }
    }

    public String calculateEquation(String[] variableAndValue) {
        String variable = variableAndValue[0];
        String value = variableAndValue[1];
        double valueDouble;
        if (value.matches("-?\\d+(\\.\\d+)?")) {
            valueDouble = Double.parseDouble(value);
        } else {
            boolean minus = false;
            if (value.matches("-[a-zA-Z]+")) {
                value = value.substring(1, value.length() - 1);
                minus = true;
            }
            value = getValueOfVariableOrUnknownVariableException(value);
            if (value.equals("Unknown variable")) {
                return "Unknown variable";
            } else {
                valueDouble = Double.parseDouble(value);
                if (minus) {
                    valueDouble *= -1;
                }
            }
        }
        return addVariable(variable, valueDouble);
    }
}
