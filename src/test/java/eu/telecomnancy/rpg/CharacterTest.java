package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharacterTest {
    @Test
    void testCreateCharacter() {
        CharacterCreator warriorCreator = new WarriorCreator();
        CharacterCreator wizardCreator = new WizardCreator();
        CharacterCreator healerCreator = new HealerCreator();

        //Création des personnages
        GameCharacter warrior = warriorCreator.createCharacter("Arthas");
        GameCharacter wizard = wizardCreator.createCharacter("Dumbledore");
        GameCharacter healer = healerCreator.createCharacter("Alice");

        assertEquals("Arthas", warrior.getName(), "Le nom du Warrior doit être 'Arthas'");
        assertEquals("Dumbledore", wizard.getName(), "Le nom du Wizard doit être 'Dumbledore'");
        assertEquals("Alice", healer.getName(), "Le nom du Healer doit être 'Alice'");

        assertEquals(30, warrior.getHealth(), "La santé du Warrior doit être de 30");
        assertEquals(40, wizard.getHealth(), "La santé du Wizard doit être de 40");
        assertEquals(50, healer.getHealth(), "La santé du Healer doit être de 50");

        //Attention, il faut faire des cast pour récupérer les attributs de chaque personnage qui sont pour le moment des GameCharacter
        assertEquals(7, ((Warrior) warrior).getStrength(), "La force du Warrior doit être de 7");
        assertEquals(5, ((Wizard) wizard).getIntelligence(), "L'intelligence du Wizard doit être de 5");
        assertEquals(3, ((Healer) healer).getWisdom(), "La sagesse du Healer doit être de 3");
        
    }

    @Test
    void testCharacterSameTypeAreDifferent(){

        //on créé d'abord les CharacterCreators
        CharacterCreator warriorCreator = new WarriorCreator();
        CharacterCreator wizardCreator = new WizardCreator();
        CharacterCreator healerCreator = new HealerCreator();

        //on créé les personnages
        GameCharacter warrior1 = warriorCreator.createCharacter("Arthas");
        GameCharacter warrior2 = warriorCreator.createCharacter("Arthur");
        GameCharacter wizard1 = wizardCreator.createCharacter("Dumbledore");
        GameCharacter wizard2 = wizardCreator.createCharacter("Gandalf");
        GameCharacter healer1 = healerCreator.createCharacter("Alice");
        GameCharacter healer2 = healerCreator.createCharacter("Bob");

        //on vérifie que les personnages de même type sont différents
        assertEquals(false, warrior1.equals(warrior2), "Les warriors doivent être différents");
        assertEquals(false, wizard1.equals(wizard2), "Les wizards doivent être différents");
        assertEquals(false, healer1.equals(healer2), "Les healers doivent être différents");

        //on vérifie qu'un changement sur un personnage n'affecte pas les personnages du même type
        warrior1.setHealth(50);
        assertEquals(50, warrior1.getHealth(), "La santé du Warrior1 doit être de 50");
        assertEquals(30, warrior2.getHealth(), "La santé du Warrior2 doit rester à 30");

        ((Wizard) wizard1).setIntelligence(60);
        assertEquals(60, ((Wizard) wizard1).getIntelligence(), "L'intelligence du Wizard1 doit être de 60");
        assertEquals(5, ((Wizard) wizard2).getIntelligence(), "L'intelligence du Wizard2 doit rester à 5");

        ((Healer) healer1).setWisdom(70);
        healer1.setHealth(100);

        assertNotEquals(healer1.getHealth(), healer2.getHealth(), "La santé des Healers doit être différente");
        assertNotEquals(((Healer) healer1).getWisdom(), ((Healer) healer2).getWisdom(), "La sagesse des Healers doit être différente");
    }
 
    
    @Test
    void LevelUpCharacter(){

        //On vérifie qu'un changement de niveau modifie bien les attributs des personnages

        //on créé d'abord les CharacterCreators
        CharacterCreator warriorCreator = new WarriorCreator();
        CharacterCreator wizardCreator = new WizardCreator();
        CharacterCreator healerCreator = new HealerCreator();

        //on créé les personnages
        GameCharacter warrior = warriorCreator.createCharacter("warror1");
        GameCharacter wizard = wizardCreator.createCharacter("wizard1");
        GameCharacter healer = healerCreator.createCharacter("healer1");

        //on vérifie que les personnages sont bien de niveau 1
        assertEquals(1, warrior.getLevel(), "Le niveau du Warrior doit être 1");
        assertEquals(1, wizard.getLevel(), "Le niveau du Wizard doit être 1");
        assertEquals(1, healer.getLevel(), "Le niveau du Healer doit être 1");

        // Donc leur attribut de base est bien celui de leur niveau
        assertEquals(7, ((Warrior) warrior).getStrength(), "La force du Warrior doit être de 7");
        assertEquals(5, ((Wizard) wizard).getIntelligence(), "L'intelligence du Wizard doit être de 5");
        assertEquals(3, ((Healer) healer).getWisdom(), "La sagesse du Healer doit être de 3");

        //on vérifie que le level up modifie bien les attributs
        warrior.setLevel(3);
        wizard.setLevel(4);
        healer.setLevel(8);

        assertEquals(3, warrior.getLevel(), "Le niveau du Warrior doit être 3");
        assertEquals(4, wizard.getLevel(), "Le niveau du Wizard doit être 4");
        assertEquals(8, healer.getLevel(), "Le niveau du Healer doit être 8");

        assertEquals(21, ((Warrior) warrior).getStrength(), "La force du Warrior doit être de 7*3=21");
        assertEquals(20, ((Wizard) wizard).getIntelligence(), "L'intelligence du Wizard doit être de 5*4=20");
        assertEquals(24, ((Healer) healer).getWisdom(), "La sagesse du Healer doit être de 3*8=24");


    }
}
