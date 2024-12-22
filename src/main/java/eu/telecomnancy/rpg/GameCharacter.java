package eu.telecomnancy.rpg;



public abstract class GameCharacter implements Cloneable{

    private final String name;
    private int health;
    private int experiencePoints;
    private int level;
    private CombatStrategy combatStrategy;


    public GameCharacter(String name) {
        this.name = name;
        this.experiencePoints = 0;
        this.level = 1;
        this.combatStrategy = new NeutralStrategy(); // on met le personnage en stratégie neutre par défaut
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
        if (this.getHealth() < 0) {
            this.setHealth(0);
        }
    }


}
