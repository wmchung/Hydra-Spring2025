package Model;

public class Enemy {
    private String enemyID;
    private String name;
    private String region;
    private String roomID;
    private int health;
    private String damageRange;  // e.g., "0-3"
    private int threshold;
    private String fleeText;
    private String winText;
    private String loseText;
    private boolean defeated;
    private boolean isAlive;


    public Enemy(String enemyID, String name, String region, String roomID,
                 int health, String damageRange, int threshold,
                 String fleeText, String winText, String loseText) {
        this.enemyID = enemyID;
        this.name = name;
        this.region = region;
        this.roomID = roomID;
        this.health = health;
        this.damageRange = damageRange;
        this.threshold = threshold;
        this.fleeText = fleeText;
        this.winText = winText;
        this.loseText = loseText;
    }


    public int getAttackPower() {
        // Optional: parse and return a random value from damageRange
        String[] parts = damageRange.split("-");
        int min = Integer.parseInt(parts[0]);
        int max = Integer.parseInt(parts[1]);
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    // Getters for each field
    public String getEnemyID() { return enemyID; }
    public String getName() {return name;}
    public String getRegion() { return region; }
    public String getRoomID() { return roomID; }
    public int getHealth() {return health;}
    public int getThreshold() { return threshold; }
    public String getFleeText() { return fleeText; }
    public String getWinText() { return winText; }
    public String getLoseText() { return loseText; }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            defeated = false;
            System.out.println(name + " has been defeated!");
        } else {
            System.out.println(name + " took " + damage + " damage. Health now: " + health);
        }
    }
    @Override
    public String toString() {
        return enemyID + ": " + name + " (" + region + ", " + roomID + ") HP: " + health +
                " | Damage: " + damageRange + " | Threshold: " + threshold;
    }
}