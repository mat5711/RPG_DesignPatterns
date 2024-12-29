package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CharacterVisitorTest {

    @Test
    public void testBuffVisitor() {
        Warrior warrior = new Warrior("TestWarrior"); // strength = 7
        Wizard wizard = new Wizard("TestWizard"); // intelligence = 5
        Healer healer = new Healer("TestHealer"); // wisdom = 3

        CharacterVisitor buff = new BuffVisitor();

        warrior.accept(buff);
        wizard.accept(buff);
        healer.accept(buff);

        assertEquals(15, warrior.getStrength(), "La force du Warrior doit augmenter de 8");
        assertEquals(13, wizard.getIntelligence(), "L'intelligence du Wizard doit augmenter de 8");
        assertEquals(11, healer.getWisdom(), "La sagesse du Healer doit augmenter de 8");
    }

    
    @Test
    public void testDamageVisitor() {
        Warrior warrior = new Warrior("TestWarrior"); // health = 30
        Wizard wizard = new Wizard("TestWizard"); // health = 40
        Healer healer = new Healer("TestHealer"); // health = 50

        CharacterVisitor damage = new DamageVisitor();

        warrior.accept(damage);
        wizard.accept(damage);
        healer.accept(damage);

        assertEquals(22, warrior.getHealth(), "Le Warrior doit perdre 8 HP");
        assertEquals(32, wizard.getHealth(), "Le Wizard doit perdre 8 HP");
        assertEquals(42, healer.getHealth(), "Le Healer doit perdre 8 HP");
    }


    @Test
    public void testDamageVisitorUnderZero() {
        Warrior warrior = new Warrior("TestWarrior"); // health = 30
        Wizard wizard = new Wizard("TestWizard"); // health = 40
        Healer healer = new Healer("TestHealer"); // health = 50

        CharacterVisitor damage = new DamageVisitor();

        warrior.accept(damage); // HP = 22
        warrior.accept(damage); // HP = 14
        warrior.accept(damage); // HP = 6
        warrior.accept(damage); // HP = 1


        wizard.accept(damage); // HP = 32
        wizard.accept(damage); // HP = 24
        wizard.accept(damage); // HP = 16
        wizard.accept(damage); // HP = 8
        wizard.accept(damage); // HP = 1

        healer.accept(damage); // HP = 42
        healer.accept(damage); // HP = 34
        healer.accept(damage); // HP = 26
        healer.accept(damage); // HP = 18
        healer.accept(damage); // HP = 10
        healer.accept(damage); // HP = 2
        healer.accept(damage); // HP = 1
        healer.accept(damage); // HP = 1
              

        assertEquals(1, warrior.getHealth(), "En dessous de 0, les HP du Warrior doivent être maintenus à 1");
        assertEquals(1, wizard.getHealth(), "En dessous de 0, les HP du Wizard doivent être maintenus à 1");
        assertEquals(1, healer.getHealth(), "En dessous de 0, les HP du Healer doivent être maintenus à 1");

    }

    @Test
    public void testHealVisitor() {
        Warrior warrior = new Warrior("TestWarrior");
        Wizard wizard = new Wizard("TestWizard");
        Healer healer = new Healer("TestHealer");

        // Modifions les HP par défaut
        warrior.setHealth(80);
        wizard.setHealth(80);
        healer.setHealth(80);

        CharacterVisitor heal = new HealVisitor();
        warrior.accept(heal);
        wizard.accept(heal);
        healer.accept(heal);

        assertEquals(88, warrior.getHealth(), "Le Warrior doit gagner 8 HP");
        assertEquals(88, wizard.getHealth(), "Le Wizard doit gagner 8 HP");
        assertEquals(88, healer.getHealth(), "Le Healer doit gagner 8 HP");
    }

    @Test
    public void testConsecutiveVisitors() {
        Warrior warrior = new Warrior("TestWarrior");
        Wizard wizard = new Wizard("TestWizard");
        Healer healer = new Healer("TestHealer");

        CharacterVisitor buff = new BuffVisitor();
        CharacterVisitor damage = new DamageVisitor();
        CharacterVisitor heal = new HealVisitor();

        // Application successive
        warrior.accept(buff);
        warrior.accept(damage);
        warrior.accept(heal);

        // Le Warrior avait 30 HP de base, après damage => 22, après heal => 30
        // La force du Warrior a été buffée de 7 à 15.
        assertEquals(30, warrior.getHealth(), "HP après buff, damage, heal doit revenir à 30");
        assertEquals(15, warrior.getStrength(), "La force après buff doit être de 15");

        // Wizard test
        wizard.accept(buff);   // intelligence= 5+8 = 13
        wizard.accept(damage); // HP=32
        wizard.accept(damage); // HP=24
        wizard.accept(heal);   // HP=32

        assertEquals(32, wizard.getHealth(), "Le Wizard doit finir à 32 HP après buffs/damage/heal");
        assertEquals(13, wizard.getIntelligence(), "L'intelligence doit être 13 après buff");

        // Healer test
        healer.accept(buff);  // wisdom= 3+8 = 11
        healer.accept(buff); // wisdom = 11+8 = 19
        healer.accept(damage); // HP=42
        healer.accept(heal);   // HP=50

        assertEquals(50, healer.getHealth(), "Le Healer doit finir à 50 HP");
        assertEquals(19, healer.getWisdom(), "La sagesse doit être 19 après le buff");
    }
    
}
