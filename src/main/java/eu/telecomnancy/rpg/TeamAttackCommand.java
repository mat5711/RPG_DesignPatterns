package eu.telecomnancy.rpg;

public class TeamAttackCommand implements Command {
    private Facade facade;
    private String attackerTeamName;
    private String defenderTeamName;
    private int attackerIndex;
    private int defenderIndex;
    private int oldDefenderHP;
    private boolean executed = false;

    public TeamAttackCommand(Facade facade, String attackerTeamName, String defenderTeamName, int attackerIndex, int defenderIndex) {
        this.facade = facade;
        this.attackerTeamName = attackerTeamName;
        this.defenderTeamName = defenderTeamName;
        this.attackerIndex = attackerIndex;
        this.defenderIndex = defenderIndex;
    }

    @Override
    public void execute() {
        // On sauvegarde l'HP du défenseur pour un éventuel undo
        Team defenderTeam = facade.getTeams().get(defenderTeamName);
        if (defenderTeam == null || defenderIndex < 0 || 
            defenderIndex >= defenderTeam.getPlayers().size()) {
            System.out.println("Attaque impossible, défenseur invalide.");
            return;
        }

        GameCharacter defender = defenderTeam.getPlayers().get(defenderIndex);
        oldDefenderHP = defender.getHealth();

        // On effectue l'attaque via la façade
        facade.teamAttack(attackerTeamName, defenderTeamName, attackerIndex, defenderIndex);
        executed = true;
    }

    @Override
    public void undo() {
        if (executed) {
            // Restaurer l'HP du défenseur
            Team defenderTeam = facade.getTeams().get(defenderTeamName);
            if (defenderTeam != null) {
                GameCharacter defender = defenderTeam.getPlayers().get(defenderIndex);
                defender.setHealth(oldDefenderHP);
                System.out.println("Annulation de l'attaque : HP du défenseur restauré à " + oldDefenderHP);
            }
            executed = false;
        }
    }
}
