package eu.telecomnancy.rpg;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Facade facade = new Facade();
        GameInvoker invoker = new GameInvoker();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Ajouter une équipe");
            System.out.println("2. Supprimer une équipe");
            System.out.println("3. Lister les équipes");
            System.out.println("4. Ajouter un personnage dans une équipe");
            System.out.println("5. Supprimer un personnage d'une équipe");
            System.out.println("6. Consulter les membres d'une équipe");
            System.out.println("7. Consulter les infos détaillées d'un personnage (HP, XP, Level, State, Strategy)");
            System.out.println("8. Attaque (TeamA vs TeamB en 1v1)");
            System.out.println("9. Heal une équipe");
            System.out.println("10. Buff une équipe");
            System.out.println("11. Changer la strategy d'un personnage");
            System.out.println("12. Undo la dernière commande");
            System.out.println("13. Quitter");

            System.out.print("\nChoisissez une option : ");
            String choix = scanner.nextLine();

            switch (choix) {

                case "1": {
                    // Ajouter une équipe
                    System.out.print("Entrez le nom de l'équipe : ");
                    String teamName = scanner.nextLine();
                    Team newTeam = new Team(teamName);
                    Command addTeamCmd = new AddTeamCommand(facade, newTeam);
                    invoker.addCommand(addTeamCmd);
                    invoker.processCommands();
                    break;
                }

                case "2": {
                    // Supprimer une équipe
                    System.out.print("Entrez le nom de l'équipe à supprimer : ");
                    String removeTeamName = scanner.nextLine();
                    Command removeTeamCmd = new RemoveTeamCommand(facade, removeTeamName);
                    invoker.addCommand(removeTeamCmd);
                    invoker.processCommands();
                    break;
                }

                case "3": {
                    // Lister les équipes
                    System.out.println("=== Liste des équipes ===");
                    if (facade.getTeams().isEmpty()) {
                        System.out.println("Aucune équipe n'a encore été créée.");
                    } else {
                        for (String name : facade.getTeams().keySet()) {
                            System.out.println("- " + name);
                        }
                    }
                    break;
                }

                case "4": {
                    // Ajouter un personnage dans une équipe
                    System.out.print("Entrez le nom de l'équipe : ");
                    String teamName = scanner.nextLine();

                    if (!facade.getTeams().containsKey(teamName)) {
                        System.out.println("L'équipe " + teamName + " n'existe pas.");
                        break;
                    }

                    // Choix du type
                    System.out.println("Type de personnage (warrior, wizard, healer) : ");
                    String type = scanner.nextLine().trim().toLowerCase();

                    System.out.print("Entrez le nom du personnage : ");
                    String characterName = scanner.nextLine();

                    CharacterCreator creator = null; // c'est obligé de l'initialiser, même à 0, sinon ça fait une erreur car java n'est pas d'accord avec le fait d'initialiser seulement dans le switch

                    switch (type) {
                        case "warrior":
                            creator = new WarriorCreator();
                            break;
                        case "wizard":
                            creator = new WizardCreator();
                            break;
                        case "healer":
                            creator = new HealerCreator();
                            break;
                        default:
                            System.out.println("Type inconnu. Annulation.");
                            break;
                    }

                    if (creator == null) {
                        // on arrête ici
                        break;
                    }

                    // On saisit le niveau initial
                    System.out.print("Entrez le level du personnage (ent) : ");
                    int level = 1;
                    try {
                        level = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Niveau invalide, utilisation du niveau 1 par défaut.");
                    }

                    GameCharacter newChar = creator.createCharacter(characterName);
                    newChar.setLevel(level);

                    facade.getTeams().get(teamName).addPlayer(newChar);
                    System.out.println("Personnage " + characterName + " ajouté à l'équipe " + teamName);
                    break;
                }

                case "5": {
                    // Supprimer un personnage d'une équipe
                    System.out.print("Entrez le nom de l'équipe : ");
                    String teamName = scanner.nextLine();

                    if (!facade.getTeams().containsKey(teamName)) {
                        System.out.println("L'équipe " + teamName + " n'existe pas.");
                        break;
                    }

                    Team team = facade.getTeams().get(teamName);
                    if (team.getPlayers().isEmpty()) {
                        System.out.println("Cette équipe ne contient aucun personnage.");
                        break;
                    }

                    System.out.println("Membres de " + teamName + " :");
                    for (int i = 0; i < team.getPlayers().size(); i++) {
                        System.out.println(i + ". " + team.getPlayers().get(i).getName());
                    }
                    System.out.print("Entrez l'indice du personnage à supprimer : ");
                    int index;
                    try {
                        index = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Indice invalide.");
                        break;
                    }

                    if (index < 0 || index >= team.getPlayers().size()) {
                        System.out.println("Indice invalide.");
                        break;
                    }

                    GameCharacter toRemove = team.getPlayers().get(index);
                    team.removePlayer(toRemove);
                    System.out.println("Personnage " + toRemove.getName() + " retiré de l'équipe " + teamName);
                    break;
                }

                case "6": {
                    // Consulter les membres d'une équipe
                    System.out.print("Entrez le nom de l'équipe : ");
                    String teamName = scanner.nextLine();

                    Team team = facade.getTeams().get(teamName);
                    if (team == null) {
                        System.out.println("Cette équipe n'existe pas.");
                        break;
                    }
                    System.out.println("=== Membres de " + teamName + " ===");
                    if (team.getPlayers().isEmpty()) {
                        System.out.println("Aucun membre dans cette équipe.");
                    } else {
                        for (GameCharacter member : team.getPlayers()) {
                            System.out.println("- " + member.getName() 
                                + " (HP=" + member.getHealth() 
                                + ", Level=" + member.getLevel() + ")");
                        }
                    }
                    break;
                }

                case "7": {
                    // Consulter HP, XP, Level, State, Strategy
                    System.out.print("Entrez le nom de l'équipe : ");
                    String teamName = scanner.nextLine();
                    Team team = facade.getTeams().get(teamName);

                    if (team == null) {
                        System.out.println("Cette équipe n'existe pas.");
                        break;
                    }

                    System.out.print("Entrez le nom du personnage : ");
                    String charName = scanner.nextLine();
                    GameCharacter found = team.getPlayer(charName);

                    if (found == null) {
                        System.out.println("Aucun personnage ne porte le nom " + charName 
                                           + " dans l'équipe " + teamName);
                    } else {
                        // On récupère le nom de la classe de la stratégie
                        String strategyName = (found.getStrategy() == null) 
                            ? "Aucune" 
                            : found.getStrategy().getClass().getSimpleName();

                        // On récupère le State
                        String stateName = (found.getState() == null)
                            ? "Aucun"
                            : found.getState().getClass().getSimpleName();

                        System.out.println("=== Détails du personnage ===");
                        System.out.println("Nom : " + found.getName());
                        System.out.println("HP : " + found.getHealth());
                        System.out.println("XP : " + found.getExperiencePoints());
                        System.out.println("Niveau : " + found.getLevel());
                        System.out.println("État : " + stateName);
                        System.out.println("Stratégie : " + strategyName);
                    }
                    break;
                }

                case "8": {
                    // Attaque 1v1
                    System.out.print("Team attaquante : ");
                    String attackerTeam = scanner.nextLine();
                    System.out.print("Team défenseuse : ");
                    String defenderTeam = scanner.nextLine();

                    int attackerIndex = 0;
                    int defenderIndex = 0;
                    try {
                        System.out.print("Index attaquant : ");
                        attackerIndex = Integer.parseInt(scanner.nextLine());
                        System.out.print("Index défenseur : ");
                        defenderIndex = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Indice invalide.");
                        break;
                    }

                    Command attackCmd = new TeamAttackCommand(
                        facade, attackerTeam, defenderTeam, attackerIndex, defenderIndex
                    );
                    invoker.addCommand(attackCmd);
                    invoker.processCommands();
                    break;
                }

                case "9": {
                    // Heal une équipe
                    System.out.print("Entrez le nom de l'équipe à soigner : ");
                    String healTeamName = scanner.nextLine();
                    Command healCmd = new HealTeamCommand(facade, healTeamName);
                    invoker.addCommand(healCmd);
                    invoker.processCommands();
                    break;
                }

                case "10": {
                    // Buff une équipe
                    System.out.print("Entrez le nom de l'équipe à buffer : ");
                    String buffTeamName = scanner.nextLine();
                    Command buffCmd = new BuffTeamCommand(facade, buffTeamName);
                    invoker.addCommand(buffCmd);
                    invoker.processCommands();
                    break;
                }

                case "11": {
                    // Changer la strategy d'un personnage
                    System.out.print("Entrez le nom de l'équipe : ");
                    String teamName = scanner.nextLine();
                    Team team = facade.getTeams().get(teamName);

                    if (team == null) {
                        System.out.println("Cette équipe n'existe pas.");
                        break;
                    }

                    if (team.getPlayers().isEmpty()) {
                        System.out.println("Cette équipe ne contient aucun personnage.");
                        break;
                    }

                    System.out.println("Membres de " + teamName + " :");
                    for (int i = 0; i < team.getPlayers().size(); i++) {
                        System.out.println(i + ". " + team.getPlayers().get(i).getName());
                    }
                    System.out.print("Entrez l'indice du personnage : ");
                    int index;
                    try {
                        index = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Indice invalide.");
                        break;
                    }

                    if (index < 0 || index >= team.getPlayers().size()) {
                        System.out.println("Indice invalide.");
                        break;
                    }

                    GameCharacter chosenChar = team.getPlayers().get(index);
                    System.out.println("Choisissez la nouvelle stratégie (aggressive, defensive, neutral) : ");
                    String strategyChoice = scanner.nextLine().trim().toLowerCase();

                    CombatStrategy newStrategy = null;
                    switch (strategyChoice) {
                        case "aggressive":
                            newStrategy = new AggressiveStrategy();
                            break;
                        case "defensive":
                            newStrategy = new DefensiveStrategy();
                            break;
                        case "neutral":
                            newStrategy = new NeutralStrategy();
                            break;
                        default:
                            System.out.println("Stratégie inconnue.");
                            break;
                    }

                    if (newStrategy != null) {
                        chosenChar.setStrategy(newStrategy);
                        System.out.println("Stratégie mise à jour pour " 
                            + chosenChar.getName() + " : " + newStrategy.getClass().getSimpleName());
                    }
                    break;
                }

                case "12": {
                    // Undo
                    invoker.undoLastCommand();
                    break;
                }

                case "13": {
                    // Quitter
                    running = false;
                    break;
                }

                default:
                    System.out.println("Option invalide. Réessayez.");
            }
        }

        scanner.close();
        System.out.println("Fermeture du jeu...");
    }
}
