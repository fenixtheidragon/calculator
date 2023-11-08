package ui;

import back.InputParser;

import java.util.Scanner;

public class UI {
    private Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        InputParser inputParser = new InputParser();
        inputParser.setInputManipulator();
        while (true) {
            System.out.print("> ");
            String input = this.scanner.nextLine();
            inputParser.setInput(input);
            inputParser.getInputManipulator().setInput(input);
            String parsed = inputParser.parseInput();
            if (parsed.equals("skip printing")) {
                continue;
            }
            System.out.println(parsed);
            if (parsed.equals("Bye!")) {
                break;
            }
        }
    }
}