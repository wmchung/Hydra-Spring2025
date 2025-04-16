import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

//Instance variables and attributes
public class Player {
    private String name;
    private Room currentRoom;
    private List<Item> inventory = new ArrayList<>();
    private int baseAttack;
    private int currentHP;
    private int maxHP;
    private Item equippedItem;

    //constructor to initialize player with a name and starting room
    public Player(String name, Room startingroom) {
        this.name = name;
        this.currentRoom = startingroom;
        this.baseAttack = 10;
        this.currentHP = 100;
        this.maxHP = 100;
        this.inventory = new ArrayList<>();
    }

    //Getters

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public Item getEquippedItem() {
        return equippedItem;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getAttackPower() {
        return (equippedItem != null) ? baseAttack + equippedItem.getAttackBoost() : baseAttack;
    }

    public int getEffectiveAttackPower() {
        Random rand = new Random();
        int roll = rand.nextInt(100); // 0–99
        if (roll < 25) { // 25% chance to crit.
            System.out.println("Critical hit!");
            return getAttackPower() * 2;
        }
        return getAttackPower(); // normal hit
    }

    //Setter to change players current room
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    //Method to move player in direction
    public void move(String direction, Map<Integer, Room> rooms) {
        //Validate direction and room exits
        if (currentRoom.getExits().containsKey(direction)) {
            int nextRoomNumber = currentRoom.getExits().get(direction);
            //Check if the next room exists in the map
            if (rooms.containsKey(nextRoomNumber)) {
                //move player to new room
                currentRoom = rooms.get(nextRoomNumber);
                System.out.println("You moved " + direction + " to" + currentRoom.getName());
            } else {
                System.out.println("You can't go that way!");
            }
        }
    }

    //method to pickup item form current room
    public void pickUpItem(String itemName) {
        if (currentRoom.hasItem(itemName)) { // Prevent adding null/empty items
            Item item = currentRoom.getItem(itemName);
            inventory.add(item);
            currentRoom.removeItem(itemName);
            System.out.println(itemName + " was added to Inventory");
        } else {
            System.out.println("Item not found in room");
        }
    }

    // Method to remove an item from the player's inventory
    public void inspectItem(String itemName) {
        for(Item item : inventory){
            if (item.getName().equalsIgnoreCase(itemName)) {
                System.out.println("Item:" + item.getName());
                System.out.println("Description:" + item.getItemDescription());
                System.out.println("Attack Boost:" + item.getAttackBoost());

                return;
            }
        }

        System.out.println("Item not in inventory");

    }
    //Method to drop item

    public void dropItem(String itemName) {
        for(Item item : inventory){
            if(item.getName().equalsIgnoreCase(itemName)){
                inventory.remove(item);
                currentRoom.addItem(item);
                System.out.println(itemName + " has been dropped from inventory");
                return;
            }
        }
        System.out.println("You don't have that item");

    }

    // Method to display the player's inventory
    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory: ");
            for (Item item : inventory) {
                System.out.println(item.getName() + ":" + item.getItemDescription());
            }
        }
    }

    public void takeDamage(int amount) {
        currentHP -= amount;
        if (currentHP < 0) {
            currentHP = 0;
        }
        System.out.println("You were hit and lost " + amount + " HP! Current HP: " + currentHP);
    }


    //method to equip or unequip items
    public void equipItem(String itemName){
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName) && item.getAttackBoost() > 0) {
                if (equippedItem != null) {
                    equippedItem.setEquipped(false);
                }
                equippedItem = item;
                item.setEquipped(true);
                System.out.println(item.getName() + " equipped. Attack boosted by " + item.getAttackBoost());
                return;
            }
        }
        System.out.println("No such weapon item found in inventory.");
    }
    public void unEquipItem(){
        if(equippedItem!=null) {
            System.out.println(equippedItem.getName() + " has been unequipped");
            equippedItem.setEquipped(false);
            equippedItem = null;
            System.out.println();
        } else {
            System.out.println("No item is currently equipped");
        }
    }
    public void heal(String itemName){
        for (Item item: inventory){
            if(item.getName().equalsIgnoreCase(itemName) && item.getHealAmount() > 0){
                currentHP = Math.min(maxHP, currentHP + item.getHealAmount());
                System.out.println("You healed " + item.getHealAmount() + " HP. Current HP: " + currentHP);
                inventory.remove(item);
                return;
            }
        }
        System.out.println("No healing item found in inventory");
    }
}

