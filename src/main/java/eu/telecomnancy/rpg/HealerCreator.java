package eu.telecomnancy.rpg;

public class HealerCreator implements CharacterCreator{

    public GameCharacter createCharacter(String name){
        Healer healer = new Healer(name);
        return healer;
    }

}
