package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StateTest {

    private Warrior conan;
    private Warrior doom;

    //Pour chaque test, on initialise deux personnages
    @BeforeEach
    public void setUp() {
        conan = new Warrior("Conan"); //HP = 30
        doom = new Warrior("Thulsa Doom"); //HP = 30
        // Au démarrage, chacun est en NormalState
    }

    @Test
    public void testInitialStateIsNormal() {
        // Vérifie que l'état initial est NormalState
        assertTrue(conan.getState() instanceof NormalState, "Conan doit démarrer en état NormalState");
        assertTrue(doom.getState() instanceof NormalState, "Thulsa Doom doit démarrer en état NormalState");
    }

    @Test
    public void testNormalToWoundedTransitionOnUpdate() {
        // Conan perd des HP (passe sous 30)
        conan.setHealth(17);
        // Appel onUpdate
        conan.update();
        // Vérifie transition vers WoundedState
        assertTrue(conan.getState() instanceof WoundedState,
            "Conan doit passer en WoundedState quand ses HP < 30 et HP > 10");
    }

    @Test
    public void testNormalToDeadTransitionOnUpdate() {
        // Conan passe à 0 HP
        conan.setHealth(0);
        conan.update();
        // Vérifie transition directe vers DeadState
        assertTrue(conan.getState() instanceof DeadState,
            "Conan doit passer en DeadState quand ses HP <= 0");
    }

    @Test
    public void testWoundedStateBehavior() {
        // On place Conan en WoundedState
        conan.setHealth(20);
        conan.update();  // => NormalState -> WoundedState

        // Conan tente de se déplacer
        conan.tryToMove();
        // Simplement vérifier que le code ne passe pas dans un autre mode
        assertTrue(conan.getState() instanceof WoundedState,
            "Conan doit rester en WoundedState après un move, s'il est toujours blessé.");

        // Conan attaque Thulsa Doom
        // En WoundedState, les dégâts sont divisés par 2
        // Conan a 7 de force, donc 7/2 = 3 de dégâts
        int oldHP = doom.getHealth();
        conan.attack(doom);
        assertEquals(oldHP - 3, doom.getHealth(),
            "Doom doit perdre 3 HP si Conan attaque en WoundedState.");

        // Conan repasse en NormalState si on remonte ses HP >= 30
        conan.setHealth(55);
        conan.update();  // => WoundedState -> NormalState
        assertTrue(conan.getState() instanceof NormalState,
            "Conan doit repasser en NormalState si ses HP >= 30.");
    }

    @Test
    public void testDeadStateBehavior() {
        // On tue Thulsa Doom
        doom.setHealth(0);
        doom.update(); // NormalState -> DeadState
        assertTrue(doom.getState() instanceof DeadState,
            "Doom doit être en DeadState quand ses HP <= 0.");

        // Un mort ne peut pas attaquer
        int oldHPConan = conan.getHealth();
        doom.attack(conan);  
        // Pas de changement de HP pour Conan
        assertEquals(oldHPConan, conan.getHealth(),
            "Un personnage en DeadState ne doit pas pouvoir attaquer.");

        // Un mort ne peut pas se déplacer
        doom.tryToMove();
        assertTrue(doom.getState() instanceof DeadState,
            "Doom reste en DeadState et ne peut pas se déplacer.");
    }

    @Test
    public void testAttackCausesStateChangeInTarget() {
        // Conan attaque Doom en mode Normal (5 de dégâts)

        // Conan doit être par défaut en NormalState
        assertTrue(conan.getState() instanceof NormalState, "Conan doit être en NormalState");

        doom.setHealth(12); // WoundedState
        conan.attack(doom);
        // Doom doit perdre 7 HP (NormalState) => 5 HP restants
        assertEquals(5, doom.getHealth(),
            "Doom doit avoir 5 HP restants après l'attaque de Conan.");

        // On appelle update pour voir si doom change d'état
        // (il devrait passer en ScaredState car HP=5 < 10 et HP > 0)
        doom.update();
        assertTrue(doom.getState() instanceof ScaredState, "Doom doit passer en ScaredState avec 5 HP.");
    }

    @Test
    public void testAutoChangeState() {
        conan.setHealth(12); // devrait passer en WoundedState
        // Sans faire conan.update(), lors d'une attaque, son état doit changer automatiquement
        conan.attack(doom); // => conan : NormalState -> WoundedState : donc doit infligé seulement la moitié des dégâts, soit 7/2 = 3
        assertEquals(27, doom.getHealth(), "Doom doit perdre 3 HP si Conan attaque en WoundedState.");
        assertTrue(conan.getState() instanceof WoundedState, "Conan doit passer automatiquement en WoundedState lors de son attaque.");
    }

    @Test
    public void testStateAndStrategy() {
        // On vérifie que les dégats infligés par un personnage dépendent de son état ET de sa stratégie
        // Conan attaque Doom en mode Normal (7 de dégâts)
        // Mais on place Conan en DefensiveStrategy ( dégâts / 2 ) => 7/2 = 3 de dégâts

        DefensiveStrategy defStrat = new DefensiveStrategy();
        conan.setStrategy(defStrat);
        conan.attack(doom);

        assertEquals(27, doom.getHealth(),
            "Doom doit perdre 3 HP si Conan attaque en NormalState avec DefensiveStrategy.");

        // On place maintenant Conan en aggressiveStrategy (dégâts * 2) et en ScaredState (inflige 1 de dégât)
        AggressiveStrategy attStrat = new AggressiveStrategy();
        conan.setStrategy(attStrat);

        conan.setHealth(5); // ScaredState

        conan.attack(doom); // doit placer conan automatiquement en ScaredState

        assertTrue(conan.getState() instanceof ScaredState, "Conan doit passer en ScaredState lors de son attaque.");

        // doom a 27 HP actuellement
        assertEquals(25, doom.getHealth(),
            "Doom doit perdre 2 HP si Conan attaque en ScaredState (1 dégât) avec AggressiveStrategy (1 * 2 = 2).");
    }
}
