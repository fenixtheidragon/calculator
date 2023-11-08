package back;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class InputManipulator {
    private ArrayList<String> parsedInput;
    private Map<String, Integer> variables;

    public InputManipulator() {
        this.variables = new HashMap<>();
        this.parsedInput = new ArrayList<>();
    }

    public InputManipulator(String input) {
        this.variables = new HashMap<>();
        this.setInput(input);
    }

    public void setInput(String input) {
        this.parsedInput = new ArrayList<>();
        String[] parsedInput = input.trim().split("\\b|\\s");
        for (int a = 0; a < parsedInput.length; a++) {
            parsedInput[a] = parsedInput[a].trim();
            if (!parsedInput[a].matches("-?(\\d|[a-zA-Z])+")) {
                String[] helper = parsedInput[a].split("\\B|\\b");
                System.out.println("helper");
                for (String str : helper) {
                    System.out.print(str);
                    str.trim();
                    this.parsedInput.add(str);
                }
            } else {
                this.parsedInput.add(parsedInput[a]);
            }
        }
        this.parsedInput = this.parsedInput.stream().filter(a -> !a.isBlank()).collect(Collectors.toCollection(ArrayList::new));
        this.parsedInput.forEach(a -> System.out.print(a + ","));
    }

    public String sumOfInput() {
        ChangerFromInfixToPostfix changer = new ChangerFromInfixToPostfix(parsedInput);
        Deque<Integer> stack = new ArrayDeque<>();
        ArrayBlockingQueue<String> queue = changer.fromInfixToPostfix();
        //queue.forEach(a-> System.out.print(a));
        System.out.println();
        for (String element : queue) {
            if (element.matches("-?\\d+")) {
                stack.push(Integer.valueOf(element));
            } else if (element.matches("-?[a-zA-Z]+")) {
                if (this.variables.containsKey(element)) {
                    stack.push(this.variables.get(element));
                } else {
                    return "Unknown variable" + "||" + parsedInput;
                }
            } else if (element.matches("[-+*/]")) {
                int firstOperand;
                int secondOperand = stack.pop();
                if (element.matches("-") && stack.size() == 1) {
                    firstOperand = 0;
                } else {
                    firstOperand = stack.pop();
                }
                int result = 0;
                switch (element) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        result = firstOperand / secondOperand;
                        break;
                }
                stack.push(result);
            } else {
                return "oops";
            }
        }
        return String.valueOf(stack.pop());
    }

    public String manipulateInputWithVariables() {
        String variable = parsedInput.get(0);
        System.out.println(variable);
        if (parsedInput.size() == 1) {
            if (this.variables.containsKey(variable)) {
                return String.valueOf(this.variables.get(variable));
            } else {
                return "Unknown variable - mIWV";
            }
        }
        String value = parsedInput.get(2);
        if (value.matches("-?\\d+")) {
            int valueInt = Integer.parseInt(value);
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
