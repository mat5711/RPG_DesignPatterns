package eu.telecomnancy.rpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CommandTest {

    private Facade facade;
    private GameInvoker invoker;

    @BeforeEach
    void globalSetup() {
        // On recrée la Façade et l'Invoker avant chaque test
        facade = new Facade();
        invoker = new GameInvoker();
    }

    // =========================================================================
    // 1) TESTS SUR AddTeamCommand
    // =========================================================================
    @Nested
    class AddTeamCommandTests {
        @BeforeEach
        void setupForAddTeam() {
            // On n'ajoute rien de particulier, juste un contexte "vide" dans la façade
        }

        @Test
        void testAddTeamCreatesEntry() {
            Team team = new Team("TeamA");
            Command addTeamCmd = new AddTeamCommand(facade, team);

            // On enfile la commande...
            invoker.addCommand(addTeamCmd);
            // ...et on exécute tout ce qui est en attente
            invoker.processCommands();

            assertTrue(facade.getTeams().containsKey("TeamA"),
                "Après l'exécution d'AddTeamCommand, la façade doit contenir 'TeamA'");
        }

        @Test
        void testUndoAddTeamRemovesIt() {
            Team team = new Team("TeamA");
            Command addTeamCmd = new AddTeamCommand(facade, team);

            // Ajout de la commande, puis exécution
            invoker.addCommand(addTeamCmd);
            invoker.processCommands();

            // Annulation
            invoker.undoLastCommand();

            assertFalse(facade.getTeams().containsKey("TeamA"),
                "Après annulation, 'TeamA' ne doit plus exister dans la façade");
        }
    }

    // =========================================================================
    // 2) TESTS SUR RemoveTeamCommand
    // =========================================================================
    @Nested
    class RemoveTeamCommandTests {
        @BeforeEach
        void setupForRemoveTeam() {
            // On crée déjà une équipe "TeamB" dans la façade
            Team initialTeam = new Team("TeamB");
            facade.addTeam(initialTeam);
        }

        @Test
        void testRemoveExistingTeam() {
            Command removeTeamCmd = new RemoveTeamCommand(facade, "TeamB");

            // On enfile la commande
            invoker.addCommand(removeTeamCmd);
            // On exécute
            invoker.processCommands();

            assertFalse(facade.getTeams().containsKey("TeamB"),
                "Après exécution de RemoveTeamCommand, 'TeamB' doit être supprimée");
        }

        @Test
        void testUndoRemoveRestoresTeam() {
            Command removeTeamCmd = new RemoveTeamCommand(facade, "TeamB");

            // Exécution
            invoker.addCommand(removeTeamCmd);
            invoker.processCommands();

            // Annulation
            invoker.undoLastCommand();

            assertTrue(facade.getTeams().containsKey("TeamB"),
                "Après annulation de RemoveTeamCommand, 'TeamB' doit être restaurée");
        }
    }

    // =========================================================================
    // 3) TESTS SUR TeamAttackCommand (1v1)
    // =========================================================================
    @Nested
    class TeamAttackCommandTests {
        @BeforeEach
        void setupForAttack() {
            // On ajoute deux équipes dans la façade
            Team attackers = new Team("Attackers");
            Team defenders = new Team("Defenders");

            // On crée des personnages
            GameCharacter warrior = new Warrior("WarAttacker");
            warrior.setHealth(50);  // pour éviter la mort trop rapide
            GameCharacter wizard = new Wizard("WizDefender");
            wizard.setHealth(40);

            attackers.addPlayer(warrior);
            defenders.addPlayer(wizard);

            facade.addTeam(attackers);
            facade.addTeam(defenders);
        }

        @Test
        void testAttackReducesDefenderHP() {
            // On attaque : Attackers[0] => Defenders[0]
            Command attackCmd = new TeamAttackCommand(facade,
                "Attackers", "Defenders", 0, 0);

            // On enfile + on exécute
            invoker.addCommand(attackCmd);
            invoker.processCommands();

            // "WizDefender" doit avoir perdu des HP
            Team defenders = facade.getTeams().get("Defenders");
            GameCharacter defender = defenders.getPlayers().get(0);

            assertTrue(defender.getHealth() < 40, 
                "Le défenseur doit avoir perdu des HP après l'attaque");
        }

        @Test
        void testUndoAttackRestoresDefenderHP() {
            int oldHP = facade.getTeams().get("Defenders")
                              .getPlayers().get(0).getHealth();

            Command attackCmd = new TeamAttackCommand(facade,
                "Attackers", "Defenders", 0, 0);

            // On enfile + on exécute
            invoker.addCommand(attackCmd);
            invoker.processCommands();

            // Undo
            invoker.undoLastCommand();

            int newHP = facade.getTeams().get("Defenders")
                              .getPlayers().get(0).getHealth();

            assertEquals(oldHP, newHP,
                "Après annulation de l'attaque, le défenseur retrouve ses HP initiaux");
        }
    }

    // =========================================================================
    // 4) TESTS SUR HealTeamCommand
    // =========================================================================
    @Nested
    class HealTeamCommandTests {
        @BeforeEach
        void setupForHeal() {
            Team healers = new Team("Healers");
            // Ajout d'un personnage blessé
            GameCharacter woundedWarrior = new Warrior("WoundWar");
            woundedWarrior.setHealth(20); // blessé
            healers.addPlayer(woundedWarrior);

            facade.addTeam(healers);
        }

        @Test
        void testHealIncreasesHP() {
            Command healCmd = new HealTeamCommand(facade, "Healers");

            invoker.addCommand(healCmd);
            invoker.processCommands();

            int hp = facade.getTeams().get("Healers")
                           .getPlayers().get(0).getHealth();
            // On attend qu'un heal augmente la vie (ex: +10)
            assertTrue(hp > 20, "Après Heal, le perso doit avoir plus de 20 HP");
        }

        @Test
        void testUndoHealRestoresPreviousHP() {
            int oldHP = facade.getTeams().get("Healers")
                              .getPlayers().get(0).getHealth();

            Command healCmd = new HealTeamCommand(facade, "Healers");
            invoker.addCommand(healCmd);
            invoker.processCommands();

            // Annule
            invoker.undoLastCommand();

            int newHP = facade.getTeams().get("Healers")
                              .getPlayers().get(0).getHealth();
            assertEquals(oldHP, newHP,
                "Après annulation du Heal, le perso retrouve ses HP initiaux");
        }
    }


}
