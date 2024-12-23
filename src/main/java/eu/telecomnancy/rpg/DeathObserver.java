package eu.telecomnancy.rpg;

public class DeathObserver implements Observer {
    
    @Override
    public void levelUp(GameCharacter character) {
    //On ne fait rien, on ne s'occupe pas du level up ici, mais on est obligé de redéfinir la méthode
    }
    
    @Override
    public void death(GameCharacter character) {
        System.out.println(character.getName() + " est mort");
    }
    
}
