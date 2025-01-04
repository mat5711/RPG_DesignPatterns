package eu.telecomnancy.rpg;


public class AddTeamCommand implements Command {
    private Facade facade; 
    private Team team;
    private boolean executed;

    public AddTeamCommand(Facade facade, Team team) {
        this.facade = facade;
        this.team = team;
        this.executed = true;
    }

    @Override
    public void execute() {
        facade.addTeam(team);
        executed = true;
    }


    @Override
    public void undo() { // on annule notre action, c'est à dire on retire la team qu'on a ajouté
        if (executed) { // on vérifie que la team n'a pas déjà été supprimée
            facade.removeTeam(team.getName());
            executed = false;
        }
    }
}
