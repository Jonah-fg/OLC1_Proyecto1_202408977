package Main;
import ejecucion.*;
import java.util.ArrayList;

public class TestCombatant {
    public static void main(String[] args) {
        System.out.println("------Prueba de Combatant-------");
        Strategy mageStrategy=new Strategy("Merlin", true, ActionType.ARCANE_BOLT, new ArrayList<>(), ActionType.MEDITATE);
        Strategy warriorStrategy= new Strategy("Ragnar",false, ActionType.SLASH, new ArrayList<>(), ActionType.REST);

        Combatant mage=new Combatant(mageStrategy, 42L);
        Combatant warrior= new Combatant(warriorStrategy, 43L);

        // Mostrar estadísticas del mago
        System.out.println("\n--- Mago ---");
        System.out.println("Nombre: "+ mage.getStrategy().getName());
        System.out.println("Es mago: "+mage.getStrategy().isMage());
        System.out.println("Vida: " + mage.getHealth() + " / " + mage.getStats().getMaxHealth());
        System.out.println("Maná: " + mage.getResource() + " / " + mage.getStats().getMaxResource());
        System.out.println("Ataque físico: " + mage.getStats().getPhysicalAttack());
        System.out.println("Poder mágico: " + mage.getStats().getMagicPower());
        System.out.println("Armadura: " + mage.getStats().getArmor());
        System.out.println("Resistencia mágica: "+ mage.getStats().getMagicResistance());
        System.out.println("Velocidad: " +mage.getStats().getSpeed());
        System.out.println("Random (1): " +mage.nextRandom());
        System.out.println("Random (2): " +mage.nextRandom());

        // Mostrar estadísticas del guerrero
        System.out.println("\n--- Guerrero ---");
        System.out.println("Nombre: " + warrior.getStrategy().getName());
        System.out.println("Es mago: " + warrior.getStrategy().isMage());
        System.out.println("Vida: " + warrior.getHealth() + " / " + warrior.getStats().getMaxHealth());
        System.out.println("Energía: " + warrior.getResource() + " / " + warrior.getStats().getMaxResource());
        System.out.println("Ataque físico: " + warrior.getStats().getPhysicalAttack());
        System.out.println("Poder mágico: " + warrior.getStats().getMagicPower());
        System.out.println("Armadura: " + warrior.getStats().getArmor());
        System.out.println("Resistencia mágica: " + warrior.getStats().getMagicResistance());
        System.out.println("Velocidad: " + warrior.getStats().getSpeed());
        System.out.println("Random (1): " + warrior.nextRandom());
        System.out.println("Random (2): " + warrior.nextRandom());

        // Probar que la vida se actualiza correctamente
        System.out.println("\n--- Probando setters ---");
        mage.setHealth(50);
        System.out.println("Vida del mago (seteada a 50): "+mage.getHealth());
        mage.setHealth(999);
        System.out.println("Vida del mago (intento de 999): " + mage.getHealth());
        mage.setHealth(-10); //  en 0
        System.out.println("Vida del mago (intento de -10): " + mage.getHealth());
        mage.setResource(200);
        System.out.println("Maná del mago (intento de 200): " + mage.getResource());
        mage.setResource(-5);
        System.out.println("Maná del mago (intento de -5): " + mage.getResource());

        // Prueba historiañ
        System.out.println("\n------ Probando historial -------");
        mage.addHistory(ActionType.ARCANE_BOLT);
        mage.addHistory(ActionType.FIREBALL);
        System.out.println("Historial del mago: " +mage.getHistory());
    }
}