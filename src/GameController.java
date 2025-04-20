import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
                if (!inCombat) player.handleMovement(argument, gameMap);
                else System.out.println("You can't move while in combat!");
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
            case "attack":
                handleCombat();
                break;
            case "flee":
                handleFlee();
                break;
            case "help":
                view.showHelp();
                view.showPuzzleStatus();
                break;
            case "save":
                handleSave();
            case "load":
                handleLoad();
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

    private void handleCombat() {
        Room room = player.getCurrentRoom();
        if (!room.hasEnemy()) {
            System.out.println("There are no enemies in this room.");
            return;
        }

        inCombat = true;
        Enemy enemy = room.getEnemies().get(0);

        System.out.println("You engage " + enemy.getName() + " in combat!");

        while (player.isAlive() && enemy.isAlive()) {
            enemy.takeDamage(player.getEffectiveAttackPower());
            if (!enemy.isAlive()) break;
            int dmg = enemy.getAttackPower();
            player.takeDamage(dmg);
            System.out.println(enemy.getName() + " attacks for " + dmg + " damage!");
        }

        if (!player.isAlive()) {
            System.out.println(enemy.getLoseText());
        } else if (!enemy.isAlive()) {
            System.out.println(enemy.getWinText());
            player.addDefeatedEnemy(enemy.getEnemyID());
            room.removeEnemy(enemy);
            player.unEquipItem();
        }

        inCombat = false;
    }

    private void handleFlee() {
        if (!inCombat) {
            System.out.println("You're not in combat.");
            return;
        }

        Room room = player.getCurrentRoom();
        Enemy enemy = room.getEnemies().get(0);
        System.out.println(enemy.getFleeText());
        player.takeDamage(2); // flee penalty

        inCombat = false;
        System.out.println("You fled the battle.");
    }

    private void handleSave() {
        if (inCombat) {
            System.out.println("You cannot save while in combat!");
            return;
        }

        String currentRoomId = player.getCurrentRoom().getRoomId();
        if (!currentRoomId.equals("AR01") && !currentRoomId.equals("AR02") && !currentRoomId.equals("AR03")) {
            System.out.println("You can only save in armory rooms");
            return;
        }

        SaveSystem saveSystem = new SaveSystem();
        try {
            //access the fields of gameMap, might not work
            saveSystem.saveGame(gameMap.rooms, gameMap.items, gameMap.enemies, player);
            System.out.println("Game saved successfully");
        } catch (IOException ioe) {
            System.out.println("Failed to save the game: " + ioe.getMessage());
        }
    }

    private void handleLoad() {
        SaveSystem saveSystem = new SaveSystem();
        File saveFile = new File("savegame.txt");

        if (!saveFile.exists()) {
            System.out.println("No save file found. Start a new game.");
            return;
        }

        try {
            saveSystem.loadGame(gameMap, player); //may need to be adjusted
            System.out.println("Game loaded successfully");
        } catch (IOException ioe) {
            System.out.println("Failed to load the game: " + ioe.getMessage());
        } catch (NoSuchElementException nse) {
            System.out.println("Save file is corrupted or incomplete.");
        }
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
