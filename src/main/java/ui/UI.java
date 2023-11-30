package ui;

import back.InputToOutputMatcher;
import back.Messages;
import java.util.Scanner;

public class UI {
    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            InputToOutputMatcher inputToOutputMatcher = new InputToOutputMatcher();
            System.out.println(Messages.HELP_MSG.getMessage());
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                String parsed = inputToOutputMatcher.matchInput(input);
                System.out.println(parsed);
                if (parsed.equals("Bye!")) {
                    break;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}