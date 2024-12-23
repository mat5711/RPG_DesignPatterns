package eu.telecomnancy.rpg;

public interface Observer {
    void levelUp(GameCharacter character);
    void death(GameCharacter character);
}
