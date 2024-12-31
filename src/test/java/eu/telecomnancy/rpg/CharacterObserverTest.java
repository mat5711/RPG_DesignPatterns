package eu.telecomnancy.rpg;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterObserverTest {

    private GameCharacter warrior;
    private Observer levelUpObserver;
    private Observer levelUpObserver2;
    private Observer deathObserver;

    @BeforeEach
    public void setup() {
        warrior = new Warrior("TestWarrior");
        levelUpObserver = new LevelUpObserver();
        levelUpObserver2 = new LevelUpObserver();
        deathObserver = new DeathObserver();

    }


    @Test
    public void testLevelUp() {

        //On vérifie dans la sortie du test unitaire que TOUS les LevelUpObserver ont bien été notifiés
        //Tous les messages doivent donc apparaitre en double

        warrior.addObserver(levelUpObserver);
        warrior.addObserver(levelUpObserver2);
        warrior.addObserver(deathObserver); // On vérifie que les messages ne s'affiche pas pour cet oberveur

        // On simule de l'XP pour déclencher un level up
        warrior.addExperiencePoints(100);

        
        assertEquals(2, warrior.getLevel(), "Le warrior doit avoir monté au niveau 2");
        assertEquals(0, warrior.getExperiencePoints(), "Les XP doivent être remis à 0 après un level up");
  
        //Il faut désormais 2*10 = 20 XP pour monter au niveau 3
        warrior.addExperiencePoints(19);
        assertEquals(19, warrior.getExperiencePoints(), "Le warrior doit avoir 19 XP");
        assertEquals(2, warrior.getLevel(), "Le warrior doit être toujours au niveau 2");

        warrior.addExperiencePoints(1);
        assertEquals(3, warrior.getLevel(), "Le warrior doit avoir monté au niveau 3");
        assertEquals(0, warrior.getExperiencePoints(), "Les XP doivent être remis à 0 après un level up");
        assertEquals(21, ((Warrior) warrior).getStrength(), "La force du Warrior doit être de 3*7=21");
    }

    @Test
    public void testDeath2(){

        warrior.addObserver(deathObserver);
        warrior.addObserver(levelUpObserver); // On vérifie que les messages ne s'affiche pas pour cet oberveur
        warrior.addObserver(levelUpObserver2); // On vérifie que les messages ne s'affiche pas pour cet oberveur

        //On simule une mort
        warrior.setHealth(-10);

        assertEquals(-10, warrior.getHealth(), "Les HP du Warrior doivent être à -10");
        assertEquals(1, warrior.getLevel(), "Le Warrior doit être de niveau 1");
        //On vérifie que le message de mort a bien été affiché
    
        //bonus : on vérifie le changement de State
        assertTrue(warrior.getState() instanceof DeadState, "Le Warrior doit être mort");
    
    }

}
