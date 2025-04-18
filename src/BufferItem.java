public class BufferItem extends Item {
    //buffs players damage
    //buffs players health capacity
    private double enemyHitMiss;
    private double playerHitChance;
    private boolean isUsed;
    public BufferItem(int itemID, String itemName, String itemDescription, String tag, String location, double enemyHitMiss, double playerHitChance, boolean isUsed) {
        super(itemID, itemName, itemDescription, tag,location);
        this.enemyHitMiss= enemyHitMiss;
        this.playerHitChance= playerHitChance;
        this.isUsed=isUsed;
    }
}
