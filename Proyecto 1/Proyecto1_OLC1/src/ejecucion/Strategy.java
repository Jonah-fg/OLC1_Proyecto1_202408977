package ejecucion;

import java.util.List;

public class Strategy {
    private final String name;
    private final boolean isMage;
    private final ActionType initialAction;
    private final List<Rule> rules;
    private final ActionType defaultAction;

    public Strategy(String name, boolean isMage, ActionType initialAction, List<Rule> rules, ActionType defaultAction) {
        this.name=name;
        this.isMage =isMage;
        this.initialAction=initialAction;
        this.rules=rules;
        this.defaultAction=defaultAction;
    }
    public String getName() {
        return name;
    }

    public boolean isMage() {
        return isMage;
    }

    public ActionType getInitialAction() {
        return initialAction;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public ActionType getDefaultAction() {
        return defaultAction;
    }
}
