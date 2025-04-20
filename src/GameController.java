import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameController {
    private GameMap gameMap;
    private Player player;
    private Room room;
    private GameView view;
    private boolean inCombat;

    public GameController(GameMap gameMap, Player player) {
        this.gameMap = new GameMap();
        this.player = player;
        this.view = new GameView();
        this.inCombat = false;
    }

    public void startGame() {
        view.showWelcome();

        Scanner scanner = new Scanner(System.in);
        while (player.isAlive() && !player.hasWon()) {
            view.showPrompt();
            String input = scanner.nextLine().trim();
            if (input.isEmpty() || !input.matches("[a-zA-Z0-9 ]+")) {
                view.showInvalidCommand();
                continue;
            }
            processCommand(input);
        }
        if (player.hasWon()) {
            view.showVictory();
        } else {
            view.showGameOver();
        }
    }

    private void processCommand(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "move":
                player.handleMovement(argument, gameMap);
                break;
            case "look":
                player.look();
                break;
            case "inventory":
                player.showInventory();
                break;
            case "equip":
                player.equipItem(argument);
                break;
            case "use":
                player.heal(argument);
                break;
            case "pickup":
                player.pickUpItem(argument);
                break;
            case "drop":
                player.dropItem(argument);
                break;
            case "talk":
                player.talkToNPC(argument);
                break;
            case "solve":
                handlePuzzleSolve(argument);
                break;
            case "fish":
                handleFishing();
                break;
            case "help":
                view.showHelp();
                view.showPuzzleStatus();
                break;
            case "quit":
                view.showExitMessage();
                System.exit(0);
                break;
            default:
                view.showInvalidCommand();
        }
    }

    private void handlePuzzleSolve(String input) {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Usage: solve <puzzleID> <solution>");
            return;
        }
        String puzzleId = parts[0];
        String answer = parts[1];

        for (Puzzle puzzle : player.getCurrentRoom().getPuzzles()) {
            if (puzzle.getPuzzleId().equalsIgnoreCase(puzzleId)) {
                if (puzzle.checkSolution(answer)) {
                    System.out.println(puzzle.getCompletionMessage());
                    puzzle.setPuzzleSolved(true);
                    player.collectKey(puzzleId); // reward: key ID = puzzle ID
                    player.addCompletedPuzzle(puzzleId);
                } else {
                    System.out.println(puzzle.getFailureMessage());
                    player.takeDamage(puzzle.getDamageOnFailure());
                }
                return;
            }
        }
        System.out.println("No matching puzzle in this room.");
    }

    private void handleFishing() {
        for (Item item : player.getInventory()) {
            if (item instanceof FishingRod) {
                ((FishingRod) item).fish(player);
                return;
            }
        }
        System.out.println("You don't have a fishing rod!");
    }

    public void help (){
        //final boss room

        //show puzzle status
        for(Puzzle puzzle : room.getPuzzles()){
            String status = puzzle.isPuzzleSolved()? "Solved!" : "Not solved.";
            System.out.println("- Puzzle in Room '" + puzzle.getPuzzleRoomId() + "': " + status);
        }
    }

}
