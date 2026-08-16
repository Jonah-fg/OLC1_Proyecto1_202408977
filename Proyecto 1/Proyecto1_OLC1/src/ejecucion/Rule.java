package ejecucion;

public class Rule{

    private final Condition condition;
    private final ActionType action;

    public Rule(Condition condition, ActionType action){
        this.condition=condition;
        this.action =action;
    }

    public boolean evaluate(CombatContext context) {
        return condition.evaluate(context);
    }

    public ActionType getAction() {
        return action;
    }
}