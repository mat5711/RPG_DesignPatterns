package eu.telecomnancy.rpg;

import java.util.List;
import java.util.ArrayList;

public abstract class GameCharacter implements Cloneable{

    protected final String name; // le nom du personnage est final (constant), il ne peut pas être modifié
    protected int health;
    protected int experiencePoints;
    protected int level;
    protected CombatStrategy combatStrategy;
    protected List<Observer> observers; //Liste des observers qui s'abonnent à ce personnage
    protected State state;
    protected int stateDamage; // Correspond à la valeur des dégâts infligés par un personnage lors d'une attaque, en fonction de son State

    //on définit un sueil et si experiencePoints est au dessus de ce sueil, on monte de niveau, et le seuil devient plus élevé
    protected int xpRequiredForNextLevel;

    public GameCharacter(String name) {
        this.name = name;
        this.experiencePoints = 0;
        this.level = 1;
        this.combatStrategy = new NeutralStrategy(); // on met le personnage en stratégie neutre par défaut
        this.xpRequiredForNextLevel = 10;
        this.state = new NormalState(); // Le personnage est dans un état normal par défaut
        this.observers = new ArrayList<Observer>();
    }


    //Pour le visitor
    // On passe par cette méthode pour accepter un visiteur, comme ça pour les Teams, on parcourt la liste et on appelle cette méthode pour chaque personnage
    // On est obligé de mettre cette méthode en abstract, car on a besoin de savoir si le personnage est un Healer, un Warrior ou un Wizard, donc on doit appeler cette méthode directement dans les classes filles
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
        checkDeath();
    }

    public int getExperiencePoints() {
        return this.experiencePoints;
    }

    //On ajoute des points d'expérience au personnage
    public void addExperiencePoints(int experiencePoints) {
        this.experiencePoints += experiencePoints;
        checkLevelUp();
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
        // On met à jour l'attribut principal (wisdom, intelligence, strength) qui dépend du level du personnage
        this.attributUpdate(level);
    }

    public void attributUpdate(int level){
        this.attributUpdate(level); // Chaque classe fille doit implémenter cette méthode pour mettre à jour son attribut principal
        // Le this. permet d'appeler la méthode de la classe fille en fonction du type de notre personnage
    }

    public String toString() {
        return name + " (Level " + level + ") with " + health + " HP and " + experiencePoints + " XP";
    }
    
    public int getMainAttribut(){
        int attribut = this.getMainAttribut();
        return attribut;
    }


    //Pour Strategy (les méthodes attack et receiveAttack se trouvent tout à la fin, car elles prennent également en compte le State du personnage):
    public CombatStrategy getStrategy(){
        return this.combatStrategy;
    }

    public void setStrategy(CombatStrategy combatStrategy){
        this.combatStrategy = combatStrategy;
    }


    //Partie Observer

    //permet à un observer de s'abonner à CE personnage (attention : si un observer veut s'abonner à plusieurs personnages, il doit appeler cette méthode à partir de chaque personnage)
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    //permet à un observer de se désabonner de CE personnage
    public void removeObserver(Observer observer) {
        if (this.observers.contains(observer)){
            this.observers.remove(observer);
        }
        else{
            System.out.println("Cet observer n'est pas abonné à ce personnage");
        }
    }
    
    public void checkLevelUp() {
        if (this.experiencePoints >= this.xpRequiredForNextLevel) {
            this.level++;
            this.experiencePoints = 0;
            this.xpRequiredForNextLevel *= 2; // il faudra 2 fois plus d'xp pour monter au niveau suivant
            notifyChangementLevel();
            
            // On met à jour l'attribut principal (wisdom, intelligence, strength) qui dépend du level du personnage
            this.attributUpdate(level);
        }
    }

    public void notifyChangementLevel() {
        //Seul les observers qui s'intéressent au level up sont notifiés, car pour les autres obersvers, la méthode ne fait rien
        for (Observer observer : observers) {
            observer.onLevelUp(this);
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
            observer.onDeath(this);
        }
    }



    // State
    public State getState(){
        //On appelle d'abord update pour avoir le véritable état du personnage
        update();
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

    // Les combats sont gérés par la stratégie de combat du personnage AINSI QUE son State

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
