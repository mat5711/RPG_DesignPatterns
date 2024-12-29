package eu.telecomnancy.rpg;

public interface State {
    
    void onEnterState(GameCharacter character);

    void onUpdate(GameCharacter character);

    void onTryToMove(GameCharacter character);

    void onAttack(GameCharacter character, GameCharacter target);
    
}
