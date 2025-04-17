import java.util.HashMap;
import java.util.Map;

public class Room {
    private String type;
    private int roomId;
    private String name;
    private String description;
    private Enemy enemy;
    private boolean visited;
    private Map<String, Integer> exits;
    private Map<String, Items> items = new HashMap<>();//Item Name and description
    private Puzzle puzzle;

    public Room(int roomId, String name, String description) {
        this.roomId=roomId;
        this.type=type;
        this.name=name;
        this.description = description;
        this.visited=true;
        this.exits=new HashMap<>();
        this.items=new HashMap<>();
    }
    // Method to add an exit and associate room number with direction
    public void addExit(String direction, int roomNumber) {
        exits.put(direction, roomNumber);
    }

    // Method to mark the room as visited
    public void visit() {
        boolean visited = true;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    //method to add items to inventory
    public void addItem(Items item) {
        items.put(item.getName(), item);
    }

    //method to remove items from room
    public boolean removeItem(String itemName) {
        return items.remove(itemName) != null;
    }

    //method to check if an item is in a room
    public boolean hasItem(String itemName){
        return items.containsKey(itemName);
    }
//Getters - gets value of each variable

    public int getRoomId() {
        return roomId;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;

    }

    public boolean isVisited() {
        return visited;
    }

    public Map<String, Integer> getExits() {
        return exits;
    }

    public Items getItem(String itemName) {
        return items.get(itemName);
    }

    public String getItemDescription(String itemName) {
        Items item = items.get(itemName);
        return (item != null) ? item.getItemDescription() : "No description available.";
    }
    public Map<String, Items> getItems() {
        return items;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }
    public String getName() {
        return name;

    }

    public Enemy getEnemy() {

        return enemy;
    }
    //Setter
    public void setMonster(Enemy enemy) {
        this.enemy = enemy;
    }

}


