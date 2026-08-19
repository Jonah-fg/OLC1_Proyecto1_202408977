// Run.java
package ejecucion;
import java.util.List;

public class Run {
    private final List<String> matchNames;
    private final int seed;

    public Run(List<String> matchNames, int seed) {
        this.matchNames= matchNames;
        this.seed=seed;
    }

    public List<String> getMatchNames() {
        return matchNames;
    }

    public int getSeed() {
        return seed;
    }
}
