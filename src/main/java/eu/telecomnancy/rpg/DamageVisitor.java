package eu.telecomnancy.rpg;

public class DamageVisitor implements CharacterVisitor{

    public void visit(Warrior warrior){
        int health = warrior.getHealth();
        if (warrior.getHealth() - 8 >= 0){
            warrior.setHealth(health-8);
        }
        else{
            warrior.setHealth(1);
        }
    }

    public void visit(Wizard wizard){
        int health = wizard.getHealth();
        if (wizard.getHealth() - 8 >= 0){
            wizard.setHealth(health-8);
        }
        else{
            wizard.setHealth(1);
        }
    }

    public void visit(Healer healer){
        int health = healer.getHealth();
        if (healer.getHealth() - 8 >= 0){
            healer.setHealth(health-8);
        }
        else{
            healer.setHealth(1);
        }
    }

    
}
