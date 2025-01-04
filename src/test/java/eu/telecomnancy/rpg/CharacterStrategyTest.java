package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharacterStrategyTest {

    private Warrior warrior; // HP de base = 30, stratégie par défaut = neutre
    private Wizard wizard;   // HP de base = 30, stratégie par défaut = neutre

    @BeforeEach
    public void setUp() {
        warrior = new Warrior("TestWarrior");
        wizard = new Wizard("TestWizard");

        // On monte leur HP pour éviter les changements de State
        warrior.setHealth(100);
        wizard.setHealth(100);
    }

    // ---------------------------------------------------------------------------------
    // STRATÉGIE NEUTRE
    // ---------------------------------------------------------------------------------
    @Test
    public void testNeutralStrategy() {
        // Stratégie par défaut neutre
        // Warrior attaque Wizard : 100 - 7 = 93
        warrior.attack(wizard);
        assertEquals(93, wizard.getHealth(), 
            "Wizard doit perdre 7 HP avec la stratégie neutre");
    }

    // ---------------------------------------------------------------------------------
    // STRATÉGIE AGGRESSIVE
    // ---------------------------------------------------------------------------------
    @Test
    public void testAggressiveStrategy() {
        // On met les deux en agressif
        CombatStrategy aggressive = new AggressiveStrategy();
        warrior.setStrategy(aggressive);
        wizard.setStrategy(aggressive);

        // Warrior attaque => warrior fait x2 dégâts => 7 * 2 = 14
        // Wizard subit x2 => 14 * 2 = 28
        warrior.attack(wizard);
        assertEquals(72, wizard.getHealth(), 
            "Wizard doit perdre 28 HP lorsque les deux personnages sont en agressif");
    }

    // ---------------------------------------------------------------------------------
    // STRATÉGIE DÉFENSIVE
    // ---------------------------------------------------------------------------------
    @Test
    public void testDefensiveStrategy() {
        // Les deux en défensif
        CombatStrategy defensive = new DefensiveStrategy();
        warrior.setStrategy(defensive);
        wizard.setStrategy(defensive);

        // Wizard attaque Warrior => 
        //    Dégâts infligés par Wizard /2 => 5/2 = 2 (arrondi)
        //    Dégâts subis par Warrior /2 => 2/2 = 1
        wizard.attack(warrior);
        assertEquals(99, warrior.getHealth(), 
            "Warrior doit perdre 1 HP lorsque les deux personnages sont en défensif");
    }

    // ---------------------------------------------------------------------------------
    // CHANGEMENT DYNAMIQUE DE STRATÉGIE
    // ---------------------------------------------------------------------------------
    @Nested
    class ChangingStrategyTests {

        // Dans ce sous-groupe, on refait un setUp() à part, 
        // car chaque test doit repartir d'un contexte "frais".
        @BeforeEach
        void init() {
            warrior = new Warrior("TestWarrior");
            wizard = new Wizard("TestWizard");
            warrior.setHealth(100);
            wizard.setHealth(100);
        }

        @Test
        void testStep1_NeutralVsNeutral() {
            // Étape 1 : Neutre vs Neutre => warrior attaque => wizard HP = 100 - 7 = 93
            warrior.attack(wizard);
            assertEquals(93, wizard.getHealth());
        }

        @Test
        void testStep2_WarriorAgressiveVsWizardNeutral() {
            // Warrior agressif, Wizard neutre
            CombatStrategy aggressive = new AggressiveStrategy();
            warrior.setStrategy(aggressive);

            // 1) Warrior attaque => x2 infligés => 7*2 =14, Wizard neutre => subit 14 => HP=86
            warrior.attack(wizard);
            assertEquals(86, wizard.getHealth(), 
                "Wizard doit tomber à 86 après l'attaque d'un warrior agressif (14 de dégâts)");

            // 2) Wizard attaque => Wizard neutre => inflige 5
            //    Warrior agressif => subit x2 => 5*2=10 => HP=90
            wizard.attack(warrior);
            assertEquals(90, warrior.getHealth(), 
                "Warrior subit 10 de dégâts (car agressif) après une attaque neutre de Wizard");
        }

        @Test
        void testStep3_WarriorAgressiveVsWizardDefensive() {
            // Warrior agressif, Wizard défensif
            CombatStrategy aggressive = new AggressiveStrategy();
            CombatStrategy defensive = new DefensiveStrategy();

            warrior.setStrategy(aggressive);
            wizard.setStrategy(defensive);

            // Warrior attaque => base=7, x2 =>14, wizard défensif => 14/2=7 => HP=93
            warrior.attack(wizard);
            assertEquals(93, wizard.getHealth(), 
                "Wizard subit 7 de dégâts (14/2) si warrior agressif et wizard défensif");
        }

        @Test
        void testStep4_WarriorNeutralVsWizardDefensive() {
            // On simule la suite : warrior devient neutre, wizard reste défensif
            // On doit reproduire minimalement le scénario :
            //   1) warrior agressif => wizard défensif => wizard HP=93
            //   2) warrior repasse neutre => warrior attaque => wizard défensif => subit 7/2=3 => HP=90

            // Warrior agressif => wizard défensif => on fait 1 attaque
            warrior.setStrategy(new AggressiveStrategy());
            wizard.setStrategy(new DefensiveStrategy());

            warrior.attack(wizard); // wizard => 100 - (14/2) => 93

            // Puis warrior redevient neutre
            warrior.setStrategy(new NeutralStrategy());

            // Warrior attaque => base=7, wizard défensif => subit 7/2=3 => HP=90
            warrior.attack(wizard);
            assertEquals(90, wizard.getHealth(), 
                "Warrior neutre fait 7 dégâts, wizard défensif subit 3 => 93 - 3 = 90");
        }
    }

}
