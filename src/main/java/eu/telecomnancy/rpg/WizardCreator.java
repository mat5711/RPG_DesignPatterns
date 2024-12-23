package eu.telecomnancy.rpg;

public class WizardCreator implements CharacterCreator{

    public GameCharacter createCharacter(String name){
        Wizard wizard = new Wizard(name);
        return wizard;
    }
}
