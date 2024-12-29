package eu.telecomnancy.rpg;


public class Wizard extends GameCharacter {

    private int intelligence;

    public Wizard(String name) {
        super(name);
        this.intelligence = getLevel() * 5; // l'intelligence du personnage est par rapport à son niveau
        super.health = 40;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getMainAttribut(){
        return this.intelligence;
    }
    
    public void attributUpdate(int level){
        this.intelligence = level * 5;
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
