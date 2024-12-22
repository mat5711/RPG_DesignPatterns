package eu.telecomnancy.rpg;

public class AggressiveStrategy implements CombatStrategy {
    
    public int degatsEncaisses(int damage) {
        return damage * 2;
    }

    public int degatsInfliges(int damage) {
        return damage * 2;
    }
    
}
