package Main;

import ejecucion.*;

import java.util.ArrayList;
import java.util.List;

public class TestSeleccionAccion {

    public static void main(String[] args) {
        System.out.println("------Prueba de selección de acción------\n");

        //estrategia para Merlin (Mago) con regla: si vida <= 30 se debe curar
        Condition condition= ctx->ctx.getSelfHealth()<=30;
        Rule ruleHeal= new Rule(condition, ActionType.HEALING_RUNE);

        List<Rule> rulesMerlin= new ArrayList<>();
        rulesMerlin.add(ruleHeal);
        //acción por defecto ARCANE_BOLT
        Strategy merlin= new Strategy("Merlin", true, ActionType.ARCANE_BOLT, rulesMerlin, ActionType.ARCANE_BOLT);

        // estrategia para Ragnar (Guerrero) sin reglas, solo default
        List<Rule> rulesRagnar =new ArrayList<>();
        Strategy ragnar=new Strategy("Ragnar", false, ActionType.SLASH, rulesRagnar, ActionType.SLASH);

        //Lista de estrategias
        List<Strategy> strategies=new ArrayList<>();
        strategies.add(merlin);
        strategies.add(ragnar);

        //partida de prueba (Match) con datos mínimos
        Scoring scoring= new Scoring(1, 1, 20, 100, 10);
        Bonuses bonuses=new Bonuses(new ArrayList<>(), 0, new ArrayList<>(), 0, 25);
        List<String> players = new ArrayList<>();
        players.add("Merlin");
        players.add("Ragnar");
        Match match=new Match("DueloPrueba", players, 10, scoring, bonuses);

        CombatEngine engine=new CombatEngine(match, 42L);
        engine.initialize(strategies);

    }
}