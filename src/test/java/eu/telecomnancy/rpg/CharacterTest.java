package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class CharacterTest {

    // ======================================================================================
    // GROUPE 1 : TEST CREATE CHARACTER
    // ======================================================================================
    @Nested
    class CreateCharacterTests {
        private GameCharacter warrior;
        private GameCharacter wizard;
        private GameCharacter healer;

        @BeforeEach
        void setup() {
            // Création des CharacterCreators
            CharacterCreator warriorCreator = new WarriorCreator();
            CharacterCreator wizardCreator = new WizardCreator();
            CharacterCreator healerCreator = new HealerCreator();

            // Création des personnages
            warrior = warriorCreator.createCharacter("Arthas");
            wizard = wizardCreator.createCharacter("Dumbledore");
            healer = healerCreator.createCharacter("Alice");
        }

        @Test
        void testNameWarrior() {
            assertEquals("Arthas", warrior.getName(), "Le nom du Warrior doit être 'Arthas'");
        }

        @Test
        void testNameWizard() {
            assertEquals("Dumbledore", wizard.getName(), "Le nom du Wizard doit être 'Dumbledore'");
        }

        @Test
        void testNameHealer() {
            assertEquals("Alice", healer.getName(), "Le nom du Healer doit être 'Alice'");
        }

        @Test
        void testHealthWarrior() {
            assertEquals(30, warrior.getHealth(), "La santé du Warrior doit être de 30");
        }

        @Test
        void testHealthWizard() {
            assertEquals(40, wizard.getHealth(), "La santé du Wizard doit être de 40");
        }

        @Test
        void testHealthHealer() {
            assertEquals(50, healer.getHealth(), "La santé du Healer doit être de 50");
        }

        @Test
        void testStrengthWarrior() {
            assertEquals(7, ((Warrior) warrior).getStrength(), "La force du Warrior doit être de 7");
        }

        @Test
        void testIntelligenceWizard() {
            assertEquals(5, ((Wizard) wizard).getIntelligence(), "L'intelligence du Wizard doit être de 5");
        }

        @Test
        void testWisdomHealer() {
            assertEquals(3, ((Healer) healer).getWisdom(), "La sagesse du Healer doit être de 3");
        }
    }

    // ======================================================================================
    // GROUPE 2 : TEST CHARACTER SAME TYPE ARE DIFFERENT
    // ======================================================================================
    @Nested
    class CharacterSameTypeDifferentTests {

        private GameCharacter warrior1;
        private GameCharacter warrior2;
        private GameCharacter wizard1;
        private GameCharacter wizard2;
        private GameCharacter healer1;
        private GameCharacter healer2;

        @BeforeEach
        void setup() {
            // CharacterCreators
            CharacterCreator warriorCreator = new WarriorCreator();
            CharacterCreator wizardCreator = new WizardCreator();
            CharacterCreator healerCreator = new HealerCreator();

            // Création de plusieurs personnages de même type
            warrior1 = warriorCreator.createCharacter("Arthas");
            warrior2 = warriorCreator.createCharacter("Arthur");

            wizard1 = wizardCreator.createCharacter("Dumbledore");
            wizard2 = wizardCreator.createCharacter("Gandalf");

            healer1 = healerCreator.createCharacter("Alice");
            healer2 = healerCreator.createCharacter("Bob");
        }

        @Test
        void testWarriorsAreDifferent() {
            assertEquals(false, warrior1.equals(warrior2), "Les warriors doivent être différents");
        }

        @Test
        void testWizardsAreDifferent() {
            assertEquals(false, wizard1.equals(wizard2), "Les wizards doivent être différents");
        }

        @Test
        void testHealersAreDifferent() {
            assertEquals(false, healer1.equals(healer2), "Les healers doivent être différents");
        }

        @Test
        void testWarriorOneHealthDoesNotAffectWarriorTwo() {
            warrior1.setHealth(50);
            assertEquals(50, warrior1.getHealth(), "La santé du Warrior1 doit être de 50");
            assertEquals(30, warrior2.getHealth(), "La santé du Warrior2 doit rester à 30");
        }

        @Test
        void testWizardOneIntelligenceDoesNotAffectWizardTwo() {
            ((Wizard) wizard1).setIntelligence(60);
            assertEquals(60, ((Wizard) wizard1).getIntelligence(), "L'intelligence du Wizard1 doit être de 60");
            assertEquals(5, ((Wizard) wizard2).getIntelligence(), "L'intelligence du Wizard2 doit rester à 5");
        }

        @Test
        void testHealerOneStatsDifferentFromHealerTwo() {
            ((Healer) healer1).setWisdom(70);
            healer1.setHealth(100);

            assertNotEquals(healer1.getHealth(), healer2.getHealth(), "La santé des Healers doit être différente");
            assertNotEquals(((Healer) healer1).getWisdom(), ((Healer) healer2).getWisdom(),
                            "La sagesse des Healers doit être différente");
        }
    }

    // ======================================================================================
    // GROUPE 3 : LEVEL UP CHARACTER
    // ======================================================================================
    @Nested
    class LevelUpCharacterTests {

        private GameCharacter warrior;
        private GameCharacter wizard;
        private GameCharacter healer;

        @BeforeEach
        void setup() {
            // CharacterCreators
            CharacterCreator warriorCreator = new WarriorCreator();
            CharacterCreator wizardCreator = new WizardCreator();
            CharacterCreator healerCreator = new HealerCreator();

            warrior = warriorCreator.createCharacter("warror1");
            wizard = wizardCreator.createCharacter("wizard1");
            healer = healerCreator.createCharacter("healer1");
        }

        @Test
        void testInitialLevel() {
            assertEquals(1, warrior.getLevel(), "Le niveau du Warrior doit être 1");
            assertEquals(1, wizard.getLevel(), "Le niveau du Wizard doit être 1");
            assertEquals(1, healer.getLevel(), "Le niveau du Healer doit être 1");
        }

        @Test
        void testInitialAttributes() {
            assertEquals(7, ((Warrior) warrior).getStrength(), "La force du Warrior doit être de 7");
            assertEquals(5, ((Wizard) wizard).getIntelligence(), "L'intelligence du Wizard doit être de 5");
            assertEquals(3, ((Healer) healer).getWisdom(), "La sagesse du Healer doit être de 3");
        }

        @Test
        void testSetLevelWarrior() {
            warrior.setLevel(3);
            assertEquals(3, warrior.getLevel(), "Le niveau du Warrior doit être 3");
            assertEquals(21, ((Warrior) warrior).getStrength(), "La force du Warrior doit être de 7*3=21");
        }

        @Test
        void testSetLevelWizard() {
            wizard.setLevel(4);
            assertEquals(4, wizard.getLevel(), "Le niveau du Wizard doit être 4");
            assertEquals(20, ((Wizard) wizard).getIntelligence(), "L'intelligence du Wizard doit être de 5*4=20");
        }

        @Test
        void testSetLevelHealer() {
            healer.setLevel(8);
            assertEquals(8, healer.getLevel(), "Le niveau du Healer doit être 8");
            assertEquals(24, ((Healer) healer).getWisdom(), "La sagesse du Healer doit être de 3*8=24");
        }
    }
}
