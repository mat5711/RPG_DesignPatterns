package eu.telecomnancy.rpg;

public interface Command {
    void execute();
    void undo(); // pour l'annulation
}
