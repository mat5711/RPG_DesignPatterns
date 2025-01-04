package eu.telecomnancy.rpg;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // On instancie la Façade
        Facade facade = new Facade();
        // On instancie l'Invoker
        GameInvoker invoker = new GameInvoker();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Ajouter une équipe");
            System.out.println("2. Supprimer une équipe");
            System.out.println("3. Attaque (TeamA vs TeamB)");
            System.out.println("4. Heal une équipe");
            System.out.println("5. Buff une équipe");
            System.out.println("6. Undo la dernière commande");
            System.out.println("7. Quitter");

            System.out.print("Choisissez une option : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.out.print("Entrez le nom de l'équipe : ");
                    String teamName = scanner.nextLine();
                    Team newTeam = new Team(teamName);
                    // On crée une commande et on l’enfile dans l’invoker
                    Command addTeamCmd = new AddTeamCommand(facade, newTeam);
                    invoker.addCommand(addTeamCmd);
                    invoker.processCommands(); // Exécute immédiatement dans cet exemple
                    break;

                case "2":
                    System.out.print("Entrez le nom de l'équipe à supprimer : ");
                    String removeTeamName = scanner.nextLine();
                    Command removeTeamCmd = new RemoveTeamCommand(facade, removeTeamName);
                    invoker.addCommand(removeTeamCmd);
                    invoker.processCommands();
                    break;

                case "3":
                    // Attaque 1v1
                    System.out.print("Team attaquante : ");
                    String attackerTeam = scanner.nextLine();
                    System.out.print("Team défenseuse : ");
                    String defenderTeam = scanner.nextLine();
                    System.out.print("Index attaquant dans l'équipe attaquante : ");
                    int attackerIndex = Integer.parseInt(scanner.nextLine());
                    System.out.print("Index défenseur dans l'équipe défenseuse : ");
                    int defenderIndex = Integer.parseInt(scanner.nextLine());

                    Command attackCmd = new TeamAttackCommand(
                        facade, 
                        attackerTeam, 
                        defenderTeam, 
                        attackerIndex, 
                        defenderIndex
                    );
                    invoker.addCommand(attackCmd);
                    invoker.processCommands();
                    break;

                case "4":
                    System.out.print("Entrez le nom de l'équipe à soigner : ");
                    String healTeamName = scanner.nextLine();
                    Command healCmd = new HealTeamCommand(facade, healTeamName);
                    invoker.addCommand(healCmd);
                    invoker.processCommands();
                    break;

                case "5":
                    System.out.print("Entrez le nom de l'équipe à buffer : ");
                    String buffTeamName = scanner.nextLine();
                    Command buffCmd = new BuffTeamCommand(facade, buffTeamName);
                    invoker.addCommand(buffCmd);
                    invoker.processCommands();
                    break;

                case "6":
                    // Undo
                    invoker.undoLastCommand();
                    break;

                case "7":
                    running = false;
                    break;

                default:
                    System.out.println("Option invalide. Réessayez.");
            }
        }

        scanner.close();
        System.out.println("Fermeture du jeu...");
    }
}
