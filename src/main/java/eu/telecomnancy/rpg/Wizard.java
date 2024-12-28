package eu.telecomnancy.rpg;

import java.util.Random;


public class Wizard extends GameCharacter {

    private int intelligence;

    public Wizard(String name) {
        super(name);
        this.intelligence = getLevel() * 10; // l'intelligence du personnage est par rapport à son niveau
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
    
    
    //Pour le clonage :
    @Override
    public Wizard clone() {
        return (Wizard) super.clone();
    }


    //pour le visitor
    @Override
    public void accept(CharacterVisitor visitor) {
        visitor.visit(this);
    }
}
