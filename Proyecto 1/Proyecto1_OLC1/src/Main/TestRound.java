package Main;

import ejecucion.*;

import java.util.ArrayList;
import java.util.List;

public class TestRound{
    public static void main(String[] args) {
        System.out.println("----Prueba de resolución de ronda -----\n");

        //Crear regla: if self_health <= 30 then HEALING_RUNE
        Condition condicion=ctx-> ctx.getSelfHealth() <= 30;
        Rule regla=new Rule(condicion, ActionType.HEALING_RUNE);

        //Estrategia de Merlin (mago)
        List<Rule> reglasMerlin=new ArrayList<>();
        reglasMerlin.add(regla);
        Strategy merlin=new Strategy("Merlin", true, ActionType.ARCANE_BOLT, reglasMerlin, ActionType.MEDITATE);

        //Estrategia de Ragnar (guerrero) sin reglas (solo default)
        List<Rule> reglasRagnar = new ArrayList<>();
        Strategy ragnar = new Strategy("Ragnar", false, ActionType.SLASH, reglasRagnar, ActionType.REST);

        List<Strategy> estrategias= new ArrayList<>();
        estrategias.add(merlin);
        estrategias.add(ragnar);

        //Configuración de partida
        List<String> jugadores =new ArrayList<>();
        jugadores.add("Merlin");
        jugadores.add("Ragnar");

        Scoring puntuacion= new Scoring(1, 1, 20, 100, 10);
        Bonuses bonificaciones=new Bonuses(new ArrayList<>(), 0, new ArrayList<>(), 0, 25);
        Match partida =new Match("DueloPrueba", jugadores, 5, puntuacion, bonificaciones);

        CombatEngine motor= new CombatEngine(partida, 42L);
        motor.initialize(estrategias);

        System.out.println("--------Estado inicial-------");
        motor.imprimirEstado();

        //Ronda 0 acciones iniciales
        System.out.println("------Ejecutando ronda 0 -----");
        motor.runRound();
        motor.imprimirEstado();

        System.out.println("------ Modificando vida de Merlin a 20 (para activar regla) ------");
        motor.getJugador1().setHealth(20);
        motor.actualizarContextoPublico();

        //Ronda 1 (se activaria el HEALING_RUNE)
        System.out.println("---Ejecutando ronda 1 ---");
        motor.runRound();
        motor.imprimirEstado();

        System.out.println("Historial de Merlin: "+ motor.getJugador1().getHistory());
        System.out.println("Historial de Ragnar: " +motor.getJugador2().getHistory());
    }
}