import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap();
        Room startingRoom = new Room("SR01", "General", "Starting Room", "You wake up in a small, dimly lit room. The dark grey walls are cracked with age. Three doors come into focus around you.");
        Player player = new Player("Hero", 100, 10, startingRoom);

        try {
            // Load game data from files
            gameMap.loadGameData("item.txt", "puzzles.txt", "Enemy.txt", "NPC.txt", "Map.txt");
            System.out.println("All files loaded successfully.");

            // Initialize game components
            GameView gameView = new GameView();
            GameController gameController = new GameController(player, gameView);

            // Start the game
            gameController.startGame();
        } catch (IOException e) {
            System.out.println("Error loading game data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}