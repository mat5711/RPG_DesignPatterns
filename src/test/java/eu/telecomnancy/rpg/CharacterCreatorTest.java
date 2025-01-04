package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterCreatorTest {

    @Nested
    class WarriorCreationTest {

        private GameCharacter warrior1;

        @BeforeEach
        void setup() {
            CharacterCreator warriorCreator = new WarriorCreator();
            warrior1 = warriorCreator.createCharacter("Arthur");
        }

        @Test
        void testWarriorNotNull() {
            assertNotNull(warrior1, "Le personnage ne doit pas être nul");
        }

        @Test
        void testWarriorIsInstanceOfWarrior() {
            assertTrue(warrior1 instanceof Warrior, "Le personnage doit être une instance de Warrior");
        }

        @Test
        void testWarriorIsAlsoGameCharacter() {
            assertTrue(warrior1 instanceof GameCharacter, "Le personnage doit aussi être une instance de GameCharacter");
        }

        @Test
        void testWarriorName() {
            assertEquals("Arthur", warrior1.getName(), "Le nom du personnage doit être 'Arthur'");
        }

        @Test
        void testWarriorHealth() {
            assertEquals(30, warrior1.getHealth(), "Les points de vie doivent être 30 par défaut pour un warrior");
        }

        @Test
        void testWarriorExperience() {
            assertEquals(0, warrior1.getExperiencePoints(), "Les points d'expérience doivent être 0 par défaut");
        }

        @Test
        void testWarriorStrength() {
            assertEquals(7, ((Warrior) warrior1).getStrength(), "La force doit être 7 par défaut");
        }
    }

    @Nested
    class WizardCreationTest {

        private GameCharacter wizard1;

        @BeforeEach
        void setup() {
            CharacterCreator wizardCreator = new WizardCreator();
            wizard1 = wizardCreator.createCharacter("Clement");
        }

        @Test
        void testWizardNotNull() {
            assertNotNull(wizard1, "Le personnage ne doit pas être nul");
        }

        @Test
        void testWizardIsInstanceOfWizard() {
            assertTrue(wizard1 instanceof Wizard, "Le personnage doit être une instance de Wizard");
        }

        @Test
        void testWizardIsAlsoGameCharacter() {
            assertTrue(wizard1 instanceof GameCharacter, "Le personnage doit aussi être une instance de GameCharacter");
        }

        @Test
        void testWizardName() {
            assertEquals("Clement", wizard1.getName(), "Le nom du personnage doit être 'Clement'");
        }

        @Test
        void testWizardHealth() {
            assertEquals(40, wizard1.getHealth(), "Les points de vie doivent être 40 par défaut pour un wizard");
        }

        @Test
        void testWizardExperience() {
            assertEquals(0, wizard1.getExperiencePoints(), "Les points d'expérience doivent être 0 par défaut");
        }

        @Test
        void testWizardIntelligence() {
            assertEquals(5, ((Wizard) wizard1).getIntelligence(), "La force doit être 5 par défaut");
        }
    }

    @Nested
    class HealerCreationTest {

        private GameCharacter healer1;

        @BeforeEach
        void setup() {
            CharacterCreator healerCreator = new HealerCreator();
            healer1 = healerCreator.createCharacter("John");
        }

        @Test
        void testHealerNotNull() {
            assertNotNull(healer1, "Le personnage ne doit pas être nul");
        }

        @Test
        void testHealerIsInstanceOfHealer() {
            assertTrue(healer1 instanceof Healer, "Le personnage doit être une instance de Healer");
        }

        @Test
        void testHealerIsAlsoGameCharacter() {
            assertTrue(healer1 instanceof GameCharacter, "Le personnage doit aussi être une instance de GameCharacter");
        }

        @Test
        void testHealerName() {
            assertEquals("John", healer1.getName(), "Le nom du personnage doit être 'John'");
        }

        @Test
        void testHealerHealth() {
            assertEquals(50, healer1.getHealth(), "Les points de vie doivent être 50 par défaut pour un healer");
        }

        @Test
        void testHealerExperience() {
            assertEquals(0, healer1.getExperiencePoints(), "Les points d'expérience doivent être 0 par défaut");
        }

        @Test
        void testHealerWisdom() {
            assertEquals(3, ((Healer) healer1).getWisdom(), "La force doit être 3 par défaut");
        }
    }

}
