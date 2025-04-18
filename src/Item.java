/**
 * Items:
 * Define item properties: Items can be consumables, weapons, keys, or artifacts, each having unique traits such as value and effects.
 * Support gameplay interaction: Items can boost player stats, heal damage, or require completing specific objectives.
 * Exists in various contexts: Items can be found in rooms or stored in player’s inventory, must be portable and independent
 */
public class Item {
    private int itemID;
    private String itemName;
    private String itemDescription;

    private String tag; //labels the item as weapon, healing, keys, or artifacts

    private String location;

    public Item(int itemID, String itemName, String itemDescription, String tag, String location) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.tag = tag;

        this.location= location;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getTag() {
        return tag;
    }

    public int getHealPoints() {
        return healPoints;
    }

    public int getDamagePoints() {
        return damagePoints;
    }

    public String getLocation() {
        return location;
    }
}
