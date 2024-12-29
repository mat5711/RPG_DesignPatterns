package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CharacterCreatorTest {

    @Test
    public void testWarriorCreation() {
        CharacterCreator warriorCreator = new WarriorCreator();
        GameCharacter warrior1 = warriorCreator.createCharacter("Arthur");

        assertNotNull(warrior1, "Le personnage ne doit pas être nul");
        assertTrue(warrior1 instanceof Warrior, "Le personnage doit être une instance de Warrior");
        assertTrue(warrior1 instanceof GameCharacter, "Le personnage doit aussi être une instance de GameCharacter");
        assertEquals("Arthur", warrior1.getName(), "Le nom du personnage doit être 'Arthur'");
        assertEquals(30, warrior1.getHealth(), "Les points de vie doivent être 30 par défaut pour un warrior");
        assertEquals(0, warrior1.getExperiencePoints(), "Les points d'expérience doivent être 0 par défaut");
        assertEquals(7, ((Warrior) warrior1).getStrength(), "La force doit être 7 par défaut"); // Attention il faut faire un cast pour accéder à la méthode getStrength qui n'est pas dans GameCharacter
    }


    @Test
    public void testWizardCreation() {
        CharacterCreator wizardCreator = new WizardCreator();
        GameCharacter wizard1 = wizardCreator.createCharacter("Clement");

        assertNotNull(wizard1, "Le personnage ne doit pas être nul");
        assertTrue(wizard1 instanceof Wizard, "Le personnage doit être une instance de Wizard");
        assertTrue(wizard1 instanceof GameCharacter, "Le personnage doit aussi être une instance de GameCharacter");
        assertEquals("Clement", wizard1.getName(), "Le nom du personnage doit être 'Clement'");
        assertEquals(40, wizard1.getHealth(), "Les points de vie doivent être 40 par défaut pour un wizard");
        assertEquals(0, wizard1.getExperiencePoints(), "Les points d'expérience doivent être 0 par défaut");
        assertEquals(5, ((Wizard) wizard1).getIntelligence(), "La force doit être 5 par défaut"); // Attention il faut faire un cast pour accéder à la méthode getStrength qui n'est pas dans GameCharacter
    }


    @Test
    public void testHealerCreation() {
        CharacterCreator healerCreator = new HealerCreator();
        GameCharacter healer1 = healerCreator.createCharacter("John");

        assertNotNull(healer1, "Le personnage ne doit pas être nul");
        assertTrue(healer1 instanceof Healer, "Le personnage doit être une instance de Healer");
        assertTrue(healer1 instanceof GameCharacter, "Le personnage doit aussi être une instance de GameCharacter");
        assertEquals("John", healer1.getName(), "Le nom du personnage doit être 'John'");
        assertEquals(50, healer1.getHealth(), "Les points de vie doivent être 50 par défaut pour un healer");
        assertEquals(0, healer1.getExperiencePoints(), "Les points d'expérience doivent être 0 par défaut");
        assertEquals(3, ((Healer) healer1).getWisdom(), "La force doit être 3 par défaut"); // Attention il faut faire un cast pour accéder à la méthode getStrength qui n'est pas dans GameCharacter
    }


}
