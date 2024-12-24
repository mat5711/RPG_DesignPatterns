package eu.telecomnancy.rpg;

public class InvicibleDecorator extends Decorator{
    

    public InvicibleDecorator(GameCharacter decoratedCharacter){
        super(decoratedCharacter);
    }

    @Override
    public void receiveAttack(int damage){
        this.setHealth(this.getHealth() - this.combatStrategy.degatsEncaisses(damage));
        if(this.getHealth() <= 0){
            this.setHealth(1); //perso invincible
        }
    }
}
