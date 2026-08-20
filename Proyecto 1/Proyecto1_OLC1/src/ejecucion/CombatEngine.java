package ejecucion;
import java.util.*;
public class CombatEngine {

    private final Match partida;
    private final long semilla;
    private Combatant jugador1;
    private Combatant jugador2;
    private CombatContext contexto;
    private int ronda;

    public CombatEngine(Match partida, long semilla) {
        this.partida= partida;
        this.semilla =semilla;
        this.ronda=0;
        this.contexto=new CombatContext();
    }

    // inicializacion de los combatientes a partir de las estrategias
    public void initialize(List<Strategy> estrategias) {
        List<String> nombresJugadores=partida.getPlayers();
        if (nombresJugadores.size() != 2){
            throw new RuntimeException("Una partida debe tener exacamente dos jugadores.");
        }

        Strategy s1=buscarEstrategia(estrategias, nombresJugadores.get(0));
        Strategy s2=buscarEstrategia(estrategias, nombresJugadores.get(1));

        // Semillas: jugador1 = semilla, jugador2=semilla +1
        this.jugador1= new Combatant(s1, semilla);
        this.jugador2=new Combatant(s2, semilla + 1);
        actualizarContexto();
    }

    private Strategy buscarEstrategia(List<Strategy> estrategias, String nombre) {
        return estrategias.stream().filter(s -> s.getName().equals(nombre)).findFirst().orElseThrow(() -> new RuntimeException("Estrategia no encotrada: "+ nombre));
    }

    //actualizacion contexto
    private void actualizarContexto() {
        contexto.setRoundNumber(ronda);
        contexto.setTotalRounds(partida.getRounds());
        contexto.setSelfHealth(jugador1.getHealth());
        contexto.setOpponentHealth(jugador2.getHealth());
        contexto.setSelfResource(jugador1.getResource());
        contexto.setOpponentResource(jugador2.getResource());
        contexto.setSelfScore(jugador1.getScore());
        contexto.setOpponentScore(jugador2.getScore());
        contexto.setSelfHistory(jugador1.getHistory());
        contexto.setOpponentHistory(jugador2.getHistory());
        if (ronda>0) {
            contexto.setRandomValue(jugador1.nextRandom());
        }
    }

    public ActionType selectAction(Combatant combatiente, CombatContext ctx) {
        Strategy estrategia=combatiente.getStrategy();
        if (ronda==0){
            return estrategia.getInitialAction();
        }
        for (Rule regla: estrategia.getRules()) {
            if (regla.evaluate(ctx)) {
                return regla.getAction();
            }
        }
        return estrategia.getDefaultAction();
    }

    public void runRound(){
        if (ronda>=partida.getRounds()) {
            System.out.println("La partda ya terminó.");
            return;
        }

        if (ronda>0) {
            contexto.setRandomValue(jugador1.nextRandom());
        }
        //Seleccion acciones
        ActionType accion1 =selectAction(jugador1, contexto);
        ActionType accion2 =selectAction(jugador2, contexto);

        // Determinar orden de ejecución
        List<Combatant> orden=determinarOrden(jugador1, accion1, jugador2, accion2);

        for (Combatant atacante : orden) {
            ActionType accion=(atacante==jugador1) ? accion1 : accion2;
            Combatant defensor=(atacante== jugador1)? jugador2 : jugador1;
            ejecutarAccion(atacante, defensor, accion);
        }

        //Avanza ronda y actuliza contexto
        ronda++;
        actualizarContexto();
        verificarVictoria();
    }

    private List<Combatant> determinarOrden(Combatant j1, ActionType a1, Combatant j2, ActionType a2) {
        int prioridad1= obtenerPrioridad(a1);
        int prioridad2=obtenerPrioridad(a2);

        if (prioridad1!= prioridad2) {
            return prioridad1> prioridad2 ? Arrays.asList(j1, j2) :Arrays.asList(j2, j1);
        }

        int velocidad1= j1.getStats().getSpeed();
        int velocidad2=j2.getStats().getSpeed();
        if (velocidad1!= velocidad2) {
            return velocidad1>velocidad2 ? Arrays.asList(j1, j2) : Arrays.asList(j2, j1);
        }
        // Misma prioridad y velocidad: el primero en la lista de players actúa prmero
        return Arrays.asList(j1, j2);
    }

