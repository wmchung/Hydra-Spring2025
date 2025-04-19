public class Consumable extends Item{
    private int uses; //
    private int healingPoints;
    public Consumable(String itemID, String itemName, String itemDescription, String tag, String roomID, int uses, int healingPoints) {
        super(itemID, itemName, itemDescription, tag, roomID);
        this.uses=uses;
        this.healingPoints=healingPoints;
    }

    public int getUses() {
        return uses;
    }

    public int getHealingPoints() {
        return healingPoints;
    }
}
