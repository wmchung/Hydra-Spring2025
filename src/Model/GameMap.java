package Model; /**
 * WIP
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Character;
import java.util.*;

public class GameMap {
    public Map<String, Room> rooms;
    public List<Item> items;
    public List<Puzzle> puzzles;
    public List<Enemy> enemies;
    public List<NPC> npcs;

    public GameMap() throws FileNotFoundException {
        this("item.txt", "puzzles.txt", "enemy.txt", "NPC.txt", "map.txt");
    }

    //method to load item, puzzle, characters, NPCS, and rooms files
    public GameMap(String itemsFile, String puzzlesFile, String enemiesFile, String NPCsFile, String roomsFile) throws FileNotFoundException {
        this.rooms = new HashMap<>();
        this.items = new ArrayList<>();
        this.puzzles = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.npcs = new ArrayList<>();

        loadItems(itemsFile);
        loadPuzzles(puzzlesFile);
        loadEnemies(enemiesFile);
        loadNPCs(NPCsFile);
        loadRooms(roomsFile);
    } //end loadGameData

    //method to load rooms
    public void loadRooms(String file) {
        Map<String, Room> tempRooms = new HashMap<>();
        List<String[]> pendingExits = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(file))) {
            scanner.nextLine(); // Skip header line

            // First pass: Create all rooms without exits
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("~");
                if (parts.length < 9) {
                    System.err.println("Invalid room data: " + line);
                    continue;
                }

                String roomId = parts[0];
                //System.out.println("Debug: Parsing room " + roomId);

                // Create basic room
                Room room = new Room(
                        roomId,
                        parts[1], // area
                        parts[2], // type
                        parts[3]  // description
                );

                // Add items if present
                if (!parts[5].equals("0")) {
                    String[] itemIds = parts[5].split(",");
                    for (String itemId : itemIds) {
                        itemId = itemId.trim();
                        for (Item item : items) {
                            if (item.getItemID().equalsIgnoreCase(itemId)) {
                                room.addItem(item);
                                //System.out.println("Debug: Added item " + itemId + " to room " + roomId);
                                break;
                            }
                        }
                    }
                }

                // Add puzzles if present
                if (!parts[6].equals("0")) {
                    String[] puzzleIds = parts[6].split(",");
                    for (String puzzleId : puzzleIds) {
                        puzzleId = puzzleId.trim();
                        for (Puzzle puzzle : puzzles) {
                            if (puzzle.getPuzzleId().equalsIgnoreCase(puzzleId)) {
                                room.addPuzzle(puzzle);
                                //System.out.println("Debug: Added puzzle " + puzzleId + " to room " + roomId);
                                break;
                            }
                        }
                    }
                }

                // Add enemies if present
                if (!parts[7].equals("0")) {
                    String[] enemyIds = parts[7].split(",");
                    for (String enemyId : enemyIds) {
                        enemyId = enemyId.trim();
                        for (Enemy enemy : enemies) {
                            if (enemy.getEnemyID().equalsIgnoreCase(enemyId)) {
                                room.addEnemy(enemy);
                                //System.out.println("Debug: Added enemy " + enemyId + " to room " + roomId);
                                break;
                            }
                        }
                    }
                }

                // Add NPCs if present
                if (!parts[8].equals("0")) {
                    String[] npcIds = parts[8].split(",");
                    for (String npcId : npcIds) {
                        npcId = npcId.trim();
                        for (NPC npc : npcs) {
                            if (npc.getNpcId().equalsIgnoreCase(npcId)) {
                                room.addNpc(npc);
                                //System.out.println("Debug: Added NPC " + npcId + " to room " + roomId);
                                break;
                            }
                        }
                    }
                    // Set checkpoint if room has NPCs
                    if (!room.getNpcs().isEmpty()) {
                        room.setCheckpoint(true);
                        //System.out.println("Debug: Room " + roomId + " set as checkpoint");
                    }
                }

                // Store room in temporary map
                tempRooms.put(roomId, room);

                // Store exits for later processing
                if (!parts[4].equals("0")) {
                    String[] exits = parts[4].split(",");
                    for (String exit : exits) {
                        String[] exitParts = exit.split(":");
                        if (exitParts.length == 2) {
                            pendingExits.add(new String[]{
                                    roomId,
                                    exitParts[0].trim(),
                                    exitParts[1].trim()
                            });
                        } else {
                            System.err.println("Error: Invalid exit format in room " + roomId + ": " + exit);
                        }
                    }
                }

                //System.out.println("Debug: Room " + roomId + " basic structure loaded.");
            }

            // Second pass: Process all exits
            for (String[] exitData : pendingExits) {
                String sourceRoomId = exitData[0];
                String direction = exitData[1].toUpperCase();
                String targetRoomId = exitData[2];

                Room sourceRoom = tempRooms.get(sourceRoomId);
                Room targetRoom = tempRooms.get(targetRoomId);

                if (targetRoom == null) {
                    System.err.println("Error: Target room " + targetRoomId +
                            " not found for exit " + direction +
                            " in room " + sourceRoomId);
                    continue;
                }

                // Add exit to the room
                sourceRoom.addExit(direction, targetRoom);
                //System.out.println("Debug: Exit added to room " + sourceRoomId +
                  //      " - " + direction + " -> " + targetRoomId);
            }

            // Update the game's room collection
            rooms = tempRooms;
            System.out.println("All rooms loaded successfully. Total rooms: " + rooms.size());

        } catch (FileNotFoundException e) {
            System.err.println("Error: Room file not found: " + file);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error loading rooms: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //method to load items from file
    public void loadItems(String file) throws FileNotFoundException {
        String line;
        try (Scanner input = new Scanner(new File(file))) {
            input.nextLine(); //skip first line
            while (input.hasNextLine()) {
                line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("~");
                    String itemID = parts[0];
                    String itemName = parts[1];
                    String itemDescription = parts[2];
                    String itemType = parts[3];
                    int itemAttribute = parts[4].isEmpty() ? 0 : Integer.parseInt(parts[4]);
                    int uses = parts[5].isEmpty() ? 0 : Integer.parseInt(parts[5]);
                    String itemRegion = parts[6];

                    // Create item based on type
                    if (itemType.equalsIgnoreCase("none") && itemID.endsWith("K")) {
                        Item puzzleKey = new Item(itemID, itemName, itemDescription, itemType, itemRegion);
                        items.add(puzzleKey);
                    }
                    if (itemType.equalsIgnoreCase("Model.Weapon")) {
                        Weapon weapon = new Weapon(itemID, itemName, itemDescription, itemType, itemRegion, itemAttribute, uses);
                        items.add(weapon);
                    } else if (itemType.equalsIgnoreCase("Healing")) {
                        Consumable consumable = new Consumable(itemID, itemName, itemDescription, itemType, itemRegion,
                                itemAttribute, uses);
                        items.add(consumable);
                    } else {
                        Item genericItem = new Item(itemID, itemName, itemDescription, itemType, itemRegion);
                        items.add(genericItem);
                    }
                }
            }
        }
    } //end loadItems

    //method to load puzzles from file
    public void loadPuzzles(String file) throws FileNotFoundException {
        String line;
        try (Scanner input = new Scanner(new File(file))) {
            input.nextLine(); //skip first line
            while (input.hasNext()) {
                line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("~", 10);
                    String puzArea = parts[0];
                    String puzId = parts[1];
                    String puzRoomId = parts[2];
                    String puzDesc = parts[3];
                    String puzAnswer = parts[4];
                    String puzPassMsg = parts[5];
                    String puzFailMsg = parts[6];
                    String puzRequiredItem = parts[7];
                    int puzDmgOnFail = Integer.parseInt(parts[8]);
                    int puzAttempts = Integer.parseInt(parts[9]);

                    Puzzle puzzle = new Puzzle(puzArea, puzId, puzRoomId, puzDesc, puzAnswer, puzPassMsg, puzFailMsg,
                            puzRequiredItem, puzDmgOnFail, puzAttempts);
                    puzzles.add(puzzle);
                }
            }
        } //end try
    } //end loadPuzzles


    //method to load characters from file
    public void loadEnemies(String file) throws FileNotFoundException {
        String line;
        try (Scanner input = new Scanner(new File(file))) {
            input.nextLine(); // Skip the first line
            while (input.hasNext()) {
                line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        String[] parts = line.split("~", 10); // Adjust split to match the expected number of fields
                        if (parts.length < 10) {
                            throw new IllegalArgumentException("Invalid character data: " + line);
                        }

                        String enemyID = parts[0];
                        String name = parts[1];
                        String region = parts[2];
                        String roomID = parts[3];
                        int health = Integer.parseInt(parts[4]); // Validate numeric fields
                        String dmgRange = parts[5];
                        int threshold = Integer.parseInt(parts[6]);
                        String fleeText = parts[7];
                        String winText = parts[8];
                        String loseText = parts[9];

                        Enemy enemy = new Enemy(enemyID, name, region, roomID, health, dmgRange, threshold, fleeText, winText, loseText);
                        enemies.add(enemy);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing numeric value in character data: " + line);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }
    }//end loadCharacters

    //method to load npcs from file
    public void loadNPCs(String file) throws FileNotFoundException {
        String line;
        try (Scanner input = new Scanner(new File(file))) {
            input.nextLine();
            while (input.hasNext()) {
                line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("~", 4);
                    String npcId = parts[0];
                    String npcDesc = parts[1];
                    String npcReg = parts[2];
                    String npcInteract = parts[3];

                    NPC npc = new NPC(npcId, npcDesc, npcReg, npcInteract);
                    npcs.add(npc);
                }
            }
        }
    } //end loadNPCs

//

    public Room getRoom(String roomID) {
        for (Room room : rooms.values()) { // Use rooms.values() to iterate over Room objects
            if (roomID.equalsIgnoreCase(room.getRoomId())) {
                //System.out.println("Debug: Retrieved room " + roomID + " with exits: " + room.getExits());
                return room;
            }
        }
        return null; // Return null if no room is found
    }

    // Method to determine the next room in a given direction
    // Fixed getRoomInDirection method
    public Room getRoomInDirection(Room currentRoom, String direction) {
        if (currentRoom == null || direction == null) {
            System.err.println("Error: Invalid current room or direction.");
            return null;
        }

        String normalizedDirection = direction.toUpperCase();
        //System.out.println("Debug: Current room exits: " + currentRoom.getExits());

        Room targetRoom = currentRoom.getExits().get(normalizedDirection);
        if (targetRoom == null) {
          //  System.out.println("Debug: No exit in direction " + normalizedDirection);
            return null;
        }
        return targetRoom;
    }

    //method to convert shorthand direction input to full directions
    public String mapDirection(String input) {
        Map<String, String> directionMap = new HashMap<>();
        directionMap.put("N", "NORTH");
        directionMap.put("E", "EAST");
        directionMap.put("S", "SOUTH");
        directionMap.put("W", "WEST");
        return directionMap.get(input.toUpperCase());
    } //end mapDirection

} //end GameMap class