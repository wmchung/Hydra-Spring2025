import Controller.GameController;
import Model.Player;
import View.GameView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            // Load game data from files
            Player player = new Player("item.txt", "puzzles.txt", "Enemy.txt", "NPC.txt", "Map.txt");
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