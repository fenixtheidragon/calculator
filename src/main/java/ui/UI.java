package ui;

import back.InputParser;

import java.util.Scanner;

public class UI {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        InputParser inputParser = new InputParser();
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            inputParser.setInput(input);
            String parsed = inputParser.parseInput();
            System.out.println(parsed);
            if (parsed.equals("Bye!")) {
                break;
            }
        }
    }
}