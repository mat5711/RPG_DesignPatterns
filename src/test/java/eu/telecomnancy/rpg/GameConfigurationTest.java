package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameConfigurationTest {

    
    @Test
    public void testSingletonInstance() {
        GameConfiguration config1 = GameConfiguration.getInstance();
        GameConfiguration config2 = GameConfiguration.getInstance();

        assertNotNull(config1, "L'instance ne doit pas être nulle");
        assertSame(config1, config2, "Les deux références doivent pointer vers la même instance");
    }
    
    
    @Test
    public void testDefaultValues() {
        GameConfiguration config3 = GameConfiguration.getInstance();

        assertEquals(1, config3.getDifficulty(), "Le niveau de difficulté par défaut doit être 1");
        assertEquals(5, config3.getSizeMax(), "La taille maximale de l'équipe par défaut doit être 5");
    }
    
    

    @Test
    public void testSetAndGetMaxTeamSize() {
        GameConfiguration config = GameConfiguration.getInstance();
        config.setSizeMax(6);
        assertEquals(6, config.getSizeMax(), "La taille maximale de l'équipe doit être mise à jour à 6");
    }

    /* 
    @Test
    public void testSetAndGetDifficultyLevel() {
        GameConfiguration config = GameConfiguration.getInstance();
        config.setDifficulty(2);
        assertEquals(2, config.getDifficulty(), "Le niveau de difficulté doit être mis à jour à 2");
    }

    @Test
    public void testSingletonConsistency() {
        GameConfiguration config1 = GameConfiguration.getInstance();
        GameConfiguration config2 = GameConfiguration.getInstance();

        config1.setDifficulty(5);
        assertEquals(5, config2.getDifficulty(), "Les changements dans une instance doivent se refléter dans l'autre");

        config2.setSizeMax(10);
        assertEquals(10, config1.getSizeMax(), "Les changements dans une instance doivent se refléter dans l'autre");
    }
    */
}
