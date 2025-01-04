package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharacterObserverTest {

    private GameCharacter warrior;
    private Observer levelUpObserver;
    private Observer levelUpObserver2;
    private Observer deathObserver;

    @BeforeEach
    public void setup() {
        // Personnage + Observers
        warrior = new Warrior("TestWarrior");
        levelUpObserver = new LevelUpObserver();
        levelUpObserver2 = new LevelUpObserver();
        deathObserver = new DeathObserver();
    }

    //---------------------------------------------------------------------------------
    // PARTIE 1 : TESTS RELATIFS AU LEVEL UP
    //---------------------------------------------------------------------------------
    @Nested
    class LevelUpTests {

        @BeforeEach
        void setUpLevelUpScenario() {
            // On abonne 2 LevelUpObservers + 1 DeathObserver
            warrior.addObserver(levelUpObserver);
            warrior.addObserver(levelUpObserver2);
            warrior.addObserver(deathObserver);
            // On simule l'ajout de 100 XP pour déclencher un premier level-up
            warrior.addExperiencePoints(100);
        }

        @Test
        void testLevelUpFirstTime_LevelIs2() {
            // Le warrior doit avoir monté au niveau 2
            assertEquals(2, warrior.getLevel(), "Le warrior doit avoir monté au niveau 2");
        }

        @Test
        void testLevelUpFirstTime_ExpResetTo0() {
            // Les XP doivent être remis à 0 après un level up
            assertEquals(0, warrior.getExperiencePoints(),
                "Les XP doivent être remis à 0 après un level up");
        }

        @Test
        void testNextThresholdNoLevelUpYet() {
            // Après le premier level up, il faut 2 * 10 = 20 XP pour passer niveau 3
            // On ajoute 19 XP => pas de level up
            warrior.addExperiencePoints(19);

            // Vérifie XP = 19
            assertEquals(19, warrior.getExperiencePoints(),
                "Le warrior doit avoir 19 XP");

            // Vérifie qu'on est toujours niveau 2
            assertEquals(2, warrior.getLevel(),
                "Le warrior doit être toujours au niveau 2");
        }

        @Test
        void testAdd1XPTriggersSecondLevelUp() {
            // On commence déjà niveau 2 après setUpLevelUpScenario()
            // Pour passer niveau 3 => 20 XP. On va ajouter 19 XP + 1 XP,
            // mais on le fait dans deux étapes pour séparer les asserts.

            // Étape 1 : 19 XP
            warrior.addExperiencePoints(19);
            // Étape 2 : 1 XP
            warrior.addExperiencePoints(1);

            // Désormais, on doit être niveau 3
            assertEquals(3, warrior.getLevel(),
                "Le warrior doit avoir monté au niveau 3");
        }

        @Test
        void testExpResetAgain() {
            // On refait la séquence pour déclencher le 2e level-up
            warrior.addExperiencePoints(19);
            warrior.addExperiencePoints(1);

            // Les XP doivent être remis à 0 après ce second level-up
            assertEquals(0, warrior.getExperiencePoints(),
                "Les XP doivent être remis à 0 après le second level up");
        }

        @Test
        void testWarriorStrengthAfterLevel3() {
            // On refait la séquence pour être niveau 3
            warrior.addExperiencePoints(19);
            warrior.addExperiencePoints(1);

            // La force du Warrior doit être 3 * 7 = 21
            assertEquals(21, ((Warrior) warrior).getStrength(),
                "La force du Warrior doit être de 3*7 = 21");
        }
    }

    //---------------------------------------------------------------------------------
    // PARTIE 2 : TESTS RELATIFS À LA MORT DU PERSONNAGE
    //---------------------------------------------------------------------------------
    @Nested
    class DeathTests {

        @BeforeEach
        void setUpDeathScenario() {
            // On abonne 1 DeathObserver + 2 LevelUpObserver
            warrior.addObserver(deathObserver);
            warrior.addObserver(levelUpObserver);
            warrior.addObserver(levelUpObserver2);
        }

        @Test
        void testHPBelowZero() {
            // On simule une mort en mettant HP à -10
            warrior.setHealth(-10);

            // Vérifie que les HP sont bien à -10
            assertEquals(-10, warrior.getHealth(),
                "Les HP du Warrior doivent être à -10");
        }

        @Test
        void testWarriorLevelIsStill1AfterDeath() {
            // On simule une mort en mettant HP à -10
            warrior.setHealth(-10);
            
            // Vérifie que le Warrior reste niveau 1
            assertEquals(1, warrior.getLevel(),
                "Le Warrior doit être de niveau 1");
        }

        @Test
        void testWarriorIsNowInDeadState() {
            // On simule une mort en mettant HP à -10
            warrior.setHealth(-10);
            
            // bonus : on vérifie le changement de State
            assertTrue(warrior.getState() instanceof DeadState,
                "Le Warrior doit être mort (DeadState)");
        }
    }

}
