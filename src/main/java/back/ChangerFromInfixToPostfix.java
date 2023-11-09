package back;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class ChangerFromInfixToPostfix {
    private final ArrayList<String> parsedInput;
    private final ArrayBlockingQueue<String> outputQueue;
    private final ArrayDeque<String> operatorStack;

    public ChangerFromInfixToPostfix(ArrayList<String> parsedInput) {
        this.parsedInput = parsedInput;
        this.outputQueue = new ArrayBlockingQueue<>(parsedInput.size());
        this.operatorStack = new ArrayDeque<>();
    }

    public ArrayBlockingQueue<String> fromInfixToPostfix() {
        for (String incomingElement : parsedInput) {
            railroadAlgorithm(incomingElement);
        }
        outputQueue.addAll(operatorStack);
        return outputQueue;
    }

    private void railroadAlgorithm(String incomingElement) {
        if (incomingElement.matches("[-+*/()]")) {
            String top = operatorStack.peek();
            if (top == null || top.matches("\\(") || incomingElement.matches("\\(") || (top.matches("[+-]") && incomingElement.matches("[*/]"))) {
                if (top != null && (top.matches("\\(") && incomingElement.matches("\\)"))) {
                    operatorStack.pop();
                    return;
                }
                operatorStack.push(incomingElement);
            } else if ((top.matches("[-+]") && incomingElement.matches("[-+]")) || (top.matches("[*/]") && (incomingElement.matches("[-+*/]"))) || incomingElement.matches("\\)")) {
                outputQueue.add(operatorStack.pop());
                railroadAlgorithm(incomingElement);
            }
        } else {
            outputQueue.add(incomingElement);
        }

    }
}
