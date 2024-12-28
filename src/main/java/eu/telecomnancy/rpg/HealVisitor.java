package eu.telecomnancy.rpg;


public class HealVisitor implements CharacterVisitor{

    public void visit(Warrior warrior){
        int health = warrior.getHealth();
        warrior.setHealth(health+8);

    }

    public void visit(Wizard wizard){
        int health = wizard.getHealth();
        wizard.setHealth(health+8);
    }

    public void visit(Healer healer){
        int health = healer.getHealth();
        healer.setHealth(health+8);
    }

    
}
