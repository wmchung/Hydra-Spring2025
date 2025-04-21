package Model;

import java.io.FileNotFoundException;
import java.util.*;

//Instance variables and attributes
public class Player {
    private Room currentRoom;
    private GameMap gameMap;
    private List<Item> inventory; //inventory
    private List<String> defeatedEnemies;
    private List<String> completedPuzzles;
    private int health; //health
    private Item equippedItem;
    private int maxHP;
    private boolean isAlive;
    private boolean hasWon;
    private int buffAmount; // Temporary attack buff amount
    private Set<String> collectedKey;
    private String currentCheckpoint;
    private int attackPower;


    //constructor to initialize player with a name and starting room
    public Player(String itemsFile, String puzzlesFile, String enemiesFile, String NPCsFile, String roomsFile) throws FileNotFoundException {
        gameMap = new GameMap(itemsFile, puzzlesFile, enemiesFile, NPCsFile, roomsFile);
        currentRoom = gameMap.getRoom("SR01");
        inventory = new ArrayList<>();
        completedPuzzles = new ArrayList<>();
        defeatedEnemies = new ArrayList<>();
        health = 15;
        attackPower = 5;
    }

    public Player() throws FileNotFoundException {
        gameMap = new GameMap();
        currentRoom = gameMap.getRoom("SR01");
        inventory = new ArrayList<>();
        completedPuzzles = new ArrayList<>();
        defeatedEnemies = new ArrayList<>();
        health = 15;
        attackPower = 5;
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

    public int getHealth() {

        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public String getCurrentCheckpoint() {
        return currentCheckpoint;
    }

    public int getMaxHP() {
        return maxHP;
    }

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

    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
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
    public void setCurrentRoom(Room currentRoom) {

        this.currentRoom = currentRoom;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public void collectKey(String key) {
        if (key != null && !key.isEmpty()) {
            collectedKey.add(key);
            System.out.println("Key collected: " + key);
        }
    }

    public boolean hasKey(String key) {
        return collectedKey.contains(key);
    }

    public boolean hasAllKeys(Set<String> requiredKeys) {
        return collectedKey.containsAll(requiredKeys);
    }

    public Set<String> getCollectedKeys() {
        return collectedKey;
    }


    public void pickUpItem(Item itemName) {
        if (currentRoom.getItems().contains(itemName)) { //suggestion
            currentRoom.removeItem(itemName);
            inventory.add(itemName);
            System.out.println(itemName + " was added to Inventory");
        } else {
            System.out.println("Item not found in room");
        }
    }

    public void examineItem(Item item) {
        if (inventory.contains(item)) {
            System.out.println("Item: " + item.getItemDescription());
            if (item instanceof Consumable) {
                System.out.println("Item Health Points: " + ((Consumable) item).getHealingPoints());
            } else if (item instanceof Weapon) {
                System.out.println("Item Attack Points: " + ((Weapon) item).getAttackPower());
            }
        }
    }//end examineItem

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
            if (!collectedKey.isEmpty()) {
                System.out.println("Keys: ");
                for (String key : collectedKey) {
                    System.out.println("- " + key);
                }
            }
        }
    }

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

    public void heal(String itemName) {
        for (Item item : inventory) {
            if (item instanceof Consumable) {
                Consumable consumable = (Consumable) item;
                if (item.getItemName().equalsIgnoreCase(itemName)) {
                    //health += consumable.getItemHP(); // No cap on health
                    health = Math.min(maxHP, health + consumable.getHealingPoints());
                    inventory.remove(consumable); // Remove after use
                    System.out.println("You healed " + consumable.getHealingPoints() + " HP. Current HP: " + health);
                    return;
                }
            }
        }
        System.out.println("No healing item found in inventory.");
    }

    public void handleMovement(String command) {
        if (command == null || command.isEmpty()) {
            System.out.println("Invalid direction.");
            return;
        }

        String direction = gameMap.mapDirection(command);
        if (direction == null) {
            System.out.println("Invalid direction. Use N, E, S, W or full directions.");
            return;
        }

        Room nextRoom = gameMap.getRoomInDirection(this.currentRoom, direction);
        if (nextRoom != null && !nextRoom.isLocked()) {
            this.setCurrentRoom(nextRoom);
            nextRoom.checkVisitedRoom();
            nextRoom.hasPuzzle();
        } else {
            System.out.println("You can't go that way.");
            System.out.println("Available exits: " + this.currentRoom.getExits().keySet());
        }
    }

    public void talkToNPC(String npcId) {
        if (currentRoom.hasNPC()) {
            for (NPC npc : currentRoom.getNpcs()) {
                if (npc.getNpcId().equalsIgnoreCase(npcId)) {
                    System.out.println(npc.getInteraction());
                    return;
                }
            }
        } else {
            System.out.println("No NPCs in this room.");
        }
    }


    public void look() {
        currentRoom.checkVisitedRoom();

        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("Items in the room:");
            for (Item item : currentRoom.getItems()) {
                System.out.println("- " + item.getItemName());
            }
        }

        if (!currentRoom.getEnemies().isEmpty()) {
            System.out.println("Enemies nearby:");
            for (Enemy enemy : currentRoom.getEnemies()) {
                System.out.println("- " + enemy.getName());
            }
        }

        if (!currentRoom.getPuzzles().isEmpty()) {
            System.out.println("There seems to be a puzzle here." );
            for (Puzzle puzzle : currentRoom.getPuzzles()) {
                System.out.println("Puzzle ID: " + puzzle.getPuzzleId());
                System.out.println(puzzle.getDescription());
            }
            System.out.println();
        }

        if (!currentRoom.getNpcs().isEmpty()) {
            System.out.println("You notice:");
            for (NPC npc : currentRoom.getNpcs()) {
                System.out.println("NPC ID: " + npc.getNpcId());
                System.out.println("- " + npc.getDescription());
            }
        }
    }

    public void setHealth(int health) {
        this.health = Math.min(health, maxHP);
    }

    public void setCurrentCheckpoint(String checkpoint) {
        this.currentCheckpoint = checkpoint;
    }
}

