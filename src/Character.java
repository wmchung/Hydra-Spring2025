public abstract class Character {
    protected String name;
    protected int health;
    protected int baseAttack;
    protected int attackPower;

    public Character(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.baseAttack = attackPower;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getAttackPower() {
        return baseAttack;
    };

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        System.out.println(name + " took " + damage + " damage. Health now: " + health);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void attack(Character target) {
        int power = getAttackPower();
        System.out.println(name + " attacks " + target.getName() + " for " + power + " damage!");
        target.takeDamage(power);
    }

    public void flee() {
        System.out.println(this.name + " attempts to flee from battle!");
    }
}