    private int obtenerPrioridad(ActionType accion){
        switch (accion){
            case MAGIC_BARRIER: case SHIELD_BLOCK: return 7;
            case WAR_CRY:
                return 6;

            case HEALING_RUNE:
                return 5;

            case ARCANE_BOLT: case SLASH:
                return 4;

            case FIREBALL: case HEAVY_STRIKE:
                return 2;

            case MEDITATE: case REST:
                return 1;

            default: return 0;
        }
    }

//ejecicion de acciones
    private void ejecutarAccion(Combatant atacante, Combatant defensor, ActionType accion) {
        // Al inicio de cada acción, se limpia la defensa
        atacante.setDefenseActive(false);
        int costo=obtenerCosto(accion, atacante.getStrategy().isMage());

        // Verificacion de sus recursoss
        if (atacante.getResource()<costo) {
            int penalizacion=partida.getScoring().getFailedActionPenalty();
            atacante.setScore(atacante.getScore()- penalizacion);
            System.out.println(atacante.getStrategy().getName() +" falló al intentar " + accion+" (sin recursos)");
            return;
        }
        //descontar
        atacante.setResource(atacante.getResource() - costo);

        switch (accion){
            case ARCANE_BOLT:
            case FIREBALL:
                aplicarDañoMagico(atacante, defensor, accion);
                atacante.addHistory(accion);
                break;

            case SLASH:
            case HEAVY_STRIKE:
                aplicarDañoFisico(atacante, defensor, accion);
                atacante.addHistory(accion);
                break;

            case MAGIC_BARRIER:
            case SHIELD_BLOCK:
                atacante.setDefenseActive(true);
                atacante.addHistory(accion);
                System.out.println(atacante.getStrategy().getName() + " activa " + accion);
                break;

            case HEALING_RUNE:
                aplicarCuracion(atacante);
                atacante.addHistory(accion);
                break;

            case MEDITATE:
            case REST:
                aplicarRecuperacionRecurso(atacante);
                atacante.addHistory(accion);
                break;

            case WAR_CRY:
                atacante.setWarCryBonus(10);
                atacante.addHistory(accion);
                System.out.println(atacante.getStrategy().getName() + " usa WAR_CRY (+10 ataque)");
                break;

            default:
                System.out.println("Acción descoocida: " + accion);
        }
    }

    private int obtenerCosto(ActionType accion, boolean esMago) {
        switch(accion){
            case ARCANE_BOLT:
                return 10;

            case FIREBALL:
                return 30;

            case MAGIC_BARRIER:
                return 20;

            case HEALING_RUNE:
                return 30;

            case MEDITATE:
                return 0;

            case SLASH:
                return 10;

            case HEAVY_STRIKE:
                return 25;

            case SHIELD_BLOCK:
                return 15;

            case WAR_CRY:
                return 20;

            case REST:
                return 0;
            default: return 0;
        }
    }

//formulas daños
    private void aplicarDañoFisico(Combatant atacante, Combatant defensor, ActionType accion) {
        int poder=(accion == ActionType.SLASH)? 12:25;
        int bonificacion=atacante.getWarCryBonus();
        atacante.setWarCryBonus(0);

        // Fórmula: poder+ataque_físico+bonificación-armadura
        int daño=poder +atacante.getStats().getPhysicalAttack() + bonificacion - defensor.getStats().getArmor();
        daño =Math.max(1, daño);

        if (defensor.isDefenseActive()) {
            daño=(int) Math.floor(daño*0.5);

            defensor.setScore(defensor.getScore()+partida.getScoring().getSuccessfulDefense());
        }
        defensor.setHealth(defensor.getHealth()- daño);

        // Puntos por daño real causado
        int puntosDaño =daño *partida.getScoring().getDamagePoint();
        atacante.setScore(atacante.getScore()+puntosDaño);

        System.out.println(atacante.getStrategy().getName() +" causa " +daño + " de daño fsico a "+defensor.getStrategy().getName());
    }

