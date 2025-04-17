public class Enemy extends Character {
    private String enemyID;
    private String region;
    private String roomID;
    private String damageRange;  // e.g., "0-3"
    private int threshold;
    private String fleeText;
    private String winText;
    private String loseText;

    public Enemy(String enemyID, String name, String region, String roomID,
                 int health, String damageRange, int threshold,
                 String fleeText, String winText, String loseText) {
        super(name, health, 0); // baseAttack can be updated later if needed
        this.enemyID = enemyID;
        this.region = region;
        this.roomID = roomID;
        this.damageRange = damageRange;
        this.threshold = threshold;
        this.fleeText = fleeText;
        this.winText = winText;
        this.loseText = loseText;
    }

    @Override
    public int getAttackPower() {
        // Optional: parse and return a random value from damageRange
        String[] parts = damageRange.split("-");
        int min = Integer.parseInt(parts[0]);
        int max = Integer.parseInt(parts[1]);
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    // Getters for each field
    public String getEnemyID() { return enemyID; }
    public String getRegion() { return region; }
    public String getRoomID() { return roomID; }
    public int getThreshold() { return threshold; }
    public String getFleeText() { return fleeText; }
    public String getWinText() { return winText; }
    public String getLoseText() { return loseText; }

    @Override
    public String toString() {
        return enemyID + ": " + name + " (" + region + ", " + roomID + ") HP: " + health +
                " | Damage: " + damageRange + " | Threshold: " + threshold;
    }
}