package eu.telecomnancy.rpg;


public interface CharacterVisitor {
    void visit(Warrior warrior);
    void visit(Wizard wizard);
    void visit(Healer healer);
}
