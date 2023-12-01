import back.Regexes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class ExpressionGenerator {
    private final Random random;
    private final char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final char[] operators = "+-*/^".toCharArray();

    public ExpressionGenerator() {
        this.random = new Random();
    }

    public String generateValidNumber() {
        double result = random.nextDouble() * random.nextInt(1, 1000);
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(3);
        return df.format(result);
    }

    public String generateValidVariable(int amountOfLetters) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (count < amountOfLetters) {
            count++;
            int letterNum = random.nextInt(51);
            sb.append(alphabet[letterNum]);
        }
        return sb.toString();
    }

    private String returnAOrBOrC() {
        int num = random.nextInt(3);
        return String.valueOf(alphabet[num]);
    }

    public String generateValidVariable() {
        return generateValidVariable(2);
    }

    public String generateValidExpression(int length) {
        int count = 0;
        int amountOfLeftBrackets = 0;
        int amountOfRightBrackets = 0;
        StringBuilder sb = new StringBuilder();
        String result = "";
        while (count < length) {
            int whatToGenerate = -1;
            if (result.matches(Regexes.NUMBER.getRegex()) || result.matches(Regexes.VARIABLE.getRegex()) || result.equals(")")) {
                whatToGenerate = 3;
                if (amountOfLeftBrackets > amountOfRightBrackets) {
                    whatToGenerate = random.nextInt(3,5);
                }
            } else if (result.matches("[-+*/(]")) {
                whatToGenerate = random.nextInt(3);
            } else if (result.isEmpty()){
                whatToGenerate = random.nextInt(4);
            } else {
                System.out.println("Something unexpected happened");
            }
            result = switch (whatToGenerate) {
                case 0 -> {
                    amountOfLeftBrackets++;
                    yield "(";
                }
                case 1 -> generateValidNumber();
                case 2 -> returnAOrBOrC();
                case 3 -> {
                    if (result.isEmpty()) {
                        yield "-";
                    }
                    yield String.valueOf(operators[random.nextInt(4)]);
                }
                case 4 -> {
                    amountOfRightBrackets++;
                    yield ")";
                }
                default -> "WRONG";
            };
            sb.append(result);
            count++;
            if (count == length) {
                if (!(result.matches(Regexes.NUMBER.getRegex()) || result.matches(Regexes.VARIABLE.getRegex()) || result.equals(")")) || amountOfLeftBrackets != amountOfRightBrackets) {
                    count = length - 1;
                }
            }
        }
        return sb.toString();
    }
}
