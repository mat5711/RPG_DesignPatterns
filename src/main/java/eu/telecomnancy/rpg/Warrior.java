package eu.telecomnancy.rpg;



public class Warrior extends GameCharacter {
    private int strength;

    public Warrior(String name) {
        super(name);
        this.strength = getLevel() * 10; // la force du personnage est par rapport à son niveau
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
