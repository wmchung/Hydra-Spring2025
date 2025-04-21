package Model;

import java.io.*;
import java.util.*;

public class SaveSystem {
    private static final String SAVE_FILE = "savegame.txt";

    // Save the game state
    public void saveGame(List<Room> rooms, List<Item> items, List<Enemy> enemies, Player player) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write("Player\n");
            writer.write(player.getHealth() + "~" + player.getCurrentCheckpoint() + "\n");

            writer.write("Rooms\n");
            for (Room room : rooms) {
                writer.write(room.getRoomId() + "~" + room.getRoomArea() + "~" + room.getRoomType() + "~" +
                        room.getDescription() + "~" + room.getExits() + "~" + room.getItems() + "~" +
                        room.getPuzzles() + "~" + room.getEnemies() + "~" + room.getNpcs() + "~" + room.isVisited() +
                        "~" + room.isLocked() + "~" + room.isCheckpoint() + "\n");
                for (Puzzle puzzle : room.getPuzzles()) {
                    writer.write("Puzzle~" + puzzle.getArea() + "~" + puzzle.getPuzzleId() + "~" + puzzle.getPuzzleRoomId() +
                            "~" + puzzle.getDescription() + "~" + puzzle.getSolution() + "~" + puzzle.getCompletionMessage()
                            + "~" + puzzle.getFailureMessage() + "~" + puzzle.getRequiredItem() + "~" + puzzle.getDamageOnFailure()
                            + "~" + puzzle.getAttempts() + "~" + puzzle.isPuzzleSolved() + "\n");
                }
                for (NPC npc : room.getNpcs()) {
                    writer.write("NPC~" + npc.getNpcId() + "~" + npc.getDescription() + "~" +  npc.getRegion() + "~" +
                            npc.getInteraction() + "\n");
                }
            }

            writer.write("Items\n");
            for (Item item : items) {
                writer.write(item.getItemID() + "~" + item.getName() + "~" + item.getItemDescription() +
                        "~" + item.getTag() + "~" + item.getRoomID() + "~" + item.getItemDescription() + "~" + item.isEquipped() + "\n");
            }

            writer.write("Enemies\n");
            for (Enemy enemy : enemies) {
                writer.write(enemy.getEnemyID() + "~" + enemy.getName() + "~" + enemy.getRegion() + "~" +
                         "~" + enemy.getRoomID() + "~" + enemy.getHealth() + "~" + enemy.getAttackPower() + "~" +
                         "~" + enemy.getThreshold() + "~" + enemy.getFleeText() + "~" + enemy.getWinText() + "~"
                        + "~" + enemy.getLoseText() + "~" + enemy.isDefeated() + "\n");
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
                    player.setHealth(Integer.parseInt(playerData[0]));
                    player.setCurrentCheckpoint(playerData[1]);
                } else if (line.equals("Rooms")) {
                    while (!(line = reader.readLine()).equals("Items")) {
                        if (line.startsWith("Puzzle")) {
                            String[] puzzleData = line.split("~");
                            Puzzle puzzle = new Puzzle(puzzleData[1], puzzleData[2], puzzleData[3], puzzleData[4],
                                    puzzleData[5], puzzleData[6], puzzleData[7], puzzleData[8], Integer.parseInt(puzzleData[9]),
                                    Integer.parseInt(puzzleData[10]));
                            currentRoom.addPuzzle(puzzle);
                        } else if (line.startsWith("NPC")) {
                            String[] npcData = line.split("~");
                            NPC npc = new NPC(npcData[1], npcData[2], npcData[3], npcData[4]);
                            currentRoom.addNpc(npc);
                        } else {
                            String[] roomData = line.split("~");
                            currentRoom = new Room(roomData[0], roomData[1], roomData[2], roomData[3]);
                            gameMap.rooms.put(currentRoom.getRoomId(), currentRoom);                        }
                    }
                } else if (line.equals("Items")) {
                    while (!(line = reader.readLine()).equals("Enemies")) {
                        String[] itemData = line.split("~");
                        Item item = new Item(itemData[0], itemData[1], itemData[2], itemData[3], (itemData[4]));
                        gameMap.items.add(item);
                    }
                } else if (line.equals("Enemies")) {
                    while ((line = reader.readLine()) != null) {
                        String[] enemyData = line.split("~");
                        Enemy enemy = new Enemy(enemyData[0], enemyData[1], enemyData[2], enemyData[3],
                                Integer.parseInt(enemyData[4]), enemyData[5], Integer.parseInt(enemyData[6]),
                                enemyData[7], enemyData[8], enemyData[9]);
                        enemy.setDefeated(Boolean.parseBoolean(enemyData[10]));
                        gameMap.enemies.add(enemy);
                    }
                }
            }
        }
    }
}