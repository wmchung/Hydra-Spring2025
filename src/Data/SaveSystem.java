package Data; /**
 * WIP
 */

import java.io.*;

public class SaveSystem {
    private static final String SAVE_FILE = "savegame.txt";

    // Method to save the game state
    public void saveGame(GameMap gameMap, Player player) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            // Save player state
            writer.write("Player\n");
            writer.write(player.getName() + "~" + player.getHealth() + "~" + player.getInventoryAsString() + "\n");

            // Save room states
            writer.write("Rooms\n");
            for (Room room : gameMap.getRooms()) {
                writer.write(room.getRoomNumber() + "~" + room.isVisited() + "~" + room.getItemsAsString() + "\n");
            }

            // Save other game states (e.g., puzzles, monsters)
            writer.write("Puzzles\n");
            for (Puzzle puzzle : gameMap.getPuzzles()) {
                writer.write(puzzle.getPuzzleId() + "~" + puzzle.isSolved() + "\n");
            }
        }
    }

    // Method to load the game state
    public void loadGame(GameMap gameMap, Player player) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("Player")) {
                    String[] playerData = reader.readLine().split("~");
                    player.setName(playerData[0]);
                    player.setHealth(Integer.parseInt(playerData[1]));
                    player.setInventoryFromString(playerData[2]);
                } else if (line.equals("Rooms")) {
                    while (!(line = reader.readLine()).equals("Puzzles")) {
                        String[] roomData = line.split("~");
                        Room room = gameMap.getRoomByNumber(Integer.parseInt(roomData[0]));
                        room.setVisited(Boolean.parseBoolean(roomData[1]));
                        room.setItemsFromString(roomData[2]);
                    }
                } else if (line.equals("Puzzles")) {
                    while ((line = reader.readLine()) != null) {
                        String[] puzzleData = line.split("~");
                        Puzzle puzzle = gameMap.getPuzzleById(Integer.parseInt(puzzleData[0]));
                        puzzle.setSolved(Boolean.parseBoolean(puzzleData[1]));
                    }
                }
            }
        }
    }
}