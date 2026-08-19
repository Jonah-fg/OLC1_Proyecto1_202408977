package ejecucion;
import java.util.List;

public class Program {
    private final List<Strategy> strategies;
    private final List<Match> matches;
    private final MainBlock main;

    public Program(List<Strategy> strategies, List<Match> matches, MainBlock main) {
        this.strategies=strategies;
        this.matches =matches;
        this.main =main;
    }
    // Getters...
    public List<Strategy> getStrategies() {
        return strategies;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public MainBlock getMain() {
        return main;
    }
}