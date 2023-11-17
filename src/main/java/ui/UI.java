package ui;

import back.InputToOutputMatcher;

import java.util.Scanner;

public class UI {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        InputToOutputMatcher inputToOutputMatcher = new InputToOutputMatcher();
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            inputToOutputMatcher.setInput(input);
            String parsed = inputToOutputMatcher.matchInput();
            System.out.println(parsed);
            if (parsed.equals("Bye!")) {
                break;
            }
        }
    }
}