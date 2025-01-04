package eu.telecomnancy.rpg;

public class Main {
    public static void main(String[] args) {
        // Façade du jeu
        Facade facade = new Facade();

        // Invoker
        GameInvoker invoker = new GameInvoker();

        // Créons des équipes
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");

        // Commandes concrètes (voir l'exemple précédent)
        Command addTeamA = new AddTeamCommand(facade, teamA);
        Command addTeamB = new AddTeamCommand(facade, teamB);

        // On enfile les commandes
        invoker.addCommand(addTeamA);
        invoker.addCommand(addTeamB);

        // Process exécute toutes les commandes en attente
        invoker.processCommands();

        // Ajout de personnages dans les équipes
        CharacterCreator warriorCreator = new WarriorCreator();
        CharacterCreator wizardCreator = new WizardCreator();
        teamA.addPlayer(warriorCreator.createCharacter("Conan"));
        teamB.addPlayer(wizardCreator.createCharacter("Merlin"));

        // Commande d'attaque 1v1
        Command attackCommand = new TeamAttackCommand(facade, "TeamA", "TeamB", 0, 0);
        // On enfile l'attaque
        invoker.addCommand(attackCommand);

        // Exécution (enfilez, puis exécutez)
        invoker.processCommands();

        // Suppose qu'on veut annuler la dernière attaque
        invoker.undoLastCommand();

        // On peut faire la même chose pour un soin ou un buff
        Command healTeamB = new HealTeamCommand(facade, "TeamB");
        invoker.addCommand(healTeamB);
        invoker.processCommands();

        // Et annuler ce soin
        invoker.undoLastCommand();
    }

    
}
