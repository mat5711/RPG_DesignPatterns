package main.java.eu.telecomnancy.rpg;

public class WarriorCreator implements CharacterCreator{
    @Override
    public GameCharacter createCharacter(String name){
        Warrior warrior  = new Warrior(name);
        return warrior;
    }
}
