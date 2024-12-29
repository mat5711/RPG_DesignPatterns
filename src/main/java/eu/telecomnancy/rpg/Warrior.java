package eu.telecomnancy.rpg;



public class Warrior extends GameCharacter {
    private int strength;

    public Warrior(String name) {
        super(name);
        this.strength = getLevel() * 7; // la force du personnage est par rapport à son niveau
        super.health = 30;
    }

    public int getStrength() {
        return strength;
    }
    
    public void setStrength(int strength) {
        this.strength = strength;
    }
    
    public int getMainAttribut(){
        return this.strength;
    }

    public void attributUpdate(int level){
        this.strength = level * 5;
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
