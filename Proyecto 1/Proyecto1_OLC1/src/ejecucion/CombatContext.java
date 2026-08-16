package ejecucion;

import java.util.List;

public class CombatContext {
    private int roundNumber;
    private int totalRounds;
    private int selfHealth;
    private int opponentHealth;
    private int selfResource;
    private int opponentResource;
    private int selfScore;
    private int opponentScore;
    private List<ActionType> selfHistory, opponentHistory;
    private double randomValue;

    // Getters y setters
    public int getRoundNumber() {
        return roundNumber;
    }
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getTotalRounds() {
        return totalRounds;
    }
    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public int getSelfHealth() {
        return selfHealth;
    }
    public void setSelfHealth(int selfHealth) {
        this.selfHealth = selfHealth;
    }

    public int getOpponentHealth() {
        return opponentHealth;
    }
    public void setOpponentHealth(int opponentHealth) {
        this.opponentHealth = opponentHealth;
    }

    public int getSelfResource() {
        return selfResource;
    }
    public void setSelfResource(int selfResource) {
        this.selfResource = selfResource;
    }

    public int getOpponentResource() {
        return opponentResource;
    }
    public void setOpponentResource(int opponentResource) {
        this.opponentResource=opponentResource;
    }

    public int getSelfScore() {
        return selfScore;
    }
    public void setSelfScore(int selfScore) {
        this.selfScore = selfScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }
    public void setOpponentScore(int opponentScore) {
        this.opponentScore=opponentScore;
    }

    public List<ActionType> getSelfHistory() {
        return selfHistory;
    }
    public void setSelfHistory(List<ActionType> selfHistory) {
        this.selfHistory=selfHistory;
    }

    public List<ActionType> getOpponentHistory() {
        return opponentHistory;
    }
    public void setOpponentHistory(List<ActionType> opponentHistory) {
        this.opponentHistory = opponentHistory;
    }

    public double getRandomValue() {
        return randomValue;
    }
    public void setRandomValue(double randomValue) { this.randomValue = randomValue; }
}