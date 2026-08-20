package ejecucion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Combatant {
    private final Strategy strategy;
    private final CombatantStats stats;
    private final Random random;

    // Estado actual
    private int health;
    private int resource;
    private int score;
    private List<ActionType> history;
    private boolean defenseActive;
    private int warCryBonus;

    public Combatant(Strategy strategy, long seed) {
        this.strategy=strategy;
        this.stats= strategy.isMage() ? StatsFactory.getMageStats() : StatsFactory.getWarriorStats();
        this.random = new Random(seed);
        this.health =stats.getMaxHealth();
        this.resource =stats.getMaxResource();
        this.score=0;
        this.history=new ArrayList<>();
        this.defenseActive=false;
        this.warCryBonus = 0;
    }

    // Getters y setters
    public Strategy getStrategy() {
        return strategy;
    }

    public CombatantStats getStats() {
        return stats;
    }

    public Random getRandom() {
        return random;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health=Math.max(0, Math.min(health, stats.getMaxHealth()));
    }

    public int getResource(){
        return resource;
    }
    public void setResource(int resource) {
        this.resource=Math.max(0, Math.min(resource, stats.getMaxResource()));
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score){
        this.score=Math.max(0, score);
    }

    public List<ActionType> getHistory() {
        return history;
    }

    public void addHistory(ActionType action) {
        history.add(action);
    }

    public boolean isDefenseActive() {
        return defenseActive;
    }
    public void setDefenseActive(boolean defenseActive) {
        this.defenseActive=defenseActive;
    }

    public int getWarCryBonus() {
        return warCryBonus;
    }
    public void setWarCryBonus(int warCryBonus) {
        this.warCryBonus = warCryBonus;
    }

    // Método para obtener un valor aleatorio
    public double nextRandom(){
        return random.nextDouble();
    }
}