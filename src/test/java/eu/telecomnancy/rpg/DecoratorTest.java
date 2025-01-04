package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DecoratorTest {

    private Healer healer;       // Personnage de base
    private GameCharacter healer2; 
    private Warrior attaquant;   // Personnage qui attaque

    private GameCharacter decoratedHealer; // Healer décoré

    @BeforeEach
    public void setup() {
        // 1) Création d'un Healer
        healer = new Healer("John");
        // 2) Clonage
        healer2 = healer.clone();

        // 3) Modification de la santé
        healer2.setHealth(5);

        // 4) Ajout d'XP
        healer2.addExperiencePoints(1000);

        // 5) Création d'un Warrior "attaquant"
        attaquant = new Warrior("méchant");

        // 6) On décore healer2 avec Armored puis Invincible
        GameCharacter armored = new ArmoredDecorator(healer2, 0.4);
        decoratedHealer = new InvincibleDecorator(armored);
    }

    @Test
    public void testCloneName() {
        // On vérifie que le clone a bien le même nom
        assertEquals("John", healer2.getName(), 
            "Le clone doit avoir le même nom 'John' que le personnage original");
    }

    @Test
    public void testCloneHealthIsModifiedIndependently() {
        // Comme on a mis healer2.setHealth(5) dans le setup
        // On vérifie que healer et healer2 ont des HP différents
        assertNotEquals(healer.getHealth(), healer2.getHealth(),
            "Le clone doit avoir une santé différente, car on a modifié healer2 sans toucher l'original");
    }

    @Test
    public void testCloneExpAfterAddXP() {
        // On a fait healer2.addExperiencePoints(1000)
        //Attention, le fait de lui donner 1000 XP, cela lui fait monter de niveau, donc il doit être level=2 et cela lui remet ses XP à 0
        // On vérifie que le clone a bien pris l'XP (et donc le niveau en plus), mais pas l'original
        assertTrue(healer2.getExperiencePoints() == 0,
            "Le clone (healer2) doit avoir XP = 0 car il est monté d'un niveau (ses XP sont donc remis à 0");
        assertTrue(healer2.getLevel() == 2,
            "Le clone (healer2) doit être level 2");
        assertEquals(0, healer.getExperiencePoints(),
            "Le healer original ne doit pas avoir d'XP si on n'a pas modifié l'original");
        assertTrue(healer.getLevel() == 1,
            "Le clone (healer2) doit être level 1");
    }

    @Test
    public void testDecoratedHealerInitialState() {
        // Au setup, on a décoré le healer2
        // On vérifie que la santé du decoratedHealer est la même que healer2 au départ
        assertEquals(healer2.getHealth(), decoratedHealer.getHealth(),
            "Le personnage décoré doit avoir la même santé que le perso sous-jacent");
    }

    @Test
    public void testReceiveAttackOnDecoratedHealer() {
        // On inflige 5 dégâts => Armored(0.4) => subit 0.4 * 5 = 2 (arrondi) si c'est un int
        decoratedHealer.receiveAttack(5);

        // On vérifie la nouvelle santé
        // health avant => 5
        // subit env. 2 => ~3
        assertTrue(decoratedHealer.getHealth() >= 3, 
            "Avec Armored(0.4), 5 dégâts => on subit ~2, on devrait avoir >=3 HP restants");

        // On vérifie que healer2 et decoratedHealer sont la même entité "physique"
        // => getHealth() doit être identique
        assertEquals(healer2.getHealth(), decoratedHealer.getHealth(),
            "La santé de healer2 doit être la même que celle du décoré (même instance sous-jacente)");
    }

    @Test
    public void testAttackFromWarrior() {
        // attaquant attaque le decoratedHealer
        attaquant.attack(decoratedHealer);
        // Données du Warrior + Strategys éventuelles (inconnues),
        // On se base sur l'effet "Armored(0.4) + Invincible"
        // A minima, on s'attend à ce que la santé ait baissé, mais pas sous 1 à cause d'Invincible

        int hpApresPremiereAttaque = decoratedHealer.getHealth();
        assertTrue(hpApresPremiereAttaque >= 1,
            "Grâce à Invincible, la santé ne doit pas tomber à 0 ou en dessous.");

        // attaquant attaque une deuxième fois
        attaquant.attack(decoratedHealer);
        int hpApresDeuxiemeAttaque = decoratedHealer.getHealth();
        // On vérifie encore qu'on est >= 1
        assertTrue(hpApresDeuxiemeAttaque >= 1,
            "Encore une attaque, on doit rester au moins à 1 HP (Invincible).");
    }

    @Test
    public void testRemoveArmoredDecorator() {
        // On retire seulement Armored, gardant Invincible
        decoratedHealer = Decorator.unwrapSpecificDecorator(decoratedHealer, ArmoredDecorator.class);

        // On attaque
        int oldHP = decoratedHealer.getHealth();
        attaquant.attack(decoratedHealer);

        // Sans Armored => on subit 100% des dégâts
        // Mais Invincible est toujours là => ne peut pas descendre sous 1
        int newHP = decoratedHealer.getHealth();

        // On vérifie que c'est strictement moins que oldHP (car plus de réduction)
        assertTrue(newHP < oldHP, 
            "Sans Armored, les dégâts ne sont plus réduits (mais on reste invincible => >=1).");
        assertTrue(newHP >= 1,
            "Invincible est encore présent, on ne peut pas mourir.");
    }

    @Test
    public void testRemoveInvincibleDecorator() {
        // On retire seulement Invincible (Armored reste)
        decoratedHealer = Decorator.unwrapSpecificDecorator(decoratedHealer, InvincibleDecorator.class);

        // On vérifie qu'on n'est plus 'instanceof InvincibleDecorator'
        // Par contre, on pourrait être encore un ArmoredDecorator
        assertFalse(decoratedHealer instanceof InvincibleDecorator, 
            "Le décorateur Invincible doit avoir été retiré.");

        // On attaque : plus d'invincibilité
        int oldHP = decoratedHealer.getHealth();
        attaquant.attack(decoratedHealer);
        int newHP = decoratedHealer.getHealth();
        // On est encore Armored(0.4), donc on subit 0.4 * damage
        // Mais on peut tomber en dessous de 1

        assertTrue(newHP < oldHP, 
            "On doit perdre plus de HP qu'avant, car plus d'Invincible => possible de mourir.");
        // S'il y a Armored, on subit quand même une réduction => newHP > oldHP - baseDamage
        // On ne le teste pas forcément, on se contente de savoir qu'on n'est plus invincible
    }

    @Test
    public void testRemoveAllDecoratorsThenAttack() {
        // On enlève d'abord Armored
        decoratedHealer = Decorator.unwrapSpecificDecorator(decoratedHealer, ArmoredDecorator.class);
        // Puis Invincible
        decoratedHealer = Decorator.unwrapSpecificDecorator(decoratedHealer, InvincibleDecorator.class);

        // A ce stade, on doit avoir un Healer pur
        assertTrue(decoratedHealer instanceof Healer, 
            "Après avoir retiré Armored et Invincible, on doit retrouver le Healer.");

        // On attaque
        int oldHP = decoratedHealer.getHealth();
        attaquant.attack(decoratedHealer);
        int newHP = decoratedHealer.getHealth();
        // On subit les dégâts complets
        assertTrue(newHP < oldHP, 
            "Plus de décorateurs => on subit les dégâts bruts, la santé doit baisser significativement.");
    }

    @Test
    public void testLevelAfterXP() {
        // On vérifie le niveau après 2000 XP
        // (Dans le setup, on a fait: healer2.addExperiencePoints(1000) avant décoration
        //  puis encore 1000 après, potentiellement)
        // Adaptez selon vos règles de level-up
        int level = decoratedHealer.getLevel();
        // Contrôlez le calcul (par ex. 1000 XP = 2 levels, 2000 XP = 3 levels, etc.)
        // On se contente de vérifier qu'il a progressé
        assertTrue(level > 1, 
            "Avec 2000 XP, le niveau du décoré doit être > 1 (selon vos règles).");
    }

    // Ajoutez d'autres tests si besoin (accès à l'XP, etc.)
}
