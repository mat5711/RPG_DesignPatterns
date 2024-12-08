package main.java.eu.telecomnancy.rpg;

import java.util.Random;


public class Wizard extends GameCharacter {

    private int intelligence;

    public Wizard(String name) {
        super(name);
        intelligence = getLevel() * 10+new Random().nextInt(10);
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
    
    
    //Pour le clonage :
    @Override
    public Wizard clone() {
        return (Wizard) super.clone();
    }

}
