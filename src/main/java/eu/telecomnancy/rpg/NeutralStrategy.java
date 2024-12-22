package eu.telecomnancy.rpg;

public class NeutralStrategy implements CombatStrategy {
    
    public int degatsEncaisses(int damage) {
        return damage;
    }

    public int degatsInfliges(int damage) {
        return damage;
    }
    
}
