package Model;

public class BufferItem extends Item {
    //buffs players damage
    //buffs players health capacity
    private double enemyHitMiss;
    private double playerHitChance;
    private boolean isUsed;
    public BufferItem(String itemID, String itemName, String itemDescription, String tag, String roomID, double enemyHitMiss, double playerHitChance, boolean isUsed) {
        super(itemID, itemName, itemDescription, tag,roomID);
        this.enemyHitMiss= enemyHitMiss;
        this.playerHitChance= playerHitChance;
        this.isUsed=isUsed;
    }

    public double getEnemyHitMiss() {
        return enemyHitMiss;
    }

    public double getPlayerHitChance() {
        return playerHitChance;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
