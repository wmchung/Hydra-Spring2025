import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameController {
    private GameMap gameMap;
    private Player player;
    private Room room;

    public GameController(Player player,Room room) {

        this.gameMap = new GameMap();
        this.player = player;
        this.room=room;
    }

    //method to start game
    public void startGame() {

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
