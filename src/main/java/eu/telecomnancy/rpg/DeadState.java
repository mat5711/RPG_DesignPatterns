package eu.telecomnancy.rpg;

public class DeadState implements State {

    @Override
    public void onEnterState(GameCharacter character){
        System.out.println("Le personnage " + character.getName() + " est mort");
    }

    @Override
    public void onUpdate(GameCharacter character){
        //Cette méthode ne fait rien, puisque le personnage mort ne peut rien faire
        System.out.println("Impossible de mettre à jour, le personnage " + character.getName() + " est déjà mort");
    }

    @Override
    public void onTryToMove(GameCharacter character){
        System.out.println("Le personnage " + character.getName() + "ne peut pas se déplacer, car mort");
    }

    @Override
    public void onAttack(GameCharacter character, GameCharacter target){
        System.out.println("Le personnage " + character.getName() + " ne peut pas attaquer, car mort");
        //Dans cet état, le personnage inflige 0 points de dégâts à la cible
        character.stateDamage = 0;
    }
}
