import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Iterator;


//Instance variables and attributes
public class Player extends Character {
    private Room currentRoom;
    private List<Item> inventory;
    private Item equippedItem;

    private int maxHP;
    private boolean isAlive;
    private boolean hasWon;

    private List<String> defeatedEnemies;
    private List<String> completedPuzzles;

    private int buffAmount; // Temporary attack buff amount

    //constructor to initialize player with a name and starting room
    public Player(String name, int health, int attackPower, Room startingRoom) {
        super(name, health, attackPower);
        this.currentRoom = startingRoom;
        this.maxHP = health;
        this.isAlive = true;
        this.hasWon = false;
        this.inventory = new ArrayList<>();
        this.defeatedEnemies = new ArrayList<>();
        this.completedPuzzles = new ArrayList<>();
        this.buffAmount = 0;
    }

    //Getters


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

        return health;
    }

    public int getMaxHP() {
        return maxHP;
    }

//    @Override
//    public int getAttackPower(String itemName) {
//
//        Weapon weapon = (Weapon) equippedItem;
//        if (equippedItem != null) {//? equippedItem.getAttackPower() : 0;
//
//        return baseAttack + boost + buffAmount;
//    } }
    public void pickUpItem(String itemName) {
        List<Item> items = currentRoom.getItems();
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.add(item);
                currentRoom.removeItem(item);
                System.out.println("Picked up: " + item.getName() + " from " + currentRoom.getRoomId() + " room.");
                return;
            }
        }
        System.out.println("Item not found in the room.");
    }

    public void addBuff(int amount) {
        buffAmount += amount;
    }

    public void clearBuff() {
        buffAmount = 0;
    }

    public void addDefeatedEnemy(String enemyID) {
        defeatedEnemies.add(enemyID);
    }

    public boolean hasDefeated(String enemyID) {
        return defeatedEnemies.contains(enemyID);
    }

    public void addCompletedPuzzle(String puzzleID) {
        completedPuzzles.add(puzzleID);
    }

    public boolean hasSolved(String puzzleID) {
        return completedPuzzles.contains(puzzleID);
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
    public void move(String direction, Map<String, Room> rooms) {
        if (currentRoom.getExits().containsKey(direction)) {
            String nextRoomId = currentRoom.getExits().get(direction);
            if (rooms.containsKey(nextRoomId)) {
                currentRoom = rooms.get(nextRoomId);
                System.out.println("You moved " + direction + " to " + currentRoom.getRoomId());
            } else {
                System.out.println("Room ID " + nextRoomId + " not found in map.");
            }
        } else {
            System.out.println("No exit in that direction.");
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    //method to pickup item form current room
    public void pickUpItem(Item itemName) {
//        if (currentRoom.hasItem(itemName)) { // Prevent adding null/empty items
//            Item item = (Item) currentRoom.getItems(itemName);
//            inventory.add(item);
//            currentRoom.removeItem(itemName);
//            System.out.println(itemName + " was added to Inventory");
//        } else {
//            System.out.println("Item not found in room");
//        }
        if (currentRoom.getItems().contains(itemName)) { //suggestion
            currentRoom.removeItem(itemName);
            inventory.add(itemName);
            System.out.println(itemName + " was added to Inventory");
        } else {
            System.out.println("Item not found in room");
        }
//        public void pickUpItem(Item itemName) { //suggestion
//            Item itemToPickUp = null;
//
//            // Search the room's items list for a matching item name
//            for (Item item : currentRoom.getItems()) {
//                if (item.getItemName().equalsIgnoreCase(itemName)) {
//                    itemToPickUp = item;
//                    break;
//                }
//            }
//
//            if (itemToPickUp != null) {
//                inventory.add(itemToPickUp);
//                currentRoom.removeItem(itemToPickUp);
//                System.out.println(itemName + " was added to Inventory");
//            } else {
//                System.out.println("Item not found in room");
//            }
//        }
    }

    // Method to remove an item from the player's inventory
//    public void inspectItem(String itemName) {
//        for (Item item : inventory) {
//            if (item.getItemName().equalsIgnoreCase(itemName)) {
//                System.out.println("Item:" + item.getName());
//                System.out.println("Description:" + item.getItemDescription());
//                System.out.println("Attack Boost:" + item.getAttackPower());
//
//                return;
//            }
//        }
//
//        System.out.println("Item not in inventory");
//
//    }
//    //Method to drop item

    public void inspectItem(Item item) {
        if (inventory.contains(item)) {
            System.out.println("Item: " + item.getItemDescription());
            if (item instanceof Consumable) {
                System.out.println("Item Health Points: " + ((Consumable) item).getHealingPoints());
            } else if (item instanceof Weapon) {
                System.out.println("Item Attack Points: " + ((Weapon) item).getAttackPower());
            }
        }
    }//end inspectItem

    public void dropItem(String itemName) {
        Iterator<Item> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                iterator.remove();
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
                System.out.println(item.getItemName() + ":" + item.getItemDescription());
            }
        }
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            isAlive = false;
            System.out.println("You have been defeated!");
        } else {
            System.out.println("You were hit and lost " + amount + " HP! Current HP: " + health);
        }
    }


    //method to equip or unequip items
//    public void equipItem(String itemName) {
//        for (Item item : inventory) {
//            if (item.getItemName().equalsIgnoreCase(itemName) && item.getAttackPower() > 0) {
//                if (equippedItem != null) {
//                    equippedItem.setEquipped(false);
//                }
//                equippedItem = item;
//                item.setEquipped(true);
//                System.out.println(item.getItemName() + " equipped. Attack boosted by " + item.getAttackPower());
//                return;
//            }
//        }
//        System.out.println("No such weapon item found in inventory.");
//    }

    public void equipItem(String itemName) { //suggestion
        for (Item item : inventory) {
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                if (equippedItem != null) {
                    unEquipItem(); //unequip the current item first
                }
                equippedItem = weapon;
                attackPower = attackPower + weapon.getAttackPower();
                System.out.println(weapon.getName() + " equipped. Attack damage increased to " + attackPower);
            } else {
                System.out.println("Item is not a weapon or not found in inventory.");
            }
        }
    }

    public void unEquipItem() {
        if (equippedItem != null) {
            System.out.println(equippedItem.getName() + " has been unequipped");
            equippedItem.setEquipped(false);
            equippedItem = null;
        } else {
            System.out.println("No item is currently equipped");
        }
    }


//    public void heal(String itemName) {
//        for (Item item : inventory) {
//            if (item.getItemName().equalsIgnoreCase(itemName) && item.getHealingPoints() > 0) {
//                health = Math.min(maxHP, health + item.getHealPoints());
//                System.out.println("You healed " + item.getHealPoints() + " HP. Current HP: " + health);
//                inventory.remove(item);
//                return;
//            }
//        }
//        System.out.println("No healing item found in inventory");
//    }
    public void heal(String itemName) { //suggestion
        for (Item item : inventory) {
         if (item instanceof Consumable) {
            Consumable consumable = (Consumable) item;
            //health += consumable.getItemHP(); // No cap on health
            health = Math.min(maxHP, health + ((Consumable) item).getHealingPoints());
            inventory.remove(consumable); // Remove the consumable after use
            System.out.println("You healed " + consumable.getHealingPoints() + " HP. Current HP: " + health);
        } else {
            System.out.println("No healing item found in inventory.");
         }
     }
    }

}