package back;

import java.util.ArrayList;

public class Parser {

    public String parseInput(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] characters = input.split("");
        for (String character: characters) {
            if (!character.isBlank()) {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }

    public ArrayList<String> parseExpression(String expression) {
        ArrayList<String> parsedInput = new ArrayList<>();
        String[] numbersVariablesAndSigns = expression.splitWithDelimiters("-?((\\d+(\\.\\d+)?)|([a-zA-Z]+))", -1);
        for (String element : numbersVariablesAndSigns) {
            element = element.trim();
            if (element.isBlank()) {
                continue;
            }
            if (element.matches("(\\d+(\\.\\d+)?)|([a-zA-Z]+)")) {
                parsedInput.add(element);
            } else if (element.matches("-((\\d+(\\.\\d+)?)|([a-zA-Z]+))")) {
                if (parsedInput.size() > 1 && !parsedInput.getLast().matches("(\\d+(\\.\\d+)?)|([a-zA-Z]+)|(\\))")) {
                    parsedInput.add("0");
                }
                parsedInput.add(element.substring(0, 1));
                parsedInput.add(element.substring(1));
            } else {
                String[] signs = element.split("");
                for (String sign : signs) {
                    sign = sign.trim();
                    if (!sign.isBlank()) {
                        parsedInput.add(sign);
                    }
                }
            }
        }

        return parsedInput;
    }

    public String[] parseEquation(String equation) {
        return equation.split("\\s*=\\s*");
    }
}