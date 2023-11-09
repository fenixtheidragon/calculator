package back;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ChangerFromInfixToPostfix {
    private ArrayList<String> parsedInput;

    public ChangerFromInfixToPostfix(ArrayList<String> parsedInput) {
        this.parsedInput = parsedInput;
    }

    public ArrayBlockingQueue<String> fromInfixToPostfix() {
        ArrayBlockingQueue<String> outputQueue = new ArrayBlockingQueue<>(parsedInput.size());
        Deque<String> operatorStack = new ArrayDeque<>();
        for (String incomingElement : parsedInput) {
            railroadAlgorithm(outputQueue, operatorStack, incomingElement);
        }
        outputQueue.addAll(operatorStack);
        return outputQueue;
    }

    private void railroadAlgorithm(Queue<String> outputQueue, Deque<String> operatorStack, String incomingElement) {
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
                railroadAlgorithm(outputQueue, operatorStack, incomingElement);
            }
        } else {
            outputQueue.add(incomingElement);
        }

    }
}
