package ejecucion;

import java.util.List;

public class Bonuses {
    private final List<ActionType> mageCombo;
    private final int mageComboPoints;
    private final List<ActionType> warriorCombo;
    private final int warriorComboPoints;
    private final int lowHealthVictory;

    public Bonuses(List<ActionType> mageCombo, int mageComboPoints, List<ActionType> warriorCombo, int warriorComboPoints, int lowHealthVictory) {
        this.mageCombo=mageCombo;
        this.mageComboPoints= mageComboPoints;
        this.warriorCombo =warriorCombo;
        this.warriorComboPoints= warriorComboPoints;
        this.lowHealthVictory=lowHealthVictory;
    }

    public List<ActionType> getMageCombo() {
        return mageCombo;
    }

    public int getMageComboPoints(){
        return mageComboPoints;
    }

    public List<ActionType> getWarriorCombo(){
        return warriorCombo;
    }

    public int getWarriorComboPoints() {
        return warriorComboPoints;
    }

    public int getLowHealthVictory(){
        return lowHealthVictory;
    }
}
