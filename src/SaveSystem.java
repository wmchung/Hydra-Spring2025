import java.io.*;
import java.util.*;

public class SaveSystem {
    private static final String SAVE_FILE = "savegame.txt";

    // Save the game state
    public void saveGame(List<Room> rooms, List<Item> items, List<Enemy> enemies, Player player) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write("Player\n");
            writer.write(player.getName() + "~" + player.getHealth() + "~" + player.getCurrentCheckpoint() + "\n");

            writer.write("Rooms\n");
            for (Room room : rooms) {
                writer.write(room.getRoomId() + "~" + room.isVisited() + "~" + room.isLocked() + "~" + room.isCheckpoint() + "\n");
                for (Puzzle puzzle : room.getPuzzles()) {
                    writer.write("Puzzle~" + puzzle.getPuzzleId() + "~" + puzzle.isPuzzleSolved() + "\n");
                }
                for (NPC npc : room.getNpcs()) {
                    writer.write("NPC~" + npc.getNpcId() + "~" + npc.getInteraction() + "\n");
                }
            }

            writer.write("Items\n");
            for (Item item : items) {
                writer.write(item.getItemID() + "~" + item.getName() + "~" + item.getItemDescription() + "\n");
            }

            writer.write("Enemies\n");
            for (Enemy enemy : enemies) {
                writer.write(enemy.getEnemyID() + "~" + enemy.getHealth() + "~" + enemy.isDefeated() + "\n");
            }
        }
    }

    // Load the game state
    public void loadGame(GameMap gameMap, Player player) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line;
            Room currentRoom = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("Player")) {
                    String[] playerData = reader.readLine().split("~");
                    player.setName(playerData[0]);
                    player.setHealth(Integer.parseInt(playerData[1]));
                    player.setCurrentCheckpoint(playerData[2]);
                } else if (line.equals("Rooms")) {
                    while (!(line = reader.readLine()).equals("Items")) {
                        if (line.startsWith("Puzzle")) {
                            String[] puzzleData = line.split("~");
                            Puzzle puzzle = new Puzzle(puzzleData[1], "Puzzle Description", Boolean.parseBoolean(puzzleData[2]));
                            currentRoom.addPuzzle(puzzle);
                        } else if (line.startsWith("NPC")) {
                            String[] npcData = line.split("~");
                            NPC npc = new NPC(npcData[1], npcData[2], npcData[3]);
                            currentRoom.addNpc(npc);
                        } else {
                            String[] roomData = line.split("~");
                            currentRoom = new Room(roomData[0], "Room Description", Boolean.parseBoolean(roomData[1]), Boolean.parseBoolean(roomData[2]));
                            currentRoom.setCheckpoint(Boolean.parseBoolean(roomData[3]));
                            gameMap.rooms.add(currentRoom);
                        }
                    }
                } else if (line.equals("Items")) {
                    while (!(line = reader.readLine()).equals("Enemies")) {
                        String[] itemData = line.split("~");
                        Item item = new Item(itemData[0], itemData[1], itemData[2]);
                        gameMap.items.add(item);
                    }
                } else if (line.equals("Enemies")) {
                    while ((line = reader.readLine()) != null) {
                        String[] enemyData = line.split("~");
                        Enemy enemy = new Enemy(enemyData[0], Integer.parseInt(enemyData[1]), "Enemy Description");
                        enemy.setDefeated(Boolean.parseBoolean(enemyData[2]));
                        gameMap.enemies.add(enemy);
                    }
                }
            }
        }
    }
}