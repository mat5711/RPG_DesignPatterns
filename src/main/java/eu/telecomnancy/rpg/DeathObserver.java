package eu.telecomnancy.rpg;

public class DeathObserver implements Observer {
    
    @Override
    public void onLevelUp(GameCharacter character) {
    //On ne fait rien, on ne s'occupe pas du level up ici, mais on est obligé de redéfinir la méthode
    }
    
    @Override
    public void onDeath(GameCharacter character) {
        System.out.println("Le personnage " + character.getName() + " est mort");
    }
    
}
