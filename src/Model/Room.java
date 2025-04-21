package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private String roomId;
    private String roomArea; //check back on this
    private String roomType;
    private String description;
    private boolean visited;
    private boolean locked;
    private boolean checkpoint;
    private String keyItem;
    private Map<String, Room> exits;
    //private Map<String, Item> items = new HashMap<>();//Item Name and description
    private List<Item> items;
    private List<Enemy> enemies;
    private List<Puzzle> puzzles;
    private List<NPC> npcs;

    public Room(String roomId, String roomArea, String roomType, String description) {
        this.roomId = roomId;
        this.roomArea = roomArea;
        this.roomType = roomType;
        this.description = description;
        this.visited = false;
        this.locked = false;
        this.checkpoint = false;
        this.keyItem = null;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.puzzles = new ArrayList<>();
        this.npcs = new ArrayList<>();
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomArea() {
        return roomArea;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isCheckpoint() {
        return checkpoint;
    }

    public String getKeyItem() {
        return keyItem;
    }

    public boolean isLocked() {
        return locked;
    }

    public Map<String, Room> getExits() {
        return exits;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    // Methods to add elements
    public void addExit(String direction, Room room) {
        exits.put(direction.toUpperCase(), room); // Store direction in uppercase for consistency
    }
    public void addItem(Item item) {
        items.add(item);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addPuzzle(Puzzle puzzle) {
        puzzles.add(puzzle);
    }

    public void addNpc(NPC npc) {
        npcs.add(npc);
    }

    // Methods to remove elements
    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void removePuzzle(Puzzle puzzle) {
        puzzles.remove(puzzle);
    }

    public void removeNpc(NPC npc) {
        npcs.remove(npc);
    }

    // Mark room as visited

    public void checkVisitedRoom() {
        if (visited) {
            System.out.println("You've been here before.");
            System.out.println(this.getDescription());
        } else {
            System.out.println(this.getDescription());
            visited = true;
        }
    }

    public boolean unlock(String item) {
        if (locked && keyItem != null && keyItem.equalsIgnoreCase(item)) {
            locked = false;
            System.out.println(getDescription());
            return true;
        }
        System.out.println("Incomplete tasks");
        return false;
    }

    public void updateCheckpointStatus() {
        if (hasNPC()) {
            setCheckpoint(true);
        }
    }

    public boolean hasEnemy() {
        return enemies != null && !enemies.isEmpty();
    }

    public boolean hasPuzzle() {
        return puzzles != null && !puzzles.isEmpty();
    }

    public boolean hasItem() {
        return items != null && !items.isEmpty();
    }

    public boolean hasNPC() {
        return npcs != null && !npcs.isEmpty();
    }

    public void setVisited(boolean isVisited) {
        this.visited = isVisited;
    }

    public void setLocked(boolean isLocked) {
        this.locked = isLocked;
    }

    public void setCheckpoint(boolean checkpoint) {
        this.checkpoint = checkpoint;
    }

}