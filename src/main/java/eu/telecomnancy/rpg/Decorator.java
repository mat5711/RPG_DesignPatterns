package eu.telecomnancy.rpg;

public class Decorator extends GameCharacter{
    protected GameCharacter decoratedCharacter;

    public Decorator(GameCharacter decoratedCharacter) {
        //on appelle le constructeur de la classe mère
        super(decoratedCharacter.getName()); //fait référence au gameCharacter du paramètre de notre méthode
        this.decoratedCharacter = decoratedCharacter;
    }

    @Override
    public String getName() {
        return decoratedCharacter.getName();
    }


    @Override
    public void accept(CharacterVisitor visitor) {
        decoratedCharacter.accept(visitor);
    }

    
}
