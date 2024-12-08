package main.java.eu.telecomnancy.rpg;

import java.util.Random;

public class Healer extends GameCharacter{
    private int intelligence;

    public Healer(String name){
        super(name); //On appelle le constructeur de la classe mère pour déjà créé un GameCharacter, et ensuite on donne les détails pour notre Healer
        this.intelligence = getIntelligence() * 10+new Random().nextInt(10); //on lui donne une intelligence random et on pourra la modifier avec setIntelligence
    }

    public int getIntelligence(){
        return this.intelligence;
    }

    public void setIntelligence(int intelligence){
        this.intelligence = intelligence;
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
