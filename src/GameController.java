import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameController {
    private GameMap gameMap;
    private Player player;
    private GameView view;

    public GameController(GameMap gameMap, Player player) {
        this.gameMap = new GameMap();
        this.player = player;
        this.view = new GameView();
    }

    //method to start game
    public void startGame() {
        view.showWelcome();
        boolean playing = true;

        while (playing && player.isAlive()) {
            view.showPrompt();
            String command = view.getCommand().toLowerCase();

            switch (command) {
                case "look":
                    player.look();
                    break;
                case "inventory":
                    player.showInventory();
                    break;
                case "help":
                    view.showHelp();
                    break;
                case "exit":
                case "quit":
                    view.showExitMessage();
                    playing = false;
                    break;
                default:
                    view.showInvalidCommand();
                    break;
            }
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
