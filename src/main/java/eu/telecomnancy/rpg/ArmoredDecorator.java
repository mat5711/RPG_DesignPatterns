package eu.telecomnancy.rpg;

public class ArmoredDecorator extends Decorator{

    private double facteurReductionDegats;    

    public ArmoredDecorator(GameCharacter decoratedCharacter, double facteurReductionDegats) {
        super(decoratedCharacter);
        this.facteurReductionDegats = facteurReductionDegats;
    }


    @Override
    public void receiveAttack(int damage) {
        // Réduction des dégâts reçus
        int reducedDamage = (int) (damage * facteurReductionDegats);
        super.receiveAttack(reducedDamage);
    }

    //Pour le clonage :
    @Override
    public ArmoredDecorator clone() {
        return (ArmoredDecorator) super.clone();
    }


}
