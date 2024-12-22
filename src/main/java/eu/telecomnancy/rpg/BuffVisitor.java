package main.java.eu.telecomnancy.rpg;

import java.util.Collection;
import main.java.eu.telecomnancy.rpg.GameCharacter;

public class BuffVisitor implements CharacterVisitor {

    public void visit(Warrior warrior){
        int strength = warrior.getStrength();
        warrior.setStrength(strength+10);
    }

    
    public void visit(Wizard wizard){
        int intelligence = wizard.getIntelligence();
        wizard.setIntelligence(intelligence+10);
    }


    public void visit(Healer healer){
        int health = healer.getHealth();
        healer.setHealth(health+10);
    }


}
