package eu.telecomnancy.rpg;

public class ArmoredDecorator extends Decorator{

    private double facteurReductionDegats;

    public ArmoredDecorator(GameCharacter decoratedCharacter, double facteurReductionDegats) {
        super(decoratedCharacter);
        this.facteurReductionDegats = facteurReductionDegats;
    }


    @Override
    public void receiveAttack(int damage){
        int reducedDamage = (int) (damage * facteurReductionDegats); //on réduit les dégâts subis
        super.receiveAttack(reducedDamage); // on applique la méthode receiveAttack de la classe mère mais en donnant les dégats réduits en paramètre
    }

}
