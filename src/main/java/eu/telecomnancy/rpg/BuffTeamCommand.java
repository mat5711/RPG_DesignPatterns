package eu.telecomnancy.rpg;

public class BuffTeamCommand implements Command {
    private Facade facade;
    private String teamName;

    // Pour undo, on stocke les valeurs du Warrior/Wizard/Healer avant le buff
    private int[] oldStrength;
    private int[] oldIntelligence;
    private int[] oldWisdom;

    private boolean executed;

    public BuffTeamCommand(Facade facade, String teamName) {
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
        // Sauvegarde des caractéristiques
        oldStrength = new int[team.getPlayers().size()];
        oldIntelligence = new int[team.getPlayers().size()];
        oldWisdom = new int[team.getPlayers().size()];

        for (int i = 0; i < team.getPlayers().size(); i++) {
            GameCharacter member = team.getPlayers().get(i);
            if (member instanceof Warrior) {
                oldStrength[i] = ((Warrior)member).getStrength();
            } else if (member instanceof Wizard) {
                oldIntelligence[i] = ((Wizard)member).getIntelligence();
            } else if (member instanceof Healer) {
                oldWisdom[i] = ((Healer)member).getWisdom();
            }
        }

        facade.buffTeam(teamName);
        executed = true;
    }

    @Override
    public void undo() {
        if (!executed) return;

        Team team = facade.getTeams().get(teamName);
        if (team == null) return;

        // On restaure les anciennes valeurs
        for (int i = 0; i < team.getPlayers().size(); i++) {
            GameCharacter member = team.getPlayers().get(i);
            if (member instanceof Warrior) {
                ((Warrior)member).setStrength(oldStrength[i]);
            } else if (member instanceof Wizard) {
                ((Wizard)member).setIntelligence(oldIntelligence[i]);
            } else if (member instanceof Healer) {
                ((Healer)member).setWisdom(oldWisdom[i]);
            }
        }
        System.out.println("Annulation du Buff : caractéristiques restaurées.");
        executed = false;
    }
}
