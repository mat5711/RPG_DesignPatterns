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
        assertEquals(30, warrior1.getHealth(), "Les points de vie doivent être 30 par défaut");
        assertEquals(0, warrior1.getExperiencePoints(), "Les points d'expérience doivent être 0 par défaut");
        assertEquals(10, ((Warrior) warrior1).getStrength(), "La force doit être 10 par défaut"); // Attention il faut faire un cast pour accéder à la méthode getStrength qui n'est pas dans GameCharacter
    }


}
