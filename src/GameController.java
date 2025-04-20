import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameController {
    private GameMap gameMap;
    private Player player;

    public GameController(Player player) {
        this.gameMap = new GameMap();
        this.player = player;
    }

    //method to start game
    public void startGame() {

    }
    public void help (){
        //final boss room

        //show puzzle status
//        for(Puzzle puzzle : gameMap.getPuzzle()){
//            String status = puzzle.isPuzzleSolved()? "Solved!" : "Not solved.";
//            System.out.println("- Puzzle in Room '" + puzzle.getRoomID() + "': " + status);
//        }
    }


}
