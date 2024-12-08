package main.java.eu.telecomnancy.rpg;

public class WarriorCreator implements CharacterCreator{
    
    //On ne met pas de constructeur, on laisse celui par défaut, il ne nous sert à rien d'en avoir un
    
    @Override
    public GameCharacter createCharacter(String name){
        Warrior warrior  = new Warrior(name);
        return warrior;
    }


}
