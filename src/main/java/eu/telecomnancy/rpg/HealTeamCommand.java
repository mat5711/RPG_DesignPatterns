package eu.telecomnancy.rpg;

public class HealTeamCommand implements Command {
    private Facade facade;
    private String teamName;
    private int[] oldHPs;
    private boolean executed;

    public HealTeamCommand(Facade facade, String teamName) {
        this.facade = facade;
        this.teamName = teamName;
    }

    @Override
    public void execute() {
        Team team = facade.getTeams().get(teamName);
        if (team == null) {
            System.out.println("Équipe introuvable : " + teamName);
            return;
        }

        // On sauvegarde les HP
        oldHPs = new int[team.getPlayers().size()];
        for (int i = 0; i < team.getPlayers().size(); i++) {
            oldHPs[i] = team.getPlayers().get(i).getHealth();
        }

        facade.healTeam(teamName);
        executed = true;
    }

    @Override
    public void undo() {
        if (!executed) return;

        Team team = facade.getTeams().get(teamName);
        if (team == null) return;

        // Restauration des HP
        for (int i = 0; i < team.getPlayers().size(); i++) {
            team.getPlayers().get(i).setHealth(oldHPs[i]);
        }
        System.out.println("Annulation du Heal : HP restaurés.");
        executed = false;
    }
}
