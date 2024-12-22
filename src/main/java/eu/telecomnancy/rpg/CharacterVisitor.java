package main.java.eu.telecomnancy.rpg;

import main.java.eu.telecomnancy.rpg.Healer;

public interface CharacterVisitor {
    void visit(Warrior warrior);
    void visit(Wizard wizard);
    void visit(Healer healer);
}
