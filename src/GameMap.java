/**
 * WIP
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameMap {
     public List<Enemy> enemies;
     public List<Room> rooms;
     public List<Item> items;
     public List<Puzzle> puzzles;
     public List<Character> characters;
     public List<NPC> npcs;

    public GameMap() {
        this.rooms = new ArrayList<>();
        this.items = new ArrayList<>();
        this.puzzles = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.npcs = new ArrayList<>();
    }

    //method to load item, puzzle, characters, NPCS, and rooms files
    public void loadGameData(String itemsFile, String puzzlesFile, String charactersFile, String NPCsFile, String roomsFile) throws FileNotFoundException {
        loadItems(itemsFile);
        loadPuzzles(puzzlesFile);
        loadCharacters(charactersFile);
        loadNPCs(NPCsFile);
        loadRooms(roomsFile);
    } //end loadGameData

    //method to load rooms
    public void loadRooms(String file) throws FileNotFoundException {
        String line;
        try (Scanner input = new Scanner(new File(file))) {
            input.nextLine(); //skip first line
            while (input.hasNextLine()) {
                line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        Room room = parseRoom(line);
                        rooms.add(room);
                        System.out.println("Room added: " + room); // debug statement
                    } catch (NumberFormatException ex) {
                        System.err.println("Error parsing room data: " + ex.getMessage());
                    }
                }
            }
        }
    } //end loadRooms

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
                    if (itemType.equalsIgnoreCase("Weapon")) {
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
                if(!line.isEmpty()) {
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
    public void loadCharacters(String file) throws FileNotFoundException {
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
                        characters.add(enemy);
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
    public void loadNPCs(String file) throws FileNotFoundException{
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

    //method to parse room data
    private Room parseRoom(String line) throws IllegalArgumentException {
        String[] parts = line.split("~", 9);

        if (parts.length <= 8) {
            throw new IllegalArgumentException("Invalid room data: " + line);
        } //validate rooms are parsing correctly

        String roomId = parts[0];
        String roomArea = parts[1];
        String roomType = parts[2];
        String roomDesc = parts[3];
        Room room = new Room(roomId, roomArea, roomType, roomDesc);
        System.out.println("Parsing room " + roomId); //debug statement

        if(!parts[4].equals("0")) {
            String[] exits = parts[4].split(",");
            for (String exit : exits) {
                if (!exit.isEmpty()) {
                    String direction = exit.substring(0, 2);
                    String nextRoomId = exit.substring(2);
                    room.addExit(direction, nextRoomId);
                    System.out.println("Exit added " + direction + " - > " + nextRoomId); //debug statement
                }
            }
        }//end room parse

        if (!parts[5].equals("0")) {
            String itemData = parts[5].replace("~", ""); // Remove enclosing ~ characters
            String[] roomItems = itemData.split(",");
            for (String itemId : roomItems) {
                if (!itemId.isEmpty()) {
                    for (Item item : items) {
                        if (item.getItemID().equalsIgnoreCase(itemId)) {
                            room.addItem(item);
                            System.out.println("Item added to room: " + item.getItemName()); //debug statement
                            break;
                        }
                    }
                }
            }
        }//end item parse

        if (!parts[6].equals("0")) {
            String[] roomPuzzles = parts[6].split("~");
            for (String puzId : roomPuzzles) {
                if (!puzId.isEmpty()) {
                    for (Puzzle puzzle : puzzles) {
                        if (puzzle.getPuzzleId().equalsIgnoreCase(puzId)) {
                            room.addPuzzle(puzzle);
                            System.out.println("Puzzle added to room " + puzId); //debug statement
                            break;
                        }
                    }
                }
            }
        } //end puzzle parse

        if (!parts[7].equals("0")) {
            String[] roomCharacters = parts[7].split("~");
            for (String enemyId : roomCharacters) {
                if (!enemyId.isEmpty()) {
                    for (Character enemy : characters ) {
                        if (enemyId.equals(enemy)) {
                            room.addEnemy((Enemy) enemy);
                            System.out.println("Character added to room " + enemyId); //debug statement
                            break;
                        }
                    }
                }
            }
        } //end character parse

        if (!parts[8].equals("0")) {
            String[] roomNPCs = parts[8].split(",");
            for (String npcId : roomNPCs) {
                if (!npcId.isEmpty()) {
                    for (NPC npc : npcs) {
                        if (npc.getNpcId().equalsIgnoreCase(npcId)) {
                            room.addNpc(npc);
                            System.out.println("NPC added to room: " + npcId);
                            break;
                        }
                    }
                }
            }
            if (!room.getNpcs().isEmpty()) {
                room.setCheckpoint(true);
            }
        }
        return room;
    } //end parseRoom

    // Method to find a room by its ID
    public Room getRoomById(String roomId) {
        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                return room;
            }
        }
        return null; //return null if no room is found
    }

    // Method to determine the next room in a given direction
    public Room getRoomInDirection(Room currentRoom, String direction) {
        String nextRoomId = currentRoom.getExits().get(direction); //retrieve the next room number
        if (nextRoomId != null) {
            return getRoomById(nextRoomId); //find and return the next room
        }
        return null; //return null if no room exists in the given direction
    }

    //method to convert shorthand direction input to full directions
    public String mapDirection(String input) {
        Map<String, String> directionMap = new HashMap<>();
        directionMap.put("N", "NORTH");
        directionMap.put("E", "EAST");
        directionMap.put("S", "SOUTH");
        directionMap.put("W", "WEST");
        return directionMap.get(input);
    } //end mapDirection

} //end GameMap class

