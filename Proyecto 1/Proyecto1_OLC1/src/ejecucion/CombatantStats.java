package ejecucion;

public class CombatantStats{
    private final int maxHealth, maxResource, physicalAttack, magicPower, armor, magicResistance, speed;
    public CombatantStats(int maxHealth, int maxResource, int physicalAttack, int magicPower, int armor, int magicResistance, int speed) {
        this.maxHealth =maxHealth;
        this.maxResource =maxResource;
        this.physicalAttack= physicalAttack;
        this.magicPower =magicPower;
        this.armor=armor;
        this.magicResistance=magicResistance;
        this.speed=speed;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public int getMaxResource() {
        return maxResource;
    }

    public int getPhysicalAttack() {
        return physicalAttack;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public int getArmor() {
        return armor;
    }

    public int getMagicResistance() {
        return magicResistance;
    }

    public int getSpeed(){
        return speed;
    }
}