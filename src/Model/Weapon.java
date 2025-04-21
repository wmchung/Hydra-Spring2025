package Model;

public class Weapon extends Item {
    private int damagePoints;
    private int amount;
    public Weapon(String itemID, String itemName, String itemDescription, String tag, String roomID, int amount, int damagePoints) {
        super(itemID, itemName, itemDescription, tag, roomID);
        this.amount=amount;
        this.damagePoints=damagePoints;
    }
    public int getDamagePoints() {
        return damagePoints;
    }

    public int getAmount() {
        return amount;
    }

    public int getAttackPower() {
        return (getTag() != null && getTag().equalsIgnoreCase("weapon")) ? damagePoints : 0;
    }
}

