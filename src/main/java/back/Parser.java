package back;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Parser {

    public String returnInputWithoutSpaces(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] characters = input.split("");
        for (String character: characters) {
            if (!character.isBlank()) {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }

    public ConcurrentLinkedQueue<String> parseExpression(String expression) {
        ArrayList<String> parsedInput = new ArrayList<>();
        String[] numbersVariablesAndSigns = expression.splitWithDelimiters(Regexes.NUM_OR_VAR.getRegex(), -1);
        for (String element : numbersVariablesAndSigns) {
            element = element.trim();
            if (element.isBlank()) {
                continue;
            }
            if (element.matches(Regexes.NUM_OR_VAR.getRegex())) {
                parsedInput.add(element);
            }/* else if (element.matches("-"+Regexes.NUMBER_OR_VARIABLE.getRegex())) {
                if (parsedInput.size() > 1 && !parsedInput.getLast().matches(Regexes.NUMBER_OR_VARIABLE.getRegex()+"|(\\))")) {
                    parsedInput.add("0");
                }
                parsedInput.add(element.substring(0, 1));
                parsedInput.add(element.substring(1));
            }*/ else {
                String[] signs = element.split("");
                for (String sign : signs) {
                    sign = sign.trim();
                    if (!sign.isBlank()) {
                        parsedInput.add(sign);
                    }
                }
            }
        }
        return new InfixToPostfixConverter(parsedInput).convert();
    }
}