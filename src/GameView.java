// GameView.java
import java.util.Scanner;

public class GameView {
    private final Scanner scanner;

    public GameView() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("\nWelcome to Trials of the Golden Gummy: A Text-Based Adventure Game!\n");
    }

    public void showPrompt() {
        System.out.print("\n> Enter command: ");
    }

    public String getCommand() {
        return scanner.nextLine().trim();
    }

    public void showInvalidCommand() {
        System.out.println("Invalid command. Type 'help' to see available options.");
    }

    public void showHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("- move <direction>");
        System.out.println("- look");
        System.out.println("- inventory");
        System.out.println("- equip <item name>");
        System.out.println("- use <item name>");
        System.out.println("- talk <npcID>");
        System.out.println("- pickup <item name>");
        System.out.println("- drop <item name>");
        System.out.println("- solve <puzzleID> <solution>");
        System.out.println("- status");
        System.out.println("- quit");
    }

    public void showExitMessage() {
        System.out.println("Thanks for playing Trials of the Golden Gummy. Goodbye!");
    }
}