    private void aplicarDañoMagico(Combatant atacante, Combatant defensor, ActionType accion) {
        int poder=(accion == ActionType.ARCANE_BOLT) ? 12:25;

        //Fórmula: poder+poder_mágico - resistencia_mágica
        int daño = poder+ atacante.getStats().getMagicPower() - defensor.getStats().getMagicResistance();
        daño=Math.max(1, daño);

        //defensa si está activa
        if (defensor.isDefenseActive()) {
            daño=(int) Math.floor(daño* 0.5);
            defensor.setScore(defensor.getScore()+ partida.getScoring().getSuccessfulDefense());
        }
        defensor.setHealth(defensor.getHealth()- daño);
        int puntosDaño=daño * partida.getScoring().getDamagePoint();
        atacante.setScore(atacante.getScore() + puntosDaño);

        System.out.println(atacante.getStrategy().getName() + " cusa "+daño+" de daño mágico a " + defensor.getStrategy().getName());
    }

//curacion y recuperacion de recurso
    private void aplicarCuracion(Combatant objetivo){
        int curacionBase=25;
        int vidaMaxima= objetivo.getStats().getMaxHealth();
        int vidaActual = objetivo.getHealth();

        int curacionReal=Math.min(curacionBase, vidaMaxima - vidaActual);
        objetivo.setHealth(vidaActual+ curacionReal);

        int puntosCuracion=curacionReal *partida.getScoring().getHealingPoint();
        objetivo.setScore(objetivo.getScore()+ puntosCuracion);
        System.out.println(objetivo.getStrategy().getName() +" se cura " + curacionReal +" HP");
    }

    private void aplicarRecuperacionRecurso(Combatant objetivo) {
        int recuperacionBase= 25;
        int recursoMaximo = objetivo.getStats().getMaxResource();
        int recursoActual =objetivo.getResource();

        int recuperacionReal=Math.min(recuperacionBase, recursoMaximo - recursoActual);
        objetivo.setResource(recursoActual + recuperacionReal);

        System.out.println(objetivo.getStrategy().getName() + " recupra " +recuperacionReal + " de recurso");
    }

    private void verificarVictoria(){
        if (jugador1.getHealth() <=0 || jugador2.getHealth()<=0) {
            Combatant ganador=(jugador1.getHealth() > 0) ? jugador1 : jugador2;
            int bonusVictoria=partida.getScoring().getVictoryBonus();
            ganador.setScore(ganador.getScore()+bonusVictoria);
            System.out.println("¡"+ganador.getStrategy().getName() +" gana la prtida!");
        }
    }

    public Combatant getJugador1() {
        return jugador1;
    }
    public Combatant getJugador2() {
        return jugador2;
    }
    public int getRonda() {
        return ronda;
    }
    public void setRonda(int ronda) {
        this.ronda = ronda;
    }
    public CombatContext getContexto() {
        return contexto;
    }

    public void actualizarContextoPublico() {
        actualizarContexto();
    }

    public void imprimirEstado(){
        System.out.println("=== Ronda " + ronda + " ===");
        System.out.println(jugador1.getStrategy().getName() + ": HP"+jugador1.getHealth() + ", "+ (jugador1.getStrategy().isMage()?"Maná" : "Energía") + " " + jugador1.getResource() + ", Puntje "+jugador1.getScore());
        System.out.println(jugador2.getStrategy().getName() + ": HP " + jugador2.getHealth() +  ", " + (jugador2.getStrategy().isMage() ? "Maná":"Energía") + " " +jugador2.getResource() +
                ", Puntaje "+jugador2.getScore());
        System.out.println("Historial "+ jugador1.getStrategy().getName() +": " + jugador1.getHistory());
        System.out.println("Historial " + jugador2.getStrategy().getName() + ": "+jugador2.getHistory());
        System.out.println();
    }
}