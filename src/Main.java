import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap();

        try {
            // Load game data from files
            gameMap.loadGameData("item.txt", "puzzles.txt", "Enemy.txt", "NPC.txt", "Map.txt");
            System.out.println("All files loaded successfully.");

            // Initialize the starting room
            Room startingRoom = gameMap.getRoomById("SR01");

            // Initialize the player with the starting room
            Player player = new Player("Hero", 100, 10, startingRoom);

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