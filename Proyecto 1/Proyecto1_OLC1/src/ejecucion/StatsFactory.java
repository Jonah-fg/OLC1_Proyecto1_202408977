package ejecucion;

public class StatsFactory {
    public static CombatantStats getMageStats() {
        return new CombatantStats(100, 120, 5, 25, 8, 18, 14);
    }
    public static CombatantStats getWarriorStats() {
        return new CombatantStats(140, 100, 22, 0, 20, 8, 10);
    }
}