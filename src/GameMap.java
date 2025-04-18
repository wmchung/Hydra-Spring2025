/**
 * WIP
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameMap {
    private ArrayList<Room> rooms;
    private ArrayList<Item> items;
    private ArrayList<Puzzle> puzzles;
    private ArrayList<Character> characters;
    private ArrayList<NPC> npcs;

    public GameMap() {
        rooms = new ArrayList<>();
        items = new ArrayList<>();
        puzzles = new ArrayList<>();
        characters = new ArrayList<>();
        npcs = new ArrayList<>();
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
                    int itemID = Integer.parseInt(parts[0]);
                    String itemName = parts[1];
                    String itemDescription = parts[2];
                    String itemType = parts[3];
                    int itemHP = parts[4].isEmpty() ? 0 : Integer.parseInt(parts[4]);
                    int itemDMG = parts[5].isEmpty() ? 0 : Integer.parseInt(parts[5]);
                    String itemRegion = parts[6];

                    // Create item based on type
                    if (itemType.equalsIgnoreCase("Weapon")) {
                        Weapon weapon = new Weapon(itemID, itemName, itemDescription, itemType, itemRegion, itemDMG);
                        items.add(weapon);
                    } else if (itemType.equalsIgnoreCase("Healing")) {
                        Consumable consumable = new Consumable(itemID, itemName, itemDescription, itemType, itemRegion,
                                itemHP);
                        items.add(consumable);
                    } else {
                        Items genericItem = new Items(itemID, itemName, itemDescription, itemType, itemRegion);
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
    public void loadCharacters(String file) throws FileNotFoundException{
        String line;
        try (Scanner input = new Scanner(new File(file))) {
            input.nextLine();
            while (input.hasNext()) {
                line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("~", 5);
                    String enemyID = parts[0];
                    String name = parts[1];
                    String region = parts[2];
                    String roomID = parts[3];
                    int health = Integer.parseInt(parts[4]);
                    String dmgRange = parts[5];
                    int threshold = Integer.parseInt(parts[6]);
                    String fleeText = parts[7];
                    String winText = parts[8];
                    String loseText = parts[9];

                    Enemy enemy = new Enemy(enemyID, name, region, roomID, health, dmgRange, threshold, fleeText, winText,
                            loseText);
                    characters.add(enemy);

                }
            }
        }
    } //end loadCharacters

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

        if (parts.length < 9) {
            throw new IllegalArgumentException("Invalid room data: " + line);
        } //validate rooms are parsing correctly

        String roomId = parts[0];
        String roomName = parts[1];
        String roomDesc = parts[2];
        Room room = new Room(roomId, roomName, roomDesc);
        System.out.println("Parsing room " + roomId); //debug statement

        String[] exits = parts[3].split(",");
        for (String exit : exits) {
            if (!exits.equals("0")) {
                String[] exitParts = exit.split(":");
                if (exitParts.length == 2) {
                    String direction = exitParts[0];
                    int nextRoomNumber = Integer.parseInt(exitParts[1]);
                    room.addExit(direction, nextRoomNumber);
                    // System.out.println("Exit added " + direction + " - > " + nextRoomNumber); //debug statement
                }
            }
        } //end room parse

        if (!parts[4].equals("0")) {
            String[] roomItems = parts[4].split("~");
            for (String itemId : roomItems) {
                if (!itemId.isEmpty()) {
                    for (Items item : items) {
                        if (item.getItemID()) {
                            room.addItem(item);
                            System.out.println("Item added to room " + item.getItemName()); //debug statement
                            break;
                        }
                    }
                }
            }
        }//end item parse

        if (!parts[5].equals("0")) {
            String[] roomPuzzles = parts[5].split("~");
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

        if (!parts[6].equals("0")) {
            String[] roomCharacters = parts[6].split("~");
            for (String enemyId : roomCharacters) {
                if (!enemyId.isEmpty()) {
                    for (Character enemy : characters ) {
                        if (enemyId.equals(enemy)) {
                            room.addCharacters(enemy);
                            System.out.println("Character added to room " + enemyId); //debug statement
                            break;
                        }
                    }
                }
            }
        } //end character parse

        return room;
    } //end parseRoom

// Method to find a room by its ID
    public Room getRoomById(String roomId) {
        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                return room;
            }
        }
        return null; // Return null if no room is found
    }

    // Method to determine the next room in a given direction
    public Room getRoomInDirection(Room currentRoom, String direction) {
        Integer nextRoomNumber = currentRoom.getExits().get(direction); // Retrieve the next room number
        if (nextRoomNumber != null) {
            return getRoomById(String.valueOf(nextRoomNumber)); // Find and return the next room
        }
        return null; // Return null if no room exists in the given direction
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

