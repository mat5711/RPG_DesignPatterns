package eu.telecomnancy.rpg;


public class BuffVisitor implements CharacterVisitor {

    public void visit(Warrior warrior){
        int strength = warrior.getStrength();
        warrior.setStrength(strength+8);
    }

    
    public void visit(Wizard wizard){
        int intelligence = wizard.getIntelligence();
        wizard.setIntelligence(intelligence+8);
    }


    public void visit(Healer healer){
        int health = healer.getWisdom();
        healer.setWisdom(health+8);
    }


}
