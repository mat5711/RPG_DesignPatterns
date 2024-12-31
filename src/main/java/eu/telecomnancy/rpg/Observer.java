package eu.telecomnancy.rpg;

public interface Observer {
    void onLevelUp(GameCharacter character);
    void onDeath(GameCharacter character);
}
