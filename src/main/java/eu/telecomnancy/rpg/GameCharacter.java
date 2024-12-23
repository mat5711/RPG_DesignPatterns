package eu.telecomnancy.rpg;

import java.util.List;

public abstract class GameCharacter implements Cloneable{

    private final String name;
    private int health;
    private int experiencePoints;
    private int level;
    private CombatStrategy combatStrategy;
    private List<Observer> observers;

    //on définit un sueil et si experiencePoints est au dessus de ce sueil, on monte de niveau, et le seuil devient plus élevé
    private int xpRequiredForNextLevel;

    public GameCharacter(String name) {
        this.name = name;
        this.experiencePoints = 0;
        this.level = 1;
        this.combatStrategy = new NeutralStrategy(); // on met le personnage en stratégie neutre par défaut
        this.xpRequiredForNextLevel = 10;

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
    
    public CombatStrategy getStrategy(){
        return this.combatStrategy;
    }

    public void setStrategy(CombatStrategy combatStrategy){
        this.combatStrategy = combatStrategy;
    }


    //attaque un autre personnage
    public void attack(GameCharacter ennemyCharacter) {
        int damage = 5; // les dégâts par défaut d'une attaque sont de 5
        int realDamage = this.combatStrategy.degatsInfliges(damage);
        ennemyCharacter.receiveAttack(realDamage);
    }

    //subir une attaque
    public void receiveAttack(int damage) {
        this.setHealth(this.getHealth() - this.combatStrategy.degatsEncaisses(damage));
        checkDeath();
    }


    //Partie Observer :
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

}
