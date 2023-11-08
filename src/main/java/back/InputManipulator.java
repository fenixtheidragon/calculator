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
        String[] parsedInput = input.split("\\b|\\s");
        for (String element : parsedInput) {
            element = element.trim();
            if (!element.matches("-?(\\d|[a-zA-Z])+")) {
                String[] helper = element.split("");
                //System.out.println("helper");//for debug
                for (String str : helper) {
                    // System.out.print(str);//for debug
                    str = str.trim();
                    this.parsedInput.add(str);
                }
            } else {
                this.parsedInput.add(element);
            }
        }
        this.parsedInput = this.parsedInput.stream().filter(a -> !a.isBlank()).collect(Collectors.toCollection(ArrayList::new));
        //this.parsedInput.forEach(a -> System.out.print(a + ","));//for debug
    }

    public String sumOfInput() {
        ChangerFromInfixToPostfix changer = new ChangerFromInfixToPostfix(parsedInput);
        Deque<Double> stack = new ArrayDeque<>();
        ArrayBlockingQueue<String> queue = changer.fromInfixToPostfix();
        if (queue.contains("(") || queue.contains(")")) {
            return "Invalid expression";
        }
        //queue.forEach(a-> System.out.print(a));//for debug
        System.out.println();
        for (String element : queue) {
            if (element.matches("-?\\d+")) {
                stack.push(Double.valueOf(element));
            } else if (element.matches("-?[a-zA-Z]+")) {
                if (this.variables.containsKey(element)) {
                    stack.push(this.variables.get(element));
                } else {
                    return "Unknown variable" + "||" + parsedInput;
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
            } else if (element.matches("\\.")) {

            } else {
                return "oops";
            }
        }
        return String.valueOf(stack.pop());
    }

    public String manipulateInputWithVariables() {
        String variable = parsedInput.get(0);
        //System.out.println(variable);//for debug
        if (parsedInput.size() == 1 && this.variables.containsKey(variable)) {
            //if (this.variables.containsKey(variable)) {
            return String.valueOf(this.variables.get(variable));
           /* } else {
                return "Unknown variable - mIWV";//for debug
            }*/
        }
        String value = parsedInput.get(2);
        if (value.matches("-?\\d+")) {
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
