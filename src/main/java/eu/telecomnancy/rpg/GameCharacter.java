package eu.telecomnancy.rpg;

import java.util.List;

public abstract class GameCharacter implements Cloneable{

    protected final String name; // le nom du personnage est final (constant), il ne peut pas être modifié
    protected int health;
    protected int experiencePoints;
    protected int level;
    protected CombatStrategy combatStrategy;
    protected List<Observer> observers;
    protected State state;
    protected int stateDamage; // Correspond à la valeur des dégâts infligés par un personnage lors d'une attaque, en fonction de son State

    //on définit un sueil et si experiencePoints est au dessus de ce sueil, on monte de niveau, et le seuil devient plus élevé
    protected int xpRequiredForNextLevel;

    public GameCharacter(String name) {
        this.name = name;
        this.experiencePoints = 0;
        this.level = 1;
        this.health = 30;
        this.combatStrategy = new NeutralStrategy(); // on met le personnage en stratégie neutre par défaut
        this.xpRequiredForNextLevel = 10;
        this.state = new NormalState(); // Le personnage est dans un état normal par défaut
    }


    //Pour le visitor
    public abstract void accept(CharacterVisitor visitor);

    //pour le clonage
    @Override
    public GameCharacter clone() {
        try {
            return (GameCharacter) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    
    //Pas de setName puisque le nom est final

    public String getName() {
        return name;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getExperiencePoints() {
        return this.experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String toString() {
        return name + " (Level " + level + ") with " + health + " HP and " + experiencePoints + " XP";
    }
    
    //Pour Strategy (les méthodes attack et receiveAttack se trouvent tout à la fin, car elles prennent également en compte le State du personnage):
    public CombatStrategy getStrategy(){
        return this.combatStrategy;
    }

    public void setStrategy(CombatStrategy combatStrategy){
        this.combatStrategy = combatStrategy;
    }


    //Partie Observer
    public void checkLevelUp() {
        if (this.experiencePoints >= this.xpRequiredForNextLevel) {
            this.level++;
            this.experiencePoints = 0;
            this.xpRequiredForNextLevel *= 2; // il faudra 2 fois plus d'xp pour monter au niveau suivant
            notifyChangementLevel();
        }
    }

    public void notifyChangementLevel() {
        //Seul les observers qui s'occupent du level up sont notifiés, car pour les autres obersvers, la méthode ne fait rien
        for (Observer observer : observers) {
            observer.levelUp(this);
        }
    }

    public void checkDeath() {
        if (this.health <= 0) {
            notifyDeath();
        }
    }

    public void notifyDeath() {
        //Pareil : seul les observers qui s'occupent de la mort sont notifiés, car pour les autres obersvers, la méthode ne fait rien
        for (Observer observer : observers) {
            observer.death(this);
        }
    }

    //permet à un observer de s'abonner à CE personnage (attention : si un observer veut s'abonner à plusieurs personnages, il doit appeler cette méthode à partir de chaque personnage)
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    //permet à un observer de se désabonner de CE personnage
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }




    // State
    public State getState(){
        return this.state;
    }

    public void setState(State state){
        this.state = state;
        state.onEnterState(this); // on avertit le personnage qu'il est dans un nouvel état
    }

    //permet de changer de State du personnage en fonction de la situation
    public void update(){
        this.state.onUpdate(this);
    }

    //permet de tenter de bouger le personnage
    public void tryToMove(){
        this.state.onTryToMove(this);
    }

    // Les combats sont gérés par la stratégie de combat du personnage ainsi que son State

    //attaquer un autre personnage
    public void attack(GameCharacter ennemyCharacter) {
        // On appelle d'abord onAttack de State pour mettre à jour stateDamage en fonction du State du personnage
        this.state.onAttack(this, ennemyCharacter);
        // On définit les dégâts réels infligés en fonciton de la stratégie adoptée pour le personnage
        int realDamage = this.combatStrategy.degatsInfliges(stateDamage);
        ennemyCharacter.receiveAttack(realDamage);
    }

    //Subir une attaque
    public void receiveAttack(int damage) {
        this.setHealth(this.getHealth() - this.combatStrategy.degatsEncaisses(damage));
        checkDeath();
    }
    

}
