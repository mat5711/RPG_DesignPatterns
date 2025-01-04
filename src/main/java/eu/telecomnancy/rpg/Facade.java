package eu.telecomnancy.rpg;

import java.util.HashMap;
import java.util.Map;

public class Facade {
    private Map<String, Team> teams; // Liste des différentes équipes

    public Facade(){
        this.teams = new HashMap<>();
    }

    //On ajoute une équipe au jeu
    public void addTeam(Team team) {
        if (teams.containsKey(team.getName())) {
            System.out.println("Une équipe avec ce nom existe déjà.");
            return;
        }
        teams.put(team.getName(), team);
        System.out.println("Équipe " + team.getName() + " ajoutée.");
    }

    //On retire une équipe du jeu
    public void removeTeam(String teamName) {
        if (teams.remove(teamName) != null) {
            System.out.println("Équipe " + teamName + " supprimée.");
        } else {
            System.out.println("Aucune équipe nommée " + teamName + " n'existe.");
        }
    }


    //attaque entre 2 teams, on donne l'indice dans la team de l'attaquant et l'indice dans la team du défenseur
    public void teamAttack(String attackerTeamName, String defenderTeamName, 
                           int attackerIndex, int defenderIndex) {
        Team attackerTeam = teams.get(attackerTeamName);
        Team defenderTeam = teams.get(defenderTeamName);

        if (attackerTeam == null || defenderTeam == null) {
            System.out.println("Impossible de réaliser l'attaque : équipe introuvable.");
            return;
        }
        if (attackerIndex < 0 || attackerIndex >= attackerTeam.getPlayers().size() ||
            defenderIndex < 0 || defenderIndex >= defenderTeam.getPlayers().size()) {
            System.out.println("Indices de personnages invalides.");
            return;
        }

        GameCharacter attacker = attackerTeam.getPlayers().get(attackerIndex);
        GameCharacter defender = defenderTeam.getPlayers().get(defenderIndex);

        attacker.attack(defender);
        System.out.println(attacker.getName() + " attaque " + defender.getName() 
                           + " ! (HP défenseur : " + defender.getHealth() + ")");
    }


    //Soigne une team
    public void healTeam(String teamName) {
        Team team = teams.get(teamName);
        if (team == null) {
            System.out.println("Équipe introuvable : " + teamName);
            return;
        }
        CharacterVisitor healVisitor = new HealVisitor();
        team.accept(healVisitor); //on a aussi implémenter dans Team la méthode accept, pour pouvoir appliquer des Visitors directement sur des Teams
        System.out.println("Équipe " + teamName + " soignée.");
    }

    //Buff une team
    public void buffTeam(String teamName) {
        Team team = teams.get(teamName);
        if (team == null) {
            System.out.println("Équipe introuvable : " + teamName);
            return;
        }
        CharacterVisitor buffVisitor = new BuffVisitor();
        team.accept(buffVisitor);
        System.out.println("Équipe " + teamName + " buffée.");
    }

    //Décore un personnage précis d'une équipe avec une armure (ArmoredDecorator)
    public void addArmor(String teamName, int characterIndex, double reductionFactor) {
        Team team = teams.get(teamName);
        if (team == null) {
            System.out.println("Équipe introuvable : " + teamName);
            return;
        }
        if (characterIndex < 0 || characterIndex >= team.getPlayers().size()) {
            System.out.println("Index de personnage invalide.");
            return;
        }

        GameCharacter original = team.getPlayers().get(characterIndex);
        // On décore ce personnage
        GameCharacter armored = new ArmoredDecorator(original, reductionFactor);
        team.getPlayers().set(characterIndex, armored); //on n'oublie pas de "remplacer" notre personnage par ce personnage décoré

        System.out.println("Personnage " + original.getName() + " protégé par une armure.");
    }

    //Décore un personnage avec l'invincibilité (InvincibleDecorator)
    public void addInvincible(String teamName, int characterIndex) {
        Team team = teams.get(teamName);
        if (team == null) {
            System.out.println("Équipe introuvable : " + teamName);
            return;
        }
        if (characterIndex < 0 || characterIndex >= team.getPlayers().size()) {
            System.out.println("Index de personnage invalide.");
            return;
        }

        GameCharacter original = team.getPlayers().get(characterIndex);
        GameCharacter invincible = new InvincibleDecorator(original);
        team.getPlayers().set(characterIndex, invincible); //pareil, on "remplace" le personnage par ce personnage avec l'invincibilité

        System.out.println("Personnage " + original.getName() + " est désormais invincible.");
    }

    //Retrait d'une décoration sur un personnage précis d'une team
    public void removeDecorator(String teamName, int characterIndex, Class<?> decoratorClass) {
        Team team = teams.get(teamName);
        if (team == null) {
            System.out.println("Équipe introuvable : " + teamName);
            return;
        }
        if (characterIndex < 0 || characterIndex >= team.getPlayers().size()) {
            System.out.println("Index de personnage invalide.");
            return;
        }

        GameCharacter character = team.getPlayers().get(characterIndex);
        // On défait le décorateur via une fonction récursive ou unwrapping
        GameCharacter unwrapped = unwrapDecorator(character, decoratorClass);
        team.getPlayers().set(characterIndex, unwrapped);

        System.out.println("Décorateur " + decoratorClass.getSimpleName() 
                           + " retiré pour " + character.getName() + ".");
    }

    //Retire le décorateur de type decoratorClass dans la chaîne d'enrobage du personnage.
    private GameCharacter unwrapDecorator(GameCharacter character, Class<?> decoratorClass) {
        if (!(character instanceof Decorator)) {
            return character; // Rien à unwrap, c’est un personnage "nu"
        }
        Decorator decorator = (Decorator) character;
        if (decoratorClass.isInstance(decorator)) {
            // On saute ce décorateur et on retourne le "périphérique" décoré
            return decorator.decoratedCharacter;
        } else {
            // Sinon, on tente de désempiler plus bas
            decorator.decoratedCharacter = unwrapDecorator(decorator.decoratedCharacter, decoratorClass);
            return decorator;
        }
    }
    
    //Renvoie toutes les équipes présentes dans la Facade
    public Map<String, Team> getTeams() {
        return teams;
    }
}
