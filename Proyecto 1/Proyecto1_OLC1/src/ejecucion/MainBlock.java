package ejecucion;
import java.util.List;

public class MainBlock{
    private final List<Run> runs;
    public MainBlock(List<Run> runs) {
        this.runs = runs;
    }
    public List<Run> getRuns() {
        return runs;
    }
}
