/**
 * WIP
 */

import java.io.*;
import java.util.*;

public class SaveSystem {
    private static final String SAVE_FILE = "savegame.txt";

    // Method to save the game state
    public void saveGame(List<Room> rooms, List<Item> items, List<Enemy> enemies, Player player) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            // Save player state
            writer.write("Player\n");
            writer.write(player.getName() + "~" + player.getHealth() + "\n");

            // Save room states
            writer.write("Rooms\n");
            for (Room room : rooms) {
                writer.write(room.getRoomId() + "~" + room.isVisited() + "~" + room.isLocked() + "\n");
                for (Puzzle puzzle : room.getPuzzles()) {
                    writer.write("Puzzle~" + puzzle.getPuzzleId() + "~" + puzzle.isPuzzleSolved() + "\n");
                }
            }

            // Save items
            writer.write("Items\n");
            for (Item item : items) {
                writer.write(item.getItemID() + "~" + item.getName() + "\n");
            }

            // Save enemies
            writer.write("Enemies\n");
            for (Enemy enemy : enemies) {
                writer.write(enemy.getEnemyID() + "~" + enemy.getHealth() + "~" + enemy.isDefeated() + "\n");
            }
        }
    }

    // Method to load the game state
    public void loadGame(GameMap gameMap, Player player) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line;
            Room currentRoom = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("Player")) {
                    String[] playerData = reader.readLine().split("~");
                    player.setName(playerData[0]);
                    player.setHealth(Integer.parseInt(playerData[1]));
                } else if (line.equals("Rooms")) {
                    while (!(line = reader.readLine()).equals("Items")) {
                        if (line.startsWith("Puzzle")) {
                            String[] puzzleData = line.split("~");
                            Puzzle puzzle = new Puzzle(puzzleData[1], puzzleData[3], puzzleData[4], puzzleData[5],
                                    puzzleData[6], puzzleData[7], puzzleData[8], puzzleData[9],
                                    Integer.parseInt(puzzleData[10]), Integer.parseInt(puzzleData[11]));
                            puzzle.setPuzzleSolved(Boolean.parseBoolean(puzzleData[2]));
                            currentRoom.addPuzzle(puzzle);
                        } else {
                            String[] roomData = line.split("~");
                            currentRoom = new Room(roomData[0], roomData[3], roomData[4], roomData[5]);
                            currentRoom.setVisited(Boolean.parseBoolean(roomData[1]));
                            currentRoom.setLocked(Boolean.parseBoolean(roomData[2]));
                            gameMap.rooms.add(currentRoom);
                        }
                    }
                } else if (line.equals("Items")) {
                    while (!(line = reader.readLine()).equals("Enemies")) {
                        String[] itemData = line.split("~");
                        Item item = new Item(itemData[0], itemData[1], itemData[2], itemData[3], itemData[4]);
                        gameMap.items.add(item); // Direct access to items
                    }
                } else if (line.equals("Enemies")) {
                    while ((line = reader.readLine()) != null) {
                        String[] enemyData = line.split("~");
                        Enemy enemy = new Enemy(enemyData[0], enemyData[3], enemyData[4], enemyData[5],
                                Integer.parseInt(enemyData[1]), enemyData[6], Integer.parseInt(enemyData[7]),
                                enemyData[8], enemyData[9], enemyData[10]);
                        enemy.health = Integer.parseInt(enemyData[1]);
                        enemy.defeated = Boolean.parseBoolean(enemyData[2]);
                        gameMap.enemies.add(enemy);
                    }
                }
            }
        }
    }
}
