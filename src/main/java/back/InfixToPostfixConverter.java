package back;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InfixToPostfixConverter {
    private final ArrayList<String> parsedInput;
    private final ConcurrentLinkedQueue<String> outputQueue;
    private final ArrayDeque<String> operatorStack;
    private int currentElement;

    public InfixToPostfixConverter(ArrayList<String> parsedInput) {
        this.parsedInput = parsedInput;
        this.outputQueue = new ConcurrentLinkedQueue<>();
        this.operatorStack = new ArrayDeque<>();
        this.currentElement = 0;
    }

    public ConcurrentLinkedQueue<String> convert() {
        while (currentElement < parsedInput.size()) {
            railroadAlgorithm(parsedInput.get(currentElement));
            currentElement++;
        }
        outputQueue.addAll(operatorStack);
        return outputQueue;
    }

    private void railroadAlgorithm(String incomingElement) {
        if (incomingElement.matches("[-+*/^()]")) {
            //distinguishing unary "+" or "-" and converting them to binary
            if (incomingElement.matches("[-+]") && (currentElement == 0 || parsedInput.get(currentElement - 1).equals("("))) {
                outputQueue.add("0");
            }
            String top = operatorStack.peek();
            //if (top == null || top.equals("(") || incomingElement.equals("(") || (top.matches("[+-]") && incomingElement.matches("[*/]"))) {
            if (top == null || top.equals("(") || incomingElement.equals("(")
                || (!incomingElement.equals(")") && precedenceOf(top) < precedenceOf(incomingElement))) {
                if (top != null && (top.equals("(") && incomingElement.equals(")"))) {
                    operatorStack.pop();
                    return;
                }
                operatorStack.push(incomingElement);
                //} else if ((top.matches("[-+]") && incomingElement.matches("[-+]")) || (top.matches("[*/]") && (incomingElement.matches("[-+*/]"))) ||
                //           incomingElement.equals(")")) {
            } else /*if (incomingElement.equals(")") || precedenceOf(top) >= precedenceOf(incomingElement)) */{
                if (top.equals("^") && incomingElement.equals("^")) {
                    operatorStack.push(incomingElement);
                }
                outputQueue.add(operatorStack.pop());
                railroadAlgorithm(incomingElement);
            }
        } else {
            outputQueue.add(incomingElement);
        }
    }

    private byte precedenceOf(String operator) throws IllegalArgumentException {
        byte precedence = switch (operator) {
            case "(",")" -> 4;
            case "^" -> 3;
            case "*","/" -> 2;
            case "+","-" -> 1;
            default -> 0;
        };
        if (precedence == 0) {
            throw new IllegalArgumentException("Invalid operator");
        }
        return precedence;
    }

    private boolean isLeftAssociative(String operator) {
        return !operator.equals("^");
    }
}
