package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class StateTest {

    // ==========================================================================
    // NESTED 1 : TEST INITIAL STATE
    // ==========================================================================
    @Nested
    class InitialStateTests {
        private Warrior conan;
        private Warrior doom;

        @BeforeEach
        void setup() {
            conan = new Warrior("Conan");    // HP=30, NormalState
            doom  = new Warrior("Thulsa Doom"); // HP=30, NormalState
        }

        @Test
        void testConanInitialStateIsNormal() {
            assertTrue(conan.getState() instanceof NormalState,
                "Conan doit démarrer en état NormalState");
        }

        @Test
        void testDoomInitialStateIsNormal() {
            assertTrue(doom.getState() instanceof NormalState,
                "Thulsa Doom doit démarrer en état NormalState");
        }
    }

    // ==========================================================================
    // NESTED 2 : NORMAL -> WOUNDED
    // ==========================================================================
    @Nested
    class NormalToWoundedTests {
        private Warrior conan;

        @BeforeEach
        void setup() {
            conan = new Warrior("Conan"); // HP=30, NormalState
            // On le met à 17 HP => mise à jour => devrait passer en Wounded
            conan.setHealth(17);
            conan.update();
        }

        @Test
        void testConanBecomesWounded() {
            assertTrue(conan.getState() instanceof WoundedState,
                "Conan doit passer en WoundedState quand ses HP < 30 et HP > 10");
        }
    }

    // ==========================================================================
    // NESTED 3 : NORMAL -> DEAD
    // ==========================================================================
    @Nested
    class NormalToDeadTests {
        private Warrior conan;

        @BeforeEach
        void setup() {
            conan = new Warrior("Conan"); // NormalState
            conan.setHealth(0);
            conan.update();
        }

        @Test
        void testConanBecomesDead() {
            assertTrue(conan.getState() instanceof DeadState,
                "Conan doit passer en DeadState quand ses HP <= 0");
        }
    }

    // ==========================================================================
    // NESTED 4 : WOUNDED STATE BEHAVIOR
    // ==========================================================================
    @Nested
    class WoundedStateTests {
        private Warrior conan;
        private Warrior doom;

        @BeforeEach
        void setup() {
            conan = new Warrior("Conan");   // HP=30
            doom  = new Warrior("Thulsa Doom");
            // On place Conan en WoundedState => HP=20 => update
            conan.setHealth(20);
            conan.update();  // Normal -> Wounded
        }

        @Test
        void testWoundedMoveDoesNotChangeStateIfStillWounded() {
            conan.tryToMove();
            assertTrue(conan.getState() instanceof WoundedState,
                "Conan doit rester en WoundedState après un move, s'il est toujours blessé.");
        }

        @Test
        void testWoundedAttackHalfDamage() {
            int oldHP = doom.getHealth(); // 30
            conan.attack(doom);  // Wounded => 7/2=3
            assertEquals(oldHP - 3, doom.getHealth(),
                "Doom doit perdre 3 HP si Conan attaque en WoundedState.");
        }

        @Test
        void testWoundedToNormalIfHPAbove30() {
            // On fait remonter Conan au-dessus de 30 HP
            conan.setHealth(55);
            conan.update();
            assertTrue(conan.getState() instanceof NormalState,
                "Conan doit repasser en NormalState si ses HP >= 30.");
        }
    }

    // ==========================================================================
    // NESTED 5 : DEAD STATE BEHAVIOR
    // ==========================================================================
    @Nested
    class DeadStateTests {
        private Warrior conan;
        private Warrior doom;

        @BeforeEach
        void setup() {
            conan = new Warrior("Conan");   // HP=30
            doom  = new Warrior("Thulsa Doom");
            // On tue doom
            doom.setHealth(0);
            doom.update();  // Normal -> Dead
        }

        @Test
        void testDoomIsDeadState() {
            assertTrue(doom.getState() instanceof DeadState,
                "Doom doit être en DeadState quand ses HP <= 0.");
        }

        @Test
        void testDeadCannotAttack() {
            int oldHPConan = conan.getHealth();
            doom.attack(conan);
            // Pas de changement pour conan
            assertEquals(oldHPConan, conan.getHealth(),
                "Un personnage en DeadState ne doit pas pouvoir attaquer.");
        }

        @Test
        void testDeadCannotMove() {
            doom.tryToMove();
            assertTrue(doom.getState() instanceof DeadState,
                "Doom reste en DeadState et ne peut pas se déplacer.");
        }
    }

    // ==========================================================================
    // NESTED 6 : ATTACK CAUSES STATE CHANGE IN TARGET
    // ==========================================================================
    @Nested
    class AttackCausesStateChangeTests {
        private Warrior conan;
        private Warrior doom;

        @BeforeEach
        void setup() {
            conan = new Warrior("Conan");      // NormalState
            doom  = new Warrior("Thulsa Doom");// NormalState
            doom.setHealth(12);               // => Wounded? => pas encore, on attend update
            // Conan attaque => fait 7 dmg
            conan.attack(doom);
            // doom => 12 - 7 = 5
            doom.update(); // => HP=5 => <10 => ScaredState
        }

        @Test
        void testDoomHealthAfterAttack() {
            assertEquals(5, doom.getHealth(),
                "Doom doit avoir 5 HP restants après l'attaque de Conan.");
        }

        @Test
        void testDoomInScaredState() {
            assertTrue(doom.getState() instanceof ScaredState,
                "Doom doit passer en ScaredState avec 5 HP.");
        }
    }

    // ==========================================================================
    // NESTED 7 : AUTO CHANGE STATE
    // ==========================================================================
    @Nested
    class AutoChangeStateTests {
        private Warrior conan;
        private Warrior doom;

        @BeforeEach
        void setup() {
            // Conan HP=30 => Normal
            // On le met à 12 => Normal, pas encore update
            conan = new Warrior("Conan");
            conan.setHealth(12);
            doom = new Warrior("Thulsa Doom"); // HP=30

            // Conan attaque doom
            // => Au moment d'attaquer, si HP < 30 => conan => WoundedState
            conan.attack(doom);
        }

        @Test
        void testDamageInWoundedOnAttack() {
            // Conan devait infliger 7/2=3 => doom => 27
            assertEquals(27, doom.getHealth(),
                "Doom doit perdre 3 HP si Conan attaque en WoundedState.");
        }

        @Test
        void testConanIsWoundedAfterAttack() {
            assertTrue(conan.getState() instanceof WoundedState,
                "Conan doit passer automatiquement en WoundedState lors de son attaque.");
        }
    }

    // ==========================================================================
    // NESTED 8 : STATE AND STRATEGY
    // ==========================================================================
    @Nested
    class StateAndStrategyTests {
        private Warrior conan;
        private Warrior doom;

        @BeforeEach
        void setup() {
            conan = new Warrior("Conan");  // NormalState
            doom  = new Warrior("Thulsa Doom");
        }

        @Test
        void testNormalDefensive() {
            // Conan NormalState => 7 dmg
            // DefensiveStrategy => *0.5 => 7/2=3
            DefensiveStrategy defStrat = new DefensiveStrategy();
            conan.setStrategy(defStrat);
            conan.attack(doom);
            // doom = 30 -3 => 27
            assertEquals(27, doom.getHealth(),
                "Doom doit perdre 3 HP si Conan attaque en NormalState avec DefensiveStrategy.");
        }

        @Test
        void testScaredAggressive() {
            // On place conan HP=5 => => ScaredState
            conan.setHealth(5);
            AggressiveStrategy attStrat = new AggressiveStrategy();
            conan.setStrategy(attStrat);

            // Conan attaque => en attaquant, Conan bascule en ScaredState (auto) => 
            // ScaredState => 1 dmg, Aggressive => x2 => total=2
            conan.attack(doom);

            assertTrue(conan.getState() instanceof ScaredState,
                "Conan doit passer en ScaredState lors de son attaque.");

            // doom => 30 -2 => 28
            assertEquals(28, doom.getHealth(),
                "Doom doit perdre 2 HP si Conan attaque en ScaredState (1 dmg) avec AggressiveStrategy (1*2=2).");
        }
    }
}
