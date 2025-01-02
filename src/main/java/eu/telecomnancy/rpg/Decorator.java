package eu.telecomnancy.rpg;

public abstract class Decorator extends GameCharacter{
    
    //Le principe est de surcharger toutes les méthodes 
    //de GameCharacter ici pour les appliquer
    //à decoratedCharacter, afin que lorsque notre
    //personnage décoré reçoit un changement,
    //ce changement est changé sur notre personnage
    //de base. Donc en gros, pour que les deux 
    //personnages ne forment enfaite qu'un seul personnage.
    
    protected GameCharacter decoratedCharacter;

    public Decorator(GameCharacter decoratedCharacter) {
        //on appelle le constructeur de la classe mère
        super(decoratedCharacter.getName()); //fait référence au gameCharacter du paramètre de notre méthode
        
        this.decoratedCharacter = decoratedCharacter;
    }



    @Override
    public int getHealth() {
        return this.decoratedCharacter.getHealth();
    }

    @Override
    public void setHealth(int health) {
        this.decoratedCharacter.setHealth(health);
    }


    @Override
    public int getExperiencePoints(){
        return this.decoratedCharacter.getExperiencePoints();
    }

    @Override
    public void addExperiencePoints(int experiencePoints){
        this.decoratedCharacter.addExperiencePoints(experiencePoints);
    }

    @Override
    public int getLevel(){
        return this.decoratedCharacter.getLevel();
    }

    @Override
    public void setLevel(int level){
        this.decoratedCharacter.setLevel(level);
    }

    @Override
    public void attributUpdate(int level){
        this.decoratedCharacter.attributUpdate(level);
    }

    @Override
    public String toString(){
        return this.decoratedCharacter.toString();
    }

    @Override
    public int getMainAttribut(){
        return this.decoratedCharacter.getMainAttribut();
    }

    @Override
    public CombatStrategy getStrategy(){
        return this.decoratedCharacter.getStrategy();
    }

    @Override
    public void setStrategy(CombatStrategy combatStrategy){
        this.decoratedCharacter.setStrategy(combatStrategy);
    }

    @Override
    public void addObserver(Observer observer){
        this.decoratedCharacter.addObserver(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        this.decoratedCharacter.removeObserver(observer);
    }

    @Override
    public void checkLevelUp(){
        this.decoratedCharacter.checkLevelUp();
    }

    @Override
    public void notifyChangementLevel(){
        this.decoratedCharacter.notifyChangementLevel();
    }

    @Override
    public void checkDeath(){
        this.decoratedCharacter.checkDeath();
    }

    @Override
    public void notifyDeath(){
        this.decoratedCharacter.notifyDeath();
    }

    @Override
    public State getState(){
        return this.decoratedCharacter.getState();
    }

    @Override
    public void setState(State state){
        this.decoratedCharacter.setState(state);
    }

    @Override
    public void update(){
        this.decoratedCharacter.update();
    }

    @Override
    public void tryToMove(){
        this.decoratedCharacter.tryToMove();
    }

    @Override
    public void attack(GameCharacter ennemyCharacter){
        this.decoratedCharacter.attack(ennemyCharacter);
    }

    @Override
    public void receiveAttack(int damage){
        this.decoratedCharacter.receiveAttack(damage);
    }


    // Pour le visiteur
    @Override
    public void accept(CharacterVisitor visitor) {
        decoratedCharacter.accept(visitor);
    }


    //Méthode pour retirer dynamiquement un Decorator
    public static GameCharacter unwrapSpecificDecorator(GameCharacter character, Class<? extends Decorator> decoratorClass) {
        
        // Si ce n'est pas un décorateur, on ne fait rien
        if (!(character instanceof Decorator)) {
            return character;
        }

        Decorator deco = (Decorator) character;

        // Si le décorateur courant est du type qu'on veut enlever
        if (decoratorClass.isInstance(deco)) {
            // On saute ce décorateur et on retourne le "niveau inférieur"
            return deco.decoratedCharacter;
        } else {
            // Sinon, on descend plus bas dans la chaîne
            deco.decoratedCharacter = unwrapSpecificDecorator(deco.decoratedCharacter, decoratorClass);
            return deco;
        }
    }

    
}
