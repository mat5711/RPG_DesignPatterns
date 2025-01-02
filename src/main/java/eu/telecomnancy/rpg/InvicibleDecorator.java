package eu.telecomnancy.rpg;

public class InvicibleDecorator extends Decorator{
    

    public InvicibleDecorator(GameCharacter decoratedCharacter){
        super(decoratedCharacter);
    }

    @Override
    public void receiveAttack(int damage){
        super.receiveAttack(damage);
        if(this.getHealth() < 1){
            this.setHealth(1); //perso invincible
        }
    }
}
