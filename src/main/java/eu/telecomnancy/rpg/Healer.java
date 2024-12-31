package eu.telecomnancy.rpg;


public class Healer extends GameCharacter{
    private int wisdom;

    public Healer(String name){
        super(name); //On appelle le constructeur de la classe mère pour avoir les caractéristiques du GameCharacter, et ensuite on donne les détails pour notre Healer
        this.wisdom = getLevel() * 3; // la sagesse du personnage est par rapport à son niveau
        super.health = 50;
    }

    public int getWisdom(){
        return this.wisdom;
    }

    public void setWisdom(int wisdom){
        this.wisdom = wisdom;
    }

    public int getMainAttribut(){
        return this.wisdom;
    }

    public void attributUpdate(int level){
        this.wisdom = level * 3;
    }

    //Pour le clonage :
    @Override
    public Healer clone() {
        return (Healer) super.clone();
    }


    //pour le visitor
    @Override
    public void accept(CharacterVisitor visitor) {
        visitor.visit(this);
    }
}
