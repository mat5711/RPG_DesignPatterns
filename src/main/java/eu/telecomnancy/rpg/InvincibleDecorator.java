package eu.telecomnancy.rpg;

public class InvincibleDecorator extends Decorator{
    

    public InvincibleDecorator(GameCharacter decoratedCharacter){
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
