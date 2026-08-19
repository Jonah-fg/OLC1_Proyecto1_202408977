package ejecucion;

import java.util.List;

public class Match {
    private final String name;
    private final List<String> players; // Nombres de las estrategias
    private final int rounds;
    private final Scoring scoring;
    private final Bonuses bonuses;

    public Match(String name, List<String> players, int rounds, Scoring scoring, Bonuses bonuses) {
        this.name =name;
        this.players =players;
        this.rounds =rounds;
        this.scoring =scoring;
        this.bonuses = bonuses;
    }

    // Getters...
    public String getName() {
        return name;
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getRounds() {
        return rounds;
    }

    public Scoring getScoring() {
        return scoring;
    }

    public Bonuses getBonuses() {
        return bonuses;
    }
}