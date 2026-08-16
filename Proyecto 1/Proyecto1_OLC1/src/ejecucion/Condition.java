package ejecucion;

@FunctionalInterface
public interface Condition{
    boolean evaluate(CombatContext context);
}