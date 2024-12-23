package eu.telecomnancy.rpg;

public class LevelUpObserver implements Observer {
    
    @Override
    public void levelUp(GameCharacter character) {
        System.out.println(character.getName() + " est monté au niveau : " + character.getLevel());
    }

    @Override
    public void death(GameCharacter character) {
    //On ne fait rien, on ne s'occupe pas de la mort ici, mais on est obligé de redéfinir la méthode
    }
    
}
