package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterStrategyTest {

    private Warrior warrior; // HP de base = 30, stratégie par défaut = neutre
    private Wizard wizard; // HP de base = 30, stratégie par défaut = neutre

    @BeforeEach
    public void setUp() {
        warrior = new Warrior("TestWarrior");
        wizard = new Wizard("TestWizard");

        // On monte leur HP de base pour éviter les changements de State pendant les tests, car ce n'est pas le but ici de les prendre en compte
        warrior.setHealth(100); //Restera en NormalState tout le temps
        wizard.setHealth(100); // Restera en NormalState tout le temps
    }

    @Test
    public void testNeutralStrategy() {


        // La stratégie par défaut est neutre, les dégâts infligés et reçus sont inchangés
        warrior.attack(wizard); // 100-7 = 93
        assertEquals(93, wizard.getHealth(), "Wizard doit perdre 7 HP avec la stratégie neutre");
    }

    @Test
    public void testAggressiveStrategy() {

        CombatStrategy aggressive = new AggressiveStrategy();

        warrior.setStrategy(aggressive);
        wizard.setStrategy(aggressive);

        // warior fait 2 fois plus de dégâts, donc 2*7 = 14 de dégâts
        // wizard subi 2 fois plus de dégâts, donc 2*14 = 28 de dégâts encaissés
        warrior.attack(wizard);
        //HP de base = 100, donc dégâts subis : 100-28 = 72
        assertEquals(72, wizard.getHealth(), "Wizard doit perdre 28 HP lorsque les deux personnages sont en agressif");
    }

    @Test
    public void testDefensiveStrategy() {

        CombatStrategy defensive = new DefensiveStrategy();

        wizard.setStrategy(defensive);
        warrior.setStrategy(defensive);

        wizard.attack(warrior);
        // wizard doit faire 2 fois moins de dégâts, donc 5/2 = 2 de dégâts
        // warrior doit subir 2 fois moins de dégâts, donc 2/2 = 1 de dégât encaissé
        assertEquals(99, warrior.getHealth(), "Warrior doit perdre 1 HP lorsque les deux personnages sont en défensif");
    }

    @Test
    public void testChangingStrategiesDynamically() {
        
        // Etape 1 : neutre vs neutre
        warrior.attack(wizard); // wizard subit 7 de dégâts
        assertEquals(93, wizard.getHealth());

        // Etape 2 : warrior devient agressif
        CombatStrategy aggressive = new AggressiveStrategy();

        warrior.setStrategy(aggressive); // Warrior fait 2 fois plus de dégâts, soit 2*7 = 14
        warrior.attack(wizard); // wizard HP = 93 - 14 = 79
        assertEquals(79, wizard.getHealth());

        wizard.attack(warrior); // warrior subit 2 fois plus de dégats, HP_initiaux = 100
        assertEquals(90, warrior.getHealth(), "Wizard attaque en neutre donc inflige 5 HP, et Warior est en aggresif, donc subit 2 fois plus de dégâts, donc subi finalement 2*5 = 10 de dégâts");

        // Etape 3 : wizard devient défensif
        CombatStrategy defensive = new DefensiveStrategy();

        wizard.setStrategy(defensive); // Wizard reçoit 2 fois moins de dégâts
        warrior.attack(wizard); // Dégâts infligés = 2*7 = 14 (agressif), mais wizard défensif => subit = 14/2 = 7
    
        assertEquals(72, wizard.getHealth());

        //warrior repasse en neutre
        CombatStrategy neutral = new NeutralStrategy();
        
        warrior.setStrategy(neutral);
        warrior.attack(wizard); // Dégats infligés = 7 (neutre), wizard défensif => subit = 7/2 = 3

        assertEquals(69, wizard.getHealth(), "Warrior est en neutre, donc fait 7 de dégâts, et wizard est en défensif, donc subit 7/2 = 3 de dégâts");
        
    }
}
