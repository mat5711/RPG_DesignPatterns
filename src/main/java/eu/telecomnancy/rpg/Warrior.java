package eu.telecomnancy.rpg;

import java.util.Random;

public class Warrior extends GameCharacter {
    private int strength;

    public Warrior(String name) {
        super(name);
        this.strength = getLevel() * 10+new Random().nextInt(10);
    }

    public int getStrength() {
        return strength;
    }
    
    public void setStrength(int strength) {
        this.strength = strength;
    }
    

    //Pour le clonage :
    @Override
    public Warrior clone() {
        return (Warrior) super.clone();
    }
    

    //Pour le visitor
    @Override
    public void accept(CharacterVisitor visitor) {
        visitor.visit(this);
    }
}
