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
    private ArrayList<Items> items;
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

    //method to load item, puzzle, and rooms files
    public void loadGameData(String itemsFile, String puzzlesFile, String charactersFile, String roomsFile) throws FileNotFoundException {
        loadItems(itemsFile);
        loadPuzzles(puzzlesFile);
        loadCharacters(charactersFile);
        loadRooms(roomsFile);
    } //end loadGameData

    //method to load rooms
    public void loadRooms(String file) throws FileNotFoundException {
        String line;
        try (Scanner input = new Scanner(new File(file))) {
            input.nextLine(); // skip first line
            while (input.hasNextLine()) {
                line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        Room room = parseRoom(line);
                        rooms.add(room);
                        // System.out.println("Room added: " + room); // debug statement
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
            input.nextLine(); // Skip the first line
            while (input.hasNext()) {
                line = input.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("~", 4);
                    String itemID = parts[0];
                    String itemName = parts[1];
                    String itemDescription = parts[2];

                    if (parts.length == 4) { //assuming the file format includes type, HP, and damage
                        String tag = parts[3];
                        if (tag.equalsIgnoreCase("Weapon")) {
                            int itemDMG = Integer.parseInt(parts[3]);
                            Weapon weapon = new Weapon(itemName, itemDescription, itemDMG);
                            items.add(weapon);
                        } else if (tag.equalsIgnoreCase("Healing")) {
                            int itemHP = Integer.parseInt(parts[3]);
                            Consumable consumable = new Consumable(itemName, itemDescription, itemHP);
                            items.add(consumable);
                        }
                    } //end if p4
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
                    String puzSol = parts[4];
                    String puzPassMsg = parts[5];
                    String puzFailMsg = parts[6];
                    String puzReqItem = parts[7];
                    int puzDmgOnfail = Integer.parseInt(parts[8]);
                    int puzAttempts = Integer.parseInt(parts[9]);

                    Puzzle puzzle = new Puzzle(puzArea, puzId, puzRoomId, puzDesc, puzSol, puzPassMsg, puzFailMsg, puzReqItem,
                            puzDmgOnfail, puzAttempts);
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
                    String monsterName = parts[0];
                    String monsterDesc = parts[1];
                    int attackDmg = Integer.parseInt(parts[2]);
                    int threshold = Integer.parseInt(parts[3]);
                    int hitPoints = Integer.parseInt(parts[4]);

                    Monster monster = new Monster(monsterName, monsterDesc, attackDmg, threshold, hitPoints);
                    monster.setName(monsterName);
                    monster.setDescription(monsterDesc);
                    monster.setAttackDmg(attackDmg);
                    monster.setThreshold(threshold);
                    monster.setHitPoints(hitPoints);
                    monsters.add(monster);
                }
            }
        }
    } //end loadMonsters

    //method to load characters from file
    public void loadNPC(String file) throws FileNotFoundException{
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
                }
            }
        }
    } //end loadNPC

    //method to parse room data
    private Room parseRoom(String line) throws IllegalArgumentException {
        String[] parts = line.split("~", 7);

        if (parts.length < 7) {
            throw new IllegalArgumentException("Invalid room data: " + line);
        } //validate rooms are parsing correctly

        int roomNumber = Integer.parseInt(parts[0]);
        String roomName = parts[1];
        String roomDescription = parts[2];
        Room room = new Room(roomNumber, roomName, roomDescription);
        // System.out.println("Parsing room " + roomNumber); //debug statement

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
            for (String itemName : roomItems) {
                if (!itemName.isEmpty()) {
                    for (Items item : items) {
                        if (item.getItemName().equalsIgnoreCase(itemName)) {
                            room.addItem(item);
                            //System.out.println("Item added to room " + itemName); //debug statement
                            break;
                        }
                    }
                }
            }
        }//end item parse

        if (!parts[5].equals("0")) {
            String[] roomPuzzles = parts[5].split("~");
            for (String puzzId : roomPuzzles) {
                if (!puzzId.isEmpty()) {
                    for (Puzzle puzzle : puzzles) {
                        if (puzzle.getPuzzleId().equalsIgnoreCase(puzzId)) {
                            room.addPuzzle(puzzle);
                            //System.out.println("Puzzle added to room " + puzzId); //debug statement
                            break;
                        }
                    }
                }
            }
        } //end puzzle parse

        if (!parts[6].equals("0")) {
            String[] roomMonsters = parts[6].split("~");
            for (String monsterName : roomMonsters) {
                if (!monsterName.isEmpty()) {
                    for (Monster monster: monsters) {
                        if (monster.getName().equals(monsterName)) {
                            room.addMonster(monster);
                            // System.out.println("Monster added to room " + monsterName); //debug statement
                            break;
                        }
                    }
                }
            }
        } //end monster parse

        return room;
    } //end parseRoom

    //method to find room by its number.
    public Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    } //end getRoomByNumber

    //method to determine room direction, navigating rooms by direction
    public Integer getRoomInDirection(Room room, String direction) {
        int index = room.getDirections().indexOf(direction); //index of direction in directions that are available
        if (index != -1) {
            return room.getNextRooms().get(index); //if valid direction return room number of next room in direction
        }
        return null;
    } //end getRoomInDirection

    public ArrayList<Room> getRooms() {
        return rooms;
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
}